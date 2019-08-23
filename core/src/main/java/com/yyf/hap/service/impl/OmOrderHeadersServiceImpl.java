package com.yyf.hap.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yyf.hap.dto.OmOrderLines;
import com.yyf.hap.mapper.OmOrderHeadersMapper;
import com.yyf.hap.mapper.OmOrderLinesMapper;
import com.yyf.hap.utils.PDFReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyf.hap.dto.OmOrderHeaders;
import com.yyf.hap.service.IOmOrderHeadersService;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderHeadersServiceImpl extends BaseServiceImpl<OmOrderHeaders> implements IOmOrderHeadersService{

    private final Logger logger = LoggerFactory.getLogger(IOmOrderHeadersService.class);
    @Autowired
    OmOrderHeadersMapper orderHeadersMapper;

    @Autowired
    OmOrderLinesMapper orderLinesMapper;

    //头表的查询
    @Override
    public List<OmOrderHeaders> selectOrders(IRequest requestContext,
                                             OmOrderHeaders dto,
                                             int page,
                                             int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<OmOrderHeaders> orderHeadersList = orderHeadersMapper.selectOrders(dto);
        //遍历orderHeadersList中的headerId查询价格和数量,进行精度处理
        for(OmOrderHeaders item : orderHeadersList){
            BigDecimal count = new BigDecimal("0");
            List<OmOrderLines> orderLinesList = orderLinesMapper.selectPriceAndNum(item.getHeaderId());
                if(orderLinesList != null){
                    for(OmOrderLines orderLines : orderLinesList){
                        BigDecimal number = new BigDecimal(orderLines.getOrderdQuantity());
                        BigDecimal price = number.multiply(orderLines.getUnitSellingPrice());
                        count = count.add(price);
                    }
                }
            item.setAccount_money(count);
        }

        return orderHeadersList;
    }

    @Override
    public List<OmOrderHeaders> batchUpdate(IRequest requestContext,
                                            @StdWho List<OmOrderHeaders> dto){
        for(OmOrderHeaders item : dto){
            //如果headerId不等于空的情况
            if(item.getHeaderId()!= null){
                orderHeadersMapper.updateByPrimaryKey(item);
            }else{
                orderHeadersMapper.insertSelective(item);
            }
            //行数据不为空,遍历行数据
            if(item.getOmOrderLines() != null){
                for(OmOrderLines order : item.getOmOrderLines()){
                    if(order.getHeaderId() != null){
                        order.setOrderQuantityUom(order.getItemUom());
                        orderLinesMapper.updateByPrimaryKey(order);
                    } else{
                        order.setHeaderId(item.getHeaderId());
                        order.setCompanyId(item.getCustomerId());
                        order.setOrderQuantityUom(order.getItemUom());
                        orderLinesMapper.insertSelective(order);
                    }

                }

            }
        }

        //精度处理
        for(OmOrderHeaders item : dto) {
            BigDecimal count = new BigDecimal("0");
            if(item.getOmOrderLines()!= null) {
                for(OmOrderLines orderLines : item.getOmOrderLines()) {
                    BigDecimal number = new BigDecimal(orderLines.getOrderdQuantity());
                    BigDecimal price = number.multiply(orderLines.getUnitSellingPrice());
                    count = count.add(price);
                    orderLines.setAmount(count);
                }
            }
        }
        return dto;
    }

    @Override
    public List<OmOrderHeaders> batchUpdate_head(IRequest requestContext,
                                                 @StdWho List<OmOrderHeaders> dto) {
        for(OmOrderHeaders item : dto){
            if(item.getHeaderId() != null){
                orderHeadersMapper.updateByPrimaryKey(item);
            }else{
               orderHeadersMapper.insertSelective(item);
            }
        }
        return dto;
    }

    @Override
    public List<OmOrderHeaders> printPDF(IRequest requestContext, List<OmOrderHeaders> dto) {
        List<OmOrderLines> omOrderLinesList = orderLinesMapper.selectLine(dto.get(0).getHeaderId());

        File file = new File("E:\\hapDemo\\21884Test\\core\\src\\main\\webapp\\WEB-INF\\template\\print.pdf");
        PDFReport util=new PDFReport(file);
        Document document = new Document();
        document.setPageSize(PageSize.A4);// 设置页面大小
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable head1 = util.createTable(6);
            PdfPCell m = util.createCell("单据打印：",  PDFReport.headfont, Element.ALIGN_LEFT,6,false);
            head1.addCell(m);
            head1.addCell(util.createCell("订单编号：",  PDFReport.keyfont, Element.ALIGN_CENTER,0,false));
            head1.addCell(util.createCell(dto.get(0).getOrderNumber(), PDFReport.textfont, Element.ALIGN_CENTER));
            head1.addCell(util.createCell("公司名称：", PDFReport.keyfont, Element.ALIGN_CENTER,0,false));
            head1.addCell(util.createCell(dto.get(0).getCompanyName(), PDFReport.textfont, Element.ALIGN_CENTER));
            head1.addCell(util.createCell("客户名称", PDFReport.keyfont, Element.ALIGN_CENTER,0,false));
            head1.addCell(util.createCell(dto.get(0).getCustomerName(), PDFReport.textfont, Element.ALIGN_CENTER));
            head1.addCell(util.createCell("",  PDFReport.headfont, Element.ALIGN_LEFT,6,false));

            PdfPTable head2 = util.createTable(6);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            head2.addCell(util.createCell("订单日期：",  PDFReport.keyfont, Element.ALIGN_CENTER,0,false));
            head2.addCell(util.createCell(sdf.format(dto.get(0).getOrderDate()), PDFReport.textfont, Element.ALIGN_CENTER));
            head2.addCell(util.createCell("订单总金额：", PDFReport.keyfont, Element.ALIGN_CENTER,0,false));
            head2.addCell(util.createCell(dto.get(0).getAccount_money().toString(), PDFReport.textfont, Element.ALIGN_CENTER));
            head2.addCell(util.createCell("订单状态", PDFReport.keyfont, Element.ALIGN_CENTER,0,false));
           if(dto.get(0).getOrderStatus().equals("NEW")){
               head2.addCell(util.createCell("新建", PDFReport.textfont, Element.ALIGN_CENTER));
           }if(dto.get(0).getOrderStatus().equals("SUBMITED")){
               head2.addCell(util.createCell("已提交", PDFReport.textfont, Element.ALIGN_CENTER));
           }if(dto.get(0).getOrderStatus().equals("APPROVED")){
               head2.addCell(util.createCell("已审批", PDFReport.textfont, Element.ALIGN_CENTER));
           }if(dto.get(0).getOrderStatus().equals("REJECTED")){
               head2.addCell(util.createCell("已拒绝", PDFReport.textfont, Element.ALIGN_CENTER));
           }

            PdfPTable table = util.createTable(7);
            table.addCell(util.createCell("主要内容：",  PDFReport.headfont, Element.ALIGN_LEFT,7,false));
            table.addCell(util.createCell("物料编码", PDFReport.keyfont, Element.ALIGN_CENTER));
            table.addCell(util.createCell("物料描述", PDFReport.keyfont, Element.ALIGN_CENTER));
            table.addCell(util.createCell("物料单位", PDFReport.keyfont, Element.ALIGN_CENTER));
            table.addCell(util.createCell("数量", PDFReport.keyfont, Element.ALIGN_CENTER));
            table.addCell(util.createCell("销售单价", PDFReport.keyfont, Element.ALIGN_CENTER));
            table.addCell(util.createCell("金额", PDFReport.keyfont, Element.ALIGN_CENTER));
            table.addCell(util.createCell("备注", PDFReport.keyfont, Element.ALIGN_CENTER));

            for(OmOrderLines omOrderLines : omOrderLinesList) {
                table.addCell(util.createCell(omOrderLines.getItemCode(), PDFReport.textfont));
                table.addCell(util.createCell(omOrderLines.getItemDescription(), PDFReport.textfont));
                table.addCell(util.createCell(omOrderLines.getOrderQuantityUom(), PDFReport.textfont));
                table.addCell(util.createCell(omOrderLines.getOrderdQuantity().toString(), PDFReport.textfont));
                table.addCell(util.createCell(omOrderLines.getUnitSellingPrice().toString(), PDFReport.textfont));
                Long a = omOrderLines.getOrderdQuantity();
                BigDecimal b = omOrderLines.getUnitSellingPrice();
                BigDecimal c = new BigDecimal(a);
                c = c.multiply(b);
                table.addCell(util.createCell(c.toString(), PDFReport.textfont));
                table.addCell(util.createCell(omOrderLines.getDescription(), PDFReport.textfont));
            }
            document.add(head1);
            document.add(head2);
            document.add(table);
            document.close();
        } catch (Exception e) {
            logger.error("printPDF Error:"+e.getMessage());
            e.printStackTrace();
        }
        return dto;
    }

    @Override
    public  void batchDelete(IRequest requestContext,@StdWho List<OmOrderHeaders> dto){
        for(OmOrderHeaders item : dto){
            if(item.getHeaderId()!= null){
                //金额为0
                if(item.getAccount_money().compareTo(BigDecimal.ZERO)==0){
                    orderHeadersMapper.deleteByHeaderId(item.getHeaderId());
                }else{
                    orderLinesMapper.deleteByHeaderId(item.getHeaderId());
                    orderHeadersMapper.delete(item);
                }
            }

            }
        }
    }

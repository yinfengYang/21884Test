package com.yyf.hap.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.yyf.hap.dto.OmOrderHeaders;
import com.yyf.hap.mapper.OmOrderHeadersMapper;
import com.yyf.hap.mapper.OmOrderLinesMapper;
import com.yyf.hap.utils.SquenceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyf.hap.dto.OmOrderLines;
import com.yyf.hap.service.IOmOrderLinesService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderLinesServiceImpl extends BaseServiceImpl<OmOrderLines> implements IOmOrderLinesService{

    @Autowired
    private OmOrderLinesMapper orderLinesMapper;

    @Autowired
    private OmOrderHeadersMapper orderHeadersMapper;

    @Override
    public List<Long> getRownNmber(IRequest requestContext,
                                   OmOrderLines dto,
                                   int page,
                                   int pageSize) {

        return orderLinesMapper.getLinNum();
    }

    /*新建的状态，headerId == 0 ,需要自动生成订单号，日期订单状态为NEW*/
    @Override
    public List<OmOrderHeaders> setOrderInfo(IRequest requestContext, OmOrderHeaders dto, int page, int pageSize) {
        SquenceFactory squenceFactory = new SquenceFactory();
        List<OmOrderHeaders> orderHeadersList = null;
        OmOrderHeaders orderHeaders = new OmOrderHeaders();
        orderHeadersList = new ArrayList<OmOrderHeaders>();
        orderHeaders.setOrderDate(new Date());
        try{
            //获得FactoryBean产生的当前日期+000000的字符串,
            String orderNumber = squenceFactory.getObject();
            List<OmOrderHeaders> resulstList = orderHeadersMapper.selectOrderNumber(orderNumber);
            while(resulstList.size()>0){
                orderNumber = squenceFactory.getObject();
                resulstList = orderHeadersMapper.selectOrderNumber(orderNumber);
            }
            orderHeaders.setOrderNumber(orderNumber);
            orderHeaders.setOrderStatus("NEW");
            orderHeadersList.add(orderHeaders);

            /*问题？？？*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderHeadersList;
    }

    @Override
    public List<OmOrderLines> selectOrderLines(IRequest requestContext,
                                               OmOrderLines dto,
                                               int page,
                                               int pageSize) {
        PageHelper.startPage(page,pageSize);
        //先更新行号再进行查询
        orderLinesMapper.updateLinesNum(dto.getHeaderId());
        List<OmOrderLines> orderLinesList = orderLinesMapper.selectOrderLines(dto);
        //为查询的数据添加总金额
        for(OmOrderLines item : orderLinesList){
            BigDecimal number = new BigDecimal(item.getOrderdQuantity());
            item.setAmount(item.getUnitSellingPrice().multiply(number));
        }
        return orderLinesList;
    }

   /* public List<OmOrderLines> batchUpdate(IRequest iRequest,
                                          @StdWho List<OmOrderLines> dto){

        for(OmOrderLines item : dto){
            if(item.getHeaderId() != null){
                item.setOrderQuantityUom(item.getItemUom());
                orderLinesMapper.updateByPrimaryKey(item);
            }
        }

        return null;
    }*/
}
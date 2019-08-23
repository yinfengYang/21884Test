package com.yyf.hap.controllers;

import com.yyf.hap.utils.Constent;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.yyf.hap.dto.OmOrderHeaders;
import com.yyf.hap.service.IOmOrderHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

    @Controller
    public class OmOrderHeadersController extends BaseController{

    @Autowired
    private IOmOrderHeadersService service;


    @RequestMapping(value = "/hap/om/order/headers/query")
    @ResponseBody
    public ResponseData query(OmOrderHeaders dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectOrders(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/hap/om/order/headers/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<OmOrderHeaders> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
                ResponseData responseData = new ResponseData(false);
                responseData.setMessage(getErrorMessage(result, request));
                return responseData;
         }

        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hap/om/order/headers/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<OmOrderHeaders> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    @RequestMapping(value = "/hap/om/order/headers/submit_head")
    @ResponseBody
    public ResponseData save_head(@RequestBody List<OmOrderHeaders> dto,
                                  BindingResult result,
                                  HttpServletRequest request
                                  ){
        getValidator().validate(dto, result);
        if (result.hasErrors()){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(service.batchUpdate_head(iRequest,dto));
    }
    @RequestMapping(value = "hap/om/order/headers/deleteAll")
    @ResponseBody
    public ResponseData delete_all(@RequestBody List<OmOrderHeaders> dto,
                                  BindingResult result,
                                  HttpServletRequest request
                                  ){
        getValidator().validate(dto,result);
        if (result.hasErrors()){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest iRequest = createRequestContext(request);
        service.batchDelete(iRequest,dto);
        return new ResponseData(dto);
    }

        @RequestMapping(value = "/hap/om/order/headers/printPDF")
        public ResponseData printPDF(@RequestBody List<OmOrderHeaders> dto,
                                     BindingResult result,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
            getValidator().validate(dto, result);
            if (result.hasErrors()) {
                ResponseData responseData = new ResponseData(false);
                responseData.setMessage(getErrorMessage(result, request));
                return responseData;
            }

            IRequest requestCtx = createRequestContext(request);
            ResponseData responseData = new ResponseData(service.printPDF(requestCtx, dto));
            responseData.setSuccess(true);
            return responseData;
        }

        @RequestMapping(value = "/hap/om/order/headers/outputPDF",method = RequestMethod.GET)
        public ResponseEntity<byte[]> printPDF(HttpServletRequest request,
                                               HttpServletResponse response)throws IOException {

            request.setCharacterEncoding("UTF-8");
            String path = Constent.filePath;
            String fileName = Constent.fileName;
            File file = null;
            HttpHeaders headers = null;
            try {
                file = new File(path);
                headers = new HttpHeaders();
                String fileName1 = new String(fileName.getBytes("UTF-8"), "iso-8859-1");//解决文件名乱码
                headers.setContentDispositionFormData("attachment", fileName1);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }catch (Exception e){
                throw  new RuntimeException(e);
            }
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        }

    }
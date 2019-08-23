package com.yyf.hap.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.yyf.hap.dto.OmOrderHeaders;
import com.yyf.hap.dto.OmOrderLines;

import java.util.List;

public interface IOmOrderLinesService extends IBaseService<OmOrderLines>, ProxySelf<IOmOrderLinesService>{


    // List<OmOrderLines> batchUpdate(IRequest iRequest, @StdWho List<OmOrderLines> dto);
     List<Long> getRownNmber(IRequest requestContext, OmOrderLines dto, int page, int pageSize);

     List<OmOrderHeaders> setOrderInfo(IRequest requestContext, OmOrderHeaders dto, int page, int pageSize);

    List<OmOrderLines> selectOrderLines(IRequest requestContext, OmOrderLines dto, int page, int pageSize);
}
package com.yyf.hap.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.yyf.hap.dto.OmOrderHeaders;

import java.util.List;

/**
 * @author yang
 */
public interface IOmOrderHeadersService extends IBaseService<OmOrderHeaders>, ProxySelf<IOmOrderHeadersService>{

     List<OmOrderHeaders> selectOrders(IRequest requestContext, OmOrderHeaders dto, int page, int pageSize);
     @Override
     List<OmOrderHeaders> batchUpdate(IRequest requestContext,@StdWho List<OmOrderHeaders> dto);
     void batchDelete(IRequest requestContext,@StdWho List<OmOrderHeaders> dto);
     List<OmOrderHeaders> batchUpdate_head(IRequest requestContext,@StdWho List<OmOrderHeaders> dto);
     List<OmOrderHeaders> printPDF(IRequest requestContext,@StdWho List<OmOrderHeaders> dto);
}
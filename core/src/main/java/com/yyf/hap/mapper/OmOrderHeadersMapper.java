package com.yyf.hap.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.yyf.hap.dto.OmOrderHeaders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmOrderHeadersMapper extends Mapper<OmOrderHeaders>{

    List<OmOrderHeaders> selectOrders(OmOrderHeaders dto);

    //根据订单号查
    List<OmOrderHeaders> selectOrderNumber(String orderNumber);

    List<OmOrderHeaders> selectByheaderId(OmOrderHeaders dto);

    void deleteByHeaderId(@Param("headerId")Long headerId);

}
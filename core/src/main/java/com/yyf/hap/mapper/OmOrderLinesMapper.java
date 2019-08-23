package com.yyf.hap.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.yyf.hap.dto.OmOrderLines;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmOrderLinesMapper extends Mapper<OmOrderLines>{

    List<OmOrderLines> selectPriceAndNum(long headerId);
    //获取所有行号
    List<Long> getLinNum();

    //根据行数据的条数来设置数据的行号
    void updateLinesNum(@Param("headerId")Long headerId);

    List<OmOrderLines> selectOrderLines(OmOrderLines dto);

    void deleteByHeaderId(@Param("headerId") long headerId);

    List<OmOrderLines> selectLine(long headerId);


}
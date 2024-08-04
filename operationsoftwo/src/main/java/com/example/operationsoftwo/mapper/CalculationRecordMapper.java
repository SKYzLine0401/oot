package com.example.operationsoftwo.mapper;

import com.example.operationsoftwo.entity.CalculationRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CalculationRecordMapper {

    void insert(CalculationRecord record);

    List<CalculationRecord> selectByCriteria(
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("operator") String operator,
            @Param("limit") int limit,
            @Param("offset") int offset);
}

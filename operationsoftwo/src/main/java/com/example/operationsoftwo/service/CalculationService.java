package com.example.operationsoftwo.service;

import com.example.operationsoftwo.entity.CalculationRecord;
import com.example.operationsoftwo.entity.CalculationRequest;
import com.example.operationsoftwo.mapper.CalculationRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalculationService {
    @Autowired
    private CalculationRecordMapper recordMapper;

    public BigDecimal calculate(CalculationRequest request) {
        BigDecimal result = null;
        BigDecimal num1 = request.getNum1();
        BigDecimal num2 = request.getNum2();
        if (num1.compareTo(new BigDecimal("99999")) > 0 || num1.compareTo(new BigDecimal("-99999")) < 0 ||
                num2.compareTo(new BigDecimal("99999")) > 0 || num2.compareTo(new BigDecimal("-99999")) < 0) {
            throw new IllegalArgumentException("Number out of range");
        }
        switch (request.getOperator()) {
            case "+":
                result = num1.add(num2);
                break;
            case "-":
                result = num1.subtract(num2);
                break;
            case "*":
                result =  num1.multiply(num2);
                break;
            case "/":
                if (num2.compareTo(BigDecimal.ZERO) == 0) {
                    throw new IllegalArgumentException("Divider cannot be zero");
                }
                result = num1.divide(num2);
                break;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
        return result;
    }

    public void saveRecord(CalculationRequest request, boolean success, BigDecimal result) {
        CalculationRecord record = new CalculationRecord();

        record.setRequestTime(LocalDateTime.now());
        record.setNum1(request.getNum1());
        record.setOperator(request.getOperator());
        record.setNum2(request.getNum2());
        record.setSuccess(success);
        record.setResult(result);

        recordMapper.insert(record);
    }

    public List<CalculationRecord> getRecords(String startTime, String endTime, String operator, int page, int size) {
        int offset = (page - 1) * size;
        return recordMapper.selectByCriteria(startTime, endTime, operator, size, offset);
    }
}

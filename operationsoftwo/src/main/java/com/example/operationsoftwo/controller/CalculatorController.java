package com.example.operationsoftwo.controller;

import com.example.operationsoftwo.entity.CalculationRecord;
import com.example.operationsoftwo.entity.CalculationRequest;
import com.example.operationsoftwo.service.CalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calculator")
@Tag(name = "Calculator API")
public class CalculatorController {

    @Autowired
    private CalculationService calculationService;

    @PostMapping("/calculate")
    @Operation(summary = "Perform calculation based on the operator")
    public Map<String, Object> calculate(@RequestBody CalculationRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            BigDecimal result = calculationService.calculate(request);
            calculationService.saveRecord(request, true, result);
            response.put("code", 0);
            response.put("data", result.toString());
        } catch (Exception e) {
            response.put("code", 1);
            response.put("errorMsg", e.getMessage());
            if(!e.getMessage().equals("Number out of range")) {
                calculationService.saveRecord(request, false, null);
            }
        }
        return response;
    }

    @GetMapping("/records")
    @Operation(summary = "Get calculation records")
    public List<CalculationRecord> getRecords(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String operator,
            @RequestParam int page,
            @RequestParam int size) {
        return calculationService.getRecords(startTime, endTime, operator, page, size);
    }
}
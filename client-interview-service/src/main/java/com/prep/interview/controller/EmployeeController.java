package com.prep.interview.controller;

import com.prep.interview.model.EmployeeConfig;
import com.prep.interview.service.EmployeeODCLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeODCLoaderService employeeODCLoaderService;

    @GetMapping("data")
    public List<EmployeeConfig> loadConfigs() throws InterruptedException {
        List<EmployeeConfig> employeeConfigs = employeeODCLoaderService.loadOdcConfigs();
        employeeConfigs.forEach(System.out::println);
        return employeeConfigs;
    }

    @GetMapping("example")
    public String getSecondCache() {
        return employeeODCLoaderService.getName();
    }

}

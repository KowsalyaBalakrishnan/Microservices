package com.prep.interview.service;

import com.prep.interview.model.EmployeeConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeODCLoaderService {

    @Cacheable(value = "odcCacheData")
    public List<EmployeeConfig> loadOdcConfigs() throws InterruptedException {
        System.out.println("Getting Data from Static Block");
        Thread.sleep(5000);
        return DataLoader.getDbEmployeeConfigs();
    }

    @Cacheable(value = "example")
    public String getName() {
        System.out.println("Getting second cache");
        return "Hey";
    }
}

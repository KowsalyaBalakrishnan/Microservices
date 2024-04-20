package com.prep.interview.service;

import com.prep.interview.model.EmployeeConfig;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader {

    @Getter
    static List<EmployeeConfig> dbEmployeeConfigs;

    static {
        System.out.println("Mock Data is ready from Static Block");
        dbEmployeeConfigs = new ArrayList<>();

        EmployeeConfig config1 = new EmployeeConfig("Chennai", "DLF", "BLock-9B", Arrays.asList(1, 2, 3));
        EmployeeConfig config2 = new EmployeeConfig("Chennai", "MEPZ", "BLock-A,B,C", Arrays.asList(2, 3, 5, 6));
        EmployeeConfig config3 = new EmployeeConfig("Bangalore", "Manyata", "BLock-9E,6F,4H,2B", Arrays.asList(8, 9, 11));

        dbEmployeeConfigs.add(config1);
        dbEmployeeConfigs.add(config2);
        dbEmployeeConfigs.add(config3);
    }

}

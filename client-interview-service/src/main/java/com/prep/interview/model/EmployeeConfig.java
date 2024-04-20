package com.prep.interview.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeConfig {

    private String location;
    private String itPark;
    private String block;
    private List<Integer> floors;
}

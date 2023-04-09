package com.base.service.controller;

import com.base.api.RandomEmployeeApi;
import com.base.models.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;
@RestController
@RequestMapping("/api/v3")
public class OpenApiController implements RandomEmployeeApi {

    /**
     * @param departmentId
     * @param authorization
     * @return
     */
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees(String departmentId, String authorization) {
        var employee1 = new Employee();
        employee1.setId(1);
        employee1.setDepartmentId(departmentId);
        employee1.setName("EmployeeOne");
        var employee2 = new Employee();
        employee2.setId(2);
        employee2.setDepartmentId(departmentId);
        employee2.setName("EmployeeTwo");
        var employee3 = new Employee();
        employee3.setId(3);
        employee3.setDepartmentId(departmentId);
        employee3.setName("EmployeeThree");

        return departmentId.equals(EMPTY) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(List.of(employee1, employee2, employee3));
    }


}

package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.repositories.accountmanage.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    public Employee getEmployeeByPhone(String phone){
        return employeeRepository.findEmployeeByPhone(phone);
    }
    public Organization getOrganizationByEmployeeId(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(NullPointerException::new);
        return employee.getOrganization();
    }
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }
}

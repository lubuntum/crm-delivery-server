package com.delivery.mydelivery.database.repositories;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

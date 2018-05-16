package com.almundo.backendtest.factory;

import com.almundo.backendtest.domain.Employee;
import com.almundo.backendtest.domain.EmployeeType;
import org.springframework.stereotype.Component;

/**
 * @author hollman_castro
 */
@Component
public class EmployeeFactory {
    public Employee createEmployee(int id, EmployeeType type) {
        return new Employee(id, type);
    }
}

package com.almundo.backendtest.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hollman_castro
 */
public enum EmployeeType {
    OPERADOR(0), SUPERVISOR(1), DIRECTOR(2);

    private int priority;

    private static final Map<Integer, EmployeeType> map;

    static {
        map = new HashMap<>();
        for (EmployeeType employeeType : EmployeeType.values()) {
            map.put(employeeType.priority, employeeType);
        }
    }

    EmployeeType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static Map<Integer, EmployeeType> getMap() {
        return map;
    }
}

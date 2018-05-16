package com.almundo.backendtest.service;

import com.almundo.backendtest.domain.Call;
import com.almundo.backendtest.domain.Employee;
import com.almundo.backendtest.domain.EmployeeType;
import com.almundo.backendtest.factory.EmployeeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author hollman_castro
 */
@Service
public class Dispatcher {
    private final Map<EmployeeType, List<Employee>> employeeMap = new Hashtable<>();
    @Value("${application.number-of-operadores}")
    private int operadores;
    @Value("${application.number-of-directores}")
    private int directores;
    @Value("${application.number-of-supervisores}")
    private int supervisores;
    private final EmployeeFactory employeeFactory;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public Dispatcher(EmployeeFactory employeeFactory,
                      ThreadPoolTaskScheduler threadPoolTaskScheduler,
                      ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.employeeFactory = employeeFactory;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @PostConstruct
    public void init() {
        logger.debug("Initializing employeeMap");
        int employeeId = 0;
        for (EmployeeType type : EmployeeType.values()) {
            employeeMap.put(type, new ArrayList<>());
            int number = type.equals(EmployeeType.OPERADOR) ? operadores :
                    type.equals(EmployeeType.SUPERVISOR) ?
                            supervisores : directores;

            for (int i = 0; i < number; i++) {
                employeeMap.get(type).add(employeeFactory.createEmployee(employeeId, type));
                employeeId++;
            }
        }
    }

    public void processCall(Call call){
        threadPoolTaskExecutor.execute(() -> process(call));
    }

    private void process(Call call) {
        boolean assigned = false;
        int enumSize = EmployeeType.values().length;
        int enumIndex = 0;

        logger.debug("Searching employee to attend call");
        while (!assigned && enumIndex < enumSize) {
            EmployeeType type = EmployeeType.getMap().get(enumIndex);
            Iterator<Employee> employeeIterator = employeeMap.get(type).iterator();
            while (!assigned && employeeIterator.hasNext()) {
                Employee employee = employeeIterator.next();
                if (employee.isAvailable()) {
                    employee.attendCall(call);
                    assigned = true;
                    logger.debug("The employee {} will attend the call", employee);
                    threadPoolTaskScheduler.schedule(() -> endCall(employee),
                            Date.from(LocalDateTime.now().plusSeconds(call.getDuration())
                            .atZone(ZoneId.systemDefault()).toInstant()));
                }
            }
            enumIndex++;
        }

        if (!assigned) {
            logger.error("The call cant be assigned");
        }
    }

    public void endCall(Employee employee) {
        employee.endCall();
        logger.debug("Employee {} has ended the call", employee);
    }
}
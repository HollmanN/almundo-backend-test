package com.almundo.backendtest.domain;

/**
 * @author hollman_castro
 */
public class Employee {
    private int id;
    private EmployeeType type;
    private boolean available;

    public Employee(int id, EmployeeType type) {
        this.id = id;
        this.type = type;
        this.available= true;
    }

    public void attendCall(Call call){
        setAvailable(false);
    }

    public void endCall(){
        setAvailable(true);
    }

    private synchronized void setAvailable(boolean available){
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public EmployeeType getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return id + " - " + type;
    }
}

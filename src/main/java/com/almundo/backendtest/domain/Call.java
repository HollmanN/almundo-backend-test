package com.almundo.backendtest.domain;

/**
 * @author hollman_castro
 */
public class Call {
    private String from;
    private int duration;

    public Call() {
    }

    public Call(String from, int duration) {
        this.from = from;
        this.duration = duration;
    }

    public Call(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

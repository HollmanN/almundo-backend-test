package com.almundo.backendtest.controller;

import com.almundo.backendtest.domain.Call;
import com.almundo.backendtest.service.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author hollman_castro
 */
@RestController
@RequestMapping("/dispatcher")
public class DispatcherController {
    @Autowired
    private Dispatcher dispatcher;

    @PostMapping
    public void processCall(@RequestBody final Call call) {
        Objects.requireNonNull(call, "The call cant be null");
        dispatcher.processCall(call);
    }
}

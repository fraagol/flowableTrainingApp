package org.flowable.training.flowableTrainingApp;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.Execution;

public class MyCustomService {

    private final RuntimeService runtimeService;

    public MyCustomService(RuntimeService runtimeService){
        this.runtimeService = runtimeService;
    }

    public void myMethod(Execution execution){
        this.runtimeService.setVariable(execution.getId(), "myTaskName","My new task name");
    }
}

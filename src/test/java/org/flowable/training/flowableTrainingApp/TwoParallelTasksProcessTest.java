package org.flowable.training.flowableTrainingApp;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.Deployment;
import org.flowable.engine.test.FlowableTest;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FlowableTest
public class TwoParallelTasksProcessTest {

    private ProcessEngine processEngine;
    private RuntimeService runtimeService;
    private TaskService taskService;

    @BeforeEach
    void setUp(ProcessEngine processEngine) {
        this.processEngine = processEngine;
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();
    }

    @Test
    @Deployment(resources = "processes/Two_Parallel_Tasks_Process.bpmn20.xml")
    public void normalExecution() {
        ProcessInstance process = this.runtimeService.startProcessInstanceByKey("twoParallelTasksProcess");

        List<Task> taskList = this.taskService.createTaskQuery().processInstanceId(process.getId()).list();
        assertEquals(taskList.size(), 2);

        taskList.forEach((task -> taskService.complete(task.getId())));

        ProcessInstance runtimeProcess = this.runtimeService.createProcessInstanceQuery().processInstanceId(process.getId()).singleResult();
        assertNull(runtimeProcess);

        HistoricProcessInstance historicProcess = this.processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(process.getId()).singleResult();
        assertNotNull(historicProcess);
        assertNotNull(historicProcess.getEndTime());

    }
}

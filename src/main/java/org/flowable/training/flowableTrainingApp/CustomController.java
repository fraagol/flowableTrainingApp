package org.flowable.training.flowableTrainingApp;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.flowable.training.flowableTrainingApp.dto.ProcessDefinitionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CustomController {

    protected final RepositoryService repositoryService;

    protected final ProcessEngine processEngine;
    private final RuntimeService runtimeService;

    public CustomController(RepositoryService repositoryService, ProcessEngine processEngine) {
        this.repositoryService = repositoryService;
        this.processEngine = processEngine;
        this.runtimeService = processEngine.getRuntimeService();
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "Participant") String name) {
        return String.format("Hi %s, welcome to the Flowable training", name);
    }

    @GetMapping("/principal")
    public String principal(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/definitions")
    public List latestDefinitions() {
        return repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .orderByProcessDefinitionName()
                .asc()
                .list()
                .stream()
                .map(processDefinition -> new ProcessDefinitionDTO(processDefinition.getKey(), processDefinition.getName()))
                .collect(Collectors.toList());

    }

    @GetMapping("/startProcess")
    public String startProcess(@RequestParam String definitionId) {
        return this.processEngine.getRuntimeService().startProcessInstanceByKey(definitionId).getId();
    }

    @GetMapping("/tasks")
    public List<Map<String, Object>> tasks() {
        List<Task> taskList = processEngine.getTaskService().createTaskQuery().active().list();

        List<Map<String, Object>> customTaskList = new ArrayList<>();

        for (Task task : taskList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", task.getId());
            map.put("processId", task.getProcessInstanceId());
            map.put("processModel", task.getProcessDefinitionId());
            map.put("name", task.getName());

            customTaskList.add(map);
        }
        return customTaskList;
    }

    @GetMapping("/completeTask")
    public void completeTask(@RequestParam String id) {
        this.processEngine.getTaskService().complete(id);
    }
}

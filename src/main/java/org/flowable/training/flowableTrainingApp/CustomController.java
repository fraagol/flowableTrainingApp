package org.flowable.training.flowableTrainingApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {

 @GetMapping("/hello")
     public String hello(@RequestParam(defaultValue = "Participant") String name) {
      return String.format("Hi %s, welcome to the Flowable training", name);
     }
}

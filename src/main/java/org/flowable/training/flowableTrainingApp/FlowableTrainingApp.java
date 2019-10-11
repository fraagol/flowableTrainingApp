package org.flowable.training.flowableTrainingApp;

import org.flowable.engine.RuntimeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowableTrainingApp {

	public static void main(String[] args) {
		SpringApplication.run(FlowableTrainingApp.class, args);
	}

	@Bean
	public MyCustomService myBean(RuntimeService runtimeService){
		return new MyCustomService(runtimeService);
	}
}

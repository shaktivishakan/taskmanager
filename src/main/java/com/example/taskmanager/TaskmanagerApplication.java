package com.example.taskmanager;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);

	}
//	@Bean
//    CommandLineRunner test(TaskRepository repo) {
//		return args -> {
//			Task t = new Task();
//			t.setTitle("Test task");
//			repo.save(t);
//
//			System.out.println("All: " + repo.findAll().size());
//			System.out.println("TODO: " + repo.findByStatus(TaskStatus.TODO).size());
//		};
//	}
}

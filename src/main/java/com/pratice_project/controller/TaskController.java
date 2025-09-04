package com.pratice_project.controller;


import com.pratice_project.ApiResponse.ApiResponse;
import com.pratice_project.Dto.TaskDto;
import com.pratice_project.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create Task
    @PostMapping("/createTask")
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@RequestBody TaskDto dto) {
        TaskDto createdTask = taskService.createTask(dto);
        ApiResponse<TaskDto> response = new ApiResponse<>(
                "Task created successfully",
                HttpStatus.CREATED.value(),
                createdTask
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all Tasks
    @GetMapping("/getallTask")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        ApiResponse<List<TaskDto>> response = new ApiResponse<>(
                "Tasks retrieved successfully",
                HttpStatus.OK.value(),
                tasks
        );
        return ResponseEntity.ok(response);
    }

    // Get Task by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> getTask(@PathVariable Long id) {
        TaskDto task = taskService.getTaskById(id);
        ApiResponse<TaskDto> response = new ApiResponse<>(
                "Task retrieved successfully",
                HttpStatus.OK.value(),
                task
        );
        return ResponseEntity.ok(response);
    }

    // Update Task
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(@PathVariable Long id, @RequestBody TaskDto dto) {
        TaskDto updatedTask = taskService.updateTask(id, dto);
        ApiResponse<TaskDto> response = new ApiResponse<>(
                "Task updated successfully",
                HttpStatus.OK.value(),
                updatedTask
        );
        return ResponseEntity.ok(response);
    }

    // Delete Task
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        ApiResponse<String> response = new ApiResponse<>(
                "Task deleted successfully",
                HttpStatus.OK.value(),
                null
        );
        return ResponseEntity.ok(response);
    }
}

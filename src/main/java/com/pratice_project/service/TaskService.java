package com.pratice_project.service;

import com.pratice_project.Dto.TaskDto;
import com.pratice_project.entity.TaskEntity;
import com.pratice_project.Repository.TaskRepository;

import com.pratice_project.handlingException.ResouceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // ✅ Create a new Task
    public TaskDto createTask(TaskDto dto) {
        TaskEntity task = new TaskEntity();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());

        TaskEntity savedTask = taskRepository.save(task);
        return mapToDTO(savedTask);
    }

    // ✅ Get all tasks
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get task by ID
    public TaskDto getTaskById(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new ResouceNotFoundException("Task not found with id: " + id));
        return mapToDTO(task);
    }

    // ✅ Update task
    public TaskDto updateTask(Long id, TaskDto dto) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new ResouceNotFoundException("Task not found with id: " + id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());

        TaskEntity updatedTask = taskRepository.save(task);
        return mapToDTO(updatedTask);
    }

    // ✅ Delete task
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResouceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    // ✅ Helper method: Convert Entity -> DTO
    private TaskDto mapToDTO(TaskEntity task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        return dto;
    }
}

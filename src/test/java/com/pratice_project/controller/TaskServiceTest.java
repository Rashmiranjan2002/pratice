package com.pratice_project.controller;

import com.pratice_project.Dto.TaskDto;
import com.pratice_project.Repository.TaskRepository;
import com.pratice_project.entity.TaskEntity;
import com.pratice_project.handlingException.ResouceNotFoundException;
import com.pratice_project.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest{

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private TaskEntity taskEntity;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        taskEntity.setTitle("Test Task");
        taskEntity.setDescription("Task description");
        taskEntity.setCompleted(false);

        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("Test Task");
        taskDto.setDescription("Task description");
        taskDto.setCompleted(false);
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        TaskDto createdTask = taskService.createTask(taskDto);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(taskEntity));

        List<TaskDto> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById_Found() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

        TaskDto result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResouceNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void testUpdateTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        TaskDto updated = new TaskDto();
        updated.setTitle("Updated Task");
        updated.setDescription("Updated Desc");
        updated.setCompleted(true);

        TaskDto result = taskService.updateTask(1L, updated);

        assertEquals("Updated Task", result.getTitle());
        assertTrue(result.isCompleted());
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResouceNotFoundException.class, () -> taskService.deleteTask(1L));
    }
}

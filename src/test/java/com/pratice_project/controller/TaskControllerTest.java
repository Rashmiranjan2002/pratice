package com.pratice_project.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratice_project.ApiResponse.ApiResponse;
import com.pratice_project.Dto.TaskDto;
import com.pratice_project.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTask() throws Exception {
        TaskDto dto = new TaskDto();
        dto.setId(1L);
        dto.setTitle("Task 1");
        dto.setDescription("Description");
        dto.setCompleted(false);

        Mockito.when(taskService.createTask(any(TaskDto.class))).thenReturn(dto);

        mockMvc.perform(post("/api/tasks/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value("Task 1"));
    }

    @Test
    void testGetAllTasks() throws Exception {
        TaskDto dto = new TaskDto();
        dto.setId(1L);
        dto.setTitle("Task 1");
        dto.setDescription("Description");

        Mockito.when(taskService.getAllTasks()).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/tasks/getallTask"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Task 1"));
    }

    @Test
    void testGetTaskById() throws Exception {
        TaskDto dto = new TaskDto();
        dto.setId(1L);
        dto.setTitle("Task 1");

        Mockito.when(taskService.getTaskById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/tasks/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Task 1"));
    }

    @Test
    void testUpdateTask() throws Exception {
        TaskDto dto = new TaskDto();
        dto.setId(1L);
        dto.setTitle("Updated Task");

        Mockito.when(taskService.updateTask(eq(1L), any(TaskDto.class))).thenReturn(dto);

        mockMvc.perform(put("/api/tasks/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Updated Task"));
    }

    @Test
    void testDeleteTask() throws Exception {
        Mockito.doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task deleted successfully"));
    }
}

package com.kanban;

import com.kanban.domain.Task;
import com.kanban.domain.TaskStatus;
import com.kanban.repository.TaskRepository;
import com.kanban.services.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UnitTests {

    @Mock
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;

    @Test //teste de agrupamento por status
    void testGetAllGroupedByStatus(){
        List<Task> tasks = Arrays.asList(
                new Task(1L, "Task 1", TaskStatus.PENDING),
                new Task (2l, "Task 2", TaskStatus.IN_PROGRESS),
                new Task(3l, "Task 3", TaskStatus.COMPLETED)
        );

        when(taskRepository.findAll()).thenReturn(tasks);

        List<List<Task>> grouped = taskService.getAllGrouped();

        assertEquals(3, grouped.size(), "Deve haver 3 grupos de status");
        assertEquals(1, grouped.get(0).size(), "Deve haver 1 tarefa no grupo PENDING");
        assertEquals(1, grouped.get(1).size(), "Deve haver 1 tarefa no grupo IN_PROGRESS");
        assertEquals(1, grouped.get(2).size(), "Deve haver 1 tarefa no grupo COMPLETED");
    }
}

package com.kanban.services;

import com.kanban.domain.Priority;
import com.kanban.domain.Task;
import com.kanban.domain.TaskStatus;
import com.kanban.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    public Task save(Task task){
        return repository.save(task);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public List<Task> findAll(){
        return repository.findAll();
    }

    public Task findById(Long id){
        return repository.findById(id).get();
    }

    public TaskStatus nextStatus(TaskStatus currentStatus){
        TaskStatus[] statuses = TaskStatus.values();
        int current = currentStatus.ordinal();

        return statuses[(current + 1) % statuses.length]; //loop para se passar do tamanho do array
    }

    public List<List<Task>> getAllGrouped(){
        List<Task> tasks = findAll();

        Map<TaskStatus, List<Task>> groupedByStatus = tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus));

        return new ArrayList<>(groupedByStatus.values());
    }
}

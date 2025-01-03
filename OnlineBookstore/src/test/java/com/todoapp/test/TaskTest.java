package com.todoapp.test;

import com.todoapp.model.Task;

public class TaskTest {
    public static void main(String[] args) {
        Task task = new Task();
        task.setId(1);
        task.setDescription("Learn Lombok");
        task.setStatus("Pending");
        task.setDueDate("2025-01-10");

        System.out.println("Task ID: " + task.getId());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Due Date: " + task.getDueDate());
    }
}

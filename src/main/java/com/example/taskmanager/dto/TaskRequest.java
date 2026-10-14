package com.example.taskmanager.dto;

import com.example.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// Data the client sends when creating or updating a task.
// Validation rules here are checked by @Valid in the controller,
// before any service code runs.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    // @NotBlank: rejects null, "" and "   " (spaces only).
    // @Size: caps length so nobody can send a huge title.
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    // Optional field (no @NotBlank), but limited in length if sent.
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    // @NotNull (not @NotBlank) because this is an enum, not a String.
    // Required so an update can't accidentally wipe the status to null.
    @NotNull(message = "Status is required")
    private TaskStatus status;

    // @FutureOrPresent: today or later. LocalDate = date only, no time.
    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;
}
package com.onlinebookstore;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Task {
    private int id;
    private String description;
    private String status;
    private String dueDate;
}

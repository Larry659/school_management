package com.lanre.school_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer lecturerId;
    private String name;
    private String department;
    private String title;
    @ManyToMany(mappedBy = "lecturerList")
    private List<Course> courseList;
}

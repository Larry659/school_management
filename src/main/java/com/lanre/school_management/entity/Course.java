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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer courseId;
    private String title;
    private Integer unit;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)

    @JoinColumn(name = "couse_material", referencedColumnName = "courseMaterialId")
    private CourseMaterial courseMaterial;

    @ManyToMany
    @JoinTable(
            name = "course_lecturer_table",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "lecturer_id"))
    private List<Lecturer> lecturerList;
}

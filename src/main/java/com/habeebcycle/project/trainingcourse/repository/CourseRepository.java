package com.habeebcycle.project.trainingcourse.repository;

import com.habeebcycle.project.trainingcourse.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

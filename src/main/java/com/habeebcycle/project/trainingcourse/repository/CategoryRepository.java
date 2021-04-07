package com.habeebcycle.project.trainingcourse.repository;

import com.habeebcycle.project.trainingcourse.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

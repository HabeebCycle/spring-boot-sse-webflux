package com.habeebcycle.project.trainingcourse.util;

import com.habeebcycle.project.trainingcourse.model.Category;
import com.habeebcycle.project.trainingcourse.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class CategoryInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final CategoryRepository repository;

    public CategoryInitializer(CategoryRepository repository) {
        log.info("Run Category Initializer...");
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (this.repository.count() > 0) {
            log.info("Category items already created.");
            return;
        }

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("UnCategorized"));
        categories.add(new Category("Bootcamp"));
        categories.add(new Category("Circuit Training"));
        categories.add(new Category("Gymnastics"));
        categories.add(new Category("Outdoor Sports"));
        categories.add(new Category("Weight Lifting"));

        categories.forEach(category -> {
            this.repository.save(category);
            log.info("Category '{}' saved with ID: {}", category.getTitle(), category.getId());
        });
    }
}

package com.habeebcycle.project.trainingcourse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.habeebcycle.project.trainingcourse.model.Category;
import com.habeebcycle.project.trainingcourse.payload.CategoryResponsePayload;
import com.habeebcycle.project.trainingcourse.repository.CategoryRepository;
import com.habeebcycle.project.trainingcourse.util.CourseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository repository;
    private final CourseMapper mapper;

    public CategoryService(CategoryRepository repository, CourseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns the instance of a category by given id.
     * @param id the given id.
     * @return category instance.
     */
    public Category getCategoryById(Long id) {
        log.debug("Try to get Category with id {}", id);

        Category category = repository.findById(id)
                .orElseGet(() -> {
                    log.error("Category with id {} does not exist.", id);
                    return null;
                });

        if(category != null)
            log.debug("Category {} with id {} was found.", category.getTitle(), id);

        return category;
    }

    /**
     * Fetches all categories.
     * @return a list of categories.
     */
    @Transactional
    public List<CategoryResponsePayload> getAllCategories() {
        return repository.findAll()
                .stream()
                .map(mapper::categoryToResponse)
                .collect(Collectors.toList());
    }

}

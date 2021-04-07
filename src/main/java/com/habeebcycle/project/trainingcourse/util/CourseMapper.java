package com.habeebcycle.project.trainingcourse.util;


import com.habeebcycle.project.trainingcourse.model.Category;
import com.habeebcycle.project.trainingcourse.model.Course;
import com.habeebcycle.project.trainingcourse.payload.CategoryResponsePayload;
import com.habeebcycle.project.trainingcourse.payload.CourseRequestPayload;
import com.habeebcycle.project.trainingcourse.payload.CourseResponsePayload;
import com.habeebcycle.project.trainingcourse.repository.CategoryRepository;
import com.habeebcycle.project.trainingcourse.repository.CourseRepository;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    public CourseMapper(CategoryRepository categoryRepository, CourseRepository courseRepository) {
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
    }

    public Course requestToCourse(CourseRequestPayload requestPayload) {
        Course course = new Course();
        course.setTitle(requestPayload.getTitle());
        course.setLongDescription(requestPayload.getLongDescription());
        course.setShortDescription(requestPayload.getShortDescription());
        course.setDuration(requestPayload.getDuration());
        course.setCreatedBy(requestPayload.getCreatedBy());
        course.setCategory(
                categoryRepository.findById(requestPayload.getCategory())
                        .orElseGet(() -> categoryRepository.getOne(1L))
        );
        return course;
    }

    public CourseResponsePayload courseToResponse(Course c) {
        CourseResponsePayload payload = new CourseResponsePayload();
        payload.setId(c.getId());
        payload.setTitle(c.getTitle());
        payload.setCategory(categoryToResponse(c.getCategory()));
        payload.setCreatedBy(c.getCreatedBy());
        payload.setLongDescription(c.getLongDescription());
        payload.setDuration(c.getDuration());
        payload.setCreatedAt(c.getCreatedAt());
        payload.setUpdatedAt(c.getUpdatedAt());
        payload.setShortDescription(c.getShortDescription());
        return payload;
    }

    public CategoryResponsePayload categoryToResponse(Category c) {
        CategoryResponsePayload response = new CategoryResponsePayload();
        response.setId(c.getId());
        response.setTitle(c.getTitle());
        response.setCourses(0L);
        return response;
    }
}

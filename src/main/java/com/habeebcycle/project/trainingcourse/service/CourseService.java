package com.habeebcycle.project.trainingcourse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.habeebcycle.project.trainingcourse.event.CourseEvent;
import com.habeebcycle.project.trainingcourse.model.Category;
import com.habeebcycle.project.trainingcourse.model.Course;
import com.habeebcycle.project.trainingcourse.payload.CategoryResponsePayload;
import com.habeebcycle.project.trainingcourse.payload.CourseRequestPayload;
import com.habeebcycle.project.trainingcourse.payload.CourseResponsePayload;
import com.habeebcycle.project.trainingcourse.repository.CourseRepository;
import com.habeebcycle.project.trainingcourse.util.CourseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

    private final CourseRepository repository;
    private final ApplicationEventPublisher publisher;

    private final CourseMapper mapper;

    private final CategoryService categoryService;

    public CourseService(CourseRepository repository, ApplicationEventPublisher publisher,
                         CategoryService categoryService, CourseMapper mapper) {
        this.repository = repository;
        this.publisher = publisher;
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    /**
     * Creates a new course from the given 'CourseRequestPayload' command.
     * @param coursePayload the course payload.
     * @return an instance of the saved course.
     */
    public Course createCourse(CourseRequestPayload coursePayload) {

        log.debug("Try to create new course {} requested by {}.", coursePayload.getTitle(), coursePayload.getCreatedBy());

        Course course = mapper.requestToCourse(coursePayload);
        Course savedCourse = repository.save(course);
        log.debug("Course {} saved to database. Created timestamp {}", savedCourse.getId(), savedCourse.getCreatedAt());

        publisher.publishEvent(new CourseEvent(savedCourse));

        return savedCourse;
    }

    /**
     * Fetches all courses.
     * @return a list of courses.
     */
    public List<CourseResponsePayload> getAllCourses() {
        return repository.findAll()
                .stream()
                .map(mapper::courseToResponse)
                .collect(Collectors.toList());
    }

    public CourseResponsePayload getCourseById(Long id) {
        Course course = repository.findById(id)
                .orElse(null);

        if (course != null) {
            return mapper.courseToResponse(course);
        }

        return null;
    }
}

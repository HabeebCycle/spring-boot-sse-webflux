package com.habeebcycle.project.trainingcourse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.habeebcycle.project.trainingcourse.event.CourseEvent;
import com.habeebcycle.project.trainingcourse.event.CourseEventProcessor;
import com.habeebcycle.project.trainingcourse.model.Course;
import com.habeebcycle.project.trainingcourse.payload.CategoryResponsePayload;
import com.habeebcycle.project.trainingcourse.payload.CourseRequestPayload;
import com.habeebcycle.project.trainingcourse.payload.CourseResponsePayload;
import com.habeebcycle.project.trainingcourse.service.CategoryService;
import com.habeebcycle.project.trainingcourse.service.CourseService;
import com.habeebcycle.project.trainingcourse.util.CourseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/course")
@CrossOrigin
public class courseController {

    private final CourseService courseService;
    private final CategoryService categoryService;
    private final CourseMapper mapper;
    private final Flux<CourseEvent> events;

    public courseController(CourseService courseService, CategoryService categoryService,
                            CourseMapper mapper, CourseEventProcessor eventProcessor) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.mapper = mapper;
        this.events = Flux.create(eventProcessor).share();
    }

    @GetMapping("/status")
    public String whatsUpService() {
        return "UP & RUNNING";
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getCourseById(@PathVariable Long id) {
        log.info("Fetching course with Id: {}", id);

        try {
            CourseResponsePayload course = courseService.getCourseById(id);

            if(course != null) {
                return ResponseEntity.ok(course);
            } else {
                return ResponseEntity.notFound().build();
                //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    ResponseEntity<?> addCourse(@RequestBody @Valid CourseRequestPayload coursePayload) {
        log.info("New course request received. [title: {}]", coursePayload.getTitle());

        try {
            Course course = courseService.createCourse(coursePayload);
            return ResponseEntity
                    .created(URI.create("/course/" + course.getId()))
                    .body(course.getId());
        } catch (Exception e) {
            log.error("ERROR OCCURRED " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CourseResponsePayload> streamCourses() {
        log.info("Start listening to the course collection");

        return events.map(e -> mapper.courseToResponse((Course) e.getSource()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCourses() {
        log.info("Fetch all courses.");

        try {
            List<CourseResponsePayload> courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCategories() {
        log.info("Fetching all categories.");

        try {
            List<CategoryResponsePayload> categories = categoryService.getAllCategories();
            return ResponseEntity.ok().body(categories);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

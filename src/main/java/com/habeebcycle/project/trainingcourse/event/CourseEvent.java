package com.habeebcycle.project.trainingcourse.event;

import com.habeebcycle.project.trainingcourse.model.Course;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CourseEvent extends ApplicationEvent {

    public CourseEvent(Course course) {
        super(course);
    }

}

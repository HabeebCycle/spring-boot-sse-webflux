package com.habeebcycle.project.trainingcourse.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CourseRequestPayload {
    @NotNull
    @Size(min = 5, max = 25, message = "The title must be between 5 and 25 characters.")
    private String title;

    @Size(max = 250, message = "The description must not be more than 250 characters.")
    private String longDescription;

    private String shortDescription;

    @NotNull
    private Long category;

    @NotNull
    private Long createdBy;

    @NotNull
    @Range(min = 15L, max = 480L)
    private Long duration = 45L;
}

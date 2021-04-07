package com.habeebcycle.project.trainingcourse.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class CourseResponsePayload {

    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private CategoryResponsePayload category;

    private Long createdBy;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private String shortDescription;

    private String longDescription;

    @NonNull
    private Long duration;
}

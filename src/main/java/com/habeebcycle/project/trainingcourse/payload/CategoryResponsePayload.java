package com.habeebcycle.project.trainingcourse.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CategoryResponsePayload {

    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private Long courses;
}

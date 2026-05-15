package com.project.TinyTales.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StoryRequest {
    private Long categoryId;

    private String title;

    private Integer time;

    private String content;
}

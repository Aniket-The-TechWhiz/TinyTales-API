package com.project.TinyTales.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponse {
    private Long id;

    private Long categoryId;

    private String title;

    private Integer time;

    private String content;
}

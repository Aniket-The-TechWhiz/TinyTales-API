package com.project.TinyTales.dto.request;

import com.project.TinyTales.model.QuoteTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteRequest {

    private String content;

    private String author;

    private QuoteTag tag;
}
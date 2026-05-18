package com.project.TinyTales.dto.response;

import com.project.TinyTales.model.QuoteTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponse {

    private String content;

    private String author;

    private QuoteTag tag;
}

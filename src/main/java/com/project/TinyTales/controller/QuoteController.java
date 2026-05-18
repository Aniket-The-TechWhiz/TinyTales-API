package com.project.TinyTales.controller;

import com.project.TinyTales.dto.request.QuoteRequest;
import com.project.TinyTales.dto.response.QuoteResponse;
import com.project.TinyTales.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    // ADD QUOTE API
    @PostMapping
    public ResponseEntity<QuoteResponse> addQuote(
            @RequestBody QuoteRequest request
    ) {

        return ResponseEntity.ok(
                quoteService.addQuote(request)
        );
    }

    // GET DAILY QUOTE API
    @GetMapping("/daily")
    public ResponseEntity<QuoteResponse> getDailyQuote() {

        return ResponseEntity.ok(
                quoteService.getDailyQuote()
        );
    }
    // GET ALL QUOTES API
    @GetMapping
    public ResponseEntity<List<QuoteResponse>> getAllQuotes() {

        return ResponseEntity.ok(
                quoteService.getAllQuotes()
        );
    }
}

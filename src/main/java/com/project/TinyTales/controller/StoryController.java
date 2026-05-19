package com.project.TinyTales.controller;

import com.project.TinyTales.dto.request.StoryRequest;
import com.project.TinyTales.dto.response.StoryResponse;

import com.project.TinyTales.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @PostMapping
    public ResponseEntity<StoryResponse> createStory(@RequestBody StoryRequest request) {
        return ResponseEntity.ok(storyService.createStory(request));
    }

    @GetMapping
    public ResponseEntity<List<StoryResponse>> getAllStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable("id") Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryResponse> updateStory(@PathVariable("id") Long id, @RequestBody StoryRequest request) {
        return ResponseEntity.ok(storyService.updateStory(id, request));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<StoryResponse>> getStoriesByCategoryId(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(storyService.getStoriesByCategoryId(categoryId));
    }
    @GetMapping("/search")
    public ResponseEntity<List<StoryResponse>> searchStories(@RequestParam String keyword) {
        // If user clears the search bar, return an empty list instantly without hitting the DB
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(storyService.searchStories(keyword.trim()));
    }
}

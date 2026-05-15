package com.project.TinyTales.controller;

import com.project.TinyTales.dto.request.StoryRequest;
import com.project.TinyTales.dto.response.StoryResponse;

import com.project.TinyTales.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

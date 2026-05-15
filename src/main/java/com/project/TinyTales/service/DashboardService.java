package com.project.TinyTales.service;

import com.project.TinyTales.dto.response.DashboardResponse;
import com.project.TinyTales.repository.CategoryRepository;
import com.project.TinyTales.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final StoryRepository storyRepository;
    private final CategoryRepository categoryRepository;
    public DashboardResponse getDashboardData() {
        Long totalStories=storyRepository.count();
        Long totalCategory=categoryRepository.count();
        return new DashboardResponse(totalCategory,totalStories);
    }
}

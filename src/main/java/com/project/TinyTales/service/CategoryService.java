package com.project.TinyTales.service;

import com.project.TinyTales.dto.request.CategoryRequest;
import com.project.TinyTales.dto.response.CategoryResponse;
import com.project.TinyTales.model.Category;
import com.project.TinyTales.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .count(category.getCount())
                        .build()).toList();
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .count(0)
                .build();

        Category saved = categoryRepository.save(category);
        return CategoryResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .count(saved.getCount())
                .build();
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        Category saved = categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .count(saved.getCount())
                .build();
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }
}

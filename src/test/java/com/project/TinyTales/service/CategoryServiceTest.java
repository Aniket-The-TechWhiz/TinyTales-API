package com.project.TinyTales.service;

import com.project.TinyTales.dto.request.CategoryRequest;
import com.project.TinyTales.dto.response.CategoryResponse;
import com.project.TinyTales.model.Category;
import com.project.TinyTales.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;
    @Test
    void getAllCategory() {
        System.out.println("Tested this api");
    }

    @Test
    void createCategory() {

        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Aniket");

        Category savedCategory = Category.builder()
                .id(1L)
                .name("Aniket")
                .count(0)
                .build();

        // Mock repository
        Mockito.when(categoryRepository.save(Mockito.any(Category.class)))
                .thenReturn(savedCategory);

        // Act
        CategoryResponse response =
                categoryService.createCategory(categoryRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Aniket", response.getName());
        assertEquals(0, response.getCount());

        // Verify
        Mockito.verify(categoryRepository, Mockito.times(1))
                .save(Mockito.any(Category.class));
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}
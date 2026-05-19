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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;
    @Test
    void getAllCategory() {

        // Arrange
        Category category1 = Category.builder()
                .id(1L)
                .name("Horror")
                .count(10)
                .build();

        Category category2 = Category.builder()
                .id(2L)
                .name("Comedy")
                .count(5)
                .build();

        List<Category> categoryList =
                Arrays.asList(category1, category2);

        Mockito.when(categoryRepository.findAll())
                .thenReturn(categoryList);

        // Act
        List<CategoryResponse> response =
                categoryService.getAllCategory();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());

        assertEquals("Horror", response.get(0).getName());
        assertEquals("Comedy", response.get(1).getName());

        // Verify
        Mockito.verify(categoryRepository, Mockito.times(1))
                .findAll();
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

        // Arrange
        Long id = 1L;

        CategoryRequest request = new CategoryRequest();
        request.setName("Updated Category");

        Category existingCategory = Category.builder()
                .id(1L)
                .name("Old Category")
                .count(0)
                .build();

        Category updatedCategory = Category.builder()
                .id(1L)
                .name("Updated Category")
                .count(0)
                .build();

        Mockito.when(categoryRepository.findById(id))
                .thenReturn(Optional.of(existingCategory));

        Mockito.when(categoryRepository.save(Mockito.any(Category.class)))
                .thenReturn(updatedCategory);

        // Act
        CategoryResponse response =
                categoryService.updateCategory(id, request);

        // Assert
        assertNotNull(response);
        assertEquals("Updated Category", response.getName());

        // Verify
        Mockito.verify(categoryRepository, Mockito.times(1))
                .findById(id);

        Mockito.verify(categoryRepository, Mockito.times(1))
                .save(Mockito.any(Category.class));
    }

    @Test
    void deleteCategory() {

        // Arrange
        Long id = 1L;

        Category category = Category.builder()
                .id(1L)
                .name("Horror")
                .count(0)
                .build();

        Mockito.when(categoryRepository.findById(id))
                .thenReturn(Optional.of(category));

        // Act
        categoryService.deleteCategory(id);

        // Verify
        Mockito.verify(categoryRepository, Mockito.times(1))
                .findById(id);

        Mockito.verify(categoryRepository, Mockito.times(1))
                .delete(category);
    }
}
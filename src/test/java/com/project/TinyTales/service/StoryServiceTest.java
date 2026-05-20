package com.project.TinyTales.service;

import com.project.TinyTales.dto.request.StoryRequest;
import com.project.TinyTales.dto.response.StoryResponse;
import com.project.TinyTales.model.Category;
import com.project.TinyTales.model.Story;
import com.project.TinyTales.repository.CategoryRepository;
import com.project.TinyTales.repository.StoryRepository;
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
class StoryServiceTest {

    @Mock
    StoryRepository storyRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    StoryService storyService;

    @Test
    void createStory() {

        // Arrange
        StoryRequest storyRequest = new StoryRequest();
        storyRequest.setCategoryId(1L);
        storyRequest.setTitle("Spring Boot");
        storyRequest.setTime(5);
        storyRequest.setContent("Learning Spring Boot");

        Category category = Category.builder()
                .id(1L)
                .name("Programming")
                .count(0)
                .build();

        Story savedStory = Story.builder()
                .id(1L)
                .title("Spring Boot")
                .content("Learning Spring Boot")
                .time(5)
                .category(category)
                .build();

        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        Mockito.when(storyRepository.save(Mockito.any(Story.class)))
                .thenReturn(savedStory);

        // Act
        StoryResponse response = storyService.createStory(storyRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Spring Boot", response.getTitle());
        assertEquals("Learning Spring Boot", response.getContent());
        assertEquals(5, response.getTime());

        // Verify
        Mockito.verify(categoryRepository, Mockito.times(1))
                .findById(1L);

        Mockito.verify(storyRepository, Mockito.times(1))
                .save(Mockito.any(Story.class));
    }

    @Test
    void getAllStories() {

        // Arrange
        Category category = Category.builder()
                .id(1L)
                .name("Programming")
                .count(1)
                .build();

        Story story1 = Story.builder()
                .id(1L)
                .title("Java")
                .content("Core Java")
                .time(3)
                .category(category)
                .build();

        Story story2 = Story.builder()
                .id(2L)
                .title("Spring")
                .content("Spring Boot")
                .time(5)
                .category(category)
                .build();

        List<Story> storyList = Arrays.asList(story1, story2);

        Mockito.when(storyRepository.findAll())
                .thenReturn(storyList);

        // Act
        List<StoryResponse> response = storyService.getAllStories();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Java", response.get(0).getTitle());
        assertEquals("Spring", response.get(1).getTitle());

        // Verify
        Mockito.verify(storyRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    void deleteStory() {

        // Arrange
        Category category = Category.builder()
                .id(1L)
                .name("Programming")
                .count(1)
                .build();

        Story story = Story.builder()
                .id(1L)
                .title("Java")
                .content("Core Java")
                .time(3)
                .category(category)
                .build();

        Mockito.when(storyRepository.findById(1L))
                .thenReturn(Optional.of(story));

        // Act
        storyService.deleteStory(1L);

        // Verify
        Mockito.verify(storyRepository, Mockito.times(1))
                .findById(1L);

        Mockito.verify(storyRepository, Mockito.times(1))
                .delete(story);

        Mockito.verify(categoryRepository, Mockito.times(1))
                .save(category);
    }

    @Test
    void updateStory() {

        // Arrange
        Category oldCategory = Category.builder()
                .id(1L)
                .name("Old Category")
                .count(1)
                .build();

        Category newCategory = Category.builder()
                .id(2L)
                .name("New Category")
                .count(0)
                .build();

        Story existingStory = Story.builder()
                .id(1L)
                .title("Old Story")
                .content("Old Content")
                .time(2)
                .category(oldCategory)
                .build();

        StoryRequest request = new StoryRequest();
        request.setCategoryId(2L);
        request.setTitle("Updated Story");
        request.setContent("Updated Content");
        request.setTime(5);

        Story updatedStory = Story.builder()
                .id(1L)
                .title("Updated Story")
                .content("Updated Content")
                .time(5)
                .category(newCategory)
                .build();

        Mockito.when(storyRepository.findById(1L))
                .thenReturn(Optional.of(existingStory));

        Mockito.when(categoryRepository.findById(2L))
                .thenReturn(Optional.of(newCategory));

        Mockito.when(storyRepository.save(Mockito.any(Story.class)))
                .thenReturn(updatedStory);

        // Act
        StoryResponse response =
                storyService.updateStory(1L, request);

        // Assert
        assertNotNull(response);
        assertEquals("Updated Story", response.getTitle());
        assertEquals("Updated Content", response.getContent());

        // Verify
        Mockito.verify(storyRepository, Mockito.times(1))
                .findById(1L);

        Mockito.verify(categoryRepository, Mockito.times(1))
                .findById(2L);

        Mockito.verify(storyRepository, Mockito.times(1))
                .save(Mockito.any(Story.class));
    }

    @Test
    void getStoriesByCategoryId() {

        // Arrange
        Category category = Category.builder()
                .id(1L)
                .name("Programming")
                .count(1)
                .build();

        Story story = Story.builder()
                .id(1L)
                .title("Java")
                .content("Java Content")
                .time(4)
                .category(category)
                .build();

        Mockito.when(storyRepository.findByCategoryId(1L))
                .thenReturn(List.of(story));

        // Act
        List<StoryResponse> response =
                storyService.getStoriesByCategoryId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Java", response.get(0).getTitle());

        // Verify
        Mockito.verify(storyRepository, Mockito.times(1))
                .findByCategoryId(1L);
    }

    @Test
    void searchStories() {

        // Arrange
        Category category = Category.builder()
                .id(1L)
                .name("Programming")
                .count(1)
                .build();

        Story story = Story.builder()
                .id(1L)
                .title("Spring Boot")
                .content("Spring Boot Tutorial")
                .time(5)
                .category(category)
                .build();

        Mockito.when(
                storyRepository
                        .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                                "spring",
                                "spring"
                        )
        ).thenReturn(List.of(story));

        // Act
        List<StoryResponse> response =
                storyService.searchStories("spring");

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Spring Boot", response.get(0).getTitle());

        // Verify
        Mockito.verify(storyRepository, Mockito.times(1))
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                        "spring",
                        "spring"
                );
    }
}
    package com.project.TinyTales.service;

    import com.project.TinyTales.dto.request.StoryRequest;
    import com.project.TinyTales.dto.response.StoryResponse;
    import com.project.TinyTales.model.Category;
    import com.project.TinyTales.model.Story;
    import com.project.TinyTales.repository.CategoryRepository;
    import com.project.TinyTales.repository.StoryRepository;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class StoryService {

        private final StoryRepository storyRepository;
        private final CategoryRepository categoryRepository;

        @Transactional
        public StoryResponse createStory(StoryRequest request) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Story story = Story.builder()
                    .category(category)
                    .title(request.getTitle())
                    .time(request.getTime())
                    .content(request.getContent())
                    .build();

            Story saved = storyRepository.save(story);

            // increment and persist count
            Integer current = category.getCount();
            category.setCount((current == null ? 0 : current) + 1);
            categoryRepository.save(category);

            return new StoryResponse(saved.getId(), category.getId(), saved.getTitle(), saved.getTime(), saved.getContent());
        }

        public List<StoryResponse> getAllStories() {
            return storyRepository.findAll().stream()
                    .map(s -> new StoryResponse(s.getId(), s.getCategory().getId(), s.getTitle(), s.getTime(), s.getContent()))
                    .toList();
        }

        @Transactional
        public void deleteStory(Long storyId) {
            Story story = storyRepository.findById(storyId)
                    .orElseThrow(() -> new RuntimeException("Story not found"));

            Category category = story.getCategory();

            storyRepository.delete(story);

            Integer current = category.getCount();
            category.setCount(Math.max(0, (current == null ? 0 : current) - 1));
            categoryRepository.save(category);
        }

        @Transactional
        public StoryResponse updateStory(Long storyId, StoryRequest request) {
            Story story = storyRepository.findById(storyId)
                    .orElseThrow(() -> new RuntimeException("Story not found"));

            Category oldCategory = story.getCategory();
            Long newCategoryId = request.getCategoryId();

            if (newCategoryId != null && !newCategoryId.equals(oldCategory.getId())) {
                Category newCategory = categoryRepository.findById(newCategoryId)
                        .orElseThrow(() -> new RuntimeException("New category not found"));

                // move story to new category
                story.setCategory(newCategory);

                // decrement old category
                Integer oldCount = oldCategory.getCount();
                oldCategory.setCount(Math.max(0, (oldCount == null ? 0 : oldCount) - 1));
                categoryRepository.save(oldCategory);

                // increment new category
                Integer newCount = newCategory.getCount();
                newCategory.setCount((newCount == null ? 0 : newCount) + 1);
                categoryRepository.save(newCategory);
            }

            story.setTitle(request.getTitle());
            story.setTime(request.getTime());
            story.setContent(request.getContent());

            Story saved = storyRepository.save(story);
            return new StoryResponse(saved.getId(), saved.getCategory().getId(), saved.getTitle(), saved.getTime(), saved.getContent());
        }


        public List<StoryResponse> getStoriesByCategoryId(Long categoryId) {

            List<Story> stories = storyRepository.findByCategoryId(categoryId);

            return stories.stream()
                    .map(s -> new StoryResponse(
                            s.getId(),
                            s.getCategory().getId(),
                            s.getTitle(),
                            s.getTime(),
                            s.getContent()
                    ))
                    .toList();
        }

        //search function
        public List<StoryResponse> searchStories(String keyword) {
            List<Story> stories = storyRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);

            return stories.stream()
                    .map(s -> {
                        StoryResponse response = new StoryResponse();
                        response.setId(s.getId());
                        // Safe check in case a story has no category assigned
                        response.setCategoryId(s.getCategory() != null ? s.getCategory().getId() : null);
                        response.setTitle(s.getTitle());
                        response.setTime(s.getTime());
                        response.setContent(s.getContent());
                        return response;
                    })
                    .toList();
        }
    }

package com.project.TinyTales.repository;

import com.project.TinyTales.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByCategoryId(Long categoryId);

}

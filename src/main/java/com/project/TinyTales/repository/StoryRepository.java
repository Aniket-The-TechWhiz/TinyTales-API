package com.project.TinyTales.repository;

import com.project.TinyTales.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}

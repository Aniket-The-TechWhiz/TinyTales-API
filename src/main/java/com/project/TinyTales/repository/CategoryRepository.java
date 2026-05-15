package com.project.TinyTales.repository;

import com.project.TinyTales.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}

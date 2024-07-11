package org.wildcodeschool.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wildcodeschool.myblog.model.Article;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByTitle(String title);

    List<Article> findByContent(String content);

    List<Article> findByCreatedAtAfter(LocalDateTime date);

    List<Article> findTop5ByOrderByCreatedAtDesc();

    List<Article> findAllById(Long categoryId);
}
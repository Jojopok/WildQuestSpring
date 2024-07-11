package org.wildcodeschool.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wildcodeschool.myblog.model.Article;

@Repository
public interface ArticleAuthorRepository extends JpaRepository<Article, Long> {
}
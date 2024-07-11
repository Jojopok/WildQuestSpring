package org.wildcodeschool.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wildcodeschool.myblog.model.Tag;

import java.util.List;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
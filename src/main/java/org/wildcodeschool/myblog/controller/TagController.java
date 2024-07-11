package org.wildcodeschool.myblog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wildcodeschool.myblog.dto.TagDTO;
import org.wildcodeschool.myblog.model.Tag;
import org.wildcodeschool.myblog.model.Article;
import org.wildcodeschool.myblog.repository.TagRepository;
import org.wildcodeschool.myblog.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;

    public TagController(TagRepository tagRepository, ArticleRepository articleRepository) {
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        if (tags.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TagDTO> tagDTOs = tags.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tagDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Tag tag = optionalTag.get();
        return ResponseEntity.ok(convertToDTO(tag));
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tagDTO) {
        Tag tag = convertToEntity(tagDTO);
        Tag savedTag = tagRepository.save(tag);
        return ResponseEntity.status(201).body(convertToDTO(savedTag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable Long id, @RequestBody TagDTO tagDTO) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Tag tag = optionalTag.get();
        tag.setName(tagDTO.getName());
        Tag updatedTag = tagRepository.save(tag);
        return ResponseEntity.ok(convertToDTO(updatedTag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Tag tag = optionalTag.get();
        tagRepository.delete(tag);
        return ResponseEntity.noContent().build();
    }

    private TagDTO convertToDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        if (tag.getArticles() != null) {
            tagDTO.setArticleIds(tag.getArticles().stream().map(Article::getId).collect(Collectors.toList()));
        }
        return tagDTO;
    }

    private Tag convertToEntity(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        if (tagDTO.getArticleIds() != null) {
            List<Article> articles = articleRepository.findAllById(tagDTO.getArticleIds());
            tag.setArticles(articles);
        }
        return tag;
    }
}
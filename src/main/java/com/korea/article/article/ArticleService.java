package com.korea.article.article;

import com.korea.article.note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getAllArticleList() {
        return articleRepository.findAll();
    }

    public void createArticle(Note note) {

        Article article = new Article();
        article.setTitle("NewTitle...");
        article.setContent("");
        article.setCreateDate(LocalDateTime.now());
        article.setNote(note);

        articleRepository.save(article);
    }

    public void modifyArticle(Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);
        article.setModifyDate(LocalDateTime.now());
        articleRepository.save(article);
    }

    public Article findById(Integer id) {
        return articleRepository.findById(id).get();
    }

    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }
}

package com.korea.article.article;

import com.korea.article.note.Note;
import com.korea.article.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final NoteService noteService;

    @PostMapping("/create")
    public String createArticle(Integer targetNoteid) {

        Note targetNote = noteService.findById(targetNoteid);
        articleService.createArticle(targetNote);

        int size = articleService.getAllArticleList().size() - 1;
        int lastNum = articleService.getAllArticleList().get(size).getId();
        return "redirect:/article/" + lastNum;
    }

    @PostMapping("/modify")
    public String modifyArticle(Integer targetArticleid, String title, String content) {
        Article article = articleService.findById(targetArticleid);
        if (title.trim().isEmpty()) {
            title = "제목없음";
        }
        articleService.modifyArticle(article, title, content);
        return "redirect:/article/" + targetArticleid;
    }

    @GetMapping("/{articleid}")
    public String detail(@PathVariable("articleid") Integer articleid, Model model) {

        Article targetArticle = articleService.findById(articleid);
        Note targetNote = targetArticle.getNote();

        model.addAttribute("noteList", noteService.getList());
        model.addAttribute("targetNote", targetNote);
        model.addAttribute("articleList", targetNote.getArticleList());
        model.addAttribute("targetArticle", targetArticle);

        return "main";
    }

    @PostMapping("/delete")
    public String deleteArticle(Integer targetArticleid) {
        Article targetArticle = articleService.findById(targetArticleid);
        Note targetNote = targetArticle.getNote();
        if (targetNote.getArticleList().size() != 1) {
            articleService.deleteArticle(targetArticle);
        }
        return "redirect:/note/" + targetNote.getId();
    }
}

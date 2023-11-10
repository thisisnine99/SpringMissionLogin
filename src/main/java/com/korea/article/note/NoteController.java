package com.korea.article.note;

import com.korea.article.article.Article;
import com.korea.article.article.ArticleService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final ArticleService articleService;


    @GetMapping("/list")
    public String list(Model model) {

        List<Note> noteList = noteService.getList();

        if(noteList.isEmpty()) {
            Note note = noteService.createNote();
            articleService.createArticle(note);
        }

        model.addAttribute("noteList", noteList);
        model.addAttribute("targetNote", noteList.get(0));
        model.addAttribute("articleList", noteList.get(0).getArticleList());
        model.addAttribute("targetArticle", noteList.get(0).getArticleList().get(0));
        return "main";
    }

    @PostMapping("/create")
    public String createNote() {
        Note note = noteService.createNote();
        articleService.createArticle(note);

        int size = noteService.getList().size() - 1;
        int lastNum = noteService.getList().get(size).getId();
        return "redirect:/note/" + lastNum;
    }

    @GetMapping("/{noteid}")
    public String detail(@PathVariable("noteid") Integer noteid, Model model) {

        Note targetNote = noteService.findById(noteid);

        model.addAttribute("noteList", noteService.getList());
        model.addAttribute("targetNote", targetNote);
        model.addAttribute("articleList", targetNote.getArticleList());
        model.addAttribute("targetArticle", targetNote.getArticleList().get(0));

        return "main";
    }

    @PostMapping("/delete")
    public String deleteNote(Integer targetNoteid) {
        Note targetNote = noteService.findById(targetNoteid);
        if (noteService.getList().size() != 1) {
            noteService.deleteNote(targetNote);
        }
        return "redirect:/";
    }

    @PostMapping("/addGroup")
    public String addGroup(Integer targetNoteid) {
        Note targetNote = noteService.findById(targetNoteid);
        Note note = noteService.addGroup(targetNote);
        articleService.createArticle(note);
        int size = noteService.getAllLIst().size() - 1;
        int lastNum = noteService.getAllLIst().get(size).getId();
        return "redirect:/note/" + lastNum;
    }

    @PostMapping("/modify")
    public String modifyNote(Integer targetNoteid, String title) {
        Note note = noteService.findById(targetNoteid);
        if (title.trim().isEmpty()) {
            title = "제목없음";
        }
        noteService.modifyNote(note, title);
        return "redirect:/note/" + targetNoteid;
    }
}

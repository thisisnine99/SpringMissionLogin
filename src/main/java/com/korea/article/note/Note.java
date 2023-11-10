package com.korea.article.note;

import com.korea.article.article.Article;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "note", cascade = CascadeType.REMOVE)
    private List<Article> articleList;

    @ManyToOne
    private Note parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Note> childList;
}

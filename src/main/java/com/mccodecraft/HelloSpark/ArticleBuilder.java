package com.mccodecraft.HelloSpark;

import java.util.Date;

/**
 * Created by james on 2/4/17.
 */
public class ArticleBuilder {
    private String title;
    private String content;
    private String summary;
    private Date createdAt;
    private Integer id;
    private boolean deleted;

    public ArticleBuilder(
            final String title,
            final String content,
            final String summary,
            final Date createdAt,
            final Integer id,
            final boolean deleted
    ) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.createdAt = createdAt;
        this.id = id;
        this.deleted = deleted;
    }

    public ArticleBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public ArticleBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public ArticleBuilder setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ArticleBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public ArticleBuilder setDeleted(boolean deleted) {
        this.deleted = true;
        return this;
    }


    public Article createArticle() {
        return new Article (title, content, summary, createdAt, id, deleted);
    }
}

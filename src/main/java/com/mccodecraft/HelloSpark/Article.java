package com.mccodecraft.HelloSpark;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by james on 2/4/17.
 */
public class Article {

    private final String title;
    private final String content;
    private final String summary;
    private final Date createdAt;
    private final Integer id;
    private boolean deleted;


    public static class ArticleBuilder {
        private String title;
        private String content;
        private String summary;
        private Date createdAt;
        private Integer id;
        private boolean deleted;

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

        public Article build() {
            return new Article(this);
        }

        public ArticleBuilder getArticleBuilder() {
            return this;
        }
    }


    public Article(ArticleBuilder builder) {
        this.title = builder.title;
        this.content = builder.content;
        this.summary = builder.summary;
        this.createdAt = builder.createdAt;
        this.id = builder.id;
        this.deleted = builder.deleted;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSummary() {
        return summary;
    }

    public Integer getId() {
        return id;
    }

    public Boolean readable() {
        return !this.deleted;
    }

    public void delete() { this.deleted = true; }

    public String getCreatedAt() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(this.createdAt);
    }

    public String getEditLink() {
        return "<a href='/article/update/" + this.id + "'>Edit</a>";
    }

    public String getDeleteLink() {
        return "<a href='/article/delete/" + this.id + "'>Delete</a>";
    }

    public String getSummaryLink() {
        return "<a href='/article/read/" + this.id + "'>" + this.summary + "</a>";
    }

}

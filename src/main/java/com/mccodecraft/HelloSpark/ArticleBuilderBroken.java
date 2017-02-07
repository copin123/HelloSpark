package com.mccodecraft.HelloSpark;

import java.util.Date;

/**
 * Created by james on 2/4/17.
 */

public class ArticleBuilderBroken {
    private String title;
    private String content;
    private String summary;
    private Date createdAt;
    private Integer id;
    private boolean deleted;

    public ArticleBuilderBroken setTitle(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilderBroken setContent(String content) {
        this.content = content;
        return this;
    }

    public ArticleBuilderBroken setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public ArticleBuilderBroken setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ArticleBuilderBroken setId(Integer id) {
        this.id = id;
        return this;
    }

    public ArticleBuilderBroken setDeleted(boolean deleted) {
        this.deleted = true;
        return this;
    }

//        public Article build() {
//            return new Article(this);
//        }
}


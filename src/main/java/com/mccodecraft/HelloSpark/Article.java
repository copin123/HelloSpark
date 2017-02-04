package com.mccodecraft.HelloSpark;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by james on 2/4/17.
 */
public class Article {

    private String title;
    private String content;
    private String summary;
    private Date createdAt;
    private Integer id;
    private boolean deleted;

   public Article(
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

   public Boolean readable() {
       return !this.deleted;
   }

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
       return "<a href='/article/read/" + this.id + "'>"+ this.summary+"</a>";
   }
}

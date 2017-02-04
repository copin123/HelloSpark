/**
 * Created by james on 2/4/17.
 */
package com.mccodecraft.HelloSpark;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import static spark.Spark.get;
import static spark.Spark.post;

public class HelloSpark {

    public static Deque<Article> articles = new ArrayDeque<Article>();
    public static void main(String[] args) {
        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                String title = "My Blog";
                String createArticleLink = "<a href='/article/create'>Write Article</a>";
                StringBuilder html = new StringBuilder();
                html.append("<h1").append(title).append("</h1>").append(createArticleLink);
                html.append("<hr>");

                if(HelloSpark.articles.isEmpty()) {
                    html.append("<b>No articles have been posted </b>");
                } else {
                    for (Article article  : Hello)
                }
            }
        });
    }
}

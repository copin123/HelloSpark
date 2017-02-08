/**
 * Created by james on 2/4/17.
 */
package com.mccodecraft.HelloSpark;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerRoute;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;

public class HelloSpark {

    public static Deque<Article> articles = new ArrayDeque<Article>();

    public static void main(String[] args) {
        get(new Route("/") {
            @Override
            public ModelAndView handle(Request request, Response response) {
                Map<String, Object> viewObjects = new HashMap<>();

                if (HelloSpark.articles.isEmpty()) {
                    viewObjects.put("hasNoArticles","Welcome, please click \"Write Article\" to begin.");
                } else {
                    ArrayList<Article> showArticles = new ArrayList<>();

                    for (Article article : HelloSpark.articles) {
                        if(article.readable()) {
                            showArticles.add(article);
                        }

                    }
                    viewObjects.put("articles", showArticles);
                }
                viewObjects.put("templateName", "articleList.ftl");
                return modelAndView(viewObjects, "layout.ftl");
            }
        });

        get(new Route("/article/create") {
            @Override
            public Object handle(Request request, Response response) {
                StringBuilder form = new StringBuilder();

                form.append("<form id='article-create-form' method='POST' action='/article/create'>")
                        .append("Title: <input type='text' name='article-title' />")
                        .append("<br/>")
                        .append("Summary: <input type='text' name='article-summary' />")
                        .append("<br/>")
                        .append("</form>")
                        .append("<textarea name='article-content' rows='4' cols='50' form='article-create-form'></textarea>")
                        .append("<br/>")
                        .append("<input type='submit' value='Publish' form='article-create-form' />");
                return form.toString();
            }
        });

        post(new Route("/article/create") {
            @Override
            public Object handle(Request request, Response response) {
                String title = request.queryParams("article-title");
                String summary = request.queryParams("article-summary");
                String content = request.queryParams("article-content");

                Article article = new Article(title, summary, content, HelloSpark.articles.size()+1);

                HelloSpark.articles.addFirst(article);

                response.status(201);
                response.redirect("/");
                return "";
            }
        });

        get(new Route("/article/read/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                StringBuilder html = new StringBuilder();

                for (Article article : HelloSpark.articles) {
                    if (id.equals(article.getId())) {
                        html.append("<a href='/'>Home</a>").append("<p/>")
                                .append("title: ").append(article.getTitle()).append("<br/>")
                                .append(article.getCreatedAt())
                                .append("<p>").append(article.getContent()).append("</p>");
                        break;
                    }
                }
                return html.toString();
            }
        });
        get(new Route("/article/update/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                StringBuilder form = new StringBuilder();

                for (Article article : HelloSpark.articles) {
                    if (id.equals(article.getId())) {
                        form.append("<form id='article-create-form' method='POST' action='/article/update/:id'>")
                                .append("Title: <input type='text' name='article-title' value='")
                                .append(article.getTitle()).append("' />")
                                .append("<br/>")
                                .append("Summary: <input type='text' name='article-summary' value='")
                                .append(article.getSummary()).append("' />")
                                .append("<input type='hidden' name='article-id' value='").append(article.getId()).append("' />")
                                .append("<br/>")
                                .append("</form>")
                                .append("<textarea name='article-content' rows='4' cols='50' form='article-create-form'>")
                                .append(article.getContent())
                                .append("</textarea>")
                                .append("<br/>")
                                .append("<input type='submit' value='Update' form='article-create-form' />");
                        break;
                    }
                }
                return form.toString();
            }
        });

        post(new Route("/article/update/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id      = Integer.parseInt(request.queryParams("article-id"));
                String title    = request.queryParams("article-title");
                String summary  = request.queryParams("article-summary");
                String content  = request.queryParams("article-content");

                for (Article article : HelloSpark.articles) {
                    if (id.equals(article.getId())) {
                        article.setTitle(title);
                        article.setContent(content);
                        article.setSummary(summary);
                    }
                }
                response.status(200);
                response.redirect("/");
                return "";
            }
        });

        get(new Route("/article/delete/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                for (Article article : HelloSpark.articles) {
                    if (id.equals(article.getId())) {
                        article.delete();
                    }
                }
                response.status(200);
                response.redirect("/");
                return "";
            }
        });

        get(new FreeMarkerRoute("/freemarker") {
            @Override
            public Object handle(Request request, Response response) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("blogTitle", "Spark Blog!");
                attributes.put("descriptionTitle", "bootstrap?!?! what!");
                attributes.put("descriptionBody1", "twitter bootstrap is cool");
                attributes.put("descriptionBody2", "let's get rolling!");

                return modelAndView(attributes, "layout.ftl");
            }
        });

    }
}

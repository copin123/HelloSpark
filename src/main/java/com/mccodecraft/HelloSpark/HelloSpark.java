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

    public static ArticleDbService<Article> articleArticleDbService = new ArticleServletDao<>();

    public static void main(String[] args) {
        get(new FreeMarkerRoute("/") {
            @Override
            public ModelAndView handle(Request request, Response response) {
                Map<String, Object> viewObjects = new HashMap<>();
                ArrayList<Article> articles = articleArticleDbService.readAll();

                if (articles.isEmpty()) {
                    viewObjects.put("hasNoArticles","Welcome, please click \"Write Article\" to begin.");
                } else {
                    Deque<Article> showArticles = new ArrayDeque<>();

                    for (Article article : articles) {
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

        get(new FreeMarkerRoute("/article/create") {
            @Override
            public Object handle(Request request, Response response) {
                Map<String, Object> viewObjects = new HashMap<>();
                viewObjects.put("templateName", "articleForm.ftl");

                return modelAndView(viewObjects, "layout.ftl");
            }
        });

        post(new FreeMarkerRoute("/article/create") {
            @Override
            public Object handle(Request request, Response response) {
                String title = request.queryParams("article-title");
                String summary = request.queryParams("article-summary");
                String content = request.queryParams("article-content");

                Article article = new Article(title, summary, content, articleArticleDbService.readAll().size());

                articleArticleDbService.create(article);

                response.status(201);
                response.redirect("/");
                return "";
            }
        });

        get(new FreeMarkerRoute("/article/read/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                Map<String, Object> viewObjects = new HashMap<>();

                viewObjects.put("templateName", "articleRead.ftl");

                viewObjects.put("article",articleArticleDbService.readOne(id));
                return modelAndView(viewObjects, "layout.ftl");
            }
        });

        get(new FreeMarkerRoute("/article/update/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                Map<String, Object> viewObjects = new HashMap<>();
                viewObjects.put("templateName", "articleForm.ftl");

                viewObjects.put("article", articleArticleDbService.readOne(id));
                return modelAndView(viewObjects, "layout.ftl");
            }
        });

        post(new Route("/article/update/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id      = Integer.parseInt(request.queryParams("article-id"));
                String title    = request.queryParams("article-title");
                String summary  = request.queryParams("article-summary");
                String content  = request.queryParams("article-content");

                articleArticleDbService.update(id, title, summary, content);
                response.status(200);
                response.redirect("/");
                return "";
            }
        });

        get(new Route("/article/delete/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                articleArticleDbService.delete(id);

                response.status(200);
                response.redirect("/");
                return "";
            }
        });
    }
}

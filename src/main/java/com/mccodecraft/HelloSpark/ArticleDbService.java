package com.mccodecraft.HelloSpark;

import java.util.ArrayList;

/**
 * Created by james on 2/7/17.
 */
public interface ArticleDbService<T> {
    public Boolean create(T entity);
    public T readOne(int id);
    public ArrayList<T> readAll();
    public Boolean update(int id, String title, String summary, String content);
    public Boolean delete(int id);
}

package com.mccodecraft.HelloSpark;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by james on 2/7/17.
 */
public class ArticlePostgresDao <T extends Article> implements ArticleDbService<T> {

    private Connection conn;
    private Statement stmnt;

    public ArticlePostgresDao() {
        String user= "postgres";
        String passwd = "postgres";
        String dbName = "sparkledb";
        String uri = "jdbc:postgressql://localhost/"+dbName;

         String createTableQuery =
                "CREATE TABLE IF NOT EXISTS article(" +
                        "id         INT         PRIMARY KEY NOT NULL," +
                        "title      VARCHAR(64) NOT NULL," +
                        "content    VARCHAR(512)NOT NULL," +
                        "summary    VARCHAR(64) NOT NULL," +
                        "deleted    BOOLEAN     DEFAULT FALSE," +
                        "createdAt  DATE        NOT NULL" +
                        ");"
         try {
             conn = DriverManager.getConnection(uri, user, passwd);
             stmnt = conn.createStatement();
             stmnt.execute(createTableQuery);
             System.out.println("Connecting to PostgreSQL database");
         } catch(Exception e) {
             System.out.println(e.getMessage());
             try {
                 if(null != stmnt) {
                     stmnt.close();
                 }
                 if(null != conn) {
                     conn.close();
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
    }

    @Override
    public Boolean create(T entity) {
        try {
            String insertQuery = "INSERT INTO article(id, title, content, summary, createdAt) VALUES(?,?,?,?,?);";
            //Prepared statements allow us to avoid sql inject attacks
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            //jdbc bindings here
            pstmt.setInt(1, entity.getId());
            pstmt.setString(2, entity.getTitle());
            pstmt.setString(3, entity.getContent());
            pstmt.setString(4, entity.getSummary());

            java.sql.Date sqlNow = new java.sql.Date(new java.util.Date().getTime());
            pstmt.setDate(5, sqlNow);
            pstmt.executeUpdate();
             // Unless closed prepared statement connections will linger
            // Not very important for a trivial app but it will burn you in a professional large codebase
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if(null !=stmnt) { stmnt.close(); }
                if(null != conn) { conn.close(); }
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T readOne(int id) {
        try {
            String selectQuery = "SELECT * FROM article wher id = ?";
            PreparedStatement pstmt = conn.prepareStatement(selectQuery);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.getResultSet();

            if(result.next()) {
                Article entity = new Article(result.getString("title"), result.getString("summary")
                        , result.getString("content"), result.getInt("id"), result.getDate("createdat")
                        , result.getBoolean("deleted");
                pstmt.close();
                return (T) entity;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            try {
                if(null !=stmnt) { stmnt.close(); }
                if(null != conn) { conn.close(); }
            } catch(SQLException ex) {
                ex.printStackTrace();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<T> readAll() {
        ArrayList results = (ArrayList<Article>) new ArrayList<T>();
        
        return null;
    }

    @Override
    public Boolean update(int id, String title, String summary, String content) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }
}

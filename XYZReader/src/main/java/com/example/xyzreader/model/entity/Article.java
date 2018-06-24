package com.example.xyzreader.model.entity;

import java.util.Date;
import java.util.Objects;

public class Article {
    private int id;
    private float aspect_ratio;
    private String title;
    private String author;
    private String photo;
    private String thumb;
    private String body;
    private String published_date;


    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return this.author; }
    public void setAuthor(String author) { this.author = author; }

    public String getBody() { return this.body; }
    public void setBody(String body) { this.body = body; }

    public String getThumb() { return this.thumb; }
    public void setThumb(String thumb) { this.thumb = thumb; }

    public String getPhoto() { return this.photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public float getAspectRatio() { return this.aspect_ratio; }
    public void setAspectRatio(float aspect_ratio) { this.aspect_ratio = aspect_ratio; }

    public String getPublishedDate() { return this.published_date; }
    public void setPublishedDate(String published_date) { this.published_date = published_date; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id &&
                Double.compare(article.aspect_ratio, aspect_ratio) == 0 &&
                Objects.equals(title, article.title) &&
                Objects.equals(author, article.author) &&
                Objects.equals(body, article.body) &&
                Objects.equals(thumb, article.thumb) &&
                Objects.equals(photo, article.photo) &&
                Objects.equals(published_date, article.published_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, body, thumb, photo, aspect_ratio, published_date);
    }
}

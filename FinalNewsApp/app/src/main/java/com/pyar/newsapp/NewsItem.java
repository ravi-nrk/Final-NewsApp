package com.pyar.newsapp;

/**
 * Created by RAVI on 30-Jun-17.
 */

public class NewsItem {
    private String author,title,description,url,urlToImage,publishedAt;

   public  NewsItem(String Author,String Title,String Description,String Url,String UrlToImage,String PublishedAt)
    {
        this.author=Author;
        this.title=Title;
        this.description=Description;
        this.url=Url;
        this.urlToImage=UrlToImage;
        this.publishedAt= PublishedAt;
    }
    public NewsItem(){

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}

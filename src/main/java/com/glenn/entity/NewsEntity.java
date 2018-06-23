package com.glenn.entity;

import javax.persistence.*;

@Entity
@Table(name = "news", schema = "netease_news", catalog = "")
@IdClass(NewsEntityPK.class)
public class NewsEntity {
    private int ds;
    private String publicTime;
    private String url;
    private String title;
    private String content;
    private String category;
    private String imgs;

    @Basic
    @Column(name = "ds")
    public int getDs() {
        return ds;
    }

    public void setDs(int ds) {
        this.ds = ds;
    }

    @Id
    @Column(name = "public_time")
    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    @Id
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "imgs")
    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsEntity that = (NewsEntity) o;

        if (ds != that.ds) return false;
        if (publicTime != null ? !publicTime.equals(that.publicTime) : that.publicTime != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (imgs != null ? !imgs.equals(that.imgs) : that.imgs != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ds;
        result = 31 * result + (publicTime != null ? publicTime.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (imgs != null ? imgs.hashCode() : 0);
        return result;
    }

    public boolean isCorrect() {
        boolean isGood = false;
        if (this.getContent() != null && !this.getContent().isEmpty() &&
                this.getUrl() != null && !this.getUrl().isEmpty() &&
                this.getCategory() != null && !this.getCategory().isEmpty() &&
                this.getTitle() != null && !this.getTitle().isEmpty() &&
                this.getPublicTime() != null && !this.getPublicTime().isEmpty()) {
            isGood = true;
        }
        return isGood;
    }
}

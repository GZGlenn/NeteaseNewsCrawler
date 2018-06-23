package com.glenn.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class NewsEntityPK implements Serializable {
    private String publicTime;
    private String url;

    @Column(name = "public_time")
    @Id
    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    @Column(name = "url")
    @Id
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsEntityPK that = (NewsEntityPK) o;

        if (publicTime != null ? !publicTime.equals(that.publicTime) : that.publicTime != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publicTime != null ? publicTime.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}

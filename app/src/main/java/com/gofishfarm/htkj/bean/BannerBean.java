package com.gofishfarm.htkj.bean;

/**
 * @author：MrHu
 * @Date ：2019-01-06
 * @Describtion ：
 */
public class BannerBean {
    private String name;
    private String link;
    private String thumbnail;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link == null ? "" : link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail == null ? "" : thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

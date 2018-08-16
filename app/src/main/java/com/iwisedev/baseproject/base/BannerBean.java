package com.iwisedev.baseproject.base;

/**
 * Created by Ledev2 on 2017-12-25.
 */

public class BannerBean {

    /**
     * banner_image_id : 254
     * title : 客厅 1
     * banner_id : 7
     * image : catalog/demo/slider/slide1.jpg
     */

    private String banner_image_id;
    private String title;
    private String banner_id;
    private String image;
    private String banner_article_id;

    public String getBanner_article_id() {
        return banner_article_id;
    }

    public void setBanner_article_id(String banner_article_id) {
        this.banner_article_id = banner_article_id;
    }

    public String getBanner_image_id() {
        return banner_image_id;
    }

    public void setBanner_image_id(String banner_image_id) {
        this.banner_image_id = banner_image_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

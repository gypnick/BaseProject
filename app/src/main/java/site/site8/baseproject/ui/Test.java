package site.site8.baseproject.ui;

/**
 * Created by Ledev2 on 2017-10-09.
 */

public class Test {

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", content_id='" + content_id + '\'' +
                ", is_stick=" + is_stick +
                ", link_url='" + link_url + '\'' +
                '}';
    }

    /**
     * id : 55
     * cover : http://image.wufazhuce.com/FsBqEyZgLTdMMN4yms2TG9AUxDlt?bid=55
     * title : 爱情交织着谎言，交织着怨恨，甚至交织着谋杀。
     * category : 11
     * content_id : 51
     * is_stick : false
     * serial_list : []
     * link_url :
     */

    private int id;
    private String cover;
    private String title;
    private int category;
    private String content_id;
    private boolean is_stick;
    private String link_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public boolean isIs_stick() {
        return is_stick;
    }

    public void setIs_stick(boolean is_stick) {
        this.is_stick = is_stick;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

}

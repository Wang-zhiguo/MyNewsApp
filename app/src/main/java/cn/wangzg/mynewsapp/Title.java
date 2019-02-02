package cn.wangzg.mynewsapp;

/**
 * Created by len_titude on 2017/5/4.
 */

public class Title {
    //标题
    private String title;
    //来源
    private String src;
    //图片url
    private String imageUrl;
    //新闻详情url
    private String uri;

    public Title(String title, String src, String imageUrl, String uri){
        this.title = title;
        this.imageUrl = imageUrl;
        this.src = src;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSrc() {
        return src;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "["+title+","+src+"]\n";
    }
}

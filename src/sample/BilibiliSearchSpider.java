package sample;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class BilibiliSearchSpider {
    String url = "https://api.bilibili.com/x/web-interface/search/type?context=";
    Connection.Response connec;
    Gson gson;
    class Result {
        @Setter @Getter private long id;
        @Setter @Getter private String author;
        @Setter @Getter private long mid;
        @Setter @Getter private String arcurl;
        @Setter @Getter private String bvid;
        @Setter @Getter private String title;
        @Setter @Getter private String description;
        @Setter @Getter private String pic;
        @Setter @Getter private long play;
        @Setter @Getter private long video_review;
        @Setter @Getter private long favorites;
        @Setter @Getter private String tag;
        @Setter @Getter private long pubdate;
        @Setter @Getter private String duration;
    }
    class Data{
        @Setter @Getter
        private int page;
        @Setter @Getter
        private int numResults;
        @Setter @Getter
        private int numPages;
        @Setter @Getter
        private ArrayList<Result> result;
    }
    public class Ret{
        @Getter @Setter private Data data;
    }

    @Getter public ArrayList<Result> res;
    @Getter private Data data;
    @Getter private String keyword;
    int current_page = 1;

    public BilibiliSearchSpider(String keyword) throws IOException {
        this.keyword = keyword;
        setupConnection(urlBuilder(1));
    }

    public void setupConnection(String urln) throws IOException {
        connec = Jsoup.connect(urln).ignoreContentType(true).execute();
        String json = connec.body();
        //System.out.println(json);
        gson = new Gson();
        data = gson.fromJson(json,Ret.class).data;
        res = data.getResult();
        processTitles();
    }

    private void processTitles(){
        for(Result r:data.result){
            r.title=r.title.replace("<em class=\"keyword\">","").replace("</em>","");
        }
    }

    public String urlBuilder(int page) throws UnsupportedEncodingException {
        return url+"&search_type=video"+"&page="+page+"&keyword="+ URLEncoder.encode(keyword,"UTF-8");
    }

    public void nextPage() throws IOException {
        setupConnection(urlBuilder(++current_page));
    }

}

package sample;

import javafx.scene.image.Image;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spider {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:93.0) Gecko/20100101 Firefox/93.0";
    private static Pattern pat;
    private static Pattern upIDpat;
    private class VideoInfo {
        public Image thumb;
        public String title;
        public String videoID;
        public String videoLength;
        public String playCounts;
        public String comments;
        public String uploadDate;
        public String uploader;
        public String upID;
    }

    private Connection connection;
    private Document mainDoc;
    public Spider() throws IOException {
        connection = Jsoup.connect("https://search.bilibili.com/all?keyword=%E9%80%86%E9%A3%8E%E7%AC%91&from_source=webtop_search").userAgent(USER_AGENT);
        mainDoc = connection.get();
        pat = Pattern.compile("(B.+(?=\\?))");
        upIDpat = Pattern.compile("com/([0-9]+)\\?from=");
    }

    public ArrayList<VideoInfo> getVideos(){
        ArrayList<VideoInfo> vi = new ArrayList<>();
        Elements els = mainDoc.select(".video-item .matrix");
        for(Element ele : els){
            vi.add(singleVideoInfoParser(ele));
        }
        return vi;
    }

    public VideoInfo singleVideoInfoParser(Element videoMatrix){
        VideoInfo vin = new VideoInfo();
        vin.videoID = videoIDParser(videoMatrix.select(".img-anchor").first().attr("href"));
        vin.thumb = new Image("https:"+videoMatrix.select("lazy-img").first().attr("src"));
        vin.title = videoMatrix.select(".img-anchor").first().attr("title");
        vin.videoLength = videoMatrix.select(".so-imgTag_rb").first().text();
        vin.playCounts = videoMatrix.select(".watch-num").first().text();
        vin.comments = videoMatrix.select("span[title=\"弹幕\"").first().text();
        vin.uploader = videoMatrix.select(".up-name").first().text();
        vin.upID = videoIDParser(videoMatrix.select(".up-name").first().attr("href"));
        vin.uploadDate = videoMatrix.select("span[title=\"上传时间\"").first().text();
        return vin;
    }

    private String videoIDParser(String url){
        Matcher mat = pat.matcher(url);
        if(mat.find())return mat.group(0);
        else{
            System.err.println("Unable to parse video BV id from url");
            return null;
        }
    }

    private String upIDParser(String url){
        Matcher mat = upIDpat.matcher(url);
        if(mat.find()){
            return mat.group(0);
        }else{
            System.out.println("Unable to parse UP ID from url");
            return null;
        }
    }
}

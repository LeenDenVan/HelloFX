package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class VideoListViewCell extends ListCell<BilibiliSearchSpider.Result> {
    @FXML
    private AnchorPane anchor_down;

    @FXML
    private AnchorPane anchor_up;

    @FXML
    private Label author_name;

    @FXML
    private Label comments;

    @FXML
    private Label fav;

    @FXML
    private ImageView pic;

    @FXML
    private Label play_count;

    @FXML
    private SplitPane split_pane;

    @FXML
    private Label title;

    @FXML
    private Label video_date;

    @FXML
    private Label video_length;

    private FXMLLoader loader;

    @Override
    protected void updateItem(BilibiliSearchSpider.Result result, boolean empty){
        super.updateItem(result,empty);

        if(empty || result == null){
            setText(null);
            setGraphic(null);
        }else{
            if(loader == null){
                loader = new FXMLLoader(getClass().getResource("/sample/ListCell.fxml"));
                loader.setController(this);

                try{
                    loader.load();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            title.setText(result.getTitle());
            author_name.setText("up主:"+result.getAuthor());
            //video_length.setText(result.getDuration()!=null?:result.getDuration());
            play_count.setText("播放量:"+result.getPlay()+"");
            comments.setText("弹幕:"+result.getVideo_review()+"");
            fav.setText("收藏:"+result.getFavorites());
            pic.setImage(new Image(result.getPic()));

            setText(null);
            setGraphic(split_pane);
        }
    }

}

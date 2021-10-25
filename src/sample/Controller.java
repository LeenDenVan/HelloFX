package sample;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML @Getter
    private Button next_page;

    @FXML @Getter
    private Button search_button;

    @FXML @Getter
    private TextField search_field;

    @FXML @Getter
    private JFXListView<BilibiliSearchSpider.Result> video_list;

    private BilibiliSearchSpider bss;

    private ObservableList<BilibiliSearchSpider.Result> ol;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ol = FXCollections.observableArrayList();
        video_list.setCellFactory(new Callback<ListView<BilibiliSearchSpider.Result>, ListCell<BilibiliSearchSpider.Result>>() {
            @Override
            public ListCell<BilibiliSearchSpider.Result> call(ListView<BilibiliSearchSpider.Result> resultListView) {
                return new VideoListViewCell();
            }
        });
        //video_list.setItems(ol);
        /*
        if(search_field.getText()!=null){
            bss = new BilibiliSearchSpider(search_field.getText());
            //ol.addAll(bss.res);
            for(BilibiliSearchSpider.Result r : bss){
                addItemToVideoList(r);
            }
        }*/
    }

    public void onNextPageButtonClicked(ActionEvent actionEvent) throws IOException {
        ol.clear();
        bss.nextPage();
        ol.addAll(bss.res);
        video_list.setItems(ol);
    }

    public void onSearchButtonClicked(ActionEvent actionEvent) throws IOException {
        String keyword = search_field.getText();
        if(keyword != null){
            ol.clear();
            bss = new BilibiliSearchSpider(keyword);
            ol.addAll(bss.res);
            video_list.setItems(ol);
        }
    }
}

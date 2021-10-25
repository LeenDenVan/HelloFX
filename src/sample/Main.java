package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    private final ChoiceBox<Pair<String, String>> assetClass = new ChoiceBox<>();
    private static FileInputStream inp;
    private static Font hymmu;
    @Override
    public void start(Stage primaryStage) throws Exception{
        boolean fullScreen = false;
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //for(String item : Font.getFamilies())System.out.println(item);
        Parent p = new StackPane(new Text("Hola Mondo"));
        Image imager = new Image(new FileInputStream("resources/images/kf.jpg"),150,0, true,false);
        Parent imageP = new Pane(new ImageView(imager));
        ListView listView = new ListView();
        BilibiliSearchSpider spider = new BilibiliSearchSpider("逆风笑");
        ArrayList<BilibiliSearchSpider.Result> aress = spider.res;
        for(BilibiliSearchSpider.Result res : aress){
            listView.getItems().add(res.getTitle());
        }
        HBox hb = new HBox(listView);
        Scene scene = new Scene(hb);
        //scene.setCursor(Cursor.WAIT);
        //vb.getStylesheets().add("style.css");
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(400);
        primaryStage.setOnCloseRequest((event) ->{
            System.out.println("Stage Closed");
        });
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,  (event) -> {
            System.out.println("Key pressed: " + event.toString());
            switch(event.getCode().getCode()) {
                case java.awt.event.KeyEvent.VK_F11: {
                    primaryStage.setFullScreen(!primaryStage.isFullScreen());
                    break;
                }
                default:  {
                    System.out.println("Unrecognized key");
                }
            }
        });
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        inp = new FileInputStream("resources/images/img.png");
        FileInputStream hymmnosIn = new FileInputStream("resources/hymmnos.ttf");
        hymmu = Font.loadFont(hymmnosIn,26.0);
        launch(args);
        inp.close();
    }
}

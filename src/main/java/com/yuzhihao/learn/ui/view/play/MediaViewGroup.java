package com.yuzhihao.learn.ui.view.play;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * 播放器组
 *
 * @author yuzhihao
 */
@Log4j2
public class MediaViewGroup extends View {

    //    private final List<MediaView> views;
    private List<PlayerView> views;
    private int sqrt;

    public MediaViewGroup(List<String> urls) {
        this.views = urls.stream().map(e->new PlayerView()).toList();
        this.sqrt = (int) Math.ceil(Math.sqrt(views.size()));

        this.addEventHandler(PlayerView.MediaEvent.CLOSE, event -> {
            this.stop();
        });


        setCenter(show1());
    }

//    public Node show() {
//
//        BorderPane root = new BorderPane();
//        root.setStyle("-fx-background-color: black;");
//
//
//
//        GridPane playerGrid = new GridPane();
//        playerGrid.getStyleClass().add("player-grid");
//
//        ColumnConstraints fillColumn = new ColumnConstraints();
//        fillColumn.setHgrow(Priority.ALWAYS);
//        fillColumn.setFillWidth(true);
//
//        RowConstraints fillRow = new RowConstraints();
//        fillRow.setVgrow(Priority.ALWAYS);
//        fillRow.setPercentHeight(25);
//        fillRow.setFillHeight(true);
//
//
//        playerGrid.getChildren().addAll(playerViews);
//
//        SplitPane splitPane = new SplitPane();
//        splitPane.setStyle("-fx-background-color: black;");
//        splitPane.setOrientation(Orientation.HORIZONTAL);
//
//        splitPane.getItems().addAll(grid, playerGrid);
//        splitPane.setDividerPosition(0, 0.395f);
//
//
//    }
    public Node show1() {
        TilePane tilePane = new TilePane();

        tilePane.setTileAlignment(Pos.CENTER);
        tilePane.setPrefColumns(sqrt);
        tilePane.setPrefRows(sqrt);
        tilePane.setHgap(10);
        tilePane.setVgap(10);

        tilePane.setPadding(new Insets(10));

//        tilePane.setPrefTileWidth(560);
//        tilePane.setPrefTileHeight(360);



        tilePane.widthProperty().addListener((observable, oldValue, newValue) -> {
            int n = ((sqrt + 1) * 10 / sqrt);
            double value = tilePane.getWidth() / sqrt - n;
            tilePane.setPrefTileWidth(value);

//            this.views.forEach(e->{
//                e.setPrefWidth(newValue.doubleValue() / sqrt - views.size()*30);
//                System.out.println("MediaViewGroup:widthProperty"+newValue.doubleValue());
//            });
            log.info("MediaViewGroup w oldValue :{},newValue:{},number:{}", oldValue, newValue, tilePane.getPrefColumns());
        });

        tilePane.heightProperty().addListener((observable, oldValue, newValue) -> {

//            if (newValue.intValue() < 720) {
            if(tilePane.getHeight() < tilePane.getWidth()){
                int n = (sqrt + 1) * 10 / sqrt;
                double value = tilePane.getHeight() / sqrt - n;
                tilePane.setPrefTileHeight(value);
            }

//            }

//            this.views.forEach(e->{
//                e.setPrefHeight(newValue.doubleValue() / sqrt - views.size()*30);
//                System.out.println("MediaViewGroup:heightProperty"+newValue.doubleValue());
//            });
            log.info("MediaViewGroup h oldValue :{},newValue:{},number:{}", oldValue, newValue, tilePane.getPrefRows());
        });

//        tilePane.prefWidthProperty().bind(widthed);

//        ReadOnlyDoubleProperty heighted = this.heightProperty();

//        tilePane.prefHeightProperty().bind(heighted);
//        tilePane.setPrefWidth(600);
//        tilePane.setPrefHeight(600);


        tilePane.getChildren().addAll(this.views);

        return tilePane;
    }


    public final void stop() {

    }

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            System.out.println(i + "," + Math.ceil(Math.abs(Math.sqrt(i))));
        }
    }

}

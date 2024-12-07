package com.yuzhihao.learn.ui.view;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.mvc.View;
import com.yuzhihao.learn.ui.bar.VlcjDefaultHeaderBar;
import com.yuzhihao.learn.ui.view.play.PlayerView;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 媒体播放页面
 *
 * @author yuzhihao
 * @since 2024-11-24 13:23:57
 *
 */
@Log4j2
public class MediaPlayerView extends View {

    private final static List<String> URLS = new ArrayList<>();

    private final List<PlayerView> views = new ArrayList<>();

    private static int sqrt = 2;

    public MediaPlayerView() {
        addEventHandler(LifecycleEvent.HIDDEN, event -> {
            this.closeEvent();
        });
    }

    public synchronized static void updatePlayer(List<String> list){
        List<String> filter = list.stream().filter(StringUtils::hasLength).toList();
        URLS.clear();
        URLS.addAll(filter);
        sqrt = (int)Math.ceil(Math.sqrt(filter.size()));
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        new VlcjDefaultHeaderBar(appBar).show();
//        appBar.setVisible(false);
        center();

    }

    /**
     * 处理停止播放事件
     */
    private void closeEvent() {
        Node center = getCenter();
        if (Objects.nonNull(center)) {
            this.views.forEach(e->e.fireEvent(new PlayerView.MediaEvent(PlayerView.MediaEvent.CLOSE) ));
        }
    }

    public void center(){
        views.clear();
        views.addAll(URLS.stream().map(e->{
            PlayerView view = new PlayerView();
//            view.setStyle("-fx-border-color: #409EFF; -fx-border-width: 1; -fx-border-style: solid;");
            view.play(e);
            return view;
        }).toList());

        GridPane playerGrid = new GridPane();
        playerGrid.setHgap(5);
        playerGrid.setVgap(5);

//        playerGrid.getStyleClass().add("player-grid");

        ColumnConstraints fillColumn = new ColumnConstraints();
        fillColumn.setHgrow(Priority.ALWAYS);
        fillColumn.setFillWidth(true);

        RowConstraints fillRow = new RowConstraints();
        fillRow.setVgrow(Priority.ALWAYS);
        fillRow.setFillHeight(true);

        for (int row = 0; row < sqrt; row++) {
            playerGrid.getRowConstraints().add(fillRow);
        }

        for (int col = 0; col < sqrt; col++) {
            playerGrid.getColumnConstraints().add(fillColumn);
        }

        int i = 0;
        for (int row = 0; row < sqrt; row++) {
            for (int col = 0; col < sqrt; col++) {
                if(i < views.size()){
                    GridPane.setConstraints(views.get(i++), row, col);
                }
            }
        }

        playerGrid.getChildren().addAll(views);

        SplitPane splitPane = new SplitPane();
//        splitPane.setStyle("-fx-background-color: black;");
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setPadding(new Insets(5));
        splitPane.getItems().addAll( playerGrid);
        splitPane.setDividerPosition(0, 0.395f);

        setCenter(splitPane);
    }

}

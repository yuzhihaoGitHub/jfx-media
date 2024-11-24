package com.yuzhihao.learn.hello.bar;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class HomeView extends View {

    public HomeView() {
        FloatingActionButton fab = new FloatingActionButton();

        fab.showOn(this);
    }

    public HomeView(Node node) {
        super(node);
        FloatingActionButton fab = new FloatingActionButton();
        fab.showOn(this);
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> {
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/vlcj-logo.png"))));

            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            Label label = new Label("这是VLCJ!!!");
            VBox root = new VBox(20, imageView, label);
            root.setAlignment(Pos.CENTER);

            setCenter(root);

            System.out.println("nav icon");

        }));
        appBar.setTitleText("摄像头播放器");


        TextField search = new TextField();
        search.setPromptText("请输入摄像头名称");
        appBar.getActionItems().addAll(
                search,
                MaterialDesignIcon.SEARCH.button(e -> System.out.println("search")),
                MaterialDesignIcon.FAVORITE.button(e -> System.out.println("fav")));


        MenuItem settings = new MenuItem("Settings");
        settings.setStyle("-fx-font-size: 8px !important;");

        settings.setGraphic(MaterialDesignIcon.SETTINGS.graphic());

        appBar.getMenuItems().addAll(settings,new MenuItem("Settings"),new MenuItem("Settings"));


        setCenter(center());
    }

    private VBox center(){
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/openduke.png"))));

        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        Label label = new Label("Hello, Gluon Mobile!!!");
        VBox root = new VBox(20, imageView, label);
        root.setAlignment(Pos.CENTER);

        return root;
    }
}

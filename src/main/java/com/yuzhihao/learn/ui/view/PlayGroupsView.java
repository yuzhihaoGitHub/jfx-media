package com.yuzhihao.learn.ui.view;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.yuzhihao.learn.ui.bar.VlcjDefaultHeaderBar;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurface;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * 分组播放摄像头列表页面
 *
 * @author yuzhihao
 * @since 2024-11-24 13:23:57
 *
 */
@Log4j2
public class PlayGroupsView extends View {

    private final MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();

    private final EmbeddedMediaPlayer embeddedMediaPlayer;
    private final EmbeddedMediaPlayer embeddedMediaPlayer1;

    private final ImageView videoImageView;
    private final ImageView videoImageView1;

    public PlayGroupsView() {
        this.embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        this.embeddedMediaPlayer1 = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        this.embeddedMediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {

            @Override
            public void playing(MediaPlayer mediaPlayer) {
            }

            @Override
            public void paused(MediaPlayer mediaPlayer) {
            }

            @Override
            public void stopped(MediaPlayer mediaPlayer) {
            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
            }
        });

        this.videoImageView = new ImageView();
        this.videoImageView.setPreserveRatio(true);

        embeddedMediaPlayer.videoSurface().set(new ImageViewVideoSurface(this.videoImageView));

        this.videoImageView1 = new ImageView();
        this.videoImageView1.setPreserveRatio(true);

        embeddedMediaPlayer1.videoSurface().set(new ImageViewVideoSurface(this.videoImageView1));

    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        new VlcjDefaultHeaderBar(appBar).show();
        setCenter(show2());
    }

    private Node show2(){

        VBox root = new VBox();
        root.getChildren().addAll(show(),show1());

        ScrollPane pane = new ScrollPane(root);
        return pane;
    }

    private Node show1(){
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: black;");

        videoImageView1.fitWidthProperty().bind(AppManager.getInstance().getView().widthProperty());
        videoImageView1.fitHeightProperty().bind(AppManager.getInstance().getView().heightProperty());

        root.widthProperty().addListener((observableValue, oldValue, newValue) -> {
            // If you need to know about resizes
        });

        root.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            // If you need to know about resizes
        });

        root.setCenter(videoImageView1);

//        embeddedMediaPlayer.media().play("http://192.168.4.231:7080/34020000001320000001/1050002775.live.flv");
//        embeddedMediaPlayer.media().play("rtmp://192.168.4.231:7935/34020000001320000001/1050002775");
        embeddedMediaPlayer1.media().play("rtsp://admin:FJTech508@192.168.4.200:554/h264/ch1/main/av_stream");

        return root;
    }

    private Node show(){
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: black;");

        videoImageView.fitWidthProperty().bind(AppManager.getInstance().getView().widthProperty());
        videoImageView.fitHeightProperty().bind(AppManager.getInstance().getView().heightProperty());

        root.widthProperty().addListener((observableValue, oldValue, newValue) -> {
            // If you need to know about resizes
        });

        root.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            // If you need to know about resizes
        });

        root.setCenter(videoImageView);

//        embeddedMediaPlayer.media().play("http://192.168.4.231:7080/34020000001320000001/1050002775.live.flv");
//        embeddedMediaPlayer.media().play("rtmp://192.168.4.231:7935/34020000001320000001/1050002775");
        embeddedMediaPlayer.media().play("rtsp://admin:FJTech508@192.168.4.224:554/h264/ch1/main/av_stream");

        return root;
    }



    public final void stop() {
        embeddedMediaPlayer.controls().stop();
        embeddedMediaPlayer.release();
        mediaPlayerFactory.release();
    }




}

package com.yuzhihao.learn.ui.view.media;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.factory.discovery.strategy.NativeDiscoveryStrategy;
import uk.co.caprica.vlcj.factory.discovery.strategy.OsxNativeDiscoveryStrategy;
import uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurface;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * 单播放器
 *
 * @author yuzhihao
 */
@Log4j2
public class MediaView  extends BorderPane {

    private final String url;

//    private static final MediaPlayerFactory MEDIA_PLAYER_FACTORY = new MediaPlayerFactory(new NativeDiscovery(new MyMacOsWellKnownDirectoryProvider()));
    private static final MediaPlayerFactory MEDIA_PLAYER_FACTORY = new MediaPlayerFactory();
    private final ImageView videoImageView = new ImageView();

    private final EmbeddedMediaPlayer mediaPlayer;

    public MediaView(String url) {
        this.url = url;
        this.mediaPlayer = MEDIA_PLAYER_FACTORY.mediaPlayers().newEmbeddedMediaPlayer();

        this.mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                log.info("播放中：{}",url);
            }
            @Override
            public void stopped(MediaPlayer mediaPlayer) {
                log.info("播放停止：{}",url);
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                log.info("播放错误：{}",url);
            }
        });

        this.videoImageView.setPreserveRatio(false);

        mediaPlayer.videoSurface().set(new ImageViewVideoSurface(this.videoImageView));
        mediaPlayer.controls().setRepeat(true);
        show();
    }

    private void show() {
        setStyle("-fx-background-color: black;");



        videoImageView.setFitWidth(200);
        videoImageView.setFitHeight(200);

//        Label top = new Label("办公室");
//        top.setAlignment(Pos.CENTER);
//        setTop(top);
//        VBox vBox = new VBox();
//        vBox.setStyle("-fx-background-color: #4b1bd5;");
//        setCenter(vBox);

        videoImageView.fitWidthProperty().bind(widthProperty());
        videoImageView.fitHeightProperty().bind(heightProperty());

        HBox box = new HBox(videoImageView);

        setCenter(box);

        this.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                log.info("MediaView h oldValue :{},newValue:{}",oldValue,newValue);
//                videoImageView.setFitHeight(newValue.doubleValue());
            }
        });
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                log.info("MediaView w oldValue :{},newValue:{}",oldValue,newValue);
//                videoImageView.setFitWidth(newValue.doubleValue());
            }
        });


        mediaPlayer.media().play(url);

    }


    public final void stop() {
        mediaPlayer.controls().stop();

    }

    public final void setwh(double w,double h) {
        videoImageView.setFitHeight(w);
        videoImageView.setFitWidth(h);
    }


}

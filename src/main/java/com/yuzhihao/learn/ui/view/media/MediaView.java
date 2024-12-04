package com.yuzhihao.learn.ui.view.media;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventType;
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

import uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurface;
import uk.co.caprica.vlcj.javafx.view.ResizableImageView;
import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.media.TrackType;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.fullscreen.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.fullscreen.libvlc.LibVlcNativeFullScreenStrategy;

/**
 * 单播放器
 *
 * @author yuzhihao
 */
@Log4j2
public class MediaView  extends BorderPane {



    private final String url;

//    private static final MediaPlayerFactory MEDIA_PLAYER_FACTORY = new MediaPlayerFactory(new NativeDiscovery(new MyMacOsWellKnownDirectoryProvider()));
    public static final MediaPlayerFactory MEDIA_PLAYER_FACTORY = new MediaPlayerFactory();
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

        this.mediaPlayer.events().addMediaPlayerEventListener(new MediaViewPlayerEventListener());

        this.videoImageView.setPreserveRatio(false);


        mediaPlayer.videoSurface().set(new ImageViewVideoSurface(this.videoImageView));
        mediaPlayer.controls().setRepeat(true);


        mediaPlayer.videoSurface().attachVideoSurface();
        show();
    }

    private void show() {
        setStyle("-fx-background-color: black;");


//        videoImageView.setFitWidth(200);
//        videoImageView.setFitHeight(200);

//        Label top = new Label("办公室");
//        top.setAlignment(Pos.CENTER);
//        setTop(top);
//        VBox vBox = new VBox();
//        vBox.setStyle("-fx-background-color: #4b1bd5;");
//        setCenter(vBox);
        ResizableImageView resizableImageView = new ResizableImageView(this.videoImageView);
//        videoImageView.fitWidthProperty().bind(widthProperty());
//        videoImageView.fitHeightProperty().bind(heightProperty());

//        HBox box = new HBox(resizableImageView);

        setCenter(resizableImageView);

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



    public static class MediaEvent extends Event{

        public static final EventType<MediaEvent> CLOSE = new EventType<>(Event.ANY, "CLOSE");

        public MediaEvent(EventType<? extends Event> eventType) {
            super(eventType);
        }
    }

    @Log4j2
    public static class MediaViewPlayerEventListener implements MediaPlayerEventListener {

        @Override
        public void mediaChanged(MediaPlayer mediaPlayer, MediaRef media) {
            log.info("mediaChanged");
        }

        @Override
        public void opening(MediaPlayer mediaPlayer) {
            log.info("opening");
        }

        @Override
        public void buffering(MediaPlayer mediaPlayer, float newCache) {
            log.info("buffering");
        }

        @Override
        public void playing(MediaPlayer mediaPlayer) {
            log.info("playing");
        }

        @Override
        public void paused(MediaPlayer mediaPlayer) {
            log.info("paused");
        }

        @Override
        public void stopped(MediaPlayer mediaPlayer) {
            log.info("stopped");
        }

        @Override
        public void forward(MediaPlayer mediaPlayer) {
            log.info("forward");
        }

        @Override
        public void backward(MediaPlayer mediaPlayer) {
            log.info("backward");
        }

        @Override
        public void finished(MediaPlayer mediaPlayer) {
            log.info("finished");
        }

        @Override
        public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
            log.info("timeChanged");
        }

        @Override
        public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
            log.info("positionChanged");
        }

        @Override
        public void seekableChanged(MediaPlayer mediaPlayer, int newSeekable) {
            log.info("seekableChanged");
        }

        @Override
        public void pausableChanged(MediaPlayer mediaPlayer, int newPausable) {
            log.info("pausableChanged");
        }

        @Override
        public void titleChanged(MediaPlayer mediaPlayer, int newTitle) {
            log.info("titleChanged");
        }

        @Override
        public void snapshotTaken(MediaPlayer mediaPlayer, String filename) {
            log.info("snapshotTaken");
        }

        @Override
        public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
            log.info("lengthChanged");
        }

        @Override
        public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
            log.info("videoOutput");
        }

        @Override
        public void scrambledChanged(MediaPlayer mediaPlayer, int newScrambled) {
            log.info("scrambledChanged");
        }

        @Override
        public void elementaryStreamAdded(MediaPlayer mediaPlayer, TrackType type, int id) {
            log.info("elementaryStreamAdded");
        }

        @Override
        public void elementaryStreamDeleted(MediaPlayer mediaPlayer, TrackType type, int id) {
            log.info("elementaryStreamDeleted");
        }

        @Override
        public void elementaryStreamSelected(MediaPlayer mediaPlayer, TrackType type, int id) {
            log.info("elementaryStreamSelected");
        }

        @Override
        public void corked(MediaPlayer mediaPlayer, boolean corked) {
            log.info("corked");
        }

        @Override
        public void muted(MediaPlayer mediaPlayer, boolean muted) {
            log.info("muted");
        }

        @Override
        public void volumeChanged(MediaPlayer mediaPlayer, float volume) {
            log.info("volumeChanged");
        }

        @Override
        public void audioDeviceChanged(MediaPlayer mediaPlayer, String audioDevice) {
            log.info("audioDeviceChanged");
        }

        @Override
        public void chapterChanged(MediaPlayer mediaPlayer, int newChapter) {
            log.info("chapterChanged");
        }

        @Override
        public void error(MediaPlayer mediaPlayer) {
            log.info("error");
        }

        @Override
        public void mediaPlayerReady(MediaPlayer mediaPlayer) {
            log.info("mediaPlayerReady");
        }
    }

}

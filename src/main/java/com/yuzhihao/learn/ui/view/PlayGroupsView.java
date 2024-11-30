package com.yuzhihao.learn.ui.view;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.yuzhihao.learn.ui.bar.VlcjDefaultHeaderBar;
import com.yuzhihao.learn.ui.view.media.MediaViewGroup;
import javafx.scene.Node;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组播放摄像头列表页面
 *
 * @author yuzhihao
 * @since 2024-11-24 13:23:57
 */
@Log4j2
public class PlayGroupsView extends View {

    public PlayGroupsView() {
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        new VlcjDefaultHeaderBar(appBar).show();
        setCenter(center());
    }

    private Node center() {
        List<String> urls = new ArrayList<>();
//        urls.add("rtsp://admin:FJTech508@192.168.4.200:554/ch1/main/av_stream");
//        urls.add("rtsp://admin:FJTech508@192.168.4.200:554/ch1/main/av_stream");
//        urls.add("rtsp://admin:FJTech508@192.168.4.200:554/ch1/main/av_stream");
        urls.add("rtsp://admin:FJTech508@192.168.4.200:554/ch1/main/av_stream");
        return new MediaViewGroup(urls);
    }


}

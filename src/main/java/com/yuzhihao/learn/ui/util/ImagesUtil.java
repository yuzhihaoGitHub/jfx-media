package com.yuzhihao.learn.ui.util;

import javafx.scene.image.Image;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 图片信息
 * 
 * @author yuzhihao 
 */
@Component
public class ImagesUtil {

    public final static Image GIF_PLAYER = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/gif/player.gif")));
    public  final static Image VOLUME_MIN_18 = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/buttons/volume_min_18dp.png")));
    public final static Image PAUSE_IMAGE = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/buttons/play_24dp.png")));
    public final static Image PLAY_IMAGE = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/buttons/pause_24dp.png")));
    public final static Image VOLUME_MAX_18 = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/buttons/volume_max_18dp.png")));
    public final static Image BACK_BJ2 = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/back/bj2.png"))); // 替换为你的图标路径
    public final static Image CAMERA_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/camera.png"))); // 替换为你的图标路径
    public final static Image MENU_SY_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/menu/sy.png"))); // 替换为你的图标路径
    public final static Image MENU_SXTLB_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/menu/sxtlb.png"))); // 替换为你的图标路径
    public final static Image MENU_LPZLB_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/menu/lpzlb.png"))); // 替换为你的图标路径
    public final static Image MENU_DBLB_1_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/menu/dblb1.png"))); // 替换为你的图标路径
    public final static Image IMAGE_404 = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/404.jpeg"))); // 替换为你的图标路径
    public final static Image RIGHT_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/right.png"))); // 替换为你的图标路径
    public final  static Image LEFT_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/left.png"))); // 替换为你的图标路径
    public final static Image FILE_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/group/file.png")));
    public final static Image MEDIA_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/group/media.png")));
    public final static Image NETWORK_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/group/network.png")));
    public final static Image SEARCH_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/group/search.png")));
    public final static Image NOTDATA_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/icons/notdata.png")));
    public final static Image OPENDUKE_ICON = new Image(Objects.requireNonNull(ImagesUtil.class.getResourceAsStream("/images/openduke.png")));

}

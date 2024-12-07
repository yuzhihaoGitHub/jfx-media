package com.yuzhihao.learn.ui.view;

import com.github.pagehelper.PageInfo;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.yuzhihao.learn.config.SpringUtils;
import com.yuzhihao.learn.config.ThreadPoolEnum;
import com.yuzhihao.learn.h2.common.SysDictConstant;
import com.yuzhihao.learn.h2.entity.SysDevice;
import com.yuzhihao.learn.h2.service.ISysDeviceService;
import com.yuzhihao.learn.ui.bar.VlcjDefaultHeaderBar;
import com.yuzhihao.learn.ui.util.ImagesUtil;
import com.yuzhihao.learn.ui.view.play.PlayerView;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 分组播放摄像头列表页面
 *
 * @author yuzhihao
 * @since 2024-11-24 13:23:57
 */
@Log4j2
public class PlayGroupsView extends View {

    private final ImageView rightIcon = new ImageView(ImagesUtil.RIGHT_ICON); // 替换为你的图标路径
    private final ImageView leftIcon = new ImageView(ImagesUtil.LEFT_ICON); // 替换为你的图标路径
    private final ImageView searchIcon = new ImageView(ImagesUtil.SEARCH_ICON);
    private final ImageView notdataIcon = new ImageView(ImagesUtil.NOTDATA_ICON);
    private final ImageView opendukeIcon = new ImageView(ImagesUtil.OPENDUKE_ICON);

    private int index = 1;
    private long total = 0;

    private final TextField searchField = new TextField();

    // 创建左侧菜单
    private final ListView<DeviceItem> menuList = new ListView<>();

    private final AtomicBoolean loadNumber = new AtomicBoolean(true);

    private String url;


    public PlayGroupsView() {
        rightIcon.setFitWidth(20); // 设置图标宽度
        rightIcon.setFitHeight(20); // 设置图标高度
        rightIcon.setPreserveRatio(true);

        leftIcon.setFitWidth(20); // 设置图标宽度
        leftIcon.setFitHeight(20); // 设置图标高度
        leftIcon.setPreserveRatio(true);

        rightIcon.setVisible(false);

        //谈入
        rightIcon.setOnMouseClicked(event -> showLeft());
        //谈出
        leftIcon.setOnMouseClicked(event -> hideLeft());

        //针对在播放视频时箭头ICON是否显示
        setOnMouseEntered(event -> {
            if(!getLeft().isVisible()){
                rightIcon.setVisible(true);
                ThreadPoolEnum.定时任务处理线程池.schedule(() -> Platform.runLater(() -> {
                    rightIcon.setVisible(false);
                }), 5, TimeUnit.SECONDS);
            }
        });
    }

    private void hideLeft() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), getLeft());
        fadeOut.setFromValue(1.0); // 从完全可见
        fadeOut.setToValue(0.0); // 到完全不可见
        fadeOut.setOnFinished(e -> {
            getLeft().setManaged(false);
            getLeft().setVisible(false); // 动画结束后设置为不可见
            showIcon();
        });
        fadeOut.play(); // 播放淡出动画
    }

    private void showLeft() {
        getLeft().setManaged(true);
        getLeft().setVisible(true);
        showIcon();
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), getLeft());
        fadeIn.setFromValue(0.0); // 从完全不可见
        fadeIn.setToValue(1.0); // 到完全可见
        fadeIn.play(); // 播放淡入动画
    }

    private void showIcon() {
        if(Objects.nonNull(getLeft())){
            rightIcon.setVisible(!getLeft().isVisible());
            leftIcon.setVisible(getLeft().isVisible());
        }
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        new VlcjDefaultHeaderBar(appBar).show();
        left(null);
        center();

        Pane pane = new Pane(leftIcon, rightIcon);
        pane.setPrefSize(20, 20);
        pane.setMaxSize(20, 20);

        pane.translateYProperty().bind(heightProperty().divide(4));

        AppManager.getInstance().getGlassPane().getChildren().add(pane);

        addEventHandler(LifecycleEvent.HIDDEN, event -> {
            this.closeEvent();
            AppManager.getInstance().getGlassPane().getChildren().remove(pane);
        });

        getLeft().setOnMouseEntered(event -> showIcon());

    }

    private void initSearchField() {
        searchField.setPromptText("搜索媒体...");
    }

    private HBox initHeader() {
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);
        Button button = new Button("", searchIcon);
        button.setStyle("-fx-background-color: transparent;-fx-background-radius: 50;-fx-border-radius: 50;");
        button.setOnAction(e -> {
            this.index = 1;
            this.menuList.getItems().clear();
            List<DeviceItem> items = queryItems();
            if (items.isEmpty()) {
                noDataLeft();
            } else {
                left(items);
            }
        });

        HBox hBox = new HBox(searchField, button);
        HBox.setMargin(button, new Insets(4, 0, 0, 0));

        return hBox;
    }

    private void iniListView(List<DeviceItem> items) {
        //设置滚动条css
        menuList.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/group/styles.css")).toExternalForm());
        menuList.setPadding(new Insets(10));
        // 添加菜单项（图标和标题）
        menuList.setCellFactory(lv -> new GroupMenuItemCell());

        //初始化数据
        menuList.getItems().addAll(Objects.isNull(items) ? queryItems() : items);

        // 监听 ListView 的滚动事件
        menuList.setOnScroll(event -> {
            if (event.getDeltaY() < 0) { // 向下滚动
                if (menuList.getItems().size() < total) {
                    if (loadNumber.getAndSet(false)) {
                        menuList.getItems().addAll(queryItems()); // 加载更多数据
                        new Thread(() -> {
                            try {
                                TimeUnit.SECONDS.sleep(1);
                                loadNumber.compareAndSet(false, true);
                            } catch (Exception e) {
                                log.error("Scroll Error in loading data", e);
                            }
                        }).start();
                    }
                }
            }
        });

        // 监听菜单选择变化
        menuList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (StringUtils.hasLength(newValue.device.getProxyUrl())) {
                    url = newValue.device.getProxyUrl();
                    center();
                }
            }
        });
    }

    /**
     * 左边
     */
    private void left(List<DeviceItem> items) {
        initSearchField();

        iniListView(items);

        HBox hBox = initHeader();

        HBox.setHgrow(searchField, Priority.ALWAYS);

        Separator separator = new Separator();

        VBox vBox = new VBox(hBox, separator, menuList);
        VBox.setMargin(separator, new Insets(20, 0, 0, 0));
        VBox.setVgrow(menuList, Priority.ALWAYS);
        vBox.setStyle("-fx-border-color: rgb(121.3, 187.1, 255); -fx-border-width: 0 1 0 0; -fx-padding: 10;"); // 仅设置右边框
        vBox.setMaxWidth(256 + 12);
        vBox.setMinWidth(256 + 12);


        // 绑定图标的位置
        StackPane root = new StackPane(vBox);


        // 监听窗口大小变化
//        root.widthProperty().addListener((obs, oldVal, newVal) -> {
//            floatingIcon.layoutXProperty().bind(Bindings.divide((ObservableNumberValue)newVal, 2).subtract(floatingIcon.fitWidthProperty().divide(2)));
//        });

//        heightProperty().addListener((obs, oldVal, newVal) -> {
//            floatingIcon.setLayoutY(newVal.doubleValue()/3);
//        });

        setLeft(root);
    }

    /**
     * 无数据左边
     */
    private void noDataLeft() {
        initSearchField();

        HBox hBox = initHeader();

        HBox.setHgrow(searchField, Priority.ALWAYS);

        notdataIcon.setFitWidth(240);
        notdataIcon.setPreserveRatio(true);

        Label label = new Label("没有找到媒体呢！");
        VBox not = new VBox(20, notdataIcon, label);
        not.setMaxWidth(256 + 12);
        not.setMinWidth(256 + 12);
        not.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(hBox);
        vBox.setStyle("-fx-border-color: rgb(121.3, 187.1, 255); -fx-border-width: 0 1 0 0; -fx-padding: 10;"); // 仅设置右边框

        StackPane root = new StackPane(not, vBox);
        setLeft(root);
    }

    /**
     * 中间
     */
    private void center() {
        if (!StringUtils.hasLength(url)) {
            emptyDisplay();
            return;
        }

//        List<String> urls = new ArrayList<>();
//        urls.add(url);
//        urls.add("rtsp://admin:FJTech508@192.168.4.200:554/ch1/main/av_stream");
//        urls.add("/Users/yuzhihao/Downloads/fengjing.mp4");
        closeEvent();

        //隐藏
        hideLeft();
        //隐藏右边箭头
        ThreadPoolEnum.定时任务处理线程池.schedule(() -> Platform.runLater(() -> rightIcon.setVisible(false)), 5, TimeUnit.SECONDS);

        PlayerView playerView = new PlayerView();
        playerView.play(url);
        setCenter(playerView);
    }

    /**
     * 处理停止播放事件
     */
    private void closeEvent() {
        Node center = getCenter();
        if (Objects.nonNull(center)) {
            center.fireEvent(new PlayerView.MediaEvent(PlayerView.MediaEvent.CLOSE));
        }
    }

    /**
     * 未播放媒体显示
     */
    private void emptyDisplay() {
        opendukeIcon.setFitHeight(200);
        opendukeIcon.setPreserveRatio(true);

        Label label = new Label("选择一个媒体播放呗!!!");
        VBox root = new VBox(20, opendukeIcon, label);
        root.setAlignment(Pos.CENTER);

        closeEvent();
        setCenter(root);
    }

    /**
     * 获取数据
     *
     * @return
     */
    private List<DeviceItem> queryItems() {
        PageInfo<SysDevice> lists = SpringUtils.getBean(ISysDeviceService.class).listsOr(index++, 20, new SysDevice(searchField.getText(), searchField.getText()));
        total = lists.getTotal();
        return lists.getList().stream().map(this::item).toList();
    }

    private DeviceItem item(SysDevice device) {
        return switch (device.getMediaType()) {
            case SysDictConstant.File -> new DeviceItem(device, new ImageView(ImagesUtil.FILE_ICON));
            case SysDictConstant.WEB_URL -> new DeviceItem(device, new ImageView(ImagesUtil.NETWORK_ICON));
            default -> new DeviceItem(device, new ImageView(ImagesUtil.MEDIA_ICON));
        };
    }

    /**
     * 自定义 ListCell
     */
    private static class GroupMenuItemCell extends ListCell<DeviceItem> {

        @Override
        protected void updateItem(DeviceItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox hbox = new HBox(10); // 10 像素的间距
                ImageView icon = item.getIcon();
                icon.setFitWidth(20); // 设置图标宽度
                icon.setFitHeight(20); // 设置图标高度

                Label label = new Label(item.getName());
                label.setMaxWidth(170);
                label.setWrapText(true);

                hbox.getChildren().addAll(icon, label);
                setGraphic(hbox);
                setStyle("-fx-padding: 10 20 10 10;"); // 设置 ListCell 的内边距
            }
        }
    }

    @Data
    private static class DeviceItem {
        private Long id;
        private String name;
        private ImageView icon;
        private SysDevice device;

        public DeviceItem(SysDevice device, ImageView icon) {
            this.icon = icon;
            this.device = device;
            this.id = device.getId();
            this.name = device.getCustomName();
        }
    }

}

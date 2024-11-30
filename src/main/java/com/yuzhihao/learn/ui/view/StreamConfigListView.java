package com.yuzhihao.learn.ui.view;

import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Toast;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.yuzhihao.learn.config.SpringUtils;
import com.yuzhihao.learn.h2.entity.SysDict;
import com.yuzhihao.learn.h2.service.ISysDictService;
import com.yuzhihao.learn.ui.bar.VlcjDefaultHeaderBar;
import com.yuzhihao.learn.ui.dialog.StreamConfigDialog;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * 播放流配置列表页面
 *
 * @author yuzhihao
 * @since 2024-11-24 13:23:57
 */
@Log4j2
public class StreamConfigListView extends View {

    private final  TableView<SysDict> tableView = new TableView<>();

    {
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(900);

        TableColumn<SysDict, String> namec = new TableColumn<>("名称");
        TableColumn<SysDict, String> typec = new TableColumn<>("配置类型");
        TableColumn<SysDict, String> stringc = new TableColumn<>("规则字符串");
        namec.setCellValueFactory(new PropertyValueFactory<>("dictLabel"));
        typec.setCellValueFactory(new PropertyValueFactory<>("configType"));
        stringc.setCellValueFactory(new PropertyValueFactory<>("remark"));
        insertTableCheckBoxColumn();
        tableView.getColumns().addAll(namec, typec,stringc);

    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        new VlcjDefaultHeaderBar(appBar).show();
        setCenter(show());
    }

    private Node show() {
        return index();
    }

    public Node index() {
        VBox vBox = new VBox(header(), footer());
        vBox.setFillWidth(true);
        return vBox;
    }

    public Node header() {

        Label title = new Label("摄像头播放URL配置");
        title.setStyle("-fx-font-size: 16px");

        Node node = MaterialDesignIcon.HELP_OUTLINE.graphic();
        node.setStyle("-fx-font-size: 16px");
        HBox hBox = new HBox(title, node);
        hBox.setSpacing(10);

        Tooltip tooltip = new Tooltip("～ 播放器根据rtsp协议来实时播放摄像头视频内容。因为每个厂商的rtsp协议路径不同，需要单独配置，但都需要用到用户名，密码，IP等参数。因此我们此页面就是用来处理摄像头rtsp协议的URL的，其中${username}${password}${ip}代表在配置列表中的用户名密码和IP,会在更新摄像头和修改摄像头配置时根据不同的厂商修改rtsp协议URL。\n～ 规则URL示例：rtsp://${username}:${password}@${ip}:554/ch1/main/av_stream");
        Tooltip.install(hBox,tooltip);
        tooltip.setWrapText(true);
        tooltip.setPrefWidth(300);

        // 设置鼠标悬停事件来显示 Tooltip
        hBox.setOnMouseEntered(event -> tooltip.show(hBox, event.getScreenX(), event.getScreenY()));
        hBox.setOnMouseExited(event -> tooltip.hide()); // 鼠标移

        GridPane pane = new GridPane();
        pane.setVgap(20);
        pane.setHgap(20);
        pane.setMinHeight(Region.USE_PREF_SIZE);
        pane.add(hBox,0,0);
        pane.add(addButton(),1,0);
        pane.add(deleteButton(),2,0);
        pane.add(updateButton(),3,0);

        VBox vBox2 = new VBox(pane);
        vBox2.setMinHeight(60);
        VBox.setMargin(pane, new Insets(20));
        return vBox2;
    }


    public Node footer(){
        refresh();
        VBox vBox = new VBox(new BorderPane(tableView));
        vBox.setPadding(new Insets(10));

        return vBox;
    }


    private Button addButton(){
        Button add = new Button("新增");

        add.setOnAction(e->{
            new StreamConfigDialog().show().ifPresent(type-> {
                if (type.equals(ButtonType.OK)) {
                    refresh();
                }
            });
        });

        return add;
    }

    private Button deleteButton(){
        Button delete = new Button("删除");
        delete.setStyle("-fx-background-color: #E6A23C");

        delete.setOnAction(e->{
            List<SysDict> collect = this.tableView.getItems().stream().filter(SysDict::isSelected).toList();
            if(collect.isEmpty()){
                new Toast("请选择要删除的配置").show();
            }else{
                Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,"确认要删除配置吗？");
                alert.showAndWait().ifPresent(type->{
                    if (ButtonType.OK.equals(type)) {
                        try {
                            SpringUtils.getBean(ISysDictService.class).delete(collect);
                            refresh();
                        } catch (Exception ex) {
                            new Toast(ex.getMessage()).show();
                        }
                    }
                });
            }
        });
        return delete;
    }

    private Button updateButton(){
        Button delete = new Button("修改");
        delete.setStyle("-fx-background-color: #F56C6C");

        delete.setOnAction(e->{
            List<SysDict> collect = this.tableView.getItems().stream().filter(SysDict::isSelected).toList();
            if(collect.size() != 1){
                new Toast("请选择要修改的配置,只能选择一个").show();
            }else{
                new StreamConfigDialog(collect.get(0)).show().ifPresent(type->{
                    if (type.equals(ButtonType.OK)) {
                        refresh();
                    }
                });
            }
        });
        return delete;
    }

    public void insertTableCheckBoxColumn(){
        // 创建一个用于选择所有行的 CheckBox
        CheckBox selectAllCheckBox = new CheckBox();
        selectAllCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> tableView.getItems().forEach(e->e.setSelected(newValue)));

        // 创建一个用于显示每个行的 CheckBox 的列
        TableColumn<SysDict, Boolean> selectCol = new TableColumn<>("Select");
        selectCol.setSortable(false); // 不允许排序
        selectCol.setResizable(false); // 不允许调整大小
        selectCol.setPrefWidth(40); // 设置列宽
        selectCol.setGraphic(selectAllCheckBox); // 设置表头的图形为 CheckBox
        selectCol.setCellValueFactory(param -> param.getValue().selectedProperty());

        // 自定义单元格工厂以显示 CheckBox
        selectCol.setCellFactory(param -> new CheckBoxTableCell(tableView));
        tableView.getColumns().add(selectCol);

    }
    public void refresh(){
        List<SysDict> dicts = SpringUtils.getBean(ISysDictService.class).manufacturerList();
        ObservableList<SysDict> items = tableView.getItems();
        items.clear();
        items.addAll(dicts);
    }


    // 自定义的 CheckBox 单元格
    public static class CheckBoxTableCell extends TableCell<SysDict, Boolean> {
        private final CheckBox checkBox = new CheckBox();

        public CheckBoxTableCell(TableView<SysDict> tableView) {
            checkBox.setOnAction(event -> tableView.getItems().get(getIndex()).setSelected(checkBox.isSelected()));
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                checkBox.setSelected(item);
                setGraphic(checkBox);
            }
        }
    }

}

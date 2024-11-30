package com.yuzhihao.learn.ui.view;

import com.baomidou.mybatisplus.extension.activerecord.AbstractModel;
import com.github.pagehelper.PageInfo;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.control.Toast;
import com.gluonhq.charm.glisten.mvc.View;
import com.yuzhihao.learn.config.SpringUtils;
import com.yuzhihao.learn.h2.entity.SysDevice;
import com.yuzhihao.learn.h2.service.ISysDeviceService;
import com.yuzhihao.learn.ui.bar.VlcjDefaultHeaderBar;
import com.yuzhihao.learn.ui.dialog.SysDeviceDialog;
import com.yuzhihao.learn.ui.util.UiUtil;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 摄像头列表页面
 *
 * @author yuzhihao
 * @since 2024-11-24 13:23:57
 */
@Log4j2
public class CameraListView extends View {

    private final  Pagination pagination = new Pagination(10,0);
    private final  TableView<SysDevice> tableView = new TableView<>();

    private final TextField nameField = new TextField();
    private final TextField ipField = new TextField();

    private int pageIndex = 1;

    {
        nameField.setPromptText("查询名称");
        ipField.setPromptText("查询IP");

        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(900);

        TableColumn<SysDevice, String> idc = new TableColumn<>("ID");
        TableColumn<SysDevice, String> namec = new TableColumn<>("名称");
        TableColumn<SysDevice, String> companyc = new TableColumn<>("厂商");
        TableColumn<SysDevice, String> sipc = new TableColumn<>("SIP协议");
        TableColumn<SysDevice, String> networkc = new TableColumn<>("网络状态");
        TableColumn<SysDevice, String> statusc = new TableColumn<>("上报状态");
        TableColumn<SysDevice, String> ipc = new TableColumn<>("IP");
        TableColumn<SysDevice, String> proxyc = new TableColumn<>("播放代理地址");
        TableColumn<SysDevice, LocalDateTime> registerc = new TableColumn<>("注册时间");
        TableColumn<SysDevice, LocalDateTime> heartc = new TableColumn<>("心跳时间");
        TableColumn<SysDevice, LocalDateTime> createtc = new TableColumn<>("创建时间");
        TableColumn<SysDevice, LocalDateTime> updatetc = new TableColumn<>("更新时间");
        idc.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
        namec.setCellValueFactory(new PropertyValueFactory<>("customName"));
        companyc.setCellValueFactory(new PropertyValueFactory<>("company"));
        sipc.setCellValueFactory(new PropertyValueFactory<>("transport"));
        networkc.setCellValueFactory(new PropertyValueFactory<>("onLine"));
        statusc.setCellValueFactory(new PropertyValueFactory<>("onLogOut"));
        ipc.setCellValueFactory(new PropertyValueFactory<>("ip"));
        proxyc.setCellValueFactory(new PropertyValueFactory<>("proxyUrl"));
        registerc.setCellValueFactory(new PropertyValueFactory<>("registerTime"));
        heartc.setCellValueFactory(new PropertyValueFactory<>("keepaliveTime"));
        updatetc.setCellValueFactory(new PropertyValueFactory<>("updateTime"));
        createtc.setCellValueFactory(new PropertyValueFactory<>("createTime"));
        insertTableCheckBoxColumn();
        tableView.getColumns().addAll(idc,namec,companyc,sipc,networkc,statusc,ipc,proxyc,registerc,heartc,updatetc,createtc);
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
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10));
        gp.setHgap(10);
        gp.setVgap(10);

        Label nameLabel = UiUtil.label("名称");
        Label ipLabel = UiUtil.label("IP");

        gp.add(nameLabel,0,0);
        gp.add(nameField,1,0);
        gp.add(ipLabel,2,0);
        gp.add(ipField,3,0);


        gp.add(playButton(),4,0);
        gp.add(addButton(),5,0);
        gp.add(deleteButton(),6,0);
        gp.add(updateButton(),7,0);


        Button query = new Button("查询");
        query.setOnAction(e->this.refresh());
        Button rester = new Button("重置");
        rester.setOnAction(e->this.queryClear());
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(query,rester);

        HBox.setHgrow(buttonBar, Priority.ALWAYS);

        hBox.getChildren().addAll(gp,buttonBar);
        VBox.setVgrow(gp, Priority.ALWAYS);

        return hBox;
    }

    private Button playButton(){
        Button play = new Button("播放");

        play.setOnAction(e->{

        });

        return play;
    }

    private Button addButton(){
        Button add = new Button("新增");

        add.setOnAction(e->{
            new SysDeviceDialog().show().ifPresent(type-> {
                if(type.equals(ButtonType.OK)) {
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
            List<SysDevice> collect = this.tableView.getItems().stream().filter(SysDevice::isSelected).toList();
            if(collect.isEmpty()){
                new Toast("请选择要删除的设备").show();
            }else{
                Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,"确认要删除设备吗？");
                alert.showAndWait().ifPresent(type->{
                    if (ButtonType.OK.equals(type)) {
                        collect.forEach(AbstractModel::deleteById);
                        refresh();
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
            List<SysDevice> collect = this.tableView.getItems().stream().filter(SysDevice::isSelected).toList();
            if(collect.size() != 1){
                new Toast("请选择要修改的设备,只能选择一个").show();
            }else{
                new SysDeviceDialog(collect.get(0)).show().ifPresent(type-> {
                    if(type.equals(ButtonType.OK)) {
                        refresh();
                    }
                });
            };
        });
        return delete;
    }

    public void insertTableCheckBoxColumn(){
        // 创建一个用于选择所有行的 CheckBox
        CheckBox selectAllCheckBox = new CheckBox();
        selectAllCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> tableView.getItems().forEach(e->e.setSelected(newValue)));

        // 创建一个用于显示每个行的 CheckBox 的列
        TableColumn<SysDevice, Boolean> selectCol = new TableColumn<>("Select");
        selectCol.setSortable(false); // 不允许排序
        selectCol.setResizable(false); // 不允许调整大小
        selectCol.setPrefWidth(40); // 设置列宽
        selectCol.setGraphic(selectAllCheckBox); // 设置表头的图形为 CheckBox
        selectCol.setCellValueFactory(param -> param.getValue().selectedProperty());

        // 自定义单元格工厂以显示 CheckBox
        selectCol.setCellFactory(param -> new CheckBoxTableCell(tableView));
        tableView.getColumns().add(selectCol);

    }

    public Node footer(){
        VBox vBox = new VBox();
        vBox.setFillWidth(true);

        pagination.setPageFactory((pageIndex)->{
            this.pageIndex = pageIndex+1;

            refresh();

            return new BorderPane(tableView);
        });

        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        VBox.setVgrow(pagination, Priority.ALWAYS);
        vBox.getChildren().add(pagination);

        return vBox;
    }

    public void refresh(){
        SysDevice device = new SysDevice();
        device.setCustomName(nameField.getText());
        device.setIp(ipField.getText());
        PageInfo<SysDevice> pages = SpringUtils.getBean(ISysDeviceService.class).lists(pageIndex, device);

        pagination.setPageCount(pages.getPages());

        ObservableList<SysDevice> items = tableView.getItems();
        items.clear();
        items.addAll(pages.getList());
    }

    public void queryClear(){
        nameField.setText("");
        ipField.setText("");
        refresh();
    }

    // 自定义的 CheckBox 单元格
    public static class CheckBoxTableCell extends TableCell<SysDevice, Boolean> {
        private final CheckBox checkBox = new CheckBox();

        public CheckBoxTableCell(TableView<SysDevice> tableView) {
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

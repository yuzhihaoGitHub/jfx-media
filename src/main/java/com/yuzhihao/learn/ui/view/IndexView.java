package com.yuzhihao.learn.ui.view;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.mvc.View;
import com.yuzhihao.learn.config.SpringUtils;
import com.yuzhihao.learn.h2.common.SysDictConstant;
import com.yuzhihao.learn.h2.entity.SysDevice;
import com.yuzhihao.learn.h2.service.ISysDeviceService;
import com.yuzhihao.learn.ui.ApplicationView;
import com.yuzhihao.learn.ui.bar.VlcjDefaultHeaderBar;
import com.yuzhihao.learn.ui.dialog.SysDeviceDialog;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.chart.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.extern.log4j.Log4j2;
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * 登陆成功后首页
 *
 * @author yuzhihao
 */
@Log4j2
public class IndexView extends View {

    public IndexView() {
        FloatingActionButton fab = new FloatingActionButton();

        fab.showOn(this);
        fab.setOnAction(event -> {
            new SysDeviceDialog().show().ifPresent(type-> {
                if(type.equals(ButtonType.OK)) {
                    getAppManager().switchView(ApplicationView.CAMERA_LIST);
                }
            });
        });

        addEventHandler(LifecycleEvent.HIDDEN, event -> {

        });

    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        new VlcjDefaultHeaderBar(appBar).show();
        center();
    }

    private void center(){
        GridPane playerGrid = new GridPane();
        playerGrid.setHgap(5);
        playerGrid.setVgap(5);

        ColumnConstraints fillColumn = new ColumnConstraints();
        fillColumn.setHgrow(Priority.ALWAYS);
        fillColumn.setFillWidth(true);

        RowConstraints fillRow = new RowConstraints();
        fillRow.setVgrow(Priority.ALWAYS);
        fillRow.setFillHeight(true);

        for (int row = 0; row < 2; row++) {
            playerGrid.getRowConstraints().add(fillRow);
            playerGrid.getColumnConstraints().add(fillColumn);
        }

        // 添加数据
        PieChart pieChart = getPieChart();
        StackedBarChart<String, Number> stackedAreaChart = getStackedAreaChart();
        LineChart<String,Number> lineChartUp = getLineChartCpu();
        LineChart<String,Number> lineChartDown = getLineChartCache();

        GridPane.setConstraints(pieChart, 0, 0);
        GridPane.setConstraints(lineChartUp, 1, 0);
        GridPane.setConstraints(stackedAreaChart, 0, 1);
        GridPane.setConstraints(lineChartDown, 1, 1);

        // 添加颜色矩形作为间隔
        for (int i = 0; i < 2; i++) {
            Rectangle rect = new Rectangle(5, 5); // 宽度为 5，高度为 100
            rect.setFill(Color.RED); // 设置颜色
            playerGrid.add(rect, 1, i); // 在第二列添加颜色矩形
        }

        playerGrid.getChildren().addAll(pieChart,stackedAreaChart, lineChartUp, lineChartDown);
//        playerGrid.setStyle("-fx-background-color: #C0C4CC");
        SplitPane splitPane = new SplitPane();
//        splitPane.setStyle("-fx-background-color: black;");
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setPadding(new Insets(5));
        splitPane.getItems().addAll( playerGrid);
        splitPane.setDividerPosition(0, 0.395f);

        setCenter(splitPane);
    }

    private static StackedBarChart<String, Number> getStackedAreaChart() {
        // 创建 X 轴和 Y 轴
        CategoryAxis xAxis = new CategoryAxis(); // 分类轴
        NumberAxis yAxis = new NumberAxis();    // 数值轴

        // 创建堆叠条形图
        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle("存储使用信息");

        // 创建数据系列 1
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("已使用存储");
        series1.getData().add(new XYChart.Data<>("已使用", Double.parseDouble(getSpace()[2])));

        // 创建数据系列 2
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("未使用存储");
        series2.getData().add(new XYChart.Data<>("未使用", Double.parseDouble(getSpace()[1])));

        // 将数据系列添加到堆叠条形图中
        stackedBarChart.getData().add(series1);
        stackedBarChart.getData().add(series2);

        return stackedBarChart;
    }

    private static PieChart getPieChart() {

        long cameraCount = SpringUtils.getBean(ISysDeviceService.class).count(Wrappers.lambdaQuery(SysDevice.class).eq(SysDevice::getMediaType, SysDictConstant.CAMERA));
        long fileCount = SpringUtils.getBean(ISysDeviceService.class).count(Wrappers.lambdaQuery(SysDevice.class).eq(SysDevice::getMediaType, SysDictConstant.File));
        long webCount = SpringUtils.getBean(ISysDeviceService.class).count(Wrappers.lambdaQuery(SysDevice.class).eq(SysDevice::getMediaType, SysDictConstant.WEB_URL));

        PieChart.Data slice1 = new PieChart.Data("文件媒体-"+fileCount, fileCount);
        PieChart.Data slice2 = new PieChart.Data("摄像头媒体-"+cameraCount, cameraCount);
        PieChart.Data slice3 = new PieChart.Data("URL媒体-"+webCount, webCount);
        PieChart pieChart = new PieChart();
        pieChart.setTitle("媒体统计信息");
        pieChart.getData().addAll(slice1,slice2,slice3);
        return pieChart;
    }


    public LineChart<String,Number> getLineChartCpu() {
        // 创建 X 轴和 Y 轴
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(100);

        // 创建折线图
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("CPU 使用信息");

        // 创建数据系列 1
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("时间");
        series.getData().add(new XYChart.Data<>(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), getCpuUsagePercentage()));
        // 创建定时任务，每秒更新一次数据
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {

            // 如果数据点超过最大数量，移除最早的数据点
            if (series.getData().size() > MAX_DATA_POINTS - 1) {
                series.getData().remove(0);
            }
            // 添加新数据点
            series.getData().add(new XYChart.Data<>(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), getCpuUsagePercentage()));


        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // 无限循环
        timeline.play(); // 开始定时任务
        // 将数据系列添加到折线图中
        lineChart.getData().add(series);

        return lineChart;
    }

    private static final int MAX_DATA_POINTS = 5; // 最多显示的数据点数量

    public LineChart<String,Number> getLineChartCache() {
        // 创建 X 轴和 Y 轴
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(100);

        // 创建折线图
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("内存使用%");


        // 创建数据系列 1
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("时间");
        series.getData().add(new XYChart.Data<>(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), getUsedMemoryPercentage()));

        // 创建定时任务，每秒更新一次数据
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            // 如果数据点超过最大数量，移除最早的数据点
            if (series.getData().size() > MAX_DATA_POINTS -1 ) {
                series.getData().remove(0);
            }

            // 添加新数据点
            series.getData().add(new XYChart.Data<>(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), getUsedMemoryPercentage()));

        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // 无限循环
        timeline.play(); // 开始定时任务

        // 将数据系列添加到折线图中
        lineChart.getData().add(series);

        return lineChart;
    }

    /**
     * 获取当前 JVM 使用的内存（以 MB 为单位）
     */
    private double getUsedMemoryPercentage() {
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed();
        long maxMemory = heapMemoryUsage.getMax();
        return (double) usedMemory / maxMemory * 100; // 计算使用百分比
    }

    /**
     * 获取当前 CPU 使用的百分比
     */
    private double getCpuUsagePercentage() {
        // 计算 CPU 使用率
        // 注意：此方法在不同的 JVM 实现中可能会有所不同
        // 这里使用的方式是获取系统负载，可能需要根据具体情况调整
        return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage() * 100 / Runtime.getRuntime().availableProcessors();
    }

    private static  final File FILE = new File(RuntimeUtil.isMac() ? "/" : "C:\\"); // 在 Windows 上可以使用 "C:\"

    private static String[] getSpace(){
        // 获取根目录（例如 C:\ 或 /）
        long start = System.currentTimeMillis();

        // 获取总空间、可用空间和已用空间
        long totalSpace = FILE.getTotalSpace(); // 总空间
        long usableSpace = FILE.getUsableSpace(); // 可用空间
        long usedSpace = totalSpace - usableSpace; // 已用空间

        // 输出结果
        log.info("总空间: " + formatSize(totalSpace));
        log.info("可用空间: " + formatSize(usableSpace));
        log.info("已用空间: " + formatSize(usedSpace));

        log.info("耗时：{}",System.currentTimeMillis() - start);
        return new String[]{formatSize(totalSpace),formatSize(usableSpace),formatSize(usedSpace)};
    }

    private static String formatSize(long size) {
        String[] units = {"KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double formattedSize = (double) size / 1024;

        while (formattedSize >= 1024 && unitIndex < units.length - 1) {
            formattedSize /= 1024;
            unitIndex++;
        }

        return String.valueOf(formattedSize);
    }

}

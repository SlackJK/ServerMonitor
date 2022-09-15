package com.slackjk.servermonitor.FDcode;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.Easing;
import com.github.appreciated.apexcharts.config.chart.animations.builder.DynamicAnimationBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.AnimationsBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.ToolbarBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.security.SecureRandom;
import java.util.ArrayList;

@Route(value = "stream")
@PermitAll
public class StreamingDataExampleView extends VerticalLayout{

    private final ApexCharts chart;
    private Thread thread;

    public StreamingDataExampleView() {

        chart = ApexChartsBuilder.get().withChart(ChartBuilder.get()
                        .withType(Type.line)
                        .withAnimations(AnimationsBuilder.get()
                                .withEnabled(true)
                                .withEasing(Easing.linear)
                                .withDynamicAnimation(DynamicAnimationBuilder.get()
                                        .withSpeed(1000)
                                        .build())
                                .build())
                        .withToolbar(ToolbarBuilder.get().withShow(false).build())
                        .withZoom(ZoomBuilder.get().withEnabled(false).build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withXaxis(XAxisBuilder.get().withRange(10.0).build())
                .withSeries(new Series<>(0))
                .build();

        chart.setDebug(true);
        chart.setWidth("500px");
        add(chart);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.add(0.0);
        thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final SecureRandom random = new SecureRandom();
                arrayList.add(random.nextDouble());
                getUI().ifPresent(ui -> ui.access(() -> {chart.updateSeries(new Series<>(arrayList.toArray()));ui.push();}));//;

                System.out.println(arrayList);
            }
        });
        thread.start();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        thread.interrupt();
    }
}
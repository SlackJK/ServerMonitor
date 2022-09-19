package com.slackjk.servermonitor.FDcode;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.DynamicAnimation;
import com.github.appreciated.apexcharts.config.chart.animations.Easing;
import com.github.appreciated.apexcharts.config.chart.animations.builder.DynamicAnimationBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.AnimationsBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.DropShadowBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.ToolbarBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.fill.builder.GradientBuilder;
import com.github.appreciated.apexcharts.config.legend.HorizontalAlign;

import com.github.appreciated.apexcharts.config.plotoptions.builder.RadialBarBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.HollowBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.TrackBuilder;
import com.github.appreciated.apexcharts.config.series.SeriesType;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.config.xaxis.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import com.slackjk.servermonitor.LoggerDataGrabber.GeneralServerStats;
import com.vaadin.flow.component.html.Div;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Charts
{
    // Credit https://github.com/rucko24/nodemcu-with-flow/blob/main/src/main/java/com/example/application/backend/services/charts/ApexChartService.java
    public static final String CPUUSAGE = "Usage%";
    private static final String FORMATTER_TIMESTAMP = "function (value, timestamp) {\n" +
            "  var date = new Date(timestamp);" +
            "  var hours = date.getHours();\n" +
            "  var minutes = date.getMinutes();\n" +
            "  var seconds = date.getSeconds();" +
            "  var ampm = hours >= 12 ? 'pm' : 'am';\n" +
            "  hours = hours % 12;\n" +
            "  hours = hours ? hours : 12; // the hour '0' should be '12'\n" +
            "  minutes = minutes < 10 ? '0'+minutes : minutes;\n" +
            "  return hours + ':' + minutes + ':' + ('0'+seconds).slice(-2) + ' ' + ampm;\n" +
            "}";
    public ApexCharts CpuUsage()
    {
        /*
        return ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withId("realtime")
                        .withType(Type.line)
                        .withAnimations(AnimationsBuilder.get()
                                .withEnabled(true)
                                .withEasing(Easing.linear)
                                .withDynamicAnimation(DynamicAnimationBuilder.get()
                                        .withSpeed(100)
                                        .build())
                                .build())
                        .withZoom(ZoomBuilder.get()
                                .withEnabled(true)
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withStroke(StrokeBuilder.get().withCurve(Curve.straight).build())
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("CPU Usage")
                        .withAlign(Align.left).build())
                .withSubtitle(TitleSubtitleBuilder.get()
                        .withText("% each second")
                        .withAlign(Align.left).build())
                .withDataLabels(DataLabelsBuilder
                        .get()
                        .withEnabled(false)
                        .build())
                .withXaxis(XAxisBuilder.get()
                        .withRange(100.0)
                        .build())
                .withYaxis(YAxisBuilder.get()
                        .withOpposite(false).withMax(100).build())
                .withLegend(LegendBuilder.get().withHorizontalAlign(HorizontalAlign.left).build())
                .withSeries(new Series<>(CPUUSAGE, SeriesType.line,Usage))
                .build();

         */
        return ApexChartsBuilder.get().withChart(ChartBuilder.get()
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
                .withXaxis(XAxisBuilder.get()
                        .withType(XAxisType.datetime)
                        .withLabels(LabelsBuilder.get()
                                .withFormatter(FORMATTER_TIMESTAMP)
                                .withShowDuplicates(false)
                                //.withRotateAlways(true)
                                .withShow(true)
                                .build())
                        .withRange(60000.0)//todo testme
                        .build())
                .withYaxis(YAxisBuilder.get()
                        .withOpposite(false).withMax(100).withMin(0.0).build())
                .withSeries(new Series<>(0))
                .build();
    }
    public ApexCharts RamUsage()
    {
        return ApexChartsBuilder.get().withChart(ChartBuilder.get()
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
                .withXaxis(XAxisBuilder.get()
                        .withType(XAxisType.datetime)
                        .withLabels(LabelsBuilder.get()
                                .withFormatter(FORMATTER_TIMESTAMP)
                                .withShowDuplicates(false)
                                //.withRotateAlways(true)
                                .withShow(true)
                                .build())
                        .withRange(60000.0)//todo testme
                        .build())
                .withYaxis(YAxisBuilder.get()
                        .withOpposite(false).withMax(GeneralServerStats.getTotalRam()).withMin(0).build())
                .withSeries(new Series<>(0))
                .build();
    }
    public ApexCharts cpuUsageGuage()
    {
        /*
                return ApexChartsBuilder.get()
                        .withChart(ChartBuilder.get()
                                .withType(Type.radialBar)
                                .build())
                        .withPlotOptions(PlotOptionsBuilder.get()
                                .withRadialBar(RadialBarBuilder.get()
                                        .withHollow(HollowBuilder.get()
                                                .withSize("50%")
                                                .build())
                                        .build())
                                .build())
                        .withSeries(60.0)
                        .withLabels("Cricket")
                        .build();

         */



        return ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.radialBar)
                        .withAnimations(AnimationsBuilder.get()
                                .withEnabled(true)
                                .withEasing(Easing.linear)
                                .withDynamicAnimation(DynamicAnimationBuilder.get()
                                        .withSpeed(10000)
                                        .build())
                                .build())
                        .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withRadialBar(RadialBarBuilder.get()
                                .withStartAngle(-90.0)
                                .withEndAngle(90.0)
                                .withTrack(TrackBuilder.get()
                                        .withBackground("#e7e7e7")
                                        .withStrokeWidth("97%")
                                        .withMargin(15.0)
                                        .withDropShadow(DropShadowBuilder.get()
                                                .withEnabled(true)
                                                .withTop(2.0)
                                                .withLeft(0.0)
                                                .withOpacity(1.0)
                                                .withOpacity(2.0)
                                                .build())
                                        .build())
                                .withHollow(HollowBuilder.get()
                                        .withSize("50%")
                                        .build())
                                .build())
                        .build())
                .withFill(FillBuilder.get()
                        .withType("gradient")
                        .withGradient(GradientBuilder.get()
                                .withShade("light")
                                .withShadeIntensity(0.4)
                                .withInverseColors(false)
                                .withOpacityFrom(1.0)
                                .withOpacityTo(1.0)
                                .withStops(new ArrayList<>(Arrays.asList(0.0, 50.0, 53.0, 91.0)))
                                .build())
                        .build())
                .withSeries(76.0)
                .withLabels("CPU Usage")
                .build();


    }
}

package com.slackjk.servermonitor.FDcode;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.slackjk.servermonitor.LoggerDataGrabber.GeneralServerStats;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.web.context.WebApplicationContext;



import javax.annotation.security.PermitAll;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@PageTitle("list")
@Route(value = "dashboard")
@PermitAll
public class Dashboard extends VerticalLayout
{
    private static int resolution;
    private static double tabScreenCoverage = 0.7;//between 0 and 1
    private static double sideBarScreenCoverage = 0.3;//between 0 and 1
    Charts charts = new Charts();

    ApexCharts chart;
    GeneralServerStats GSS = new GeneralServerStats();
    Series cpuUsage = new Series<>(GSS.cpuUsage.toArray());
    public Dashboard() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);
        H1 z = new H1(String.valueOf(resolution));
        VerticalLayout pageContent = new VerticalLayout();
        HorizontalLayout content = new HorizontalLayout();
        VerticalLayout sideBar= new VerticalLayout();

        Tabs x = createTabs();
        int xwidth = (int) (resolution*tabScreenCoverage);
        x.setWidth("1500px");
        pageContent.add(x);

        Button y = createDarkModeSelector();
        int ywidth = (int) (resolution*sideBarScreenCoverage);
        y.setWidth("180px");
        sideBar.add(y);

        content.setSpacing(true);
        content.add(sideBar,pageContent);

        TimeUnit.SECONDS.sleep(10);

        List<String> Time = GSS.Time;
        chart = charts.CpuUsage(cpuUsage,Time);
        chart.setWidth("1500px");

        add(content,chart);

        /*
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    schedule.scheduleAtFixedRate(() -> {
                        try {
                            final CompletableFuture<String> cf = HttpClient
                                    .newBuilder()
                                    .build()
                                    .sendAsync(HttpRequest
                                                    .newBuilder(new URI(HUMIDITY))
                                                    .GET()
                                                    .version(HttpClient.Version.HTTP_2)
                                                    .build(),
                                            HttpResponse.BodyHandlers.ofString())
                                    .thenApplyAsync(HttpResponse::body);
                            cf.whenCompleteAsync((data, error) -> {
                                        if (getUI().isEmpty()) return;
                                        if (Objects.nonNull(error)) {
                                            cf.completeExceptionally(error);
                                            getUI().get().access(() -> {
                                                Notification.show("Error: " + error);
                                            });
                                        } else {
                                            if (getUI().isEmpty()) return;
                                            getUI().get().access(() -> {

                                                //chart.updateSeries(radomSeries[SECURE_RANDOM.nextInt(randomSeries .length)]);

                                                chart.updateSeries(new Series<>(data));
                                            });

                                        }
                                    }, EXEC)
                                    .join();
                        } catch (URISyntaxException e) {
                            throw new RuntimeException();
                        }
                    }, 1, 1, TimeUnit.SECONDS);

                }
            }
        });
        thread2.start();
        /* TODO try me



         */


    }
    @Override
    protected void onAttach(AttachEvent attachEvent)
    {
        super.onAttach(attachEvent);
        ArrayList arrayList = new ArrayList<>();
        arrayList.add(new Coordinate<>(System.currentTimeMillis(),0.0));
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    getUI().get().access(()->{
                                chart.updateSeries(new Series<>(arrayList.toArray()));
                            }
                    );
                    try {

                        arrayList.add(GSS.getResourceLoad());
                        //System.out.println(arrayList);
                        if(arrayList.size()>99)
                            arrayList.remove(0);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread2.start();
    }

    private Tabs createTabs()
    {
        return new Tabs(
                new Tab("System Health"),
                new Tab("MScraper Health")
        );
    }
    private Button createDarkModeSelector()
    {
        return new Button("Dark Mode", click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList(); // (1)

            if (themeList.contains(Lumo.DARK)) { // (2)
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });
    }
    private static void setResolution()
    {
        while (true)
        {

        }
    }
    public static void getResolution()
    {
        CompletableFuture x = CompletableFuture.supplyAsync(()->{setResolution();return null;});
    }

}

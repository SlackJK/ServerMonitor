package com.slackjk.servermonitor.FDcode;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Series;
import com.slackjk.servermonitor.LoggerDataGrabber.GeneralServerStats;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
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
import java.sql.Time;
import java.util.List;
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
    GeneralServerStats GSS = new GeneralServerStats();
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
        Series cpuUsage = GSS.cpuUsage;
        List<String> Time = GSS.Time;
        ApexCharts chart = charts.CpuUsage(cpuUsage,Time);
        chart.setWidth("1500px");

        add(content,chart);
        /*
        while (true)
        {
            cpuUsage = GSS.updateCPULoad(cpuUsage,chart);
        }

         */
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

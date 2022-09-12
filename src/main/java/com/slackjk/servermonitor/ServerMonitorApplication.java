package com.slackjk.servermonitor;

import com.slackjk.servermonitor.LoggerDataGrabber.GeneralServerStats;
import com.slackjk.servermonitor.LoggerDataGrabber.ServerStatsThroughSQL;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import static com.slackjk.servermonitor.FDcode.Dashboard.getResolution;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ServerMonitorApplication{

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ServerMonitorApplication.class, args);
        GeneralServerStats GSS = new GeneralServerStats();
        //GSS.getResourceLoad(300);
    }
}

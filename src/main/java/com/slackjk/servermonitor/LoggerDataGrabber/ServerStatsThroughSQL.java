package com.slackjk.servermonitor.LoggerDataGrabber;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerStatsThroughSQL
{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public final String dbName = "TimeSensitiveLogs";
    InfluxDB influxDB = InfluxDBFactory.connect("","","");
    public ServerStatsThroughSQL()
    {
        testInflux();
        setupInflux();
    }
    private void setupInflux()
    {
        influxDB.createDatabase(dbName);
        influxDB.createRetentionPolicy(
                "defaultPolicy", "TimeSensitiveLogs", "1h", 1, true);
    }
    private void testInflux()
    {
        Pong response = this.influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            System.out.println("Error pinging server.");
            return;
        }
    }
    public Point createMemPoint(long memUsage)
    {
        return  Point.measurement("memUsage").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("usage",memUsage)
                .build();

    }
    public Point createCPUPoint(double cpuUsage)
    {
        return Point.measurement("cpuUsage").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("usage",cpuUsage)
                .build();
    }

}


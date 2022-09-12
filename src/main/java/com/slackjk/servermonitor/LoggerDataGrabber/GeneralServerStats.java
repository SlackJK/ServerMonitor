package com.slackjk.servermonitor.LoggerDataGrabber;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Series;
import com.sun.management.OperatingSystemMXBean;
import org.influxdb.dto.BatchPoints;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GeneralServerStats
{
    //ServerStatsThroughSQL SSTS = new ServerStatsThroughSQL();

    OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    public Series cpuUsage = new Series<>();
    public List<String> Time = new ArrayList<>();
    private int cpuUsageBlockSize = 10;
    private int memUsageBlockSize = 10;
    public GeneralServerStats() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getResourceLoad(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();
    }
    private void getResourceLoad(long Milliseconds) throws InterruptedException//Only use this method if multithreading
    {
        ArrayList cpu = new ArrayList<>();
        ArrayList mem = new ArrayList<>();
        while(true)
        {
            cpu.add(operatingSystemMXBean.getSystemCpuLoad());
            if(cpu.size()>=cpuUsageBlockSize)
            {
                cpuUsage.setData(cpu.toArray());
                cpu = new ArrayList<>();
            }
            mem.add(getMemoryLoad());
            if(mem.size()>=memUsageBlockSize)
            {
                cpuUsage.setData(cpu.toArray());
                mem = new ArrayList<>();
            }
            Time.add(String.valueOf(Instant.now().getEpochSecond()));
            if(Time.size()>=cpuUsageBlockSize)
            {
                Time = new ArrayList<>();
            }
            TimeUnit.MILLISECONDS.sleep(Milliseconds);
        }
    }
    private long getMemoryLoad()
    {
        return operatingSystemMXBean.getTotalPhysicalMemorySize()-operatingSystemMXBean.getFreePhysicalMemorySize();
    }
    public void getSystemHardwareStatistics() throws IOException//linux only
    {
        List<String> SystemsSpecs= Files.lines(Paths.get("/proc/cpuinfo")).collect(Collectors.toList());
        System.out.println(SystemsSpecs);
    }
    public void getBasicSystemStatistics()
    {
        Properties properties = System.getProperties();
        String os = properties.getProperty("os.name");
        String os_arch = properties.getProperty("os.arch");
        String os_version = properties.getProperty("os.version");
        String java_version = properties.getProperty("java.version");

    }
    public Series updateCPULoad(Series previousCPUUsage, ApexCharts chart)
    {
        if(cpuUsage.getData().equals(previousCPUUsage.getData()))
        {
            chart.updateSeries(cpuUsage);
        }
        return cpuUsage;
    }
}

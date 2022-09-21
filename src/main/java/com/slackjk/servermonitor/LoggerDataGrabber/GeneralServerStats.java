package com.slackjk.servermonitor.LoggerDataGrabber;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.sun.management.OperatingSystemMXBean;
import org.influxdb.dto.BatchPoints;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GeneralServerStats
{
    //ServerStatsThroughSQL SSTS = new ServerStatsThroughSQL();

    static OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    public List cpuUsage = new ArrayList<>();
    public List memUsage = new ArrayList<>();
    public List<String> Time = new ArrayList<>();
    private int cpuUsageBlockSize = 100;
    private int memUsageBlockSize = 100;
    public GeneralServerStats() throws InterruptedException {
        /*
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

         */
    }
    public Coordinate getCPULoad()
    {

            return new Coordinate(System.currentTimeMillis(),((int)(operatingSystemMXBean.getSystemCpuLoad()*10000))/100.0);
            /*
            if(cpuUsage.size()>=cpuUsageBlockSize)
            {
                cpuUsage.remove(0);
            }
            memUsage.add(getMemoryLoad());
            if(memUsage.size()>=memUsageBlockSize)
            {
                memUsage.remove(0);
            }
            Time.add(String.valueOf(Instant.now().getEpochSecond()));
            if(Time.size()>=cpuUsageBlockSize)
            {
                Time.remove(0);
            }
            TimeUnit.MILLISECONDS.sleep(Milliseconds);

             */

    }
    public Coordinate getMemoryLoad()
    {
        return new Coordinate<>(System.currentTimeMillis(),(operatingSystemMXBean.getTotalPhysicalMemorySize()-operatingSystemMXBean.getFreePhysicalMemorySize())/1000000);
    }
    public double getCPUUsage()
    {
        return (((int)(operatingSystemMXBean.getSystemCpuLoad()*10000))/100.0);
    }
    public static long getTotalRam()
    {
       return operatingSystemMXBean.getTotalPhysicalMemorySize()/1000000;
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
    //think about 2d as u need to store the cooordinates series for each group
    public ArrayList<Double> getCPUTemp()//todo add fan speed and temp measurements
    {
        ArrayList<Double> out = new ArrayList<>();
        JSensors.get.components().cpus.forEach(cpu -> cpu.sensors.temperatures
                .forEach(temperature ->
                        out.add(temperature.value)));
        return out;
    }
    public ArrayList<Double> getCPUFanSpeed()//todo add fan speed and temp measurements
    {
        ArrayList<Double> out = new ArrayList<>();
        JSensors.get.components().cpus.forEach(cpu -> cpu.sensors.fans
                .forEach(fan ->
                        out.add(fan.value)));
        return out;
    }
    public ArrayList<Double> getGPUTemp()//todo add fan speed and temp measurements
    {
        ArrayList<Double> out = new ArrayList<>();
        JSensors.get.components().gpus.forEach(gpu -> gpu.sensors.temperatures
                .forEach(temperature ->
                        out.add(temperature.value)));
        return out;
    }
    public ArrayList<Double> getGPUFanSpeed()//todo add fan speed and temp measurements
    {
        ArrayList<Double> out = new ArrayList<>();
        JSensors.get.components().gpus.forEach(gpu -> gpu.sensors.fans
                .forEach(fan ->
                        out.add(fan.value)));
        return out;
    }

    public ArrayList<Double> getMoboTemp()//todo add fan speed and temp measurements
    {
        ArrayList<Double> out = new ArrayList<>();
        JSensors.get.components().mobos.forEach(mobo -> mobo.sensors.temperatures
                .forEach(temperature ->
                        out.add(temperature.value)));
        return out;
    }
    public ArrayList<Double> getDiskLoad()//todo add fan speed and temp measurements
    {
        ArrayList<Double> out = new ArrayList<>();
        JSensors.get.components().disks.forEach(disk -> disk.sensors.loads
                .forEach(load ->
                        out.add(load.value)));
        return out;
    }

}

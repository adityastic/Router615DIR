package com.adityagupta.router615dir.data;

public class ConnectedDevicesData {
    String name,mac,ip;

    public ConnectedDevicesData(String name, String mac, String ip) {
        this.name = name;
        this.mac = mac;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }

    public String getIp() {
        return ip;
    }
}

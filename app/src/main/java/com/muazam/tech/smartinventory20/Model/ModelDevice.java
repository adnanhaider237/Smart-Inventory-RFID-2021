package com.muazam.tech.smartinventory20.Model;

public class ModelDevice {
    String DeviceCode, DeviceID, DeviceNameE, DeviceNameA,LastSession;

    public ModelDevice(String deviceCode, String deviceID, String deviceNameE, String deviceNameA, String lastSession) {
        DeviceCode = deviceCode;
        DeviceID = deviceID;
        DeviceNameE = deviceNameE;
        DeviceNameA = deviceNameA;
        LastSession = lastSession;
    }

    public String getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        DeviceCode = deviceCode;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getDeviceNameE() {
        return DeviceNameE;
    }

    public void setDeviceNameE(String deviceNameE) {
        DeviceNameE = deviceNameE;
    }

    public String getDeviceNameA() {
        return DeviceNameA;
    }

    public void setDeviceNameA(String deviceNameA) {
        DeviceNameA = deviceNameA;
    }

    public String getLastSession() {
        return LastSession;
    }

    public void setLastSession(String lastSession) {
        LastSession = lastSession;
    }
}

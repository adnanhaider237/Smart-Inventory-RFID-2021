package com.muazam.tech.smartinventory20.Model;

public class ModelPhyInvHdr {
    /*String SessionId,
            DeviceID,
            UserCode,
            SiteCode,
            LocationCode,
            StartTime,
            CloseTime,
            Status;*/
    String PhyInvSessionNum;
    String DeviceId;
    String DeviceCode;
    String PhyInvDocNum;
    String SiteCode;
    String UserCode;
    String StartTime;
    String PostingDate;
    String CloseTime;
    String StatusCode;
    String Status;
    String BrCode;
    String PhyInvHdrId;

    public String getPhyInvHdrId() {
        return PhyInvHdrId;
    }

    public void setPhyInvHdrId(String phyInvHdrId) {
        PhyInvHdrId = phyInvHdrId;
    }

    public ModelPhyInvHdr(String phyInvSessionNum, String deviceId, String deviceCode, String phyInvDocNum,
                          String siteCode, String usercode, String startTime, String postingDate, String CloseTime,
                          String statusCode, String status, String brCode) {
        PhyInvSessionNum = phyInvSessionNum;
        DeviceId = deviceId;
        DeviceCode = deviceCode;
        PhyInvDocNum = phyInvDocNum;
        SiteCode = siteCode;
        UserCode = usercode;
        StartTime = startTime;
        PostingDate = postingDate;
        StatusCode = statusCode;
        Status = status;
        BrCode = brCode;
        this.CloseTime = CloseTime;
    }

    public ModelPhyInvHdr(String phyInvSessionNum, String deviceId,
                          String deviceCode, String phyInvDocNum,
                          String siteCode, String startTime, String postingDate,
                          String closeTime, String statusCode, String status,
                          String brCode, String phyInvHdrId, String temp) {
        PhyInvSessionNum = phyInvSessionNum;
        DeviceId = deviceId;
        DeviceCode = deviceCode;
        PhyInvDocNum = phyInvDocNum;
        SiteCode = siteCode;
        StartTime = startTime;
        PostingDate = postingDate;
        CloseTime = closeTime;
        StatusCode = statusCode;
        Status = status;
        BrCode = brCode;
        PhyInvHdrId = phyInvHdrId;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String closeTime) {
        CloseTime = closeTime;
    }

    public String getPhyInvSessionNum() {
        return PhyInvSessionNum;
    }

    public void setPhyInvSessionNum(String phyInvSessionNum) {
        PhyInvSessionNum = phyInvSessionNum;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        DeviceCode = deviceCode;
    }

    public String getPhyInvDocNum() {
        return PhyInvDocNum;
    }

    public void setPhyInvDocNum(String phyInvDocNum) {
        PhyInvDocNum = phyInvDocNum;
    }

    public String getSiteCode() {
        return SiteCode;
    }

    public void setSiteCode(String siteCode) {
        SiteCode = siteCode;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getPostingDate() {
        return PostingDate;
    }

    public void setPostingDate(String postingDate) {
        PostingDate = postingDate;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBrCode() {
        return BrCode;
    }

    public void setBrCode(String brCode) {
        BrCode = brCode;
    }
}

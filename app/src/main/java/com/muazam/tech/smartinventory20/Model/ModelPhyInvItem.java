package com.muazam.tech.smartinventory20.Model;

public class ModelPhyInvItem {
    /*String SessionId ,DeviceID ,SiteCode ,LocationCode,UnitId ;*/
    String PhyInvSessionNum;
    String DeviceId;
    String DeviceCode;
    String PhyInvDocNum;
    String SiteCode;
    String LocCode;
    String TagNumber;
    String TagASCII;
    String BrCode;
    String FlagDel;

    public ModelPhyInvItem(String phyInvSessionNum, String deviceId, String deviceCode, String phyInvDocNum, String siteCode, String locCode, String tagNumber, String tagASCII, String brCode, String flagDel) {
        PhyInvSessionNum = phyInvSessionNum;
        DeviceId = deviceId;
        DeviceCode = deviceCode;
        PhyInvDocNum = phyInvDocNum;
        SiteCode = siteCode;
        LocCode = locCode;
        TagNumber = tagNumber;
        TagASCII = tagASCII;
        BrCode = brCode;
        FlagDel = flagDel;
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

    public String getLocCode() {
        return LocCode;
    }

    public void setLocCode(String locCode) {
        LocCode = locCode;
    }

    public String getTagNumber() {
        return TagNumber;
    }

    public void setTagNumber(String tagNumber) {
        TagNumber = tagNumber;
    }

    public String getTagASCII() {
        return TagASCII;
    }

    public void setTagASCII(String tagASCII) {
        TagASCII = tagASCII;
    }

    public String getBrCode() {
        return BrCode;
    }

    public void setBrCode(String brCode) {
        BrCode = brCode;
    }

    public String getFlagDel() {
        return FlagDel;
    }

    public void setFlagDel(String flagDel) {
        FlagDel = flagDel;
    }
}

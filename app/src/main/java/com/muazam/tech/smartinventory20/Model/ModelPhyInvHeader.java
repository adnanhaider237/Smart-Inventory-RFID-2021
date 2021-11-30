package com.muazam.tech.smartinventory20.Model;

public class ModelPhyInvHeader {
    String SiteCode, UserCode, DateG;

    public ModelPhyInvHeader(String siteCode, String userCode, String dateG) {
        SiteCode = siteCode;
        UserCode = userCode;
        DateG = dateG;
    }

    public String getSiteCode() {
        return SiteCode;
    }

    public void setSiteCode(String siteCode) {
        SiteCode = siteCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getDateG() {
        return DateG;
    }

    public void setDateG(String dateG) {
        DateG = dateG;
    }
}

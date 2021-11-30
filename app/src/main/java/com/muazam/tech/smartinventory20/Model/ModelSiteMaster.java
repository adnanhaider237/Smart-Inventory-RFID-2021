package com.muazam.tech.smartinventory20.Model;

public class ModelSiteMaster {
    String SiteCode, SiteNameE, SiteNameA, CusCode;

    public ModelSiteMaster(String siteCode, String siteNameE, String siteNameA, String cusCode) {
        SiteCode = siteCode;
        SiteNameE = siteNameE;
        SiteNameA = siteNameA;
        CusCode = cusCode;
    }

    public String getSiteCode() {
        return SiteCode;
    }

    public void setSiteCode(String siteCode) {
        SiteCode = siteCode;
    }

    public String getSiteNameE() {
        return SiteNameE;
    }

    public void setSiteNameE(String siteNameE) {
        SiteNameE = siteNameE;
    }

    public String getSiteNameA() {
        return SiteNameA;
    }

    public void setSiteNameA(String siteNameA) {
        SiteNameA = siteNameA;
    }

    public String getCusCode() {
        return CusCode;
    }

    public void setCusCode(String cusCode) {
        CusCode = cusCode;
    }
}

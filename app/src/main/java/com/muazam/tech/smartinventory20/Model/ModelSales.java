package com.muazam.tech.smartinventory20.Model;

public class ModelSales {

    public String SiteCode;
    public String SiteNameE;
    public String SiteNameA;
    public String CusCode;

    public ModelSales(String siteCode, String siteNameE, String siteNameA, String cusCode) {
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

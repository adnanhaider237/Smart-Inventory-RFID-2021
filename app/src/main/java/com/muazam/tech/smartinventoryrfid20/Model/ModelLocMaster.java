package com.muazam.tech.smartinventoryrfid20.Model;

public class ModelLocMaster {
    String id,CusCode,SiteCode,LocCode,LocNameE,LocNameA,FlagDefault;

    public ModelLocMaster(String id, String cusCode, String siteCode, String locCode, String locNameE, String locNameA, String flagDefault) {
        this.id = id;
        CusCode = cusCode;
        SiteCode = siteCode;
        LocCode = locCode;
        LocNameE = locNameE;
        LocNameA = locNameA;
        FlagDefault = flagDefault;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCusCode() {
        return CusCode;
    }

    public void setCusCode(String cusCode) {
        CusCode = cusCode;
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

    public String getLocNameE() {
        return LocNameE;
    }

    public void setLocNameE(String locNameE) {
        LocNameE = locNameE;
    }

    public String getLocNameA() {
        return LocNameA;
    }

    public void setLocNameA(String locNameA) {
        LocNameA = locNameA;
    }

    public String getFlagDefault() {
        return FlagDefault;
    }

    public void setFlagDefault(String flagDefault) {
        FlagDefault = flagDefault;
    }
}

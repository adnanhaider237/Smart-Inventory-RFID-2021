package com.muazam.tech.smartinventory20.Model;

public class ModelSummary {
    String Session, Site, Location, StartTime, CloseTime, Status, no_of_tags;

    public ModelSummary(String session, String site, String location, String startTime, String closeTime, String status, String no_of_tags) {
        Session = session;
        Site = site;
        Location = location;
        StartTime = startTime;
        CloseTime = closeTime;
        Status = status;
        this.no_of_tags = no_of_tags;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String closeTime) {
        CloseTime = closeTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNo_of_tags() {
        return no_of_tags;
    }

    public void setNo_of_tags(String no_of_tags) {
        this.no_of_tags = no_of_tags;
    }
}

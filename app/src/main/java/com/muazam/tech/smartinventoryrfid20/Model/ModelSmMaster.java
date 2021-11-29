package com.muazam.tech.smartinventoryrfid20.Model;

public class ModelSmMaster {
    public String SmCode;
    public String Smname;
    public String SmnameA;
    public String Smtel1;
    public String Smrating;
    public String Designation;
    public String email;
    public String mob;
    public String faxno;

    public ModelSmMaster(String smCode, String smname, String smnameA, String smtel1, String smrating, String designation, String email, String mob, String faxno) {
        SmCode = smCode;
        Smname = smname;
        SmnameA = smnameA;
        Smtel1 = smtel1;
        Smrating = smrating;
        Designation = designation;
        this.email = email;
        this.mob = mob;
        this.faxno = faxno;
    }

    public String getSmCode() {
        return SmCode;
    }

    public void setSmCode(String smCode) {
        SmCode = smCode;
    }

    public String getSmname() {
        return Smname;
    }

    public void setSmname(String smname) {
        Smname = smname;
    }

    public String getSmnameA() {
        return SmnameA;
    }

    public void setSmnameA(String smnameA) {
        SmnameA = smnameA;
    }

    public String getSmtel1() {
        return Smtel1;
    }

    public void setSmtel1(String smtel1) {
        Smtel1 = smtel1;
    }

    public String getSmrating() {
        return Smrating;
    }

    public void setSmrating(String smrating) {
        Smrating = smrating;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno;
    }
}

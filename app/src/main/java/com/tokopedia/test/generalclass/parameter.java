package com.tokopedia.test.generalclass;

/**
 * Created by dist-admin on 11/30/2017.
 */

public class parameter {
    private String asourceid;
    private String asourcename;
    private boolean astatus = true;

    public void setAsourcename(String ival) {
        asourcename = ival;
    }

    public String getAsourcename() {
        return asourcename;
    }

    public void set_asourceid(String ival) {
        asourceid = ival;
    }

    public String get_asourceid() {
        return asourceid;
    }

    public void  set_status(boolean istatus){
        astatus = istatus;
    }

    public boolean get_status(){
        return astatus;
    }
}

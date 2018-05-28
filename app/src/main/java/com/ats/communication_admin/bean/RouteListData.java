package com.ats.communication_admin.bean;

import java.util.List;

/**
 * Created by MAXADMIN on 17/2/2018.
 */

public class RouteListData {

    List<Route> route;
    Info info;

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "RouteListData{" +
                "route=" + route +
                ", info=" + info +
                '}';
    }
}

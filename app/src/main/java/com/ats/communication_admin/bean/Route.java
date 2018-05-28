package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 17/2/2018.
 */

public class Route {

    private int routeId;
    private String routeName;
    private int delStatus;

    public Route(int routeId, String routeName, int delStatus) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.delStatus = delStatus;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", routeName='" + routeName + '\'' +
                ", delStatus=" + delStatus +
                '}';
    }
}

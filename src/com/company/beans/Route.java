package com.company.beans;

public class Route {
    private int id;
    private String originId;
    private String destinationId;

    public Route(int id, String originId, String destinationId) {
        this.id = id;
        this.originId = originId;
        this.destinationId = destinationId;
    }

    public Route()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
}

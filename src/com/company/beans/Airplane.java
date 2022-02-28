package com.company.beans;

import com.company.dao.AirplaneTypeDao;

public class Airplane {
    private int id;
    private int typeId;

    public Airplane(int id, int typeId) {
        this.id = id;
        this.typeId = typeId;
    }

    public Airplane()
    {

    }

    public int getId() {
        return id;
    }

    public int seats() {
        AirplaneTypeDao atd = new AirplaneTypeDao();
        AirplaneType at = atd.getById(this.typeId);
        return at.getMaxCapacity();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}

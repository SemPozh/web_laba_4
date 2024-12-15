package com.itmo.backend.model.dto;

public class ShotDTO {
    private int x;
    private double y;
    private int r;
    private boolean byAreaClick;

    public ShotDTO(){

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public boolean isByAreaClick() {
        return byAreaClick;
    }

    public void setByAreaClick(boolean byAreaClick) {
        this.byAreaClick = byAreaClick;
    }
}

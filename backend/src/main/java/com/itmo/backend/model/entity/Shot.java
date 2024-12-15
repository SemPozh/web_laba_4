package com.itmo.backend.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shots")
public class Shot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x")
    private int x;
    @Column(name = "y")
    private double y;
    @Column(name="r")
    private int r;
    @Column(name = "result")
    private boolean result;
    @Column(name = "byAreaClick")
    private boolean byAreaClick;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    public Shot(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public boolean isByAreaClick() {
        return byAreaClick;
    }

    public void setByAreaClick(boolean byAreaClick) {
        this.byAreaClick = byAreaClick;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

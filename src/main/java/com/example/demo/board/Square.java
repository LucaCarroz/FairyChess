package com.example.demo.board;

import javafx.scene.layout.StackPane;

public class Square extends StackPane {

    private final int x,y;
    private boolean occupied;
    private final String name;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupancy(boolean occupied){
        this.occupied = occupied;
    }

    public String getName() {
        return name;
    }

    public Square(int x, int y, String name){
        this.x = x;
        this.y = y;
        this.occupied = false;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Square";
    }




}

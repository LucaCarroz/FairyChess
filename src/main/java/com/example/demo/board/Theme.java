package com.example.demo.board;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum Theme {
    CORAL("#b1e4b9", "#70a2a3"),
    DUSK("#cbb7ae", "#716677"),
    WHEAT("#eaefce", "#bbbe65"),
    MARINE("#9dacff", "#6f74d2"),
    EMERALD("#adbd90", "#6e8f72"),
    SANDCASTLE("#e4c16f", "#b88b4a");


    Theme(String color1, String color2){
        this.color1 = Color.web(color1);
        this.color2 = Color.web(color2);
    }
    public final Paint color1;
    public final Paint color2;
}

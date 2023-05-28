package com.example.demo.utils;

import java.util.Arrays;
import java.util.List;

public class Math {
    public static int min(int... ints){
        return Arrays.stream(ints).min().orElse(-1);
    }
    public static int max(int... ints){
        return Arrays.stream(ints).max().orElse(-1);
    }
}

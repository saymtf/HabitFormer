package com.saymtf.habitformer;

/**
 * Created by mitchellfenton on 7/23/16.
 * Times are in the 1000ms == 1sec
 * 10000ms = 10sec
 * 100000ms = 1min
 * 1000000ms = 10min
 * ...
 */
public enum HabitTypes {
    READ ("read", 1500000),
    WRITE ("write", 1200000),
    PROGRAM ("program", 2500000);

    private final String name;
    private final int time;

    HabitTypes(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

}

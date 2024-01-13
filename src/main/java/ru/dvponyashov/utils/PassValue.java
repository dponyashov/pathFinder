package ru.dvponyashov.utils;

public enum PassValue {
    FREE_POINT(0),
    LITTLE_SLOW(1),
    MIDDLE_SLOW(2),
    VERY_SLOW(3),
    STOP_VALUE(4);

    private final int value;
    PassValue(int value){
        this.value = value;
    }
    public int value(){
        return value;
    }

    public static PassValue fromValue(int value) {
        for (final PassValue v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        return FREE_POINT;
    }
}

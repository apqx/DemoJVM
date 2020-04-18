package me.apqx.utils;


public class StringUtilJ {
    public final static StringUtilJ INSTANCE;

    static {
        INSTANCE = new StringUtilJ();
    }

    private StringUtilJ() {}

    public String sayHello(String input) {
        return "Hello " + input;
    }

}

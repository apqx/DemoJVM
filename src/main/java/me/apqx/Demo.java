package me.apqx;


import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Demo {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(Math.exp(i));
        }
        System.out.println(1e2);
    }



}
class Tools {
    private Object object;

    private Man man = new Man(object);

    public Tools() {
        object = "3";
    }
}

class Man {
    public Man(Object object) {
        System.out.println(object == null);
    }
}

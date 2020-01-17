package me.apqx;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Demo {
    public static void main(String[] args) {
        String str = "{\n" +
                "            \"name\": \"MA\",\n" +
                "            \"id\": 4,\n" +
                "            \"param\": {\n" +
                "                \"MA1\": 5,\n" +
                "                \"MA2\": 10,\n" +
                "                \"MA3\": 20,\n" +
                "                \"MA4\": 30,\n" +
                "                \"MA5\": 60\n" +
                "            },\n" +
                "            \"isOpen\": 1\n" +
                "        }";
        Obj obj = new GsonBuilder().create().fromJson(str, Obj.class);
        System.out.println(obj.param);
    }

    class Obj {
        JsonObject param;
    }

}

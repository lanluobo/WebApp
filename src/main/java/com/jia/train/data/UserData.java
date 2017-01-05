package com.jia.train.data;

import com.jia.train.po.Passenger;
import java.util.List;

/**
 * Created by jiaxl on 2017/1/5.
 */
public class UserData {
    private static List<Passenger>passengers;
    private static String name;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserData.name = name;
    }

    public static List<Passenger> getPassengers() {
        return passengers;
    }

    public static void setPassengers(List<Passenger> passengers) {
        UserData.passengers = passengers;
    }

}

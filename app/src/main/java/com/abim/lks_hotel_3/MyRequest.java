package com.abim.lks_hotel_3;

public class MyRequest {
    private static final String baseURl = "http://192.168.92.61:987/";
    private static final String loginURL = "api/login";
    private static final String fdURL = "api/fd";
    private static final String roomURL = "api/room";
    private static final String checkOutURL = "api/fdcheckout";

    public static String getBaseURl() {
        return baseURl;
    }

    public static String getLoginURL() {
        return getBaseURl() + loginURL;
    }

    public static String getFdURL() {
        return getBaseURl() + fdURL;
    }

    public static String getRoomURL() {
        return getBaseURl() + roomURL;
    }

    public static String getCheckOutURL() {
        return getBaseURl() + checkOutURL;
    }
}

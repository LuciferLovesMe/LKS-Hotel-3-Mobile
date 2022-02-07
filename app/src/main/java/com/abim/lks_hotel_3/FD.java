package com.abim.lks_hotel_3;

public class FD {
    private int id, price;
    private String name;

    public FD(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}

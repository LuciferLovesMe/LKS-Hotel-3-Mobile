package com.abim.lks_hotel_3;

public class Room {
    private int reser_id, roomNumber;

    public Room(int reser_id, int roomNumber) {
        this.reser_id = reser_id;
        this.roomNumber = roomNumber;
    }

    public int getReser_id() {
        return reser_id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}

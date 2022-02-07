package com.abim.lks_hotel_3;

public class Cart {
    private int emp_id, reservation_id, qty, total, fdid;
    private String fdName;

    public Cart(int emp_id, int reservation_id, int qty, int total, int fdid, String fdName) {
        this.emp_id = emp_id;
        this.reservation_id = reservation_id;
        this.qty = qty;
        this.total = total;
        this.fdid = fdid;
        this.fdName = fdName;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public int getQty() {
        return qty;
    }

    public int getTotal() {
        return total;
    }

    public int getFdid() {
        return fdid;
    }

    public String getFdName() {
        return fdName;
    }
}

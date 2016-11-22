package com.example.nn.qlsinhvien.models;


public class Sinhvien {
    private String MSSV;
    private  String HOTEN;
    private  String LOP;
    private int ID;

    public final static String TABLE_NAME = "sinhvien";
    public final static String COL_MSSV = "MSSV";
    public final static String COL_HOTEN = "HOTEN";
    public final static String COL_LOP = "LOP";
    public final static String WHERECLAUSE = "ID=?";

    public String getHOTEN() {
        return HOTEN;
    }

    public void setHOTEN(String HOTEN) {
        this.HOTEN = HOTEN;
    }

    public String getLOP() {
        return LOP;
    }

    public void setLOP(String LOP) {
        this.LOP = LOP;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String toString() {
        return "MSSV: "+MSSV+" - Name: "+HOTEN+" - Class: "+LOP;
    }
}

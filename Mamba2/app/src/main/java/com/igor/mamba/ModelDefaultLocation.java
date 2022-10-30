package com.igor.mamba;

public class ModelDefaultLocation {

    private String droplocationname;
    private int droplocationdistance;
    private  String addresstype;
    private   String recievername;
    private   int isActive;
    private  String dropadress;
    
    public ModelDefaultLocation() {

    }

    public String getDroplocationname() {
        return droplocationname;
    }

    public void setDroplocationname(String droplocationname) {
        this.droplocationname = droplocationname;
    }

    public int getDroplocationdistance() {
        return droplocationdistance;
    }

    public void setDroplocationdistance(int droplocationdistance) {
        this.droplocationdistance = droplocationdistance;
    }

    public String getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype;
    }

    public String getRecievername() {
        return recievername;
    }

    public void setRecievername(String recievername) {
        this.recievername = recievername;
    }

    public String getDropadress() {
        return dropadress;
    }

    public void setDropadress(String dropadress) {
        this.dropadress = dropadress;
    }



    public int getActive() {
        return isActive;
    }

    public void setActive(int active) {
        isActive = active;
    }
}

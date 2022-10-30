package com.igor.mamba;

import java.io.Serializable;

public class Product implements Serializable
{
    int product_id;
    int quant;
    String product_title;
    String product_price;
    String product_img1;
    String unitsStored;
    String product_desc;
    String RemainingUnits;
    String status;

    public Product() {
    }

    public Product(int product_id, int quant,String product_title, String product_price, String product_img1,String unitsStored,String RemainingUnits,String product_desc,String status) {
        this.product_id = product_id;
        this.quant = quant;
        this.product_title = product_title;
        this.product_price = product_price;
        this.product_img1 = product_img1;
        this.unitsStored = unitsStored;
        this.RemainingUnits = RemainingUnits;
        this.product_desc = product_desc;
        this.status = status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_img1() {
        return product_img1;
    }

    public void setProduct_img1(String product_img1) {
        this.product_img1 = product_img1;
    }

    public String getUnitsStored() {
        return unitsStored;
    }

    public void setUnitsStored(String unitsStored) {
        this.unitsStored = unitsStored;
    }

    public String getRemainingUnits() {
        return RemainingUnits;
    }

    public void setRemainingUnits(String remainingUnits) {
        RemainingUnits = remainingUnits;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

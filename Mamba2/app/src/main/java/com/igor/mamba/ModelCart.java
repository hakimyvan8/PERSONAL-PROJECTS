package com.igor.mamba;

public class ModelCart
{
    private int cart_id;
    private Object user_id;
    private int p_id;
    private String totalCpst;
    private String p_qty;
    private String p_title;
    private String p_NetPrice;
    private String t_kgs;

    private String product_img1;
   private String ToatalcartCost;

    public ModelCart() {

    }


    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public Object getUser_id() {
        return user_id;
    }

    public void setUser_id(Object user_id) {
        this.user_id = user_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getTotalCpst() {
        return totalCpst;
    }

    public void setTotalCpst(String totalCpst) {
        this.totalCpst = totalCpst;
    }

    public String getP_qty() {
        return p_qty;
    }

    public void setP_qty(String p_qty) {
        this.p_qty = p_qty;
    }

    public String getP_title() {
        return p_title;
    }

    public void setP_title(String p_title) {
        this.p_title = p_title;
    }

    public String getP_NetPrice() {
        return p_NetPrice;
    }

    public void setP_NetPrice(String p_NetPrice) {
        this.p_NetPrice = p_NetPrice;
    }

    public String getToatalcartCost() {
        return ToatalcartCost;
    }

    public void setToatalcartCost(String toatalcartCost) {
        ToatalcartCost = toatalcartCost;
    }

    public String getProduct_img1() {
        return product_img1;
    }

    public void setProduct_img1(String product_img1) {
        this.product_img1 = product_img1;
    }

    public String getT_kgs() {
        return t_kgs;
    }

    public void setT_kgs(String t_kgs) {
        this.t_kgs = t_kgs;
    }
}

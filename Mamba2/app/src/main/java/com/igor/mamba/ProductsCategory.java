package com.igor.mamba;

public class ProductsCategory {
    private String p_cat_title;
    private String catproduct_desc;
    private String product_img1;

    public ProductsCategory() {
    }

    public ProductsCategory(String p_cat_title,String catproduct_desc, String product_img1) {
        this.p_cat_title = p_cat_title;
        this.catproduct_desc = catproduct_desc;
        this.product_img1 = product_img1;
    }

    public String getP_cat_title() {
        return p_cat_title;
    }

    public void setP_cat_title(String p_cat_title) {
        this.p_cat_title = p_cat_title;
    }

    public String getCatproduct_desc() {
        return catproduct_desc;
    }

    public void setCatproduct_desc(String catproduct_desc) {
        this.catproduct_desc = catproduct_desc;
    }

    public String getProduct_img1() {
        return product_img1;
    }

    public void setProduct_img1(String product_img1) {
        this.product_img1 = product_img1;
    }
}

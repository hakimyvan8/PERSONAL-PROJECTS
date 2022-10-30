package com.igor.mamba;

import android.graphics.drawable.Icon;

public class timeModel {

           private Icon imageIcon;
           private String messageString;
           private String date;
           private OrderStatus status;

    public timeModel(Icon imageIcon, String messageString, String date, OrderStatus status) {
        this.imageIcon = imageIcon;
        this.messageString = messageString;
        this.date = date;
        this.status = status;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }



    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Icon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(Icon imageIcon) {
        this.imageIcon = imageIcon;
    }
}

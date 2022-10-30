package com.igor.mamba;


public class URLs {
    public static final String IP ="172.17.8.24";
    public static final String ROOT_URL = "http://"+IP+"/admin_area/Customer/";
    public static final String URL_REGISTER = ROOT_URL+"register.php";
    public static final String URL_LOGIN= ROOT_URL+"login.php";
    public static final String URL_LOGINDRIVER= ROOT_URL+"driverlogin.php";
    public static final String URL_RESSETPASS= "http://"+IP+"/admin_area/resetpassword.php";
    public static final String URL_STATUS= ROOT_URL+"statuschecker.php";
    public static final String URL_ADDTOCART= ROOT_URL+"cart.php";
    public static final String URL_SUPPLIERLOGIN= ROOT_URL+"loginSUPPLIER.php";
    public static final String URL_ADDTOORDER= ROOT_URL+"orderitem.php";
    public static final String postdata = ROOT_URL+"updateadress.php" ;
    public static final String getdefaultlocations =" http://"+URLs.IP+"/admin_area/Customer/getshippingdetails.php";
    public static final String PURCHASEDITEMS = "http://"+URLs.IP+"/admin_area/Customer/loadorderitems.php";
    public static String driver  = ROOT_URL+"driverorder.php";
    public static String accept   =ROOT_URL+"acceptdriver.php";
    public static String confirm   =ROOT_URL+"confirmorder.php";
}

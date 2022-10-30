<?php

if(!isset($_SESSION['supplierEmail'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>


<?php

$admin_session = $_SESSION['supplierEmail'];

$get_admin = "select * from supplier where supplierEmail='$admin_session'";

$run_admin = mysqli_query($con,$get_admin);

$row_admin = mysqli_fetch_array($run_admin);

$supplier_id = $row_admin['supId'];

$admin_name = $row_admin['supplierBusiness'];

$admin_email = $row_admin['supplierEmail'];

$admin_contact = $row_admin['supplierPhone'];

?>



<!DOCTYPE html>

<html>

<head>

<title> Insert Listing </title>


<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({ selector:'#product_desc,#product_features' });</script>

</head>

<body>

<div class="row"><!-- row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<ol class="breadcrumb"><!-- breadcrumb Starts -->

<li class="active">

<i class="fa fa-dashboard"> </i> Dashboard / Insert Item Listing

</li>

</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- row Ends -->



<div class="row"><!-- 2 row Starts --> 

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<div class="panel panel-default"><!-- panel panel-default Starts -->

<div class="panel-heading"><!-- panel-heading Starts -->

<h3 class="panel-title">

<i class="fa fa-money fa-fw"></i> Insert Item Title

</h3>

</div><!-- panel-heading Ends -->

<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Title </label>

<div class="col-md-6" >

<input type="text" name="item_title" class="form-control" required >

</div>

</div><!-- form-group Ends -->



<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Variant I </label>

<div class="col-md-6" >

<select name="item_cat" class="form-control" >

<option> Select  Item Category </option>


<?php

$get_p_cats = "select * from itemcategory";

$run_p_cats = mysqli_query($con,$get_p_cats);

while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

$p_cat_id = $row_p_cats['itemCatID'];

$p_cat_title = $row_p_cats['Category'];

echo "<option value='$p_cat_title' >$p_cat_title</option>";

}

?>

</select>

</div><!-- form-group Ends --> <br> <br> <br>



<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" >Item Quantity I (KG) </label>

<div class="col-md-6" >

<input type="text" name="Item_quantity" class="form-control" required >

</div>

</div><!-- form-group Ends -->





<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Category II </label>

<div class="col-md-6" >

<select name="item_catII" class="form-control" >

<option> Select  Item Category </option>



<?php

$get_p_cats = "select * from itemcategory";

$run_p_cats = mysqli_query($con,$get_p_cats);

while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

$p_cat_id = $row_p_cats['itemCatID'];

$p_cat_title = $row_p_cats['Category'];

echo "<option value='$p_cat_title' >$p_cat_title</option>";

}

?>

</select>

</div><!-- form-group Ends --> <br> <br> <br>




<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" >Item Quantity II(KG) </label>

<div class="col-md-6" >

<input type="text" name="Item_quantityII" class="form-control" required >

</div>

</div><!-- form-group Ends -->




<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Category III </label>

<div class="col-md-6" >

<select name="item_catIII" class="form-control" >

<option> Select  Item Category </option>


<?php

$get_p_cats = "select * from itemcategory";

$run_p_cats = mysqli_query($con,$get_p_cats);

while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

$p_cat_id = $row_p_cats['itemCatID'];

$p_cat_title = $row_p_cats['Category'];

echo "<option value='$p_cat_title' >$p_cat_title</option>";

}

?>

</select>

</div><!-- form-group Ends --> <br> <br> <br>




<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" >Item Quantity III (KG) </label>

<div class="col-md-6" >

<input type="text" name="Item_quantityIII" class="form-control" required >

</div>

</div><!-- form-group Ends -->






<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Price/ KG </label>

<div class="col-md-6" >

<input type="text" name="item_price" class="form-control" required >

</div>

</div><!-- form-group Ends -->


      


<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" ></label>

<div class="col-md-6" >

<input type="submit" name="submit" value="Insert Listing" class="btn btn-primary form-control" >

</div>

</div><!-- form-group Ends -->

</form><!-- form-horizontal Ends -->

</div><!-- panel-body Ends -->

</div><!-- panel panel-default Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- 2 row Ends --> 


</body>

</html>

<?php

if(isset($_POST['submit'])) {


    $item_title= $_POST['item_title'];

    $item_cat= $_POST['item_cat'];

    $item_catII= $_POST['item_catII'];

    $item_catIII= $_POST['item_catIII'];

    $Item_quantity = $_POST['Item_quantity'];
    
    $Item_quantityII = $_POST['Item_quantityII'];
    
    $Item_quantityIII = $_POST['Item_quantityIII'];
    
    $item_price = $_POST['item_price'];
    
    
    $insert_supplier = "insert into supplierlisting (title,category,InitialPrice,supplierId,quantity,categoryII,categoryIII,quantityII,quantityIII) values ('$item_title','$item_cat','$item_price',$supplier_id,'$Item_quantity','$item_catII','$item_catIII','$Item_quantityII','$Item_quantityIII')";
    
    $run_supplier = mysqli_query($con,$insert_supplier);
    
    
    if($run_supplier){
    
    echo "<script>alert('Supplier Has Been Inserted successfully')</script>";
    
    
    }
    
    
    }
    
    
    ?>

<?php } ?>

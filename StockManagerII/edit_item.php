<?php

if(!isset($_SESSION['admin_email'])){

    echo "<script>window.open('login.php','_self')</script>";

}

else {

?>

<?php

if(isset($_GET['edit_item'])){

$edit_id = $_GET['edit_item'];

$get_p = "select * from supplier where supId='$edit_id'";

$run_edit = mysqli_query($con,$get_p);

$row_edit = mysqli_fetch_array($run_edit);

$supId = $row_edit['supId'];

$supplierBusiness= $row_edit['supplierBusiness'];

$supplierPhone = $row_edit['supplierPhone'];

$itemTitle = $row_edit['itemTitle'];


}
?>


<!DOCTYPE html>

<html>

<head>

<title> Order Items </title>


<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({ selector:'#product_desc,#product_features' });</script>

</head>

<body>

<div class="row"><!-- row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<ol class="breadcrumb"><!-- breadcrumb Starts -->

<li class="active">

<i class="fa fa-dashboard"> </i> Dashboard / Order Items

</li>

</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- row Ends -->


<div class="row"><!-- 2 row Starts --> 

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<div class="panel panel-default"><!-- panel panel-default Starts -->

<div class="panel-heading"><!-- panel-heading Starts -->

<h3 class="panel-title">

<i class="fa fa-money fa-fw"></i> Order Item
</h3>

</div><!-- panel-heading Ends -->

<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Supplier Business </label>

<div class="col-md-6" >

<input type="text" name="title" class="form-control" required value="<?php echo $supplierBusiness; ?>">

</div>

</div><!-- form-group Ends -->



<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Supplier Phone </label>

<div class="col-md-6" >

<input type="text" name="phone" class="form-control" required value="<?php echo $supplierPhone; ?>" >

</div>

</div><!-- form-group Ends -->


 

    <div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Title </label>

<div class="col-md-6" >

<select name="itemTitle" class="form-control" >

<option> Select  a Product Category </option>

<?php

$get_p_cats = "select * from supplierlisting where supplierId='$edit_id'  ";

$run_p_cats = mysqli_query($con,$get_p_cats);

while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

$p_cat_id = $row_p_cats['listID'];

$title = $row_p_cats['title'];

echo "<option value='$p_cat_id' >$title</option>";

}


?>
</select>

</div><!-- form-group Ends --> <br> <br> <br>



<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Variant </label>

<div class="col-md-6" >


<select name="itemVar" class="form-control" >

<option> Select  an Item Variant </option>

<option> No Item </option>

<?php

$get_p_cats = "select * from supplierlisting where supplierId='$edit_id'  ";

$run_p_cats = mysqli_query($con,$get_p_cats);

while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

$list_id = $row_p_cats['supplierId'];

$cat = $row_p_cats['category'];

echo "<option value='$cat' >$cat</option>";

}


?>
</select>

</div><!-- form-group Ends --> <br> <br> <br>





<div class="form-group" ><!-- form-group Starts -->

            <label class="col-md-3 control-label" > Quantity (KG) </label>

            <div class="col-md-6" >

                <input type="text" name="quantity" class="form-control" required >

            </div>

        </div><!-- form-group Ends -->





<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Variant II </label>

<div class="col-md-6" >


<select name="itemVarII" class="form-control" >

<option> Select  an Item Variant </option>

<option> No Item </option>

<?php

$get_p_cats = "select * from supplierlisting where supplierId='$edit_id'  ";

$run_p_cats = mysqli_query($con,$get_p_cats);

while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

$list_id = $row_p_cats['supplierId'];

$cat = $row_p_cats['categoryII'];

echo "<option value='$cat' >$cat</option>";

}


?>
</select>

</div><!-- form-group Ends --> <br> <br> <br>





<div class="form-group" ><!-- form-group Starts -->

            <label class="col-md-3 control-label" > Quantity (KG) </label>

            <div class="col-md-6" >

                <input type="text" name="quantityII" class="form-control" required >

            </div>

        </div><!-- form-group Ends -->




<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Variant III </label>

<div class="col-md-6" >

<select name="itemVarIII" class="form-control" >

<option> Select  an Item Variant </option>

<option> No Item </option>

<?php

$get_p_cats = "select * from supplierlisting where supplierId='$edit_id'  ";

$run_p_cats = mysqli_query($con,$get_p_cats);

while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

$list_id = $row_p_cats['supplierId'];

$cat = $row_p_cats['categoryIII'];

echo "<option value='$cat' >$cat</option>";

}


?>
</select>

</div><!-- form-group Ends --> <br> <br> <br>



<div class="form-group" ><!-- form-group Starts -->

            <label class="col-md-3 control-label" > Quantity (KG) </label>

            <div class="col-md-6" >

                <input type="text" name="quantityIII" class="form-control" required >

            </div>

        </div><!-- form-group Ends -->





        
        <div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Price/KG </label>

<div class="col-md-6" >

<select name="itemprice" class="form-control" >

<option> Select Item Price </option>

<?php

$get_p_cats = "select * from supplierlisting where supplierId='$edit_id'  ";

$run_p_cats = mysqli_query($con,$get_p_cats);

while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

$list_id = $row_p_cats['supplierId'];

$cat = $row_p_cats['InitialPrice'];

echo "<option value='$cat' >$cat</option>";

}


?>
</select>

</div><!-- form-group Ends --> <br> <br> <br>





        <div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Recipient </label>

<div class="col-md-6" >

    <input type="text" name="recipient" class="form-control" required >

</div>

</div><!-- form-group Ends -->





<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Recipient Phone </label>

<div class="col-md-6" >

    <input type="text" name="recipientPhone" class="form-control" required >

</div>

</div><!-- form-group Ends -->





<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Location </label>

<div class="col-md-6" >

    <input type="text" name="location" class="form-control" required >

</div>

</div><!-- form-group Ends -->




   
<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" ></label>

<div class="col-md-6" >

<input type="submit" name="submit" value="Update Product" class="btn btn-primary form-control" >

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

if(isset($_POST['submit'])){

$title = $_POST['title'];
$phone = $_POST['phone'];
$itemTitle = $_POST['itemTitle'];
$itemVar = $_POST['itemVar'];
$itemVarII = $_POST['itemVarII'];
$itemVarIII = $_POST['itemVarIII'];
$quantity = $_POST['quantity'];
$quantityII = $_POST['quantityII'];
$quantityIII= $_POST['quantityIII'];
$recipient = $_POST['recipient'];
$recipientPhone = $_POST['recipientPhone'];
$itemprice = $_POST['itemprice'];
$location = $_POST['location'];
$status = 'pending';


$insert_supplier = "insert into supplierorder (supplierId,itemtitle,itemCat,itemquantity,supplierName,supplierPhone,status,recipientName,recipientPhone,location,date,itemCatII,itemCatIII,itemquantityII,itemquantityIII,price) values ('$supId','$itemTitle','$itemVar','$quantity','$title','$phone','$status','$recipient',
'$recipientPhone','$location',NOW(),'$itemVarII','$itemVarIII','$quantityII','$quantityIII','$itemprice')";
    
$run_supplier = mysqli_query($con,$insert_supplier);


if($run_supplier){

echo "<script>alert('Order Has been Placed successfully')</script>";


}
}

?>

<?php } ?>

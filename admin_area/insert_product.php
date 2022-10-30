<?php

if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>



<!DOCTYPE html>

<html>

<head>

<title> Insert Products </title>


<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({ selector:'#product_desc,#product_features' });</script>

</head>

<body>

<div class="row"><!-- row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<ol class="breadcrumb"><!-- breadcrumb Starts -->

<li class="active">

<i class="fa fa-dashboard"> </i> Dashboard / Insert Products

</li>

</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- row Ends -->


<div class="row"><!-- 2 row Starts --> 

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<div class="panel panel-default"><!-- panel panel-default Starts -->

<div class="panel-heading"><!-- panel-heading Starts -->

<h3 class="panel-title">

<i class="fa fa-money fa-fw"></i> Insert Products

</h3>

</div><!-- panel-heading Ends -->

<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Product Title </label>

<div class="col-md-6" >

<input type="text" name="product_title" class="form-control" required >

</div>

</div><!-- form-group Ends -->



<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Product Category </label>

<div class="col-md-6" >

<select name="product_cat" class="form-control" >

<option> Select  a Product Category </option>


<?php

$get_p_cats = "select * from category";

$run_p_cats = mysqli_query($con,$get_p_cats);

while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

$p_cat_id = $row_p_cats['id'];

$p_cat_title = $row_p_cats['title'];

echo "<option value='$p_cat_id' >$p_cat_title</option>";

}

?>

</div><!-- form-group Ends -->



<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Product Image 1 </label>

<div class="col-md-6" >

<input type="file" name="product_img1" class="form-control" required >

</div>

</div><!-- form-group Ends -->


<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Product Price </label>

<div class="col-md-6" >

<input type="text" name="product_price" class="form-control" required >

</div>

</div><!-- form-group Ends -->


        <div class="form-group" ><!-- form-group Starts -->

            <label class="col-md-3 control-label" > Units Stored </label>

            <div class="col-md-6" >

                <input type="text" name="product_units" class="form-control" required >

            </div>

        </div><!-- form-group Ends -->
        

        <div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" >  Department </label>

<div class="col-md-6" >

<select name="admins_dept" class="form-control" >

<option> Select  Department </option>


<?php

$get_admin = "select * from admins ";

$run_admin = mysqli_query($con,$get_admin);

while ($row_admin=mysqli_fetch_array($run_admin)) {

$admin_id = $row_admin['admin_id'];

$admin_job = $row_admin['admin_job'];

echo "<option value='$admin_id' >$admin_job</option>";

}
?>


</div><!-- form-group Ends -->




<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Product Tabs </label>

<div class="col-md-6" >

<ul class="nav nav-tabs"><!-- nav nav-tabs Starts -->

<li class="active">

<a data-toggle="tab" href="#description"> Product Description </a>

</li>

</ul><!-- nav nav-tabs Ends -->

<div class="tab-content"><!-- tab-content Starts -->

<div id="description" class="tab-pane fade in active"><!-- description tab-pane fade in active Starts -->

<br>

<textarea name="product_desc" class="form-control" rows="15" id="product_desc">

</textarea>

</div><!-- description tab-pane fade in active Ends -->

</div><!-- tab-content Ends -->


</div>

</div><!-- form-group Ends -->


<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" ></label>

<div class="col-md-6" >

<input type="submit" name="submit" value="Insert Product" class="btn btn-primary form-control" >

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

    $product_title = $_POST['product_title'];
    $product_cat = $_POST['product_cat'];
    $product_price = $_POST['product_price'];
    $admins_dept = $_POST['admins_dept'];
    $product_desc = $_POST['product_desc'];
    $product_units = $_POST['product_units'];


    $response = array();

    $product_img1 = $_FILES['product_img1']['name'];

    $temp_name1 = $_FILES['product_img1']['tmp_name'];
    move_uploaded_file($temp_name1, "product_images/$product_img1");

    $insert_product = "insert into product(userId,title,metaTitle,summary,price,quantity,updatedAt,publishedAt,startsAt,endsAt,product_img1) 
    values (0,'$product_title','$product_title','$product_desc','$product_price','$product_units',NOW(),NOW(),NOW(),NOW(),'$product_img1')";

    $run_product = mysqli_query($con, $insert_product);

    if ($run_product) {

        echo "<script>alert('Product has been inserted successfully')</script>";


        $stmt = $con->prepare($sql = "SELECT title,metaTitle,summary,price,quantity,product_img1 FROM product;");


        $stmt ->execute();
        $stmt -> bind_result($p_title,$p_Ttile,$p_desc,$p_price,$product_quant,$p_img);

        $products = array();

        while($stmt ->fetch()){

            $temp = array();

            $temp['title'] = $p_title;
            $temp['metaTitle'] = $p_Ttile;
            $temp['summary'] = $p_desc;
            $temp['product_price'] = $p_price;
            $temp['quantity'] = $product_quant;
            $temp['product_img1'] = $p_img;


            array_push($products,$temp);


        }
    }
}


?>

<?php } ?>

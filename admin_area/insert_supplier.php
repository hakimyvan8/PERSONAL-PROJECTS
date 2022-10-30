<?php



if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>

<div class="row" ><!-- 1  row Starts -->

<div class="col-lg-12" ><!-- col-lg-12 Starts -->

<ol class="breadcrumb" ><!-- breadcrumb Starts -->

<li class="active" >

<i class="fa fa-dashboard" ></i> Dashboard / Insert Supplier

</li>



</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- 1  row Ends -->

<div class="row" ><!-- 2 row Starts -->

<div class="col-lg-12" ><!-- col-lg-12 Starts -->

<div class="panel panel-default" ><!-- panel panel-default Starts -->

<div class="panel-heading" ><!-- panel-heading Starts -->

<h3 class="panel-title" >

<i class="fa fa-money fa-fw" ></i> Insert Supplier

</h3>


</div><!-- panel-heading Ends -->


<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

<div class="form-group"><!-- form-group Starts -->





<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label">Supplier Business Name: </label>

<div class="col-md-6"><!-- col-md-6 Starts -->

<input type="text" name="supplier_business" class="form-control" required>

</div><!-- col-md-6 Ends -->

</div><!-- form-group Ends -->





<label class="col-md-3 control-label">TaxNo: </label>

<div class="col-md-6"><!-- col-md-6 Starts -->

<input type="text" name="TaxNo" class="form-control" required>

</div><!-- col-md-6 Ends -->

</div><!-- form-group Ends -->


<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label">Supplier Email: </label>

<div class="col-md-6"><!-- col-md-6 Starts -->

<input type="text" name="supplier_email" class="form-control" required>

</div><!-- col-md-6 Ends -->

</div><!-- form-group Ends -->


<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label">Supplier Password: </label>

<div class="col-md-6"><!-- col-md-6 Starts -->

<input type="password" name="supplier_pass" class="form-control" required>

</div><!-- col-md-6 Ends -->

</div><!-- form-group Ends -->



<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label">Supplier State: </label>

<div class="col-md-6"><!-- col-md-6 Starts -->
            
<input type="text" name="supplier_state" class="form-control" required>

</div><!-- col-md-6 Ends -->

</div><!-- form-group Ends -->


    <div class="form-group"><!-- form-group Starts -->

        <label class="col-md-3 control-label">Supplier district: </label>

        <div class="col-md-6"><!-- col-md-6 Starts -->
            
       <input type="text" name="supplier_district" class="form-control" required>

        </div><!-- col-md-6 Ends -->

    </div><!-- form-group Ends -->




<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label">Supplier phone: </label>

<div class="col-md-6"><!-- col-md-6 Starts -->

<input type="text" name="supplier_phone" class="form-control" required>

</div><!-- col-md-6 Ends -->

</div><!-- form-group Ends -->



<div class="form-group"><!-- form-group Starts -->

<label class="col-md-3 control-label"></label>

<div class="col-md-6"><!-- col-md-6 Starts -->

<input type="submit" name="submit" value="Insert User" class="btn btn-primary form-control">

</div><!-- col-md-6 Ends -->

</div><!-- form-group Ends -->


</form><!-- form-horizontal Ends -->

</div><!-- panel-body Ends -->

</div><!-- panel panel-default Ends -->

</div><!-- col-lg-12 Ends -->


</div><!-- 2 row Ends -->


<?php



if(isset($_POST['submit'])){




$TaxNo= $_POST['TaxNo'];

$supplier_email = $_POST['supplier_email'];

$supplier_pass = $_POST['supplier_pass'];

$supplier_state = $_POST['supplier_state'];

$supplier_district = $_POST['supplier_district'];

$supplier_Business = $_POST['supplier_business'];

$supplier_phone = $_POST['supplier_phone'];
    
    

$uuid = uniqid('', true);

$insert_supplier = "insert into supplier (supplier_id,TaxNo,supplierEmail,supplierPass,supplierState,supplierDistrict,supplierBusiness,supplierPhone) values ('$uuid','$TaxNo','$supplier_email','$supplier_pass','$supplier_state','$supplier_district','$supplier_Business','$supplier_phone')";

$run_supplier = mysqli_query($con,$insert_supplier);


if($run_supplier){

echo "<script>alert('Supplier Has Been Inserted successfully')</script>";


}


}


?>



<?php }  ?>
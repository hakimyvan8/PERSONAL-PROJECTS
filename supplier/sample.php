<?php

if(!isset($_SESSION['admin_email'])){

    echo "<script>window.open('login.php','_self')</script>";

}

else {

    ?>

    <?php

    if(isset($_GET['edit_order'])){

        $edit_id = $_GET['edit_order'];

        $get_p = "select * from orderdetails where customer_id='$edit_id'";

        $run_edit = mysqli_query($con,$get_p);

        $row_edit = mysqli_fetch_array($run_edit);

        $customerid = $row_edit['customer_id'];

        $orderNo = $row_edit['orderNo'];

        $invoiceNo = $row_edit['invoiceNo'];

        $orderdate = $row_edit['orderdate'];

        $orderstatus = $row_edit['orderstatus'];

        $state = $row_edit['state'];

        $district = $row_edit['district'];

        $sector = $row_edit['sector'];

        $firstname = $row_edit['firstname'];

        $shippingcost = $row_edit['shippingcost'];

    }

    ?>


    <!DOCTYPE html>

    <html>

    <head>

        <title> Order Details </title>


        <script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
        <script>tinymce.init({ selector:'#product_desc,#product_features' });</script>

    </head>

    <body>

    <div class="row"><!-- row Starts -->

        <div class="col-lg-12"><!-- col-lg-12 Starts -->

            <ol class="breadcrumb"><!-- breadcrumb Starts -->

                <li class="active">

                    <i class="fa fa-dashboard"> </i>  View Order Details

                </li>

                <div class="container py-5">

                    <button onclick="window.print()" name="btn-btn-primary" class="btn">Print Out</button>

                </div>

            </ol><!-- breadcrumb Ends -->

        </div><!-- col-lg-12 Ends -->

    </div><!-- row Ends -->


    <div class="row"><!-- 2 row Starts -->

        <div class="col-lg-12"><!-- col-lg-12 Starts -->

            <div class="panel panel-default"><!-- panel panel-default Starts -->

                <div class="panel-heading"><!-- panel-heading Starts -->

                    <h3 class="panel-title">

                        <i class="fa fa-eye fa-fw"></i> View Order Details

                    </h3>

                </div><!-- panel-heading Ends -->

                <div class="panel-body"><!-- panel-body Starts -->

                    <form class="form-horizontal" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->

                        <div class="form-group" ><!-- form-group Starts -->

                            <label class="col-md-3 control-label" > Product Title </label>

                            <div class="col-md-6" >

                                <input type="text" name="product_title" class="form-control" required value="<?php echo $firstname; ?>">

                            </div>

                        </div><!-- form-group Ends -->



                        <div class="form-group" ><!-- form-group Starts -->

                            <label class="col-md-3 control-label" > Product Category </label>

                            <div class="col-md-6" >

                                <select name="product_cat" class="form-control" >

                                    <option value="<?php echo $p_cat; ?>" > </option>


                                    <?php

                                    $get_p_cats = "select * from product_categories";

                                    $run_p_cats = mysqli_query($con,$get_p_cats);

                                    while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

                                        $p_cat_id = $row_p_cats['p_cat_id'];

                                        $p_cat_title = $row_p_cats['p_cat_title'];

                                        echo "<option value='$p_cat_id' >$p_cat_title</option>";

                                    }


                                    ?>


                                </select>

                            </div>

                        </div><!-- form-group Ends -->

                        <div class="form-group" ><!-- form-group Starts -->

                            <label class="col-md-3 control-label" > Product Image 1 </label>

                            <div class="col-md-6" >

                                <input type="file" name="product_img1" class="form-control" >
                                <br><img src="product_images/<?php echo $p_image1; ?>" width="70" height="70" >

                            </div>

                        </div><!-- form-group Ends -->


                        <div class="form-group" ><!-- form-group Starts -->

                            <label class="col-md-3 control-label" > Product Price </label>

                            <div class="col-md-6" >

                                <input type="text" name="product_price" class="form-control" required value="<?php echo $p_price; ?>" >

                            </div>

                        </div><!-- form-group Ends -->


                        <div class="form-group" ><!-- form-group Starts -->

                            <label class="col-md-3 control-label" > Units Stored </label>

                            <div class="col-md-6" >

                                <input type="text" name="product_units" class="form-control" required value="<?php echo $p_Units; ?>" >

                            </div>

                        </div><!-- form-group Ends -->


                        <div class="form-group" ><!-- form-group Starts -->

                            <label class="col-md-3 control-label" > Units Remaining </label>

                            <div class="col-md-6" >

                                <input type="text" name="product_RemUnits" class="form-control" required value="<?php echo $p_RemUnits; ?>" >

                            </div>

                        </div><!-- form-group Ends -->



                        <div class="form-group" ><!-- form-group Starts -->

                            <label class="col-md-3 control-label" > status </label>

                            <div class="col-md-6" >

                                <input type="text" name="status" class="form-control" required value="<?php echo $status; ?>" >

                            </div>

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

<?php echo $p_desc; ?>

</textarea>

                                    </div><!-- description tab-pane fade in active Ends -->


                                </div><!-- tab-content Ends -->

                            </div>

                        </div><!-- form-group Ends -->

                        <div class="form-group" ><!-- form-group Starts -->

                            <label class="col-md-3 control-label" ></label>

                            <div class="col-md-6" >

                                <input type="submit" name="update" value="Update Product" class="btn btn-primary form-control" >

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

    if(isset($_POST['update'])){

        $product_title = $_POST['product_title'];
        $product_cat = $_POST['product_cat'];
        $product_price = $_POST['product_price'];
        $product_desc = $_POST['product_desc'];
        $product_units = $_POST['product_units'];
        $product_RemUnits = $_POST['product_RemUnits'];
        $status = $_POST['status'];

        $product_img1 = $_FILES['product_img1']['name'];

        $temp_name1 = $_FILES['product_img1']['tmp_name'];

        if(empty($product_img1)){

            $product_img1 = $new_p_image1;

        }


        move_uploaded_file($temp_name1,"product_images/$product_img1");

        $update_product = "update products set p_cat_id='$product_cat',date=NOW(),product_title='$product_title',product_img1='$product_img1',product_price='$product_price',product_desc='$product_desc',unitsStored='$product_units',RemainingUnits='$product_RemUnits',status='$status' where product_id='$p_id'";

        $run_product = mysqli_query($con,$update_product);

        if($run_product){

            echo "<script> alert('Product has been updated successfully') </script>";


        }

    }

    ?>

<?php } ?>

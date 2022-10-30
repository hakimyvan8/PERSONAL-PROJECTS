<?php
require_once 'includes/db.php';

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

                                    $get_p_cats = "select * from product_categories";

                                    $run_p_cats = mysqli_query($con,$get_p_cats);

                                    while ($row_p_cats=mysqli_fetch_array($run_p_cats)) {

                                        $p_cat_id = $row_p_cats['p_cat_id'];

                                        $p_cat_title = $row_p_cats['p_cat_title'];

                                        echo "<option value='$p_cat_id' >$p_cat_title</option>";

                                    }


                                    ?>

                            </div><!-- form-group Ends -->

                            <div class="form-group" ><!-- form-group Starts -->






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

    if(isset($_POST['submit'])){

        $product_title = $_POST['product_title'];
        $product_cat = $_POST['product_cat'];
        $product_price = $_POST['product_price'];
        $product_desc = $_POST['product_desc'];

        $status = "product";

        $product_img1 = $_FILES['product_img1']['name'];

        $temp_name1 = $_FILES['product_img1']['tmp_name'];
        move_uploaded_file($temp_name1,"product_images/$product_img1");

        $insert_product = "insert into products (p_cat_id,date,product_title,product_img1,product_price,product_desc,status) values ('$product_cat',NOW(),'$product_title','$product_img1','$product_price','$product_desc','$status')";

        $run_product = mysqli_query($con,$insert_product);

        if($run_product){

            echo "<script>alert('Product has been inserted successfully')</script>";

            echo "<script>window.open('index.php?view_products','_self')</script>";

        }

    }

    ?>

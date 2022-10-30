
<?php

if(!isset($_SESSION['supplierEmail'])){

    echo "<script>window.open('login.php','_self')</script>";

}

else{

    ?>

    <?php

    if(isset($_GET['edit_order'])){

        $edit_id = $_GET['edit_order'];

        $get_p = "SELECT  * FROM supplierorder WHERE orderid = $edit_id;  ";


        $run_edit = mysqli_query($con,$get_p);

        $row_edit = mysqli_fetch_array($run_edit);

        $id = $row_edit['id'];
        $orderid = $row_edit['orderid'];
        $Name = $row_edit['recipientName'];
        $phone = $row_edit['recipientPhone'];
        $createdAt= $row_edit['date'];
        $status= $row_edit['status'];

    }
    
    ?>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <div class="row"><!-- 1 row Starts -->

        <div class="col-lg-12"><!-- col-lg-12 Starts -->

            <h1 class="page-header">Order Number: #<?php echo $orderid; ?></h1>

            <ol class="breadcrumb"><!-- breadcrumb Starts -->

                <li class="active">

                    <i class="fa fa-dashboard"></i> Dashboard

                </li>

            </ol><!-- breadcrumb Ends -->

        </div><!-- col-lg-12 Ends -->

    </div><!-- 1 row Ends -->


    <div class="row"><!-- 2 row Starts -->

        <div class="col-lg-6 col-md-6"><!-- col-lg-3 col-md-6 Starts -->

            <div class="panel panel-white"><!-- panel panel-primary Starts -->

                <div class="panel-heading"><!-- panel-heading Starts -->

                    <div class="row"><!-- panel-heading row Starts -->

                        <div class="col-xs-9 text-left" style="font-size: large"><!-- col-xs-9 text-right Starts -->

                            <div style="color:darkgreen">Order Created at</div>

                            <div> <b> <?php echo $createdAt; ?> </b> </div>


                            <div style="color:mediumvioletred">Full Name</div>

                            <div> <b> <?php echo $row_edit['recipientName']; ?> </b> </div>


                            <div style="color:lightskyblue">Phone</div>

                            <div> <b> <?php echo $row_edit['recipientPhone'];; ?> </b> </div>


                        </div><!-- col-xs-9 text-right Ends -->


                    </div><!-- panel-heading row Ends -->

                </div><!-- panel-heading Ends -->

            </div><!-- panel panel-primary Ends -->

        </div><!-- col-lg-3 col-md-6 Ends -->



<div class="row" ><!-- 3 row Starts -->

<div class="col-lg-8" ><!-- col-lg-8 Starts -->

    <div class="panel panel-white" ><!-- panel panel-primary Starts -->

        <div class="panel-heading" ><!-- panel-heading Starts -->

            <h3 class="panel-title" style="font-size: large" > <b> <!-- panel-title Starts --> </b>
                                  Ordered Items

            </h3><!-- panel-title Ends -->

        </div><!-- panel-heading Ends -->

        <div class="panel-body" ><!-- panel-body Starts -->

            <div class="table-responsive" ><!-- table-responsive Starts -->

                <table class="table table-bordered table-hover table-striped" ><!-- table table-bordered table-hover table-striped Starts -->

                    <thead><!-- thead Starts -->

                    <tr>
                        <th>No:</th>
                        <th> Product Name:</th>
                        <th> Product Name:</th>
                        <th> Product Name:</th>
                        <th>Quantity OrderedI:</th>
                        <th>Quantity OrderedII:</th>
                        <th>Quantity OrderedIII:</th>
                        <th>Price:</th>
                        <th>Net Price:</th>
                        <th>Total Price</th>
                    </tr>

                    </thead><!-- thead Ends -->

                    <tbody><!-- tbody Starts -->

                    <?php

                    $i = 0;

                    $get_orderitem =  "SELECT  * FROM supplierorder WHERE orderid = $edit_id; ";


                    $run_orderitem = mysqli_query($con,$get_orderitem);

                    while($row_orderitem = mysqli_fetch_array($run_orderitem)){

                       
                      $order_no = $row_orderitem['orderid'];

                      $itemCat = $row_orderitem['itemCat'];

                      $itemCatII = $row_orderitem['itemCatII'];

                      $itemCatIII = $row_orderitem['itemCatIII'];

                      $itemquantity = $row_orderitem['itemquantity'];

                      $itemquantityII = $row_orderitem['itemquantityII'];

                      $itemquantityIII = $row_orderitem['itemquantityIII'];

                      $price = $row_orderitem['price'];

                      $createdAt= $row_orderitem['date'];

                        $i++;

                        ?>

                        <tr>

                            <td><?php echo $i; ?></td>
                            <td><?php echo $itemCat; ?></td>
                            <td><?php echo $itemCatII; ?></td>
                            <td><?php echo $itemCatIII; ?></td>
                            <td><?php echo $itemquantity; ?></td>
                            <td><?php echo $itemquantityII; ?></td>
                            <td><?php echo $itemquantityIII; ?></td>
                            <td><?php echo $price; ?></td>
                            <td><?php echo $createdAt; ?></td>

                        </tr>

                    <?php } ?>

                    </tbody><!-- tbody Ends -->

                </table><!-- table table-bordered table-hover table-striped Ends -->

            </div><!-- table-responsive Ends -->

            <div class="text-right" style="font-size: large" ><!-- text-right Starts -->

            <?php 
            $query = "SELECT SUM(price) AS sum FROM supplierorder WHERE orderId='$orderid'";
            $query_result = mysqli_query($con,$query);
            while($row = mysqli_fetch_assoc($query_result)){

                $output = $row['sum'];
                $outputII = $output * ($itemquantity + $itemquantityII + $itemquantityIII) ;
            }

            ?>

            <div>Sub Total: RWF&nbsp; &nbsp;<span> <strong> <b> <?php echo $outputII; ?> </b> </strong> </span> </div>&nbsp;

            </div><!-- text-right Ends -->
                
            <div class="col-md-6"style="color:green">

<input type="submit" name="approve" value="Approve Order" class="btn btn-primary form-control color:green" >

</div>


<div class="col-md-6"style="color:red">

<input  type="submit" name="decline" value="Decline Order" class="btn btn-primary form-control" >
</div>

<?php

if(isset($_POST['approve'])) {

    $product_title = $_POST['product_title'];
    $product_cat = $_POST['product_cat'];
    $product_price = $_POST['product_price'];
    $product_desc = $_POST['product_desc'];
    $product_units = $_POST['product_units'];
    $product_RemUnits = $_POST['product_RemUnits'];
    $status = $_POST['status'];

    $response = array();

    $product_img1 = $_FILES['product_img1']['name'];

    $temp_name1 = $_FILES['product_img1']['tmp_name'];
    move_uploaded_file($temp_name1, "product_images/$product_img1");

    $insert_product = "insert into products (p_cat_id,date,product_title,product_img1,product_price,product_desc,status,unitsStored,RemainingUnits) values ('$product_cat',NOW(),'$product_title','$product_img1','$product_price','$product_desc',
    '$status','$product_units','$product_RemUnits')";

    $run_product = mysqli_query($con, $insert_product);

    if ($run_product) {

        echo "<script>alert('Product has been inserted successfully')</script>";


        $stmt = $con->prepare($sql = "SELECT product_title,product_img1,product_price,product_desc,status,unitsStored,RemainingUnits FROM products;");


        $stmt ->execute();
        $stmt -> bind_result($p_title,  $p_img,$p_price,$product_desc,$status,$product_units,$product_RemUnits);

        $products = array();

        while($stmt ->fetch()){

            $temp = array();

            $temp['product_title'] = $p_title;
            $temp['product_img1'] = $p_img;
            $temp['product_price'] = $p_price;
            $temp['product_desc'] = $product_desc;
            $temp['status'] = $status;
            $temp['unitsStored'] = $product_units;
            $temp['RemainingUnits'] = $product_RemUnits;


            array_push($products,$temp);


        }
        $response['products'] = $products;
        echo json_encode($response);
    }
}


?>

   
</script>
 <?php } ?>
 
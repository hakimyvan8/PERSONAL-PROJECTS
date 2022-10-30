<?php


if(!isset($_SESSION['admin_email'])){

    echo "<script>window.open('login.php','_self')</script>";

}

else {


    ?>

    <div class="row"><!-- 1 row Starts -->

        <div class="col-lg-12"><!-- col-lg-12 Starts -->

            <ol class="breadcrumb"><!-- breadcrumb Starts  --->

                <li class="active">

                    <i class="fa fa-dashboard"></i> Dashboard / View Orders

                </li>

                <div class="container py-5">

                    <button onclick="window.print()" name="btn-btn-primary" class="btn">Print Out</button>

                </div>

            </ol><!-- breadcrumb Ends  --->

        </div><!-- col-lg-12 Ends -->

    </div><!-- 1 row Ends -->


    <div class="row"><!-- 2 row Starts -->

        <div class="col-lg-12"><!-- col-lg-12 Starts -->

            <div class="panel panel-default"><!-- panel panel-default Starts -->

                <div class="panel-heading"><!-- panel-heading Starts -->

                    <h3 class="panel-title"><!-- panel-title Starts -->

                        <i class="fa fa-money fa-fw"></i> View Orders

                    </h3><!-- panel-title Ends -->

                </div><!-- panel-heading Ends -->

                <div class="panel-body"><!-- panel-body Starts -->

                    <div class="table-responsive"><!-- table-responsive Starts -->

                        <table class="table table-bordered table-hover table-striped"><!-- table table-bordered table-hover table-striped Starts -->

                            <thead><!-- thead Starts -->

                            <tr>

                                <th>No:</th>
                                <th>Order No:</th>
                                <th>Invoice:</th>
                                <th>Name:</th>
                                <th>State</th>
                                <th>District</th>
                                <th>Sector</th>
                                <th>Order Status:</th>

                            </tr>

                            </thead><!-- thead Ends -->


                            <tbody><!-- tbody Starts -->

                            <?php

                            $i = 0;

                            $get_orders = "select * from orderdetails  order by 1 DESC LIMIT 0,5";

                            $run_orders = mysqli_query($con,$get_orders);

                            while ($row_orders = mysqli_fetch_array($run_orders)) {

                                $uuid = $row_orders['customer_id'];

                                $order_no = $row_orders['orderNo'];

                                $name = $row_orders['firstname'];

                                $Invoice = $row_orders['invoiceNo'];

                                $state = $row_orders['state'];

                                $district = $row_orders['district'];

                                $sector = $row_orders['sector'];

                                $order_status = $row_orders['orderstatus'];

                                $i++;

                                ?>

                                <tr>

                                    <td><?php echo $i; ?></td>

                                    <td><?php echo $order_no; ?></td>

                                    <td bgcolor="yellow" ><?php echo $Invoice; ?></td>

                                    <td><?php echo $name; ?></td>

                                    <td><?php echo $state; ?></td>

                                    <td><?php echo $district; ?></td>

                                    <td><?php echo $sector; ?></td>

                                    <td>
                                        <?php

                                        $get_customer_order = "select * from orderdetails where customer_id='$uuid'";

                                        $run_customer_order = mysqli_query($con,$get_customer_order);

                                        $row_customer_order = mysqli_fetch_array($run_customer_order);

                                        $order_date = $row_customer_order['order_date'];

                                        $due_amount = $row_customer_order['due_amount'];

                                        echo $order_date;

                                        ?>
                                    </td>

                                    <td>$<?php echo $due_amount; ?></td>

                                    <td>
                                        <?php

                                        if($order_status=='pending'){

                                            echo $order_status='pending';

                                        }
                                        else{

                                            echo $order_status='Complete';

                                        }


                                        ?>
                                    </td>


                                </tr>

                            <?php } ?>

                            </tbody><!-- tbody Ends -->

                        </table><!-- table table-bordered table-hover table-striped Ends -->

                    </div><!-- table-responsive Ends -->

                </div><!-- panel-body Ends -->

            </div><!-- panel panel-default Ends -->

        </div><!-- col-lg-12 Ends -->

    </div><!-- 2 row Ends -->


<?php } ?>
<?php

include("securityFinance.php");
    ?>

    <div class="row"><!-- 1 row Starts -->

        <div class="col-lg-12"><!-- col-lg-12 Starts -->

            <h1 class="page-header">Dashboard</h1>

            <ol class="breadcrumb"><!-- breadcrumb Starts -->

                <li class="active">

                    <i class="fa fa-dashboard"></i> Dashboard

                </li>

            </ol><!-- breadcrumb Ends -->

        </div><!-- col-lg-12 Ends -->

    </div><!-- 1 row Ends -->


        <div class="col-lg-3 col-md-6"><!-- col-lg-3 col-md-6 Starts -->

            <div class="panel panel-red"><!-- panel panel-red Starts -->

                <div class="panel-heading"><!-- panel-heading Starts -->

                    <div class="row"><!-- panel-heading row Starts -->

                        <div class="col-xs-3"><!-- col-xs-3 Starts -->

                            <i class="fa fa-support fa-5x"> </i>

                        </div><!-- col-xs-3 Ends -->

                        <div class="col-xs-9 text-right"><!-- col-xs-9 text-right Starts -->

                            <div class="huge"> <?php echo $count_pending_orders; ?> </div>

                            <div>Orders</div>

                        </div><!-- col-xs-9 text-right Ends -->

                    </div><!-- panel-heading row Ends -->

                </div><!-- panel-heading Ends -->

                <a href="indexFINANCE.php?view_orders">

                    <div class="panel-footer"><!-- panel-footer Starts -->

                        <span class="pull-left"> View Details </span>

                        <span class="pull-right"> <i class="fa fa-arrow-circle-right"></i> </span>

                        <div class="clearfix"></div>

                    </div><!-- panel-footer Ends -->

                </a>

            </div><!-- panel panel-red Ends -->

        </div><!-- col-lg-3 col-md-6 Ends -->


    </div><!-- 2 row Ends -->

    <div class="row" ><!-- 3 row Starts -->

        <div class="col-lg-8" ><!-- col-lg-8 Starts -->

            <div class="panel panel-primary" ><!-- panel panel-primary Starts -->

                <div class="panel-heading" ><!-- panel-heading Starts -->

                    <h3 class="panel-title" ><!-- panel-title Starts -->

                        <i class="fa fa-money fa-fw" ></i> New Orders

                    </h3><!-- panel-title Ends -->

                </div><!-- panel-heading Ends -->

                <div class="panel-body" ><!-- panel-body Starts -->

                    <div class="table-responsive" ><!-- table-responsive Starts -->

                        <table class="table table-bordered table-hover table-striped" ><!-- table table-bordered table-hover table-striped Starts -->

                            <thead><!-- thead Starts -->

                            <tr>
                                <th>No:</th>
                                <th>User ID:</th>
                                <th>Order No</th>
                                <th>Shipping Cost</th>
                                <th>Total</th>
                                <th>Created At</th>
                                <th>Payment</th>

                            </tr>

                            </thead><!-- thead Ends -->

                            <tbody><!-- tbody Starts -->

                            <?php

                            $i = 0;

                            $get_order = "select * from orders order by 1 DESC LIMIT 0,5";
                            $run_order = mysqli_query($con,$get_order);

                            while($row_order=mysqli_fetch_array($run_order)){

                                $uuid = $row_order['id'];

                                $order_no = $row_order['userId'];

                                $shipping = $row_order['shipping'];

                                $Total = $row_order['grandTotal'];

                                $createdAt= $row_order['createdAt'];

                                $payment = $row_order['paymentsecreat'];

                                $i++;

                                ?>

                                <tr>

                                    <td><?php echo $i; ?></td>
                                    <td>
                                        <?php echo $uuid; ?>
                                    </td>

                                    <td><?php echo $order_no; ?></td>
                                    <td><?php echo $shipping; ?></td>
                                    <td><?php echo $Total; ?></td>
                                    <td><?php echo $createdAt; ?></td>
                                    <td><?php echo $payment; ?></td>

                                </tr>

                            <?php } ?>

                            </tbody><!-- tbody Ends -->


                        </table><!-- table table-bordered table-hover table-striped Ends -->

                    </div><!-- table-responsive Ends -->

                    <div class="text-right" ><!-- text-right Starts -->

                        <a href="indexFINANCE.php?view_orders" >

                            View All Orders <i class="fa fa-arrow-circle-right" ></i>

                        </a>

                    </div><!-- text-right Ends -->


                </div><!-- panel-body Ends -->

            </div><!-- panel panel-primary Ends -->

        </div><!-- col-lg-8 Ends -->

        <div class="col-md-4"><!-- col-md-4 Starts -->

            <div class="panel"><!-- panel Starts -->

                <div class="panel-body"><!-- panel-body Starts -->

                    <div class="mb-md"><!-- mb-md Starts -->

                        <div class="widget-content-expanded"><!-- widget-content-expanded Starts -->

                            <i class="fa fa-user"></i> <span>Email: </span> <?php echo $admin_email; ?>  <br>

                        </div><!-- widget-content-expanded Ends -->

                        <hr class="dotted short">

                        <h5 class="text-muted">About</h5>

                        <p>
                            <?php echo $admin_about; ?>
                        </p>

                    </div><!-- mb-md Ends -->

                </div><!-- panel-body Ends -->

            </div><!-- panel Ends -->

        </div><!-- col-md-4 Ends -->

    </div><!-- 3 row Ends -->

<?php ?>
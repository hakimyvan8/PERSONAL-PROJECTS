<?php

if(!isset($_SESSION['admin_email'])){

    echo "<script>window.open('login.php','_self')</script>";

}

else {


    ?>

    <div class="row"><!-- 1 row Starts -->

        <div class="col-lg-12"><!-- col-lg-12 Starts -->

            <ol class="breadcrumb"><!-- breadcrumb Starts -->

                <li class="active">

                    <i class="fa fa-dashboard"></i> Dashboard / View Customers

                </li>

            </ol><!-- breadcrumb Ends -->

        </div><!-- col-lg-12 Ends -->

    </div><!-- 1 row Ends -->

    <div class="row"><!-- 2 row Starts -->

        <div class="col-lg-12"><!-- col-lg-12 Starts -->

            <div class="panel panel-default"><!-- panel panel-default Starts -->

                <div class="panel-heading"><!-- panel-heading Starts -->

                    <h3 class="panel-title"><!-- panel-title Starts -->

                        <i class="fa fa-money fa-fw"></i> View Customers

                    </h3><!-- panel-title Ends -->

                </div><!-- panel-heading Ends -->


                <div class="panel-body" ><!-- panel-body Starts -->

                    <div class="table-responsive" ><!-- table-responsive Starts -->

                        <table class="table table-bordered table-hover table-striped" id="manageUsers" ><!-- table table-bordered table-hover table-striped Starts -->

                            <thead><!-- thead Starts -->

                            <tr>

                            <th>user No:</th>
<th>First Name:</th>
<th>Last Name:</th>
<th>Email:</th>
<th>Password:</th>
<th>Phone Number:</th>
<th>Status:</th>
    <th>Customer Delete:</th>
                                <th>Approve Customer:</th>

                            </tr>

                            </thead><!-- thead Ends -->


                            <tbody><!-- tbody Starts -->

                            <?php

                            $i=0;

                            $get_c = "select * from users where status='pending'";

                            $run_c = mysqli_query($con,$get_c);

                            while($row_c=mysqli_fetch_array($run_c)){

                                $c_id = $row_c['id'];

                                $c_name = $row_c['firstName'];
                                
                                $c_lname = $row_c['lastName'];
                                
                                $c_email = $row_c['email'];
                                
                                $c_password = $row_c['passwordHash'];
                                
                                $c_contact = $row_c['mobile'];
                                
                                $c_status= $row_c['status'];

                                $i++;




                                ?>

                                <tr>

                                <td><?php echo $i; ?></td>

<td><?php echo $c_name; ?></td>

<td><?php echo $c_lname; ?></td>

    <td><?php echo $c_email; ?></td>

    <td><?php echo $c_password; ?></td>

<td><?php echo $c_contact; ?></td>

    <td><?php echo $c_status; ?></td>

                                    <td>

                                        <a href="index.php?customer_delete=<?php echo $c_id; ?>" >

                                            <i class="fa fa-trash-o" ></i> Delete

                                        </a>


                                    </td>

                                    <td>

                                        <a href="index.php?customer_approve=<?php echo $c_id; ?>" >

                                            <i class="fa fa-pencil" ></i> Approve

                                        </a>


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
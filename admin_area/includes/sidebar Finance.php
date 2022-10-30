<?php

include("securityFinance.php")

    ?>

    <nav class="navbar navbar-inverse navbar-fixed-top" ><!-- navbar navbar-inverse navbar-fixed-top Starts -->

        <div class="navbar-header" ><!-- navbar-header Starts -->

            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse" ><!-- navbar-ex1-collapse Starts -->


                <span class="sr-only" >Toggle Navigation</span>

                <span class="icon-bar" ></span>

                <span class="icon-bar" ></span>

                <span class="icon-bar" ></span>


            </button><!-- navbar-ex1-collapse Ends -->

            <a class="navbar-brand" href="indexFINANCE.php?dashboardF" >Admin Panel</a>


        </div><!-- navbar-header Ends -->

        <ul class="nav navbar-right top-nav" ><!-- nav navbar-right top-nav Starts -->

            <li class="dropdown" ><!-- dropdown Starts -->

                <a href="#" class="dropdown-toggle" data-toggle="dropdown" ><!-- dropdown-toggle Starts -->

                    <i class="fa fa-user" ></i>

                    <?php echo $admin_name; ?> <b class="caret" ></b>


                </a><!-- dropdown-toggle Ends -->

                <ul class="dropdown-menu" ><!-- dropdown-menu Starts -->

                    <li><!-- li Starts -->

                        <a href="indexFINANCE.php?user_profile=<?php echo $admin_id; ?>" >

                            <i class="fa fa-fw fa-user" ></i> Profile


                        </a>

                    </li><!-- li Ends -->



                    <li><!-- li Starts -->

                        <a href="logout.php">

                            <i class="fa fa-fw fa-power-off"> </i> Log Out

                        </a>

                    </li><!-- li Ends -->

                </ul><!-- dropdown-menu Ends -->




            </li><!-- dropdown Ends -->


        </ul><!-- nav navbar-right top-nav Ends -->

        <div class="collapse navbar-collapse navbar-ex1-collapse"><!-- collapse navbar-collapse navbar-ex1-collapse Starts -->

            <ul class="nav navbar-nav side-nav"><!-- nav navbar-nav side-nav Starts -->

                <li><!-- li Starts -->

                    <a href="indexFINANCE.php?dashboardFINANCE">

                        <i class="fa fa-fw fa-dashboard"></i> Dashboard

                    </a>

                </li><!-- li Ends -->


                <li><!-- contact us li Starts -->

                    <a href="#" data-toggle="collapse" data-target="#contact_us"><!-- anchor Starts -->

                        <i class="fa fa-fw fa-pencil"> </i> Contact Us Section

                        <i class="fa fa-fw fa-caret-down"></i>

                    </a><!-- anchor Ends -->

                    <ul id="contact_us" class="collapse">

                        <li>

                            <a href="indexFINANCE.php?edit_contact_us"> Edit Contact Us </a>

                        </li>

                        <li>

                            <a href="indexFINANCE.php?insert_enquiry"> Insert Enquiry Type </a>

                        </li>

                        <li>

                            <a href="indexFINANCE.php?view_enquiry"> View Enquiry Types </a>

                        </li>

                    </ul>

                </li><!-- contact us li Ends -->

                <li><!-- about us li Starts -->

                    <a href="indexFINANCE.php?edit_about_us">

                        <i class="fa fa-fw fa-edit"></i> Edit About Us Page

                    </a>

                </li><!-- about us li Ends -->


                <li>

                    <a href="indexFINANCE.php?view_orders">

                        <i class="fa fa-fw fa-list"></i> View Orders

                    </a>

                </li>

                <li>

                    <a href="indexFINANCE.php?view_payments">

                        <i class="fa fa-fw fa-pencil"></i> View Payments

                    </a>

                </li>

                <li><!-- li Starts -->

                    <a href="logout.php">

                        <i class="fa fa-fw fa-power-off"></i> Log Out

                    </a>

                </li><!-- li Ends -->

            </ul><!-- nav navbar-nav side-nav Ends -->

        </div><!-- collapse navbar-collapse navbar-ex1-collapse Ends -->

    </nav><!-- navbar navbar-inverse navbar-fixed-top Ends -->

<?php ?>
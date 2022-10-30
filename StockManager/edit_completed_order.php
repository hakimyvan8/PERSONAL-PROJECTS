
<?php

if(!isset($_SESSION['admin_email'])){

    echo "<script>window.open('login.php','_self')</script>";

}

else{

    ?>

    <?php

    if(isset($_GET['edit_order'])){

        $edit_id = $_GET['edit_order'];

        $get_p = "SELECT order_item.quantity,order_item.price,product.title, orders.id,orders.shipping,orders.grandtotal,
        orders.paymentsecreat,orders.province,orders.firstName,orders.lastName,orders.mobile,orders.email,orders.createdAt
         FROM order_item INNER JOIN orders on order_item.orderId = orders.id 
         LEFT JOIN product on order_item.productId = product.id WHERE order_item.orderId = $edit_id;  ";


        $run_edit = mysqli_query($con,$get_p);

        $row_edit = mysqli_fetch_array($run_edit);

        $id = $row_edit['id'];
        $orderid = $row_edit['id'];
        $price = $row_edit['price'];
        $createdAt= $row_edit['createdAt'];

    }
    
    ?>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <div class="row"><!-- 1 row Starts -->

        <div class="col-lg-12"><!-- col-lg-12 Starts -->

            <h1 class="page-header">Order Number: #<?php echo $orderid; ?></h1>


            <div class="container py-5">

            <button onclick="window.print()" name="btn-btn-primary" class="btn">Print Out</button>

            </div>
s

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

                            <div> <b> <?php echo $row_edit['firstName']; ?> <?php echo $row_edit['lastName']; ?> </b> </div>


                            <div style="color:lightskyblue">Phone</div>

                            <div> <b> <?php echo $row_edit['mobile'];; ?> </b> </div>


                            <div style="color:deepskyblue">Email</div>

                            <div> <b> <?php echo$row_edit['email'];?> </b> </div>



                            <div style="color:deepskyblue">Delivery Dropoint</div>

                            <div> <b> <?php echo $row_edit['province'].'/'.'Rwanda'; ?> </b> </div>


                            <div style="color:red">Transaction ID</div>

                            <div> <b> <?php echo $row_edit['paymentsecreat'];; ?> </b> </div>


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
                        <th>Quantity Ordered:</th>
                        <th>Net Price:</th>
                        <th>Total Price</th>
                    </tr>

                    </thead><!-- thead Ends -->

                    <tbody><!-- tbody Starts -->

                    <?php

                    $i = 0;

                    $get_orderitem =  "SELECT order_item.quantity,order_item.price,product.title, orders.id,orders.shipping,orders.grandtotal,
                    orders.paymentsecreat,orders.city,orders.firstName,orders.lastName,orders.mobile,orders.email,orders.createdAt
                     FROM order_item INNER JOIN orders on order_item.orderId = orders.id 
                     LEFT JOIN product on order_item.productId = product.id WHERE order_item.orderId = $edit_id;";


                    $run_orderitem = mysqli_query($con,$get_orderitem);

                    while($row_orderitem = mysqli_fetch_array($run_orderitem)){

                        $uuid = $row_orderitem['id'];

                        $productTitle = $row_orderitem['title'];

                        $productQuantity= $row_orderitem['quantity'];

                        $InitPrice = $row_orderitem['price'];

                        $totalPrice = $row_orderitem['grandtotal'];

                        $i++;

                        ?>

                        <tr>

                            <td><?php echo $i; ?></td>
                            <td><?php echo $productTitle; ?></td>
                            <td><?php echo $productQuantity; ?></td>
                            <td><?php echo $InitPrice; ?></td>
                            <td><?php echo $totalPrice; ?></td>

                        </tr>

                    <?php } ?>

                    </tbody><!-- tbody Ends -->

                </table><!-- table table-bordered table-hover table-striped Ends -->

            </div><!-- table-responsive Ends -->

            <div class="text-right" style="font-size: large" ><!-- text-right Starts -->

            <?php 
            $query = "SELECT SUM(price) AS sum FROM order_item WHERE orderId='$orderid'";
            $query_result = mysqli_query($con,$query);
            while($row = mysqli_fetch_assoc($query_result)){

                $output = $row['sum'];
            }

            ?>

            <div>Sub Total: &nbsp; &nbsp;<span> <strong> <b> <?php echo $output; ?> </b> </strong> </span> </div>&nbsp;

            </div><!-- text-right Ends -->

            
            <div class="text-right" style="font-size: large" ><!-- text-right Starts -->

            <?php 
            $query = "SELECT SUM(price) AS sum FROM order_item WHERE orderId='$orderid'";
            $query_result = mysqli_query($con,$query);
            while($row = mysqli_fetch_assoc($query_result)){

                $output = $row['sum'];
            }

            ?>

            <div>Shipping Fee: &nbsp;<span> <strong> <b> <?php echo $row_edit['shipping'];?> </b> </strong> </span> </div>&nbsp;
                
                
                
                



<div  >


</div>

</div><!-- form-group Ends -->
                

        </div><!-- panel-body Ends -->

    </div><!-- panel panel-primary Ends -->

</div><!-- col-lg-8 Ends -->
<style>
    :root {
  --lightgray: #efefef;
  --blue: steelblue;
  --white: #fff;
  --black: rgba(0, 0, 0, 0.8);
  --bounceEasing: cubic-bezier(0.51, 0.92, 0.24, 1.15);
}

* {
  padding: 0;
  margin: 0;
}


.btn-group {
  text-align: center;
}

.open-modal {
  font-weight: bold;
  background: var(--blue);
  color: var(--white);
  padding: 0.75rem 1.75rem;
  margin-bottom: 1rem;
  border-radius: 5px;
}


/* MODAL
–––––––––––––––––––––––––––––––––––––––––––––––––– */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem;
  background: var(--black);
  cursor: pointer;
  visibility: hidden;
  opacity: 0;
  transition: all 0.35s ease-in;
}

.modal.is-visible {
  visibility: visible;
  opacity: 1;
}

.modal-dialog {
  position: relative;
  max-width: 500px;
  max-height: 75vh;
  border-radius: 5px;
  background: var(--white);
  overflow: auto;
  cursor: default;
}

.modal-dialog > * {
  padding: 1rem;
}

.modal-header,
.modal-footer {
  background: var(--lightgray);
}

.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.modal-header .close-modal {
  font-size: 1.5rem;
}

.modal p + p {
  margin-top: 1rem;
}


/* ANIMATIONS
–––––––––––––––––––––––––––––––––––––––––––––––––– */
[data-animation] .modal-dialog {
  opacity: 0;
  transition: all 0.5s var(--bounceEasing);
}

[data-animation].is-visible .modal-dialog {
  opacity: 1;
  transition-delay: 0.2s;
}

[data-animation="slideInOutDown"] .modal-dialog {
  transform: translateY(100%);
}

[data-animation="slideInOutTop"] .modal-dialog {
  transform: translateY(-100%);
}

[data-animation="slideInOutLeft"] .modal-dialog {
  transform: translateX(-100%);
}

[data-animation="slideInOutRight"] .modal-dialog {
  transform: translateX(100%);
}

[data-animation="zoomInOut"] .modal-dialog {
  transform: scale(0.2);
}

[data-animation="rotateInOutDown"] .modal-dialog {
  transform-origin: top left;
  transform: rotate(-1turn);
}

[data-animation="mixInAnimations"].is-visible .modal-dialog {
  animation: mixInAnimations 2s 0.2s linear forwards;
}

[data-animation="slideInOutDown"].is-visible .modal-dialog,
[data-animation="slideInOutTop"].is-visible .modal-dialog,
[data-animation="slideInOutLeft"].is-visible .modal-dialog,
[data-animation="slideInOutRight"].is-visible .modal-dialog,
[data-animation="zoomInOut"].is-visible .modal-dialog,
[data-animation="rotateInOutDown"].is-visible .modal-dialog {
  transform: none;
}

@keyframes mixInAnimations {
  0% {
    transform: translateX(-100%);
  }

  10% {
    transform: translateX(0);
  }

  20% {
    transform: rotate(20deg);
  }

  30% {
    transform: rotate(-20deg);
  }

  40% {
    transform: rotate(15deg);
  }

  50% {
    transform: rotate(-15deg);
  }

  60% {
    transform: rotate(10deg);
  }

  70% {
    transform: rotate(-10deg);
  }

  80% {
    transform: rotate(5deg);
  }

  90% {
    transform: rotate(-5deg);
  }

  100% {
    transform: rotate(0deg);
  }
}


/* FOOTER
–––––––––––––––––––––––––––––––––––––––––––––––––– */
.page-footer {
  position: absolute;
  bottom: 1rem;
  right: 1rem;
}

.page-footer span {
  color: #e31b23;
}


.button {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 16px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  transition-duration: 0.4s;
  cursor: pointer;
}

.button1 {
  background-color: white; 
  color: black; 
  border: 2px solid #4CAF50;
}

.button1:hover {
  background-color: #4CAF50;
  color: white;
}

.button2 {
  background-color: white; 
  color: black; 
  border: 2px solid #008CBA;
}

.button2:hover {
  background-color: #008CBA;
  color: white;
}
.button3 {
  background-color: white; 
  color: black; 
  border: 2px solid #f44336;
}

.button3:hover {
  background-color: #f44336;
  color: white;
}
* { box-sizing: border-box}
body{
  font-family: 'Asap', sans-serif;
}
.phoneswrapper{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.phone{
  height:  600px;
  width: 300px; 
  padding: 10px 20px;
  border-radius: 32px;
  border: 8px solid #3e3e3e;
  overflow: hidden;
  position: relative;
  box-shadow: 0px 17px 20px rgba(0, 0, 0, 0.15);

}
.phone_head {
    display: flex;
    flex-direction: row;
    justify-content: space-between; 
     align-items: center;
    height: 45px;
}
.phone_head .title{
  font-size: 24px;
  font-weight: 500;
  color: #34394F;
}
.icon_bubble.msg {
    width: 30px;
    height: 30px;
    background: linear-gradient( 90deg, #d13eea, #53d9ea);
    border-radius: 50%;
}

.divider {
    height: 1px;
    width: 111%;
    background:#A52A2A;
    margin-bottom: 12px;
}
.grad_pb{ 
  color: white; 
  }
img.chat_avatar {
    width: 45px;
  height: 40px;
    border-radius: 7px;
  margin-right: 8px;
}
img.chat_avatar {
    width: 45px;
   box-shadow: 1px 6px 18px rgba(31, 37, 72, 0.45);
    border-radius: 7px;
}

.chat {
    display: flex;
    justify-content: space-between;
    padding: 15px 0px;
    border-bottom: 1px solid #d3d3d35e;
}

.contact_name {font-weight: 500;

  color: #34394F;
     font-size: 15px;
    margin-bottom: 2px;}

.contact_msg {
   
     font-size: 11px;
    color: #a5a5a5;
    font-weight: lighter;
}
.chat_info {width: 50%;}

.chat_date {
    font-size: 12px;
    color: #5a5a5a;
  margin-bottom: 2px;
}

.chat_new{
  padding: 2px 5px;
  font-size: 11px;
  border-radius: 2px;
}
.chat_status {
    width: 25%;
    display: flex;
    flex-direction: column;
    align-items: center; 
}

.phone_footer {
    position: absolute;
    bottom: 0;
    height: 61px;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    right: 0px;
    background: white;
}

.footer_divider {
    height: 5px;
    width: 45%;
    border-radius: 10px;
  
     margin-top: 35px;
}
.btn {
  box-sizing: border-box;
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  background-color: transparent;
  border: 2px solid #e74c3c;
  border-radius: 0.6em;
  color: #e74c3c;
  cursor: pointer;
  display: flex;
  align-self: center;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1;
  margin: 20px;
  padding: 1.2em 2.8em;
  text-decoration: none;
  text-align: center;
  text-transform: uppercase;
  font-family: "Montserrat", sans-serif;
  font-weight: 700;
}
.btn:hover, .btn:focus {
  color: #fff;
  outline: 0;
}

.first {
  transition: box-shadow 300ms ease-in-out, color 300ms ease-in-out;
}
.first:hover {
  box-shadow: 0 0 40px 40px #e74c3c inset;
}

.second {
  border-radius: 3em;
  border-color: #1abc9c;
  color: #fff;
  background-image: linear-gradient(to right, rgba(26, 188, 156, 0.6), rgba(26, 188, 156, 0.6) 5%, #1abc9c 5%, #1abc9c 10%, rgba(26, 188, 156, 0.6) 10%, rgba(26, 188, 156, 0.6) 15%, #1abc9c 15%, #1abc9c 20%, rgba(26, 188, 156, 0.6) 20%, rgba(26, 188, 156, 0.6) 25%, #1abc9c 25%, #1abc9c 30%, rgba(26, 188, 156, 0.6) 30%, rgba(26, 188, 156, 0.6) 35%, #1abc9c 35%, #1abc9c 40%, rgba(26, 188, 156, 0.6) 40%, rgba(26, 188, 156, 0.6) 45%, #1abc9c 45%, #1abc9c 50%, rgba(26, 188, 156, 0.6) 50%, rgba(26, 188, 156, 0.6) 55%, #1abc9c 55%, #1abc9c 60%, rgba(26, 188, 156, 0.6) 60%, rgba(26, 188, 156, 0.6) 65%, #1abc9c 65%, #1abc9c 70%, rgba(26, 188, 156, 0.6) 70%, rgba(26, 188, 156, 0.6) 75%, #1abc9c 75%, #1abc9c 80%, rgba(26, 188, 156, 0.6) 80%, rgba(26, 188, 156, 0.6) 85%, #1abc9c 85%, #1abc9c 90%, rgba(26, 188, 156, 0.6) 90%, rgba(26, 188, 156, 0.6) 95%, #1abc9c 95%, #1abc9c 100%);
  background-position: 0 0;
  background-size: 100%;
  transition: background 300ms ease-in-out;
}
.second:hover {
  background-position: 100px;
}

.third {
  border-color: #3498db;
  color: #fff;
  box-shadow: 0 0 40px 40px #3498db inset, 0 0 0 0 #3498db;
  transition: all 150ms ease-in-out;
}
.third:hover {
  box-shadow: 0 0 10px 0 #3498db inset, 0 0 10px 4px #3498db;
}

.fourth {
  border-color: #f1c40f;
  color: #fff;
  background-image: linear-gradient(45deg, #f1c40f 50%, transparent 50%);
  background-position: 100%;
  background-size: 400%;
  transition: background 300ms ease-in-out;
}
.fourth:hover {
  background-position: 0;
}

.fifth {
  border-color: #8e44ad;
  border-radius: 0;
  color: #8e44ad;
  position: relative;
  overflow: hidden;
  z-index: 1;
  transition: color 150ms ease-in-out;
}
.fifth:after {
  content: "";
  position: absolute;
  display: block;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 100%;
  background: #8e44ad;
  z-index: -1;
  transition: width 150ms ease-in-out;
}
.fifth:hover {
  color: #fff;
}
.fifth:hover:after {
  width: 110%;
}

.sixth {
  border-radius: 3em;
  border-color: #2ecc71;
  color: #2ecc71;
  background-image: linear-gradient(to bottom, transparent 50%, #2ecc71 50%);
  background-position: 0% 0%;
  background-size: 210%;
  transition: background 150ms ease-in-out, color 150ms ease-in-out;
}
.sixth:hover {
  color: #fff;
  background-position: 0 100%;
}
.loader {
    top: 10%;
    left: 65%;
    transform: translate(-50%, -50%);
    width: 50px;
    height: 50px;
    background: transparent;
    border: 3px solid #3c3c3c;
    border-radius: 50%;
    text-align: left;
    line-height: 120px;
  
    font-family: sans-serif;
    font-size: 16px;
    color: #fff000;
    letter-spacing: 4px;
    text-shadow: 0 0 10px #fff000;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
}
.loader::before {
    content: '';
    position: absolute;
    top: -3px;
    left: -3px;
    width: 100%;
    height: 100%;
    border: 3px solid transparent;
    border-top: 3px solid #fff000;
    border-right: 3px solid #fff000;
    border-radius: 50%;
    animation: animateCircle 2s linear infinite;
}
.loader span {
    display: block;
    position: absolute;
    top: calc(50% - 2px);
    left: 50%;
    width: 50%;
    height: 4px;
    background:transparent;
    transform-origin: left;
    animation: animate 2s linear infinite;
}
.loader span::before {
    content: '';
    position: absolute;
    top: -6px;
    right: -8px;
    border-radius: 50%;
    width: 16px;
    height: 16px;
    background: #fff000;
    box-shadow: 0 0 10px #fff000;
}
@keyframes animateCircle {
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
}
@keyframes animate {
    0% {
        transform: rotate(45deg);
    }
    100% {
        transform: rotate(405deg);
    }
}
.success-container{
	  left: 50%;
		top:50%;
		width:600px;
		transform: translate(-50%, -50%);
		position:fixed;
}
.modalbox.success {
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  -webkit-border-radius: 2px;
  -moz-border-radius: 2px;
  border-radius: 2px;
  background: #fff;
  padding: 25px 25px 15px;
  text-align: center;
}
.modalbox.success.animate .icon {
  -webkit-animation: fall-in 0.75s;
  -moz-animation: fall-in 0.75s;
  -o-animation: fall-in 0.75s;
  animation: fall-in 0.75s;
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
}
.modalbox.success h1 {
  font-family: 'Montserrat', sans-serif;
}
.modalbox.success p {
  font-family: 'Open Sans', sans-serif;
}
.modalbox.success .icon {
  position: relative;
  margin: 0 auto;
  margin-top: -75px;
  background: #4caf50;
  height: 100px;
  width: 100px;
  border-radius: 50%;
}
.modalbox.success .icon span {
  postion: absolute;
  font-size: 4em;
  color: #fff;
  text-align: center;
  padding-top: 20px;
}
.center {
  float: none;
  margin-left: auto;
  margin-right: auto;
/* stupid browser compat. smh */
}
.center .change {
  clearn: both;
  display: block;
  font-size: 10px;
  color: #ccc;
  margin-top: 10px;
}

#id_confrmdiv
{

  position: absolute;
  float: left;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  display: none; 
  width:9%;
  height:10%;
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    z-index: 9999; 
}

.icon-container {
  width: 50px;
  height: 50px;
  position: relative;
}

img {
  height: 100%;
  width: 100%;
  border-radius: 50%;
}

.status-circle {
  width: 15px;
  height: 15px;
  border-radius: 50%;
  background-color: grey;
  border: 2px solid white;
  bottom: -110%;
  right:-210%;
  position: absolute;
}
 </style>

   


<div id='back'>

<button class="button button2" onclick="approve()">Approve</button>
<button class="button button3"  onclick="decline()">Decline</button>
     <p><span id="txtHint"></span></p>

     </div>
</div>
<div id="id_confrmdiv">
<div class="success-container">
	<div class="row">
		<div class="modalbox success col-sm-8 col-md-6 col-lg-5 center animate">
			<div class="icon">
				<span class="glyphicon glyphicon-ok"></span>
			</div>
			<h1>Assigned</h1>
      <p id="name"></p>
			<p>User Alerted! 
				<br>Pick Up Request Sent to the Driver, Users Timeline has been updated </p>
        
         
		</div>
	</div>
</div>
</div>


<div class="modal" id="modal1" data-animation="slideInOutLeft" >
  <div class="modal-dialog" style ="background-color:#333333;">
    <header class="modal-header" style ="background-color:#333333;">
    <P style="color:var(--white)"> <B>CLICK TO ASSIGN ACTIVE DRIVER </B></P>
   </header>
    <div class="phoneswrapper">
   <p style="color:white;">   CLICK  Esc to Close Modal</p>
 
   <?php

      $querry = $con->prepare("SELECT * FROM driver where Not driverID = 5");
      $querry->execute();
      $querry->bind_result($driverID,$FullName,$phone,$NumberPlate,$DriverLicense,$password,$status,$imagelocation,$admin_email);
      $querry->store_result();

      while($querry->fetch() > 0 )
      
      {
        if($status =="online") {?>

      
    </div>
    <div class="divider"> </div>
    <div class="phone_body">
       <div class="chat">
       <div class='icon-container' >
         <img class="chat_avatar" src="<?php echo $imagelocation; ?>" style="margin-left: 50px; width:100px; height:100px;">
         <div class='status-circle' style="background-color:green;">
  </div>
</div>
         <div class="chat_info">
           <div class="contact_name" style="color:white;" id="fullname"><?php echo 'Driver Name : '.$FullName;?> </div>
          <?php if($status =="online") {?>
           <div class="chat_new grad_pb" style="color:green;" id="status"> 
        
           </div>
           <button class="btn fourth" onclick="assigndriver(<?php echo $phone; ?>)" id="assign">Assign Driver</button>  
           
         </div>
        
      
         <?php } ?>
         <?php }else{ ?>
         
         
          <?php } ?>
          <?php } ?>
</div>
</div>

  </div>
</div>



</footer>


<script>

    function assigndriver(val){

      alert("hello iam driver juan" + val);

      document.getElementById('id_confrmdiv').style.display="block"; //this is the replace of this line
      document.getElementById('name').innerHTML="contact : 0"+val; 
      var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {

        document.getElementById("txtHint").innerHTML = this.responseText;
        }
    };
    xmlhttp.open("GET", "assigndriver.php?q="+val+"&orderid=<?php echo $edit_id ; ?>", true);
    xmlhttp.send();
      
document.getElementById('id_truebtn').onclick = function(){
   // Do your delete operation
    alert('true');



};
document.getElementById('id_falsebtn').onclick =function(){
   document.getElementById('id_confrmdiv').style.display="none"; 
   return false;
};


    }
    function approve(){
        document.getElementById("txtHint").innerHTML = "requesting";

        var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {

        document.getElementById("txtHint").innerHTML = this.responseText;
       
  
    const isVisible = "is-visible";

    document.getElementById("modal1").classList.add(isVisible);



document.addEventListener("keyup", e => {
  // if we press the ESC
  if (e.key == "Escape" && document.querySelector(".modal.is-visible")) {
    document.querySelector(".modal.is-visible").classList.remove(isVisible);
    document.getElementById('id_confrmdiv').style.display="none"; 
  }
});
 }
    };
    xmlhttp.open("GET", "paymentsstatus.php?q=<?php echo $edit_id ; ?>", true);
    xmlhttp.send();
   }
   function decline(){
        document.getElementById("txtHint").innerHTML = "requesting";

        var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {

        document.getElementById("txtHint").innerHTML = this.responseText;
        }
    };
    xmlhttp.open("GET", "paymentsstatusdecline.php?q= <?php echo $edit_id; ?>", true);
    xmlhttp.send();
   }
   
</script>
 <?php } ?>
 
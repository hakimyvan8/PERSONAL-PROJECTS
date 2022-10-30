<?php

include("securityAdmin.php");
?>


<?php
$get_pro = "select * from orders";

$run_pro = mysqli_query($con,$get_pro);
while($row_pro=mysqli_fetch_array($run_pro)){

    $product_id = $row_pro['id'];

    $pro_qty = $row_pro['p_qty'];

    ?>

<?php }?>
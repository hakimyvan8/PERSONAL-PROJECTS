<?php

include("includes/db.php");
include("SimpleXLSXGen.php");



$order = [
    ['Order No', 'Customer Email', 'Invoice No', 'Product Title', 'Product Qty','Order Date','Total Amount','Order Status']
];

$orderno = 0;
$sql = "SELECT * FROM products";
$res = mysqli_query($con, $sql);
if (mysqli_num_rows($res) > 0) {
    foreach ($res as $row) {
        $orderno++;
        $order = array_merge($order, array(array($orderno, $row['customer_id'], $row['invoice_no'], $row['unitsStored'], $row['qty'],$row['order_date'],$row['due_amount'],$row['order_status'])));
    }
}

$xlsx = SimpleXLSXGen::fromArray($order);
$xlsx->downloadAs('stock.xlsx'); // This will download the file to your local system

// $xlsx->saveAs('employees.xlsx'); // This will save the file to your server

echo "<pre>";
print_r($order);

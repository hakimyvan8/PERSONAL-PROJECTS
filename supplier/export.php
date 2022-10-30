<?php

include("includes/db.php");
include("SimpleXLSXGen.php");



$products = [
    ['Product ID', 'Product Title', 'Product Price', 'Units Stored', 'Units Remaining']
];

$id = 0;
$sql = "SELECT * FROM products";
$res = mysqli_query($con, $sql);
if (mysqli_num_rows($res) > 0) {
    foreach ($res as $row) {
        $id++;
        $products = array_merge($products, array(array($id, $row['product_title'], $row['product_price'], $row['unitsStored'], $row['RemainingUnits'])));
    }
}

$xlsx = SimpleXLSXGen::fromArray($products);
$xlsx->downloadAs('stock.xlsx'); // This will download the file to your local system

// $xlsx->saveAs('employees.xlsx'); // This will save the file to your server

echo "<pre>";
print_r($products);

<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$image = $_POST['image'];
                $name = $_POST['name'];
		
	define('DB_USERNAME', 'root');
define('DB_PASSWORD', '');
define('DB_HOST', 'localhost');
define('DB_NAME', 'chat_demo');
		
		$sql ="SELECT id FROM users ORDER BY id ASC";
	$conn = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
		$res = mysqli_query($conn,$sql);
		
		$id = 0;
		
		while($row = mysqli_fetch_array($res)){
				$id = $row['id'];
		}
		
		$path = "uploads/$id.png";
		
		$actualpath = "http://192.168.0.103/chatdemo/include/$path";
		
		$sql = "INSERT INTO images (u_id,photo,name) VALUES ('$id','$actualpath','$name')";
		
		if(mysqli_query($conn,$sql)){
			file_put_contents($path,base64_decode($image));
			echo "Inserted Successfully";
		}
		
		mysqli_close($conn);
	}else{
		echo "Error";
	}
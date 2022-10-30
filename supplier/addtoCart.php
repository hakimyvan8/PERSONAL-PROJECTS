<?php
    
    $id = $_POST['id'];
    $title = $_POST['title'];
    $cost = $_POST['cost'];
    $imgstr = $_POST['image'];
    
    require 'connect.php';
    
    if($id == "-1"){
        
        $res=mysqli_query($conn,"INSERT INTO `cart`( `title`, `cost`) VALUES ('$title','$cost')");
        
        $result = array();
        
        if($res){
            $result['success']="1";
        }else{
             $result['success']="0";
        }
            
    }else{
        $resu=mysqli_query($con,"SELECT `img` FROM `items` WHERE itemId='$id'");
        $row = mysqli_fetch_array($resu);

        $res=mysqli_query($con,"UPDATE `items` SET `title`='$title',`cost`='$cost' WHERE itemId = '$id'");
        
        $result = array();
     
        if($res){
            $result['success']="1";
        }else{
             $result['success']="0";
        }
            
    }
    
    
   
    echo json_encode($result);
    
    mysqli_close($conn);

 ?>

<?php

if (PHP_SAPI == 'cli-server') {
    $_SERVER['SCRIPT_NAME'] = '/index.php';
    $url  = parse_url($_SERVER['REQUEST_URI']);

    $file = getenv('STATIC_DIR') . $url['path'];
    if (is_file($file)) {
        $extension = pathinfo($file, PATHINFO_EXTENSION);
        if ($extension == 'css') {
            header('Content-Type: text/css');
        }
        if ($extension == 'js') {
            header('Content-Type: text/javascript');
        }
        if ($extension == 'svg') {
            header('Content-Type: image/svg+xml');
        }
        echo file_get_contents($file);
        exit;
    }
}
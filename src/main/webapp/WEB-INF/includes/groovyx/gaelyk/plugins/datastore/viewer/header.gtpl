<!DOCTYPE html>
<html>
    <head>
		<title>${twitterBootstrap.brand} Data Viewer</title>
		<link rel="shortcut icon" href="http://gaelyk.appspot.com/favicon.ico"
			type="image/ico">
		<link rel="icon" href="http://gaelyk.appspot.com/favicon.ico"
			type="image/ico">
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-migrate-1.1.0.min.js"></script>
		<script type="text/javascript" src="$twitterBootstrap.js"></script>
		<script type="text/javascript" src="/gpr/dataviewer/js/datastore-viewer.js"></script>
		<link rel="stylesheet" type="text/css"
			href="$twitterBootstrap.css" />
		<link rel="stylesheet" type="text/css"
			href="$twitterBootstrap.responsive" />
		
		<link rel="stylesheet" type="text/css" href="/gpr/dataviewer/css/datastore-viewer.css"/>
        <style type="text/css">
            /* Override some defaults */
            html, body {
                background-color: #eee;
            }
            body {
                padding-top: 40px; /* 40px to make the container go all the way to the bottom of the topbar */
            }
            .container > footer p {
                text-align: center; /* center align it with the container */
            }
            .container {
                width: 1024px; /* downsize our container to make the content feel a bit tighter and more cohesive. NOTE: this removes two full columns from the grid, meaning you only go to 14 columns and not 16. */
            }

            /* The white background content wrapper */
            .content {
                background-color: #fff;
                padding: 20px;
                margin: 0 -20px; /* negative indent the amount of the padding to maintain the grid system */
                -webkit-border-radius: 0 0 6px 6px;
                   -moz-border-radius: 0 0 6px 6px;
                        border-radius: 0 0 6px 6px;
                -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.15);
                   -moz-box-shadow: 0 1px 2px rgba(0,0,0,.15);
                        box-shadow: 0 1px 2px rgba(0,0,0,.15);
            }

            /* Page header tweaks */
            .page-header {
                background-color: #f5f5f5;
                padding: 8px 20px 10px;
                margin: -20px -20px 20px;
            }

            /* Styles you shouldn't keep as they are for displaying this base example only */
            .content .span10,
            .content .span4 {
                min-height: 500px;
            }
            /* Give a quick and non-cross-browser friendly divider */
            .content .span4 {
                margin-left: 0;
                padding-left: 19px;
                border-left: 1px solid #eee;
            }		
        </style>
        <style type="text/css">
		body {
			padding-top: ${twitterBootstrap.gap}px;
		}
		</style>
    </head>
    <body>
    	<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href="/_ah/gaelyk-datastore-viewer/browse">${twitterBootstrap.brand} Data Viewer</a>
					<ul class="nav">
	                    <li class="active"><a href="/_ah/gaelyk-datastore-viewer/browse">Home</a></li>
	                </ul>
				</div>
			</div>
		</div>
        <div class="container">
            <div class="content">
 
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<title>SIMULATEUR DE TRANSPORT</title>
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
</head>
<body>
	<div id="map"></div>
	<div id="app">
		<center><h4>SIMULATEUR DES RÉSEAUX DE TRANSPORT</h4></center>
		<form class="form-inline" name="app" id="formApp" onsubmit="return false">
			<div class="input-group">	
				<span class="input-group-addon">		
					<input type="radio" name="choix" value="s" checked="checked" title="coucher pour choisi source vers destinataire" />
				</span>
				<input class="form-control"  class="typeahead" name="source" id="s" placeholder="Source" data-items="8" data-provide="typeahead" data-source="" autocomplete="off">			
			</div>
			<div class="input-group">
				<span class="input-group-addon">				
					<input type="radio" name="choix" value="d" title="coucher pour choisi destinataire vers source" />
					</span>
					<input class="form-control"  class="span6 typeahead" name="destination" id="d" placeholder="Destination" data-items="8" data-provide="typeahead" data-source="" autocomplete="off">
			</div>
			<button type="submit" class="btn btn-default" id="btn" name="btn" >execute</button>
		</form>
		<div id="myResult">
		<table id="result" class="table table-striped">
			<caption style="padding-left: 15px">Optional table caption.</caption>
		  <thead>
		  	<tr><td>#</td><td>depuis</td><td>vers</td><td>temps d'arriver</td></tr>
		  </thead>
		  <tbody id="chemin">		  	
		  </tbody>
		</table>
		</div>
		
	</div>
	<div id="ligend">
		<table style="width: 100%;border-top:thin solid #aaa">
			<tr><td  style="padding-right: 10px" align="right">Surface</td><td style="width: 80%" align="left"><div style="height:5px;width:80%;background-color: #00A575;opacity:0.5"></div></td></tr>	
			<tr><td  style="padding-right: 10px" align="right">Ferré</td><td style="width: 80%" align="left"><div style="height:5px;width:80%;background-color: #541153;opacity:0.5"></div></td></tr>	
			<tr><td  style="padding-right: 10px" align="right">Privé</td><td style="width: 80%" align="left"><div style="height:5px;width:80%;background-color: #A5AE35;opacity:0.5"></div></td></tr>	
			<tr><td  style="padding-right: 10px" align="right">Tram</td><td style="width: 80%" align="left"><div style="height:5px;width:80%;background-color: #44550f;opacity:0.5"></div></td></tr>		
		</table>	
	</div>
	
	<div class="modal fade" id="notice" role="dialog" >
  		<div class="modal-dialog" role="document">
		    <div class="modal-content">
		    	<div class="modal-body">
					<center><h3 id="msg">Chemin indisponible</h3></center>				
				</div>
			</div>
		</div>
	</div>
	
	<script src="js/jquery-1.11.0.min.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/controller.js" type="text/javascript"></script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDbFxtTiH_oNxfIZoYkDwuBtJGiBEb6jr0&signed_in=true&callback=initialize"></script>
</body>
</html>
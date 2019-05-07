<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Usuario</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" href="css/admin.css" >
</head>
<body>
	
		<nav class="navbar navbar-expand navbar-dark static-top d-flex justify-content-between" style="background-color:#3c8dbc">
			<a class="navbar-brand mr-1" href="usuario">Usuario</a>  
			<!-- Navbar -->
		</nav>
		  
			  <div id="wrapper">
				<!-- Sidebar -->
				<ul class="sidebar navbar-nav" style="background-color:#1C2331">
				  <li class="nav-item">
					<a class="nav-link text-white" href="usuario">
					  <i class="fas fa-fw fa-table"></i>
					  <span>Registro</span></a>
				  </li>
				</ul>
		  
				<div id="content-wrapper" style="background-color:#e6e6e6">
				  <div class="container-fluid">
					<!-- DataTables Example -->
					<div class="card">
					  <div class="card-header">
						Textos registrados
					  </div>
					  <div class="card-body pt-0">
						<div class="table-responsive">
						  <table class="table text-center" id="dataTable" width="100%" cellspacing="0">
							<thead>
							  <tr>
							  	<th class="align-middle">Fecha</th>
								<th class="align-middle">Texto</th>
							  </tr>
							</thead>
							<tbody>
							  <tr>
								<td class="align-middle">07-05-2019</td>
								<td class="align-middle">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec urna erat, dictum id elit vitae, rutrum dignissim massa. Etiam gravida mauris sed nunc cursus suscipit. Aliquam erat volutpat.</td>
							  </tr> 
							</tbody>
						  </table>
						</div>
					  </div>
					</div>
				  </div>
				  <!-- /.container-fluid --> 
				</div>
				<!-- /.content-wrapper -->  
			  </div>
			  <!-- /#wrapper -->
		  
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
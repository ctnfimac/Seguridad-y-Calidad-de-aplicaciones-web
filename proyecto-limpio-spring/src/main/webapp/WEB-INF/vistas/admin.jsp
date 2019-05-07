<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Administrador</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" href="css/admin.css" >
</head>
<body>
	
		<nav class="navbar navbar-expand navbar-dark static-top d-flex justify-content-between" style="background-color:#1C2331">
			<a class="navbar-brand mr-1" href="admin">Administrador</a>  
			<!-- Navbar -->
		</nav>
		  
			  <div id="wrapper">
				<!-- Sidebar -->
				<ul class="sidebar navbar-nav " style="background-color:#23bfcc">
				  <li class="nav-item">
					<a class="nav-link text-white" href="admin">
					  <i class="fas fa-fw fa-table"></i>
					  <span>Clientes</span></a>
				  </li>
				</ul>
		  
				<div id="content-wrapper" style="background-color:#e6e6e6">
				  <div class="container-fluid white">
					<!-- DataTables Example -->
					<div class="card">
					  <div class="card-header ">
						Tabla de clientes
					  </div>
					  <div class="card-body white pt-0">
						<div class="table-responsive">
						  <table class="table text-center" id="dataTable" width="100%" cellspacing="0">
							<thead>
							  <tr>
							  	<th class="align-middle">Id</th>
								<th class="align-middle">Nombre</th>
								<th class="align-middle">Estado</th>
								<th class="align-middle">Fecha</th>
								<th class="align-middle">Texto</th>
								<th class="align-middle">Operación</th>
							  </tr>
							</thead>
							<tbody>
							  <tr>
								<td class="align-middle">01</td>
								<td class="align-middle">Zeus</td>
								<td class="align-middle">habilitado</td>
								<td class="align-middle">07-05-2019</td>
								<td class="align-middle"><p>kldalkdakdjklasjdlkasjdlasjd</p></td>
								<td class="align-middle">
									<div class="btn-group" role="group">
										<a href="#" class="btn text-white btn-danger">Habilitar</a>
										<a href="#" class="btn text-white btn-primary">Deshabilitar</a>
									</div>
								</td>
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
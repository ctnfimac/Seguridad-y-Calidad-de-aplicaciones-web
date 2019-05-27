<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		   <ul class="navbar-nav ml-auto ml-md-0">
				<li class="nav-item dropdown no-arrow">
					<a class="nav-link dropdown-toggle text-white" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<i class="fas fa-user-circle fa-fw"></i>
					</a>
					<div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
						<a class="dropdown-item" href="#">${nombre}</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#" data-toggle="modal" data-target="#modalCambiarContrasenia">Cambiar contrase�a</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="login">Inicio</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="cerrarSession">Cerrar Sesi�n</a>
					</div>
				</li>
			</ul>
		</nav>
		  
			  <div id="wrapper">
				<!-- Sidebar -->
				<ul class="sidebar navbar-nav" style="background-color:#1C2331">
				  <li class="nav-item">
					<a class="nav-link text-white" href="usuario">
					  <i class="fas fa-fw fa-table"></i>
					  <span>Notas</span></a>
				  </li>
				  <li class="nav-item">
					<a class="nav-link text-white" href="usuario-historial">
					  <i class="fas fa-fw fa-table"></i>
					  <span>Historial</span></a>
				  </li>
				</ul>
		  
				<div id="content-wrapper" style="background-color:#e6e6e6">
				  <div class="container-fluid">
					<!-- DataTables Example -->
					<div class="card">
					  <div class="card-header">
						Mis Notas <a href="#" class="btn btn-success float-right" data-toggle="modal" data-target="#modalAgregarNota">Agregar Nota</a>
					  </div>
					  <div class="card-body pt-0">
						<div class="table-responsive">
						  <table class="table text-center" id="dataTable" width="100%" cellspacing="0">
							<thead>
							  <tr>
							  	<th class="align-middle">Fecha</th>
								<th class="align-middle">Nota</th>
							  </tr>
							</thead>
							<tbody>
								<c:forEach items = "${notas}"  var="nota">
								  <tr>
									<td class="align-middle">${nota.getFechaModificacion()}</td>
									<td class="align-middle">${nota.getDescripcion()}</td>
								  </tr> 
								</c:forEach>
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
			  
	<!-- Modal -->
		<div class="modal fade" id="modalAgregarNota" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content pb-3">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Escriba su nota</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body pb-3">
		        <form:form action="registrar-nota" class="text-center px-5" method="POST" modelAttribute="nota">
					<div class="form-group">
					    <form:textarea path="Descripcion" class="form-control" id="Descripcion" rows="3"></form:textarea>
					</div>
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Crear Nota</button>
				</form:form>
		      </div>
		    </div>
		  </div>
		</div>
	<!-- Fin Modal --> 
	
	
	<!-- Modal Cambiar Contrase�a-->
	<div class="modal fade" id="modalCambiarContrasenia" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content pb-3">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Cambiar Contrase�a</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body pb-3">
	        <form:form action="cambiar-contrasenia" class="text-center px-5" method="POST" modelAttribute="usuario">
	        	<form:input path="id" value="${id}" class="form-control mb-4" type="hidden" /> <!-- se puede poner el id o email del usuario para usarlo como indice en el cambio de contrase�a -->
				<form:input path="password" class="form-control mb-4" id="password" type="password" placeholder="Contrase�a Actual"/>
				<form:input path="password2" class="form-control mb-4" id="password2" type="password" placeholder="Contrase�a Nueva" onkeyup="validatePassword(this.value);" /><span id="password"></span>
				<input class="btn btn-lg btn-primary btn-block" type="submit" value="Cambiar Contrase�a">
			</form:form>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- Fin Modal Cambiar Contrase�a-->  
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
<script>
            function validatePassword(password) {
                
                // Do not show anything when the length of password is zero.
                if (password.length === 0) {
                    document.getElementById("password2").innerHTML = "";
                    return;
                }
                // Create an array and push all possible values that you want in password
                var matchedCase = new Array();
                matchedCase.push("[$@$!%*#?&_]"); // Special Charector
                matchedCase.push("[A-Z]");      // Uppercase Alpabates
                matchedCase.push("[0-9]");      // Numbers
                matchedCase.push("[a-z]");     // Lowercase Alphabates

                // Check the conditions
                var ctr = 0;
                for (var i = 0; i < matchedCase.length; i++) {
                    if (new RegExp(matchedCase[i]).test(password)) {
                        ctr++;
                    }
                }
                // Display it
                var color = "";
                var strength = "";
                switch (ctr) {
                    case 0:
                    case 1:
                    case 2:
                        strength = "Very Weak";
                        color = "red";
                        break;
                    case 3:
                        strength = "Medium";
                        color = "orange";
                        break;
                    case 4:
                        strength = "Strong";
                        color = "green";
                        break;
                }
                document.getElementById("password2").innerHTML = strength;
                document.getElementById("password2").style.color = color;
            }
        </script>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <title>Registrar usuario</title>
   <link href="css/bootstrap.min.css" rel="stylesheet" >
   <link href="css/bootstrap-theme.min.css" rel="stylesheet">
   <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
   <link href="css/estilos.css" rel="stylesheet">
</head>
<body class="registrar">
	   <c:if test="${not empty rol && rol == 'admin'}">
		  <c:set var="link"  value = "admin"/>
       </c:if>
       <c:if test="${not empty rol && rol == 'user'}">
		  <c:set var="link"  value = "usuario"/>
       </c:if>		
	  <nav class="navbar navbar-expand navbar-dark static-top d-flex justify-content-between" style="background-color:#1C2331">
		<a class="navbar-brand mr-1" href="admin">S&C de app web</a>  
		 <ul class="navbar-nav ml-auto ml-md-0">
			<li class="nav-item">
				<a class="nav-link text-white" href="login">Login</a>
			</li>
			<li class="nav-item">
			  <c:if test="${not empty link}">
				<a class="nav-link text-white" href="${link}">
					<i class="fas fa-user-circle fa-fw"></i>Entrar
				</a>
			  </c:if>		
			</li>
		</ul>
	  </nav>
	  
	  
	  	<section id="section-registrar" class="mt-5">
			<div class="container  bg-light ">
				<div class="row d-flex align-items-center justify-content-center rounded-sm">
					<div class="col-xs-12 col-md-5">
						<h3 class="text-center pb-3">Registrar Usuario</h3>
						 <form:form action="registrar-usuario" class="text-center px-5" method="POST" modelAttribute="usuario">
							<form:input path="email" class="form-control mb-4" id="email" type="email" placeholder="E-mail"/>
							<form:input path="nombre" class="form-control mb-4" id="nombre" type="text" placeholder="nombre"/>
							<div class="row">
							    <div class="col">
									<form:input path="password"  class="form-control mb-4" type="password" id="password" placeholder="Contrase�a" onkeyup="validatePassword(this.value);" />
							    </div>
							    <div class="col">
									<form:input path="password2"  class="form-control mb-4" type="password" id="password2" placeholder="Repetir Contrase�a" onkeyup="validatePassword(this.value);" /> 		      		  
							    </div>
							 </div>
							<button class="btn btn-lg btn-success btn-block" Type="Submit"/>Registrar</button>
						  </form:form>
						<c:if test="${not empty error}">
					        <div class="alert alert-dismissible alert-danger text-center py-2 mt-2">
							  ${error}!
							</div>					  
				        </c:if>	
				        <c:if test="${(not empty msjregistro) && (errorRegistro==1)}">
					        <div class="alert alert-dismissible alert-danger text-center py-2 mt-2">
							  ${msjregistro}!
							</div>
				        </c:if>
				         <c:if test="${(not empty msjregistro) && (errorRegistro==0)}">
					        <div class="alert alert-dismissible alert-success text-center py-2 mt-2">
							  ${msjregistro}!
							</div>
				        </c:if>
						<!-- Default form login -->
					</div>
					<div class="col-xs-12 col-md-7 p-0 m-0 overflow-hidden" style="background-color:#131030">
						<img src="img/registro.jpg"/>
					</div>
				</div>
			</div>
		</section>

		
		
	<!-- Modal Recuperar Contrase�a-->
	<div class="modal fade" id="modalRecuperarContrasenia" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content pb-3">
	      <div class="modal-header">
	        <h6 class="modal-title" id="exampleModalLabel">Escriba su direcci�n de email para recuperar su contrase�a</h6>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body pb-3">
	        <form action="recuperarContrasenia" class="text-center px-5" method="GET" >
	        	<input value="" class="form-control mb-4" type="hidden" /> <!-- se puede poner el id o email del usuario para usarlo como indice en el cambio de contrase�a -->
				<input class="form-control mb-4" name="email" id="email" type="email" placeholder="Email"/>
				<input class="btn btn-lg btn-warning btn-block text-white" Type="Submit" value="Recuperar Contrase�a"/>
			</form>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- Fin Modal Recuperar Contrase�a--> 
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</body>
	<script>
            function validatePassword(password) {
                
                // Do not show anything when the length of password is zero.
                if (password.length === 0) {
                    document.getElementById("password").innerHTML = "";
                    return;
                }
                // Create an array and push all possible values that you want in password
                var matchedCase = new Array();
                matchedCase.push("[A-Z]");      // Uppercase Alpabates
                matchedCase.push("[a-z]");     // Lowercase Alphabates
                matchedCase.push("[0-9]");      // Numbers
                matchedCase.push("[$@$!%*#?&_]"); // Special Charector

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
                document.getElementById("password").innerHTML = strength;
                document.getElementById("password").style.color = color;
            }
        </script>
</html>
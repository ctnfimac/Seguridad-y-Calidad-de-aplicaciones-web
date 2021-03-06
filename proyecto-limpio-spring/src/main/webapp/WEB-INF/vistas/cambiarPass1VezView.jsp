<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <link href="css/bootstrap.min.css" rel="stylesheet" >
   <link href="css/bootstrap-theme.min.css" rel="stylesheet">
   <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
   <link href="css/estilos.css" rel="stylesheet">
</head>
<body>
	  	
	  <nav class="navbar navbar-expand navbar-dark static-top d-flex justify-content-between" style="background-color:#1C2331">
		<a class="navbar-brand mr-1" href="login">S&C de app web</a>  
	  </nav>
	<section id="section-cambiandopass" class="mt-5">
		<div class="container">
			<div class="row d-flex align-items-center justify-content-center rounded-sm">
				<div class="col-xs-12 col-md-5 bg-light py-5">
				  	 <div class="alert alert-dismissible alert-warning">
					  <h4 class="alert-heading text-center">Cambio de Contrase�a!</h4>
					  <p class="mb-0 text-center">Este paso es obligatorio en su primer inicio de sesi�n</p>
					 </div>
					 <form:form action="actualizarPassPorPrimeraVez" class="text-center px-5" method="POST" modelAttribute="usuario" autocomplete="off">
			        	<div class="form-group mb-4">
							<form:input path="password" class="form-control" id="password" type="password" placeholder="Contrase�a Nueva" onkeyup="validatePassword(this.value);" autocomplete="off"/>
							<small id ="calidadPassword" class="form-text text-left"></small>
						</div>
						<form:input path="password2" class="form-control mb-4" id="password2" type="password" placeholder="Repetir nueva Contrase�a" autocomplete="off"/>
						<input class="btn btn-lg btn-primary btn-block" type="submit" value="Cambiar Contrase�a">
				
					 <c:if test="${(not empty msjcambio) && (errorCambio==3)}">
				        <div class="alert alert-dismissible alert-success text-center py-2 mt-4">
						  ${msjcambio}
						</div>
			        </c:if>
			        <c:if test="${(not empty msjcambio) && (errorCambio==1)}">
				        <div class="alert alert-dismissible alert-danger text-center py-2 mt-4">
						  ${msjcambio}
						</div>
			        </c:if>
			         <c:if test="${(not empty msjcambio) && (errorCambio==0)}">
				        <div class="alert alert-dismissible alert-success text-center py-2 mt-4">
						  ${msjcambio}!
						</div>
			        </c:if>
					<!-- Default form login -->
					</form:form>
				
				</div>
			</div>
		</div>
	</section>
		 
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</body>
	<script>
            function validatePassword(password) {
                
                // Do not show anything when the length of password is zero.
                if (password.length === 0) {
                    document.getElementById("calidadPassword").innerHTML = "";
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
                        strength = "Seguridad de contrase�a: D�bil";
                        color = "red";
                        break;
                    case 3:
                        strength = "Seguridad de contrase�a: Media";
                        color = "orange";
                        break;
                    case 4:
                        strength = "Seguridad de contrase�a: Fuerte";
                        color = "green";
                        break;
                }
                document.getElementById("calidadPassword").innerHTML = strength;
                document.getElementById("calidadPassword").style.color = color;
            }
        </script>
</html>
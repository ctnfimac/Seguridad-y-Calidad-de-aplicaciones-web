<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <link href="css/bootstrap.min.css" rel="stylesheet" >
   <link href="css/bootstrap-theme.min.css" rel="stylesheet">
   <link href="css/estilos.css" rel="stylesheet">
</head>
<body>
		<section id="section-login" class="py-5 p-0">
			<div class="container  bg-light ">
				<div class="row d-flex align-items-center justify-content-center rounded-sm">
					<div class="col-xs-12 col-md-5 p-0">
						<h3 class="text-center pb-3">Iniciar Sesi�n</h3>
						<form:form action="validar-login" class="text-center px-5" method="POST" modelAttribute="usuario">
							<form:input path="email" class="form-control mb-4" id="email" type="email" placeholder="Usuario o E-mail"/>
							<form:input path="password"  class="form-control mb-4" type="password" id="password" placeholder="Password"/>     		  
							<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Login</button>
						</form:form>
						<p class="text-center mt-4" data-toggle="modal" data-target="#exampleModal">�No ten�s una cuenta?<a href="#"> Registrarse</a></p>
						<p class="text-center"><a href="#">�Olvidaste tu contrase�a?</a></p>
						<c:if test="${not empty error}">
					        <h5 class="text-center"><span>${error}</span></h5>
					        <br>
				        </c:if>	
						<!-- Default form login -->
					</div>
					<div class="col-xs-12 col-md-7 p-0 m-0 overflow-hidden" style="background-color:#10537f">
						<img src="img/login.jpg"/>
					</div>
				</div>
			</div>
		</section>
		
		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content pb-3">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Registro de Usuario</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body pb-3">
		        <form:form action="validar-login" class="text-center px-5" method="POST" modelAttribute="usuario">
					<form:input path="email" class="form-control mb-4" id="email" type="email" placeholder="Usuario o E-mail"/>
					<form:input path="password"  class="form-control mb-4" type="password" id="password" placeholder="Password"/>
					<form:input path="password2"  class="form-control mb-4" type="password" id="password2" placeholder="Repetir Password"/>     		      		  
					<button class="btn btn-lg btn-success btn-block" Type="Submit"/>Registrar</button>
				</form:form>
		      </div>
<!-- 		      <div class="modal-footer"> -->
<!-- 		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> -->
<!-- 		        <button type="button" class="btn btn-primary">Save changes</button> -->
<!-- 		      </div> -->
		    </div>
		  </div>
		</div>
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</body>
</html>

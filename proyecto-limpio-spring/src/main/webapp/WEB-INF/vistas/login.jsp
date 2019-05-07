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
							<form:form action="validar-login" class="text-center p-5" method="POST" modelAttribute="usuario">
						    	<h3 class="form-signin-heading">Iniciar Sesión</h3>
								<form:input path="email" class="form-control mb-4" id="email" type="email" placeholder="Usuario o E-mail"/>
								<form:input path="password"  class="form-control mb-4" type="password" id="password" placeholder="Password"/>     		  
								<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Login</button>
							</form:form>
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
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</body>
</html>

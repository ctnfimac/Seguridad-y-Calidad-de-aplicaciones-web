package ar.edu.unlam.tallerweb1.controladores;

import java.util.Date;
import java.util.List;
//para el email
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Nota;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioNota;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;  

@Controller
public class ControladorUsuario {
	
	@Inject
	private ServicioLogin servicioLogin;
	
	@Inject
	private ServicioUsuario servicioUsuario;
	
	@Inject
	private ServicioNota servicioNota;
	
	@Inject
	private ServicioLog servicioLog;
	
	@RequestMapping(path="/usuario")
	public ModelAndView irAusuario(HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null) {
			if(servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))){
				ModelMap modelo = new ModelMap();
				Nota nota = new Nota();
				modelo.put("nota", nota);
				List<Nota> notasUsuario = servicioNota.getByUsuario((long) misession.getAttribute("sessionId"));
				modelo.put("notas", notasUsuario);
				modelo.put("nombre",misession.getAttribute("sessionNombre"));
				modelo.put("id", (long) misession.getAttribute("sessionId"));
				modelo.put("usuario", new Usuario());
				return new ModelAndView("usuario", modelo);
			}else{
				return new ModelAndView("redirect:cerrarSession");
			}	
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
	@RequestMapping(path="/usuario-historial")
	public ModelAndView irAusuarioHistorial(HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null) {
			if(servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))){
				ModelMap modelo = new ModelMap();
				List<Log> logUsuario = servicioLog.getLogByUsuario((long) misession.getAttribute("sessionId"));
				modelo.put("logsUsuario", logUsuario);
				modelo.put("id", (long) misession.getAttribute("sessionId"));
				modelo.put("usuario", new Usuario());
				modelo.put("nombre",misession.getAttribute("sessionNombre"));
				return new ModelAndView("usuario-historial",modelo);
			}else{
				return new ModelAndView("redirect:cerrarSession");
			}
		}else{
			return new ModelAndView("redirect:login");
		}
	}

	@RequestMapping(path="/registrarUsuarioView")
	public ModelAndView irAregistrarUsuarioView(HttpServletRequest request){
		ModelMap modelo = new ModelMap();
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") == null)misession.invalidate();
		else modelo.put("rol", misession.getAttribute("ROL"));
		servicioLogin.cargarDatos();	
		modelo.put("usuario", new Usuario());
		return new ModelAndView("registrarUsuarioView",modelo);
	}

	@RequestMapping(path="/registrar-usuario", method = RequestMethod.POST)
	public ModelAndView registrarUsuario(@ModelAttribute("usuario") Usuario usuarioNuevo,HttpServletRequest request){
		ModelMap modelo = new ModelMap();
		try{	
			if(this.validacionDeUsuario(usuarioNuevo)==0) {
				usuarioNuevo.setPassword(Md5Crypt.md5Crypt(usuarioNuevo.getPassword().getBytes()));
				servicioLogin.registrarUsuario(usuarioNuevo);
				modelo.put("errorRegistro", 0);
				modelo.put("msjregistro", "se registro exitosamente, <a href='login'>inicie sesión</a>");
			}else if(this.validacionDeUsuario(usuarioNuevo)==1){
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "complete todos los campos");
			}else if(this.validacionDeUsuario(usuarioNuevo)==2){
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "las contraseñas son distintas");
			}
			else if(this.validacionDeUsuario(usuarioNuevo)==3){
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "la contraseña tiene menos de 12 caracteres");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			modelo.put("errorRegistro", 1);
			modelo.put("msjregistro", "Hubo problemas para dar de alta su usuario");
		}

        
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		return new ModelAndView("registrarUsuarioView",modelo);
	}
	
	private Integer validacionDeUsuario(Usuario usuarioNuevo) {
		Integer error = 0; // 0 no hay ningun error
		if(usuarioNuevo.getEmail().isEmpty() || usuarioNuevo.getNombre().isEmpty() 
				|| usuarioNuevo.getPassword().isEmpty() || usuarioNuevo.getPassword2().isEmpty()){
			error = 1 ; // los campos estan vacios
		}else if(!usuarioNuevo.getPassword().isEmpty() && !usuarioNuevo.getPassword2().isEmpty() && 
				!usuarioNuevo.getPassword().equals(usuarioNuevo.getPassword2())){
			error = 2 ; // las contraseñas son distintas
		}else if(usuarioNuevo.getPassword().length() < 12 || usuarioNuevo.getPassword2().length() < 12 ){
			error = 3 ;// la contraseña ingresada tiene menos de 12 caracteres
		}
		return error;
	}

	@RequestMapping(path="/registrar-nota", method = RequestMethod.POST)
	public ModelAndView registrarNota(@ModelAttribute("nota") Nota nuevaNota,HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null && servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))) {
			ModelMap modelo = new ModelMap();
			try{
				if(!nuevaNota.getDescripcion().isEmpty()){
					servicioNota.nuevaNota((long) misession.getAttribute("sessionId"), nuevaNota.getDescripcion());
					modelo.put("errorRegistro", 0);
					modelo.put("msjregistro", "se registro exitosamente");
				}else{
					modelo.put("errorRegistro", 1);
					modelo.put("msjregistro", "complete todos los campos");
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "Hubo problemas para dar de alta su nota");
			}
			
			Nota nota = new Nota();
			modelo.put("nota", nota);
	
			List<Nota> notasUsuario = servicioNota.getByUsuario((long) misession.getAttribute("sessionId"));
			modelo.put("notas", notasUsuario);
			modelo.put("id", (long) misession.getAttribute("sessionId"));
			modelo.put("usuario", new Usuario());
			modelo.put("nombre",misession.getAttribute("sessionNombre"));
			return new ModelAndView("usuario", modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
	@RequestMapping(path="/cambiar-contrasenia", method = RequestMethod.POST)
	public ModelAndView cambiarContraseña(@ModelAttribute("usuario") Usuario usuario,HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null && servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))) {
			ModelMap modelo = new ModelMap();
			String contraseniaAnt = usuario.getPassword();
			String contraseniaNueva = usuario.getPassword2();
			try{
				if(!contraseniaAnt.isEmpty() && !contraseniaNueva.isEmpty()){
					if(contraseniaAnt.equals(contraseniaNueva)){
							if(contraseniaAnt.length() >= 12 && contraseniaNueva.length() >= 12){
								//servicioUsuario.cambiarContrasenia(usuario.getId(), contraseniaNueva );
								servicioUsuario.cambiarContrasenia(usuario.getId(),  Md5Crypt.md5Crypt(contraseniaNueva.getBytes()) );
							}else{
								modelo.put("errorCambio", 1);
								modelo.put("msjcambio", "las contraseñas tienen menos de 12 caracteres");
							}
						}
						else{
							modelo.put("errorCambio", 1);
							modelo.put("msjcambio", "las contraseñas son distintas");
						}
				}else{
					modelo.put("errorCambio", 1);
					modelo.put("msjcambio", "complete todos los campos");
				}
				
				/*
				 if(contraseniaAnt.length() >= 12 && contraseniaNueva.length() >= 12){
								servicioUsuario.cambiarContrasenia(idUsuario, Md5Crypt.md5Crypt(contraseniaNueva.getBytes()));
								//System.out.println("cambiando la pass");
								modelo.put("errorCambio", 3);
								modelo.put("msjcambio", "Contraseña actualizada ya puede <a href='login'>iniciar sesión</a>");
							}else{
								modelo.put("errorCambio", 1);
								modelo.put("msjcambio", "las contraseñas tienen menos de 12 caracteres");
							}
				 * */
			}catch(Exception e){
				//System.out.println(e.getMessage());
				modelo.put("errorCambio", 1);
				modelo.put("msjcambio", "Hubo problemas para actualizar la contraseña");
			}
			List<Nota> notasUsuario = servicioNota.getByUsuario((long) misession.getAttribute("sessionId"));
			modelo.put("notas", notasUsuario);
			modelo.put("nombre",misession.getAttribute("sessionNombre"));
			modelo.put("nota", new Nota());
			return new ModelAndView("usuario",modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
	
	
//	@RequestMapping(path="/recuperar-contraseña", method = RequestMethod.POST)
//	public ModelAndView recuperarContraseña(Long idUsuario){
//		ModelMap modelo = new ModelMap();
//		
//		Boolean recuperoExitoso = servicioUsuario.recuperarContrasenia(idUsuario);
//		
//		try{
//			if(recuperoExitoso == true){
//				
//				modelo.put("errorRecupero", 1);
//				modelo.put("msjRecupero", "Se ha enviado un mail de recuperación.");
//				
//			}else{
//				modelo.put("errorRecupero", 1);
//				modelo.put("msjRecupero", "El usuario no se encuentra registrado.");
//			}
//		}catch(Exception e){
//			System.out.println(e.getMessage());
//			modelo.put("errorRecupero", 1);
//			modelo.put("msjRecupero", "Hubo problemas para recuperar la contraseña");
//		}
//		
//		return new ModelAndView("usuario",modelo);
//	}
	
	@RequestMapping(path="/ingresar-texto", method = RequestMethod.POST)
	public ModelAndView ingresarTexto(Long idUsuario, String texto,HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null && servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))) {
			ModelMap modelo = new ModelMap();
			
			if(!texto.isEmpty()) {
				
				servicioNota.nuevaNota(idUsuario, texto);
				
			}else {
				modelo.put("errorIngreso", 1);
				modelo.put("msjIngreso", "Debe Ingresar un texto.");
			}
	
			return new ModelAndView("usuario",modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
   
	@RequestMapping(path="/recuperarContrasenia", method= RequestMethod.GET)
	public ModelAndView recuperarContrasenia(@RequestParam(value="email") String email){
		Usuario usuarioBuscado = null;
		usuarioBuscado = servicioUsuario.getUsuarioByEmail(email);
		ModelMap modelo = new ModelMap();
		if(usuarioBuscado != null){
			String idEncriptado = Md5Crypt.md5Crypt(usuarioBuscado.getId().toString().getBytes());
			Date fechaSolicitud = new Date();
			String keyLog = Md5Crypt.md5Crypt((usuarioBuscado.getId().toString()+ fechaSolicitud.toString()).getBytes());
			System.out.println("keyLog original:" + keyLog);
			String link = "http://localhost:8080/proyecto-limpio-spring/obteniendoPass?id="+idEncriptado+"&keylog="+keyLog;
			//String msj = usuarioBuscado.getNombre() + ", este es un mensaje para recuperar tu contraseña por favor entre al siguiente enlace: <a href='christianperalta.com'>enlace</a>" ;
			String msj = "<h3>"+ usuarioBuscado.getNombre() +", si ha solicitado recuperar su contraseña haga click en el siguiente <a href='"+link+"'>Link</a> </h3>";

			servicioUsuario.persistirSolicitudCambioDeContrasenia(usuarioBuscado.getId(),fechaSolicitud,keyLog);
			
			enviarEmail(usuarioBuscado.getEmail(),msj);
			modelo.put("errorRegistro", 0);
			modelo.put("msjregistro", "Se envió un email a su cuenta de correo");
		}else{
			modelo.put("errorRegistro", 1);
			modelo.put("error", "El usuario con el email ingresado no existe");
		}
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		return new ModelAndView("login",modelo);
	}
	
	@RequestMapping(path="/obteniendoPass", method= RequestMethod.GET)
	public ModelAndView obteniendoPass(@RequestParam(value="id") String id, @RequestParam(value="keylog") String keylog){
		ModelMap modelo = new ModelMap();
		modelo.put("usuario", new Usuario());
		// busco si el usuario indicado esta cambiando de contraseña, si es asi entro a la
		// vista de cambiarcontraseña si no es asi voy a otra vista
		Integer respuesta = servicioUsuario.usuarioCambiandoPass(id,keylog);
		
		if(respuesta == 0) {
			modelo.put("errorRegistro", 1);
			modelo.put("msjregistro", "error al querer recuperar la contraseña");
			return new ModelAndView("redirect: login");
		}else if(respuesta == 1){
			modelo.put("usuario", new Usuario());
			modelo.put("id", id);
			System.out.println("id a modificar:" + this.servicioUsuario.getId(id));
			return new ModelAndView("cambiarContrasenia",modelo);
		}else{
			modelo.put("errorRegistro", 1);
			modelo.put("msjregistro", "error al querer recuperar la contraseña");
			return new ModelAndView("redirect: login");
		}

	}
	
	@RequestMapping(path="/actualizarPass", method = RequestMethod.POST)
	public ModelAndView actualizarPass(@ModelAttribute("usuario") Usuario usuario,HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		System.out.println("id: " + this.servicioUsuario.getId(usuario.getIdE()));
		Long idUsuario = this.servicioUsuario.getId(usuario.getIdE()) ;
		if(idUsuario != 0 ) {
			ModelMap modelo = new ModelMap();
			String contraseniaAnt = usuario.getPassword();
			String contraseniaNueva = usuario.getPassword2();
			try{
				if(!contraseniaAnt.isEmpty() && !contraseniaNueva.isEmpty()){
					if(contraseniaAnt.equals(contraseniaNueva)){
							if(contraseniaAnt.length() >= 12 && contraseniaNueva.length() >= 12){
								servicioUsuario.cambiarContrasenia(idUsuario, Md5Crypt.md5Crypt(contraseniaNueva.getBytes()));
								//System.out.println("cambiando la pass");
								modelo.put("errorCambio", 3);
								modelo.put("msjcambio", "Contraseña actualizada ya puede <a href='login'>iniciar sesión</a>");
							}else{
								modelo.put("errorCambio", 1);
								modelo.put("msjcambio", "las contraseñas tienen menos de 12 caracteres");
							}
						
						}
						else{
							modelo.put("errorCambio", 1);
							modelo.put("msjcambio", "las contraseñas son distintas");
						}
				}else{
					modelo.put("errorCambio", 1);
					modelo.put("msjcambio", "complete todos los campos");
				}
			}catch(Exception e){
				//System.out.println(e.getMessage());
				modelo.put("errorCambio", 1);
				modelo.put("msjcambio", "Hubo problemas para actualizar la contraseña");
			}
			modelo.put("id", usuario.getIdE());
			modelo.put("usuario", new Usuario());
			System.out.println("id a modificar:" + this.servicioUsuario.getId(usuario.getIdE()));
			return new ModelAndView("cambiarContrasenia",modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
	private void enviarEmail(String email,String msj){
		  String host="c1360878.ferozo.com";  
		  final String user="unlam@christianperalta.com";//change accordingly  
		  final String password="M5a9h@26yF";//change accordingly  
		    
		  String to= email;//change accordingly  
		  
		   //Get the session object  
		   Properties props = new Properties();  
		   props.put("mail.smtp.host",host);  
		   props.put("mail.smtp.auth", "true");  
		     
		    Session session = Session.getDefaultInstance(props,  
		    new javax.mail.Authenticator() {  
		      protected PasswordAuthentication getPasswordAuthentication() {  
		    return new PasswordAuthentication(user,password);  
		      }  
		    });  
		  
		   //Compose the message  
		    try {  
		     MimeMessage message = new MimeMessage(session); 
		     message.addHeader("Content-type", "text/html; charset=UTF-8");
		     message.setFrom(new InternetAddress(user));  
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		     message.setSubject("javatpoint"); 
		    
		     message.setText(msj, "UTF-8", "html");

		     Transport.send(message);  
		
		   
		     } catch (MessagingException e) {e.printStackTrace();} 
	}
}

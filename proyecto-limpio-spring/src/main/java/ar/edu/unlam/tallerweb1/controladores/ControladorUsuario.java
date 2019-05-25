package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

//para el email
import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;  

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
			if(!usuarioNuevo.getEmail().isEmpty() && !usuarioNuevo.getNombre().isEmpty() 
					&& !usuarioNuevo.getPassword().isEmpty() && !usuarioNuevo.getPassword2().isEmpty()){
				if(!usuarioNuevo.getPassword().isEmpty() && !usuarioNuevo.getPassword2().isEmpty() & 
						usuarioNuevo.getPassword().equals(usuarioNuevo.getPassword2())){
					servicioLogin.registrarUsuario(usuarioNuevo);
					modelo.put("errorRegistro", 0);
					modelo.put("msjregistro", "se registro exitosamente! <a href='login'>inicie sesión</a>");
				}else{
					modelo.put("errorRegistro", 1);
					modelo.put("msjregistro", "las contraseñas son diferentes");
				}
			}else{
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "complete todos los campos");
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
							servicioUsuario.cambiarContrasenia(usuario.getId(), contraseniaNueva );
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
				System.out.println(e.getMessage());
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
	
	@RequestMapping(path="/recuperar-contraseña", method = RequestMethod.POST)
	public ModelAndView recuperarContraseña(Long idUsuario){
		ModelMap modelo = new ModelMap();
		
		Boolean recuperoExitoso = servicioUsuario.recuperarContrasenia(idUsuario);
		
		try{
			if(recuperoExitoso == true){
				
				modelo.put("errorRecupero", 1);
				modelo.put("msjRecupero", "Se ha enviado un mail de recuperación.");
				
			}else{
				modelo.put("errorRecupero", 1);
				modelo.put("msjRecupero", "El usuario no se encuentra registrado.");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			modelo.put("errorRecupero", 1);
			modelo.put("msjRecupero", "Hubo problemas para recuperar la contraseña");
		}
		
		return new ModelAndView("usuario",modelo);
	}
	
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
			String msj = usuarioBuscado.getNombre() + ", este es un mensaje para recuperar tu contraseña por favor entre en el siguiente enlace http://localhost:8080/proyecto-limpio-spring/obteniendoPass?id="+usuarioBuscado.getId();
			
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
	public ModelAndView obteniendoPass(@RequestParam(value="id") Long id){
		ModelMap modelo = new ModelMap();
		modelo.put("pass", servicioUsuario.getPassById(id));
		servicioLogin.recuperarContraseniaLog(id);
		return new ModelAndView("recuperarPass",modelo);
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
		     message.setFrom(new InternetAddress(user));  
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		     message.setSubject("javatpoint");  
		     message.setText(msj);  
		       
		    //send the message  
		     Transport.send(message);  
		  
		     //System.out.println("message sent successfully...");  
		   
		     } catch (MessagingException e) {e.printStackTrace();} 
	}
}

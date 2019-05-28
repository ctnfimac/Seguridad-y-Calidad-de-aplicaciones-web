package ar.edu.unlam.tallerweb1.controladores;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdmin;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;

@Controller
public class ControladorLogin {

	private int cantIntentoIngreso = 0;
	
	// La anotacion @Inject indica a Spring que en este atributo se debe setear (inyeccion de dependencias)
	// un objeto de una clase que implemente la interface ServicioLogin, dicha clase debe estar anotada como
	// @Service o @Repository y debe estar en un paquete de los indicados en applicationContext.xml
	@Inject
	private ServicioLogin servicioLogin;
	@Inject
	private ServicioUsuario servicioUsuario;
	@Inject
	private ServicioAdmin servicioAdmin;
	/*
	@Inject
	private ServicioCache cacheManager;
	*/
	// Este metodo escucha la URL localhost:8080/NOMBRE_APP/login si la misma es invocada por metodo http GET
	@RequestMapping("/login")
	public ModelAndView irALogin(HttpServletRequest request) {
		ModelMap modelo = new ModelMap();
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") == null)misession.invalidate();
		else modelo.put("rol", misession.getAttribute("ROL"));
		servicioLogin.cargarDatos();
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		
		return new ModelAndView("login", modelo);
	}

	// Este metodo escucha la URL validar-login siempre y cuando se invoque con metodo http POST
	// El método recibe un objeto Usuario el que tiene los datos ingresados en el form correspondiente y se corresponde con el modelAttribute definido en el
	// tag form:form
	@RequestMapping(path = "/validar-login", method = RequestMethod.POST)
	public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
		ModelMap model = new ModelMap();		
		Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario);		
		if (usuarioBuscado != null) {
			
			this.cantIntentoIngreso = 0;
			
			request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
			request.getSession().setAttribute("sessionId", usuarioBuscado.getId());
			request.getSession().setAttribute("sessionNombre", usuarioBuscado.getNombre());

			Long id = usuarioBuscado.getId();
			servicioLogin.saveLogIngreso(id);
			
			if(usuarioBuscado.getRol() == "admin") {
				return new ModelAndView("redirect:/admin");	
			}
			else {
				if(usuarioBuscado.getHabilitado() == true) {
					return new ModelAndView("redirect:/usuario");
				}
				else {
					model.put("error", "Su usuario se encuentra deshabilitado. Comuniquese con el Administrador");
					return new ModelAndView("login", model);	
				}
			}
			
		} else 
		{	
			Usuario usuarioIntento = servicioUsuario.getUsuarioByEmail(usuario.getEmail());	
			
			if(usuarioIntento != null) {
				if(cantIntentoIngreso <= 3) {		
					Usuario user = servicioLogin.consultarUsuario(usuario);				
					if(user == null) {
						model.put("error", "Usuario o clave incorrecta");
						this.cantIntentoIngreso ++;
					}				
				}else {
					
					servicioAdmin.cambiarEstadoUsuario(usuarioIntento.getId(), false);
					this.cantIntentoIngreso = 0;
					model.put("error", "Su usuario ha sido deshabilitado. Comuniquese con el Administrador");
					return new ModelAndView("login", model);	
				}
			}
		}

		return new ModelAndView("login", model);
	}

	// Escucha la URL /home por GET, y redirige a una vista.
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public ModelAndView irAHome() {
		return new ModelAndView("home");
	}

	// Escucha la url /, y redirige a la URL /login, es lo mismo que si se invoca la url /login directamente.
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView inicio() {
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(path = "/cerrarSession", method = RequestMethod.GET)
	public ModelAndView irACerrarSesision(HttpServletRequest request) {
		HttpSession misession= (HttpSession) request.getSession();
		
		servicioLogin.cerrarLogSession((Long)misession.getAttribute("sessionId"));
		
		misession.removeAttribute("sessionId");
		misession.removeAttribute("sessionNombre");
		misession.removeAttribute("ROL");
		return new ModelAndView("redirect:login");
	}
	
}

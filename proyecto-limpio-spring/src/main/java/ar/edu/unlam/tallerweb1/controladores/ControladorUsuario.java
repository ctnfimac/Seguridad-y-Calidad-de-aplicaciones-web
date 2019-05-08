package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;

@Controller
public class ControladorUsuario {
	
	@Inject
	private ServicioLogin servicioLogin;
	
	@RequestMapping(path="/usuario")
	public ModelAndView irAusuario(){
		return new ModelAndView("usuario");
	}
	
	@RequestMapping(path="/registrar-usuario", method = RequestMethod.POST)
	public ModelAndView registrarUsuario(@ModelAttribute("usuario") Usuario usuarioNuevo){
		ModelMap modelo = new ModelMap();
		try{
			if(!usuarioNuevo.getEmail().isEmpty() && !usuarioNuevo.getNombre().isEmpty() 
					&& !usuarioNuevo.getPassword().isEmpty() && !usuarioNuevo.getPassword2().isEmpty()){
				if(!usuarioNuevo.getPassword().isEmpty() && !usuarioNuevo.getPassword2().isEmpty() & 
						usuarioNuevo.getPassword().equals(usuarioNuevo.getPassword2())){
					servicioLogin.registrarUsuario(usuarioNuevo);
					modelo.put("errorRegistro", 0);
					modelo.put("msjregistro", "se registro exitosamente");
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
		return new ModelAndView("login",modelo);
	}
	
//	ayuda para ver por consola que usuarios estan registrados
//	@RequestMapping(path="/mostrarusuarios")
//	public ModelAndView mostrarUsuarios(){
//		List<Usuario> usuarios = servicioLogin.obtenerUsuarios();
//		ModelMap modelo = new ModelMap();
//
//		for(Usuario usuario : usuarios){
//			System.out.println("nombre: " + usuario.getNombre());
//			System.out.println("email: " + usuario.getEmail());
//			System.out.println("contraseña: " + usuario.getNombre());
//			System.out.println("estado: " + usuario.getEstado());
//			System.out.println("rol: " + usuario.getRol());
//			System.out.println("------------------------------------");
//		}
//		Usuario usuario = new Usuario();
//		modelo.put("usuario", usuario);
//		return new ModelAndView("login",modelo);
//	}
}

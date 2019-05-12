package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdmin;
@Controller
public class ContoladorAdmin {
	
	@Inject
	private ServicioAdmin servicioAdmin;
	
	@RequestMapping(path="/admin")
	public ModelAndView admin(){
		return new ModelAndView("admin");
	}
	
	@RequestMapping(path="/admin-historial", method= RequestMethod.GET)
	public ModelAndView adminHistorial(@RequestParam Long id){
		ModelMap modelo = new ModelMap();
		List<Log> historial = null;
		//aca busco todos el historial de un usuario por id
		modelo.put("historial", historial);
		return new ModelAndView("admin-historial", modelo);
	}
	
	@RequestMapping(path = "/cambiarEstadoUsuario", method = RequestMethod.POST)
	public ModelAndView cambiarEstadoUsuario(Long idUsuario, Boolean estado, HttpServletRequest request) {
		ModelMap model = new ModelMap();
		
		servicioAdmin.cambiarEstadoUsuario(idUsuario, estado);
		
		List<Usuario> usuarios = servicioAdmin.listarUsuarios();

		model.put("usuarios", usuarios);
		
		return new ModelAndView("admin", model);
	}
}

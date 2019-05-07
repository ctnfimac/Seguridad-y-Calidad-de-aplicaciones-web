package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorUsuario {
	
	@RequestMapping(path="/usuario")
	public ModelAndView irAusuario(){
		return new ModelAndView("usuario");
	}
}

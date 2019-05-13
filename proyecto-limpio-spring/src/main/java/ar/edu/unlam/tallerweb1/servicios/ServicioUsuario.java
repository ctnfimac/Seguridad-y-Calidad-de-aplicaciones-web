package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioUsuario {
	
	void cambiarContrasenia(Long idUsuario, String contrasenia);

	Boolean recuperarContrasenia(Long idUsuario);
	Boolean getHabilitado(Long id);
}

package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioAdmin {
	
	void cambiarEstadoUsuario(Long idUsuario, Boolean estado);
	
	List<Usuario> listarUsuarios();
}

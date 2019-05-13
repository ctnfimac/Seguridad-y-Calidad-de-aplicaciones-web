package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioLogin {

	Usuario consultarUsuario(Usuario usuario);
	void cargarDatos();
	void registrarUsuario(Usuario usuario);
	List<Usuario> obtenerUsuarios();
	void saveLogIngreso(Long idUsuario);
	void cerrarLogSession(Long idUsuario);
}

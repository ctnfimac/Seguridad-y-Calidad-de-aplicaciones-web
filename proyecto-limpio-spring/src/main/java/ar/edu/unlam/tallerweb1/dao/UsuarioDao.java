package ar.edu.unlam.tallerweb1.dao;

import java.util.Date;
import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del DAO de Usuarios.
public interface UsuarioDao {
	
	Usuario consultarUsuario (Usuario usuario);
	void cargarDatos();
	void registrarUsuario(Usuario usuario);
	List<Usuario> obtenerUsuarios();
	Usuario GetUsuarioById(Long idUsuario);
	void cambiarContrasenia(Long idUsuario, String contrasenia, String salt);
	Boolean getHabilitado(Long id);
	Usuario getUsuarioByEmail(String email);
	String getPassById(Long id);
	void persistirSolicitudCambioDeContrasenia(Long id, Date fechaSolicitud,String keyLog);
	Integer usuarioCambiandoPass(String id, String keylog);
	Long getId(String idEncript);
}

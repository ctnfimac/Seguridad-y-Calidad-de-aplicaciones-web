package ar.edu.unlam.tallerweb1.servicios;

import java.util.Date;
import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioUsuario {
	
	void cambiarContrasenia(Long idUsuario, String contrasenia);

	Boolean recuperarContrasenia(Long idUsuario);
	Boolean getHabilitado(Long id);
	Usuario getUsuarioByEmail(String email);
	String getPassById(Long id);
	void persistirSolicitudCambioDeContrasenia(Long id, Date fechaSolicitud, String keyLog);
	Integer usuarioCambiandoPass(String id, String keylog);
	public Long getId(String idEncript);
}

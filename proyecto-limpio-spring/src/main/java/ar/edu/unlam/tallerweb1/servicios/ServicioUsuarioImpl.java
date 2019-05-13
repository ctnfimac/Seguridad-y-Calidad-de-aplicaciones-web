package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.AdminDao;
import ar.edu.unlam.tallerweb1.dao.LogDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Funcionalidad;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario{

	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private ServicioLog servicioLog;

	@Override
	public void cambiarContrasenia(Long idUsuario, String contrasenia) {
		
		usuarioDao.cambiarContrasenia(idUsuario, contrasenia);	
		
		String funcionalidad = "Cambio de contraseņa";
		
		String mensajeLog;
		mensajeLog = String.format("\"El usuario %s cambio su contrasenia. ", Long.toString(idUsuario));

		servicioLog.guardarLog(idUsuario, funcionalidad, mensajeLog);
	}

	@Override
	public Boolean recuperarContrasenia(Long idUsuario) {
		
		Usuario usuario = usuarioDao.GetUsuarioById(idUsuario);
		
		if(usuario != null) {
			
			String nuevaContrasenia = "123456";
			
			usuarioDao.cambiarContrasenia(idUsuario, nuevaContrasenia);
			
			String funcionalidad = "Recupero de contraseņa";
			
			String mensajeLog;
			mensajeLog = String.format("\"El usuario %s recuperķ su contrasenia. ", Long.toString(idUsuario));

			servicioLog.guardarLog(idUsuario, funcionalidad, mensajeLog);
			
			return true;
		}else {
			
			return false;
		}	
	}

	@Override
	public Boolean getHabilitado(Long id) {
		return usuarioDao.getHabilitado(id);
	}

	@Override
	public Usuario getUsuarioByEmail(String email) {
		return usuarioDao.getUsuarioByEmail(email);
	}

	@Override
	public String getPassById(Long id) {
		return usuarioDao.getPassById(id);
	}
}

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
		
		String funcionalidad = "Cambio de contraseña";
		
		String mensajeLog;
		mensajeLog = String.format("\"El usuario %u cambio su contrasenia. ", idUsuario);

		servicioLog.guardarLog(idUsuario, funcionalidad, mensajeLog);
	}

	@Override
	public Boolean recuperarContrasenia(Long idUsuario) {
		
		Usuario usuario = usuarioDao.GetUsuarioById(idUsuario);
		
		if(usuario != null) {
			
			String nuevaContrasenia = "123456";
			
			usuarioDao.cambiarContrasenia(idUsuario, nuevaContrasenia);
			
			String funcionalidad = "Recupero de contraseña";
			
			String mensajeLog;
			mensajeLog = String.format("\"El usuario %u recuperó su contrasenia. ", idUsuario);

			servicioLog.guardarLog(idUsuario, funcionalidad, mensajeLog);
			
			return true;
		}else {
			
			return false;
		}	
	}
}

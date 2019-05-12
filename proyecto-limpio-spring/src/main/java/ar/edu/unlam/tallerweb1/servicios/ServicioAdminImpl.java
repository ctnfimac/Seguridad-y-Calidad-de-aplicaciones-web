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

@Service("servicioAdmin")
@Transactional
public class ServicioAdminImpl implements ServicioAdmin{

	@Inject
	private AdminDao adminDao;
	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private ServicioLog servicioLog;

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioDao.obtenerUsuarios();
	}

	@Override
	public void cambiarEstadoUsuario(Long idUsuario, Boolean estado) {
		
		adminDao.cambiarEstado(idUsuario, estado);	
		
		String funcionalidad = null;
		if(estado == true)
			funcionalidad = "Usuario Habilitado";
		else
			funcionalidad = "Usuario Deshablitado";
		
//		String mensajeLog;
//		mensajeLog = String.format("\"El usuario %s fue %s. ", Long.toString(idUsuario), estado);
//		
//		// esta mal hay que mandar el id de usuario logeado 
//		servicioLog.guardarLog(idUsuario, funcionalidad, mensajeLog);
	}
}

package ar.edu.unlam.tallerweb1.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.codec.digest.Md5Crypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Funcionalidad;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {
	
	static Boolean datosCargados = false; // flag para cargar los datos una sola vez
	
	// Como todo dao maneja acciones de persistencia, normalmente estará inyectado el session factory de hibernate
	// el mismo está difinido en el archivo hibernateContext.xml
	@Inject
    private SessionFactory sessionFactory;

	@Override
	public Usuario consultarUsuario(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario resultado = null;
		Usuario usuarioExistente = null;
		usuarioExistente = (Usuario) session.createCriteria(Usuario.class)
		.add(Restrictions.eq("email", usuario.getEmail()))
		.uniqueResult();
		
		if(usuarioExistente != null){
			String encrypted2 = Md5Crypt.md5Crypt(usuario.getPassword().getBytes(), usuarioExistente.getPassword());
			if(usuarioExistente.getPassword().equals(encrypted2)) resultado = usuarioExistente;
		}

		return resultado;
	}

	@Override
	public void cargarDatos() {
		if(!this.datosCargados){
			final Session session = sessionFactory.getCurrentSession();
			this.datosCargados = true;
			Funcionalidad login = new Funcionalidad("Login");
			Funcionalidad logout = new Funcionalidad("Logout");
			Funcionalidad registro = new Funcionalidad("Regristro de usuario");
			Funcionalidad resetPassword = new Funcionalidad("Cambio de contrase�a");
			Funcionalidad recuperarContrase�a = new Funcionalidad("Recupero de contrase�a");
			Funcionalidad ingresartexto = new Funcionalidad("Ingreso texto nuevo");
			Funcionalidad modificacionTexto = new Funcionalidad("Modificacion de texto");
			Funcionalidad habilitarUsuario = new Funcionalidad("Usuario Habilitado");
			Funcionalidad rechazarUsuario = new Funcionalidad("Usuario Deshablitado");
			Funcionalidad verActividadPersonal = new Funcionalidad("Ver Historial de actividad");
			Funcionalidad verActividadUsuarios = new Funcionalidad("Ver Historial de usuarios");
			
			Usuario usuario1 = new Usuario("admin@admin.com", Md5Crypt.md5Crypt("admin".getBytes()), "admin");
			Usuario usuario2 = new Usuario("christian_estel87@hotmail.com", Md5Crypt.md5Crypt("123456".getBytes()), "user");
			Usuario usuario3 = new Usuario("usuario2@usuario2.com", Md5Crypt.md5Crypt("123456".getBytes()), "user");
			usuario1.setNombre("admin");
			usuario2.setNombre("Homero");
			usuario3.setNombre("usuario2");
			usuario3.setHabilitado(false);
			usuario1.setFechaAltaDeUsuario(new Date());
			usuario2.setFechaAltaDeUsuario(new Date());
			usuario3.setFechaAltaDeUsuario(new Date());
			
			session.save(usuario1);
			session.save(usuario2);
			session.save(usuario3);
			session.save(login);
			session.save(logout);		
			session.save(registro);
			session.save(resetPassword);
			session.save(recuperarContrase�a);
			session.save(ingresartexto);
			session.save(modificacionTexto);
			session.save(habilitarUsuario);
			session.save(rechazarUsuario);
			session.save(verActividadPersonal);
			session.save(verActividadUsuarios);
		}
	}

	@Override
	public void registrarUsuario(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();
		usuario.setFechaAltaDeUsuario(new Date());
		session.save(usuario);
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		final Session session = sessionFactory.getCurrentSession();
		List<Usuario> usuarios = null;
		usuarios = session.createCriteria(Usuario.class)
				.add(Restrictions.eq("rol","user"))
				.list();
		return usuarios;
	}

	@Override
	public Usuario GetUsuarioById(Long idUsuario) {
		
		final Session session = sessionFactory.getCurrentSession();
		
		return (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", idUsuario))
				.uniqueResult();
	}

	@Override
	public void cambiarContrasenia(Long idUsuario, String contrasenia) {
		
		final Session session = sessionFactory.getCurrentSession();
//		Transaction tx = null;
		
//	      try{
//	         tx = session.beginTransaction();
	         Usuario user = (Usuario)session.get(Usuario.class, idUsuario); 
	         user.setPassword(contrasenia);
	         session.update(user); 
//	         tx.commit();
//	      }catch (HibernateException e) {
//	         if (tx!=null) tx.rollback();
//	         e.printStackTrace(); 
//	      }finally {
//	         session.close(); 
//	      }		
	}

	@Override
	public Boolean getHabilitado(Long id) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		Boolean resultado = false;
		resultado = usuario.getHabilitado();
		return resultado;
	}

	@Override
	public Usuario getUsuarioByEmail(String email) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email",email))
				.uniqueResult();
		return usuario;
	}

	@Override
	public String getPassById(Long id) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id",id))
				.uniqueResult();
		String pass = "";
		pass = usuario.getPassword();
		return pass;
	}

	@Override
	public void persistirSolicitudCambioDeContrasenia(Long id, Date fechaSolicitud, String keyLog) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		
		usuario.setFechaDeRecuperacionDePass(fechaSolicitud);
		usuario.setKeyLog(keyLog);
		usuario.setRecuperandoPass(true);
		
		System.out.println("nombre: " + usuario.getNombre());
		System.out.println("keylog: " + usuario.getKeyLog());
		System.out.println("getFechaDeRecuperacionDePass: " + usuario.getFechaDeRecuperacionDePass());
		System.out.println("recuperandoPass: " + usuario.getRecuperandoPass());
		session.update(usuario);
	}

	@Override
	public Integer usuarioCambiandoPass(String id, String keylog) {
		Integer respuesta = 0;
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		
		//obtener lista de ids y comparar con el parametro id
		List<Usuario> listaUsuarios = session.createCriteria(Usuario.class)
						.list();
		Long idBuscado = 0L;
		for(Usuario u : listaUsuarios){
			String encrypted2 = Md5Crypt.md5Crypt(u.getId().toString().getBytes(), id);
			if(id.equals(encrypted2)){
				idBuscado = u.getId();
				break;
			}
		}
	
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", idBuscado))
				.uniqueResult();
		System.out.println("usuario keyLog:" + usuario.getKeyLog());
		if(usuario != null){
			// preguntar si el valor de recuperandoPass esta en true
			if(usuario.getRecuperandoPass()){
				// preguntar si el tiempo esta dentro de lo establecido
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				Date fecha = new Date();
				Long diferenciaDeTiempo = fecha.getTime() - usuario.getFechaDeRecuperacionDePass().getTime();
				//long diffMinutes = diferenciaDeTiempo / (60 * 1000) % 60;
				long diffSeconds = diferenciaDeTiempo / 1000;// % 60;
				//System.out.println(diffSeconds + " segundos");
				if(diffSeconds > 0 && diffSeconds < 120){
					System.out.println("keylog en servicio: " + keylog);
					System.out.println("usuario.getKeyLog en servicio: " + usuario.getKeyLog());
					if(keylog.equals(usuario.getKeyLog())){
						usuario.setKeyLog("");
						usuario.setRecuperandoPass(false);
						usuario.setFechaDeRecuperacionDePass(null);
						respuesta = 1; // todo ok para realizar el cammbio de pass
					}else{
						usuario.setKeyLog("");
						usuario.setRecuperandoPass(false);
						usuario.setFechaDeRecuperacionDePass(null);
						respuesta = 4; // no coincide los keylog
					}
				}else{
					respuesta = 3;//error de diferencia de tiempo
					usuario.setKeyLog("");
					usuario.setRecuperandoPass(false);
					usuario.setFechaDeRecuperacionDePass(null);
					//reseteo el keylog, fecha y estado
				}
			}else respuesta = 2; // no esta habilitado el flag recuperandoPass
		}
		System.out.println("respuesta:" + respuesta);
		return respuesta;
	}

	@Override
	public Long getId(String idEncript) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;

		//obtener lista de ids y comparar con el parametro id
		List<Usuario> listaUsuarios = session.createCriteria(Usuario.class)
						.list();
		Long idBuscado = 0L;
		for(Usuario u : listaUsuarios){
			String encrypted2 = Md5Crypt.md5Crypt(u.getId().toString().getBytes(), idEncript);
			if(idEncript.equals(encrypted2)){
				idBuscado = u.getId();
				break;
			}
		}
		return idBuscado;
	}
}


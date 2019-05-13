package ar.edu.unlam.tallerweb1.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

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
	
	// Como todo dao maneja acciones de persistencia, normalmente estarÃ¡ inyectado el session factory de hibernate
	// el mismo estÃ¡ difinido en el archivo hibernateContext.xml
	@Inject
    private SessionFactory sessionFactory;

	@Override
	public Usuario consultarUsuario(Usuario usuario) {

		// Se obtiene la sesion asociada a la transaccion iniciada en el servicio que invoca a este metodo y se crea un criterio
		// de busqueda de Usuario donde el email y password sean iguales a los del objeto recibido como parametro
		// uniqueResult da error si se encuentran mÃ¡s de un resultado en la busqueda.
		final Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario.getEmail()))
				.add(Restrictions.eq("password", usuario.getPassword()))
				.uniqueResult();
	}

	@Override
	public void cargarDatos() {
		if(!this.datosCargados){
			final Session session = sessionFactory.getCurrentSession();
			this.datosCargados = true;
			Funcionalidad login = new Funcionalidad("Login");
			Funcionalidad logout = new Funcionalidad("Logout");
			Funcionalidad registro = new Funcionalidad("Regristro de usuario");
			Funcionalidad resetPassword = new Funcionalidad("Cambio de contraseña");
			Funcionalidad recuperarContraseña = new Funcionalidad("Recupero de contraseña");
			Funcionalidad ingresartexto = new Funcionalidad("Ingreso texto nuevo");
			Funcionalidad modificacionTexto = new Funcionalidad("Modificacion de texto");
			Funcionalidad habilitarUsuario = new Funcionalidad("Usuario Habilitado");
			Funcionalidad rechazarUsuario = new Funcionalidad("Usuario Deshablitado");
			Funcionalidad verActividadPersonal = new Funcionalidad("Ver Historial de actividad");
			Funcionalidad verActividadUsuarios = new Funcionalidad("Ver Historial de usuarios");
			
			Usuario usuario1 = new Usuario("admin@admin.com", "admin", "admin");
			Usuario usuario2 = new Usuario("usuario1@usuario1.com", "123456", "user");
			Usuario usuario3 = new Usuario("usuario2@usuario2.com", "123456", "user");
			usuario1.setNombre("admin");
			usuario2.setNombre("usuario1");
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
			session.save(recuperarContraseña);
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
}

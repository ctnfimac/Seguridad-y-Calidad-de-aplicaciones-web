package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Funcionalidad;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.inject.Inject;

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
			Usuario usuario2 = new Usuario("ignacio@user.com", "123456", "user");
			
			session.save(usuario1);
			session.save(usuario2);
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
		session.save(usuario);
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		final Session session = sessionFactory.getCurrentSession();
		List<Usuario> usuarios = null;
		usuarios = session.createCriteria(Usuario.class).list();
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
		Transaction tx = null;
		
	      try{
	         tx = session.beginTransaction();
	         Usuario user = (Usuario)session.get(Usuario.class, idUsuario); 
	         user.setPassword(contrasenia);
	         session.update(user); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
		
	}



}

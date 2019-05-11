package ar.edu.unlam.tallerweb1.dao;

import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ar.edu.unlam.tallerweb1.modelo.Log;

@Repository("logDao")
public class LogDaoImpl implements LogDao {
	
	@Inject
    private SessionFactory sessionFactory;

	@Override
	public void guardarLog(Log nuevoLog) {
		
		final Session session = sessionFactory.getCurrentSession();
		
		session.save(nuevoLog);
	}
	
}

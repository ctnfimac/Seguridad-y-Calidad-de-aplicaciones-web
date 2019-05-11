package ar.edu.unlam.tallerweb1.servicios;

public interface ServicioLog {

	void guardarLog(Long idUsuario, String funcionalidad, String descripcion);
}

package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Log {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	@ManyToOne (cascade = {CascadeType.ALL})
	private Usuario usuario;
	
	@ManyToOne (cascade = {CascadeType.ALL})
	private Funcionalidad funcionalidad;
	
	private String descripcion;
	
	private Date fechaModificacion;
	
	public Log(){}

	public Log(Usuario usuario, Funcionalidad funcionalidad, String descripcion, Date fechaModificacionDeNota) {
		this.usuario = usuario;
		this.funcionalidad = funcionalidad;
		this.descripcion = descripcion;
		this.fechaModificacion = fechaModificacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcionalidad getFuncionalidad() {
		return funcionalidad;
	}

	public void setFuncionalidad(Funcionalidad funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacionDeNota(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	
}

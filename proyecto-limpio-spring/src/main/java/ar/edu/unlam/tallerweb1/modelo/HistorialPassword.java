package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class HistorialPassword {
	@Id
	@GeneratedValue
	private Long id;
	
	private Date fechaUltimaModificacion;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	private Usuario usuario;
	
	private String Password;
	private Boolean Activa; // true si la pass esta activa - false si no esta activa
	
	public HistorialPassword(){}

	public HistorialPassword(Date fechaUltimaModificacion, Usuario usuario, String password, Boolean activa) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.usuario = usuario;
		Password = password;
		Activa = activa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public Boolean getActiva() {
		return Activa;
	}

	public void setActiva(Boolean activa) {
		Activa = activa;
	}
}

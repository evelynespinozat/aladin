package com.certicom.ittsa.domain;

public class UsuarioAutorizante {

	private int IdAutorizante;
	private Integer IdUsuario;
	private Integer IdAgencia;
	private String descAgencia;
	private Integer IdOficina;
	private String descOficina;
	private String Nombres;
	private String Email;
	private String Dni;
	private boolean Estado;
	private String abreNombre;
	private String cargo;
	
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Integer getIdUsuario() {
		return IdUsuario;
	}

	public String getAbreNombre() {
		return abreNombre;
	}

	public void setAbreNombre(String abreNombre) {
		this.abreNombre = abreNombre;
	}


	public void setIdUsuario(Integer idUsuario) {
		IdUsuario = idUsuario;
	}

	public int getIdAutorizante() {
		return IdAutorizante;
	}

	public void setIdAutorizante(int idAutorizante) {
		IdAutorizante = idAutorizante;
	}

	public Integer getIdAgencia() {
		return IdAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		IdAgencia = idAgencia;
	}

	public String getDescAgencia() {
		return descAgencia;
	}

	public void setDescAgencia(String descAgencia) {
		this.descAgencia = descAgencia.toUpperCase();
	}

	public Integer getIdOficina() {
		return IdOficina;
	}

	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}

	public String getDescOficina() {
		return descOficina;
	}

	public void setDescOficina(String descOficina) {
		this.descOficina = descOficina.toUpperCase();
	}

	public String getNombres() {
		return Nombres;
	}

	public void setNombres(String nombres) {
		Nombres = nombres.toUpperCase();
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email.toUpperCase();
	}

	public boolean isEstado() {
		return Estado;
	}

	public void setEstado(boolean estado) {
		Estado = estado;
	}

	public String getDni() {
		return Dni;
	}

	public void setDni(String dni) {
		Dni = dni.toUpperCase();
	}
	
}

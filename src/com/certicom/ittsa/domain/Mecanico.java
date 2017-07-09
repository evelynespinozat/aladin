package com.certicom.ittsa.domain;

public class Mecanico {

	private Integer idMecanico;
	private boolean estado;
	private String nombre;
	private String apePaterno;
	private String apeMaterno;
	private Integer edad;
	private String direccion;
	private String especialidad;
	private Integer dni;

	private String nomCompleto;

	public Mecanico() {
	}

	public String getNomCompleto() {
		setNomCompleto(getNombre() + " " + getApePaterno()+ " " + getApeMaterno());
		return nomCompleto;
	}

	public void setNomCompleto(String nomCompleto) {
		this.nomCompleto = nomCompleto;
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public Integer getIdMecanico() {
		return idMecanico;
	}

	public void setIdMecanico(Integer idMecanico) {
		this.idMecanico = idMecanico;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApePaterno() {
		return apePaterno;
	}

	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}

	public String getApeMaterno() {
		return apeMaterno;
	}

	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

}

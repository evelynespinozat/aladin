package com.certicom.ittsa.domain;

import java.io.Serializable;

public class Agencia implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1390126420488226058L;
	private Integer Idagencia;
	private boolean Estado;
	private String Grupo;
	private String Email;
	private String Telefono2;
	private String Telefono1;
	private String Direccion;
	private String Descripcion;
	private double combustible;
	private Integer diascroquis;
	private Integer code_mtc;
	
	public Agencia(){
	}
	
	public Integer getCode_mtc() {
		return code_mtc;
	}

	public void setCode_mtc(Integer code_mtc) {
		this.code_mtc = code_mtc;
	}

	public double getCombustible() {
		return combustible;
	}

	public void setCombustible(double combustible) {
		this.combustible = combustible;
	}

	public Integer getIdagencia() {
		return Idagencia;
	}

	public boolean isEstado() {
		return Estado;
	}

	public String getGrupo() {
		return Grupo;
	}

	public String getEmail() {
		return Email;
	}

	public String getTelefono2() {
		return Telefono2;
	}

	public String getTelefono1() {
		return Telefono1;
	}

	public String getDireccion() {
		return Direccion;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setIdagencia(Integer idagencia) {
		Idagencia = idagencia;
	}


	public void setEstado(boolean estado) {
		Estado = estado;
	}


	public void setGrupo(String grupo) {
		Grupo = grupo.toUpperCase();
	}


	public void setEmail(String email) {
		Email = email.toUpperCase();
	}


	public void setTelefono2(String telefono2) {
		Telefono2 = telefono2.toUpperCase();
	}


	public void setTelefono1(String telefono1) {
		Telefono1 = telefono1.toUpperCase();
	}


	public void setDireccion(String direccion) {
		Direccion = direccion.toUpperCase();
	}


	public void setDescripcion(String descripcion) {
		Descripcion = descripcion.toUpperCase();
	}


	public Integer getDiascroquis() {
		return diascroquis;
	}

	public void setDiascroquis(Integer diascroquis) {
		this.diascroquis = diascroquis;
	}
}

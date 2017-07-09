package com.certicom.ittsa.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Datos_Familiares {
	
	private Integer idDatosF;
	private Integer idPiloto;
	private Integer idTerramoza;
	private String estadoCivil;
	private String apellidos;
	private String nombres;
	private String dni;
	private Date fechaNac;
	private String nroContacto;
	private String parentesco;
	private Date FechaUltimaActualizacion;
	private String UsuarioUltimaActualizacion;
	
	/***para Reportes**/
	private String fechaNacString;
	
	public Datos_Familiares(){}

	public Integer getIdDatosF() {
		return idDatosF;
	}

	public Integer getIdPiloto() {
		return idPiloto;
	}

	public Integer getIdTerramoza() {
		return idTerramoza;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public String getDni() {
		return dni;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public String getNroContacto() {
		return nroContacto;
	}

	public String getParentesco() {
		return parentesco;
	}

	public void setIdDatosF(Integer idDatosF) {
		this.idDatosF = idDatosF;
	}

	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}

	public void setIdTerramoza(Integer idTerramoza) {
		this.idTerramoza = idTerramoza;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil.toUpperCase();
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos.toUpperCase();
	}

	public void setNombres(String nombres) {
		this.nombres = nombres.toUpperCase();
	}

	public void setDni(String dni) {
		this.dni = dni.toUpperCase();
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public void setNroContacto(String nroContacto) {
		this.nroContacto = nroContacto.toUpperCase();
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco.toUpperCase();
	}

	public String getFechaNacString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		fechaNacString = formato.format(getFechaNac());
		return fechaNacString;
	}

	public void setFechaNacString(String fechaNacString) {
		this.fechaNacString = fechaNacString;
	}

	public Date getFechaUltimaActualizacion() {
		return FechaUltimaActualizacion;
	}

	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		FechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	public String getUsuarioUltimaActualizacion() {
		return UsuarioUltimaActualizacion;
	}

	public void setUsuarioUltimaActualizacion(String usuarioUltimaActualizacion) {
		UsuarioUltimaActualizacion = usuarioUltimaActualizacion;
	}

	

	
	
	
}

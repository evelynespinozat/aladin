package com.certicom.ittsa.domain;

import java.util.Date;

public class Formacion {
	
	private Integer formacionId;
	private String tipoFormacion;
	private String nivelAcademico;
	private String anio;
	private String especialidad;
	private Integer idPiloto;
	private Integer idTerramoza;
	private String titulo;
	private String centroEstudios;
	private byte[] imagen;
	private String rutaImagen;
	private Date FechaUltimaActualizacion;
	private String UsuarioUltimaActualizacion;
	
	public Formacion(){
		
	}

	public Integer getFormacionId() {
		return formacionId;
	}

	public String getTipoFormacion() {
		return tipoFormacion;
	}

	public String getNivelAcademico() {
		return nivelAcademico;
	}

	public String getAnio() {
		return anio;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public Integer getIdPiloto() {
		return idPiloto;
	}

	public Integer getIdTerramoza() {
		return idTerramoza;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getCentroEstudios() {
		return centroEstudios;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setFormacionId(Integer formacionId) {
		this.formacionId = formacionId;
	}

	public void setTipoFormacion(String tipoFormacion) {
		this.tipoFormacion = tipoFormacion.toUpperCase();
	}

	public void setNivelAcademico(String nivelAcademico) {
		this.nivelAcademico = nivelAcademico.toUpperCase();
	}

	public void setAnio(String anio) {
		this.anio = anio.toUpperCase();
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad.toUpperCase();
	}

	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}

	public void setIdTerramoza(Integer idTerramoza) {
		this.idTerramoza = idTerramoza;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo.toUpperCase();
	}

	public void setCentroEstudios(String centroEstudios) {
		this.centroEstudios = centroEstudios.toUpperCase();
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
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

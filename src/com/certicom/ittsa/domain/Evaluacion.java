package com.certicom.ittsa.domain;

import java.util.Date;

import org.primefaces.model.StreamedContent;

public class Evaluacion {
	
	private Integer evaluacionId;
	private String nombre;
	private String observacion;
	private Integer pilotoId;
	private Integer terramozaId;
	private Piloto piloto;
	private byte[] imagen;
	private String rutaImagen;
	private Date FechaUltimaActualizacion;
	private String UsuarioUltimaActualizacion;
	//ara vista
	private StreamedContent archivoFoto;
	
	public Evaluacion()
	{
		
	}

	public Integer getEvaluacionId() {
		return evaluacionId;
	}

	public String getNombre() {
		return nombre;
	}

	public String getObservacion() {
		return observacion;
	}

	public Integer getPilotoId() {
		return pilotoId;
	}

	public Integer getTerramozaId() {
		return terramozaId;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public StreamedContent getArchivoFoto() {
		return archivoFoto;
	}

	public void setEvaluacionId(Integer evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion.toUpperCase();
	}

	public void setPilotoId(Integer pilotoId) {
		this.pilotoId = pilotoId;
	}

	public void setTerramozaId(Integer terramozaId) {
		this.terramozaId = terramozaId;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public void setArchivoFoto(StreamedContent archivoFoto) {
		this.archivoFoto = archivoFoto;
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

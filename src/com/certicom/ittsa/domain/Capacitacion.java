package com.certicom.ittsa.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Capacitacion {
	
	private Integer capacitacionId;
	private String curso;
	private Date fecha;
	private String anio;
	private String titulo;
	private String centroEstudios;
	private Integer idPiloto;
	private Integer idTerramoza;
	private byte[] imagen;
	private String rutaImagen;
	private Date FechaUltimaActualizacion;
	private String UsuarioUltimaActualizacion;
	
	/**Para reportes*/
	private String fechaString;
	
	public Capacitacion()
	{
		
	}

	public Integer getCapacitacionId() {
		return capacitacionId;
	}

	public String getCurso() {
		return curso;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getAnio() {
		return anio;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getCentroEstudios() {
		return centroEstudios;
	}

	public Integer getIdPiloto() {
		return idPiloto;
	}

	public Integer getIdTerramoza() {
		return idTerramoza;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setCapacitacionId(Integer capacitacionId) {
		this.capacitacionId = capacitacionId;
	}

	public void setCurso(String curso) {
		this.curso = curso.toUpperCase();
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setAnio(String anio) {
		this.anio = anio.toUpperCase();
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo.toUpperCase();
	}

	public void setCentroEstudios(String centroEstudios) {
		this.centroEstudios = centroEstudios.toUpperCase();
	}

	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}

	public void setIdTerramoza(Integer idTerramoza) {
		this.idTerramoza = idTerramoza;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public String getFechaString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		fechaString = formato.format(getFecha());
		return fechaString;
	}

	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
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

package com.certicom.ittsa.domain;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Documento {

	private Integer documentoId;
	private String nombre;
	private String observacion;
	private Integer IdPiloto;
	private Integer IdTerramoza;
	private byte[] imagen;
	private String rutaImagen;
	private Date fechaCaducidad;
	private Date FechaUltimaActualizacion;
	private String UsuarioUltimaActualizacion;
	
	/**Para Reportes**/
	private String fechaCadString;

	
	public Documento() {}


	public Integer getDocumentoId() {
		return documentoId;
	}


	public String getNombre() {
		return nombre;
	}


	public String getObservacion() {
		return observacion;
	}


	public Integer getIdPiloto() {
		return IdPiloto;
	}


	public Integer getIdTerramoza() {
		return IdTerramoza;
	}


	public byte[] getImagen() {
		return imagen;
	}


	public String getRutaImagen() {
		return rutaImagen;
	}


	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}


	public void setDocumentoId(Integer documentoId) {
		this.documentoId = documentoId;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}


	public void setObservacion(String observacion) {
		this.observacion = observacion.toUpperCase();
	}


	public void setIdPiloto(Integer idPiloto) {
		IdPiloto = idPiloto;
	}


	public void setIdTerramoza(Integer idTerramoza) {
		IdTerramoza = idTerramoza;
	}


	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}


	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}


	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}


	public String getFechaCadString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		fechaCadString = formato.format(getFechaCaducidad());
		return fechaCadString;
	}


	public void setFechaCadString(String fechaCadString) {
		this.fechaCadString = fechaCadString;
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

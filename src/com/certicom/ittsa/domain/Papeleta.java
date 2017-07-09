package com.certicom.ittsa.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Papeleta {
	
	private Integer papeletaid;
	private Date fechaPapeleta;
	private String lugar;
	private Integer idBus;
	private String motivo;
	private String policia;
	private Integer idPiloto;
	private byte[] imagen;
	private String rutaImagen;
	private Date FechaUltimaActualizacion;
	private String UsuarioUltimaActualizacion;
	
	/**Para Reportes*/
	private String fechaString;
	private String nroBus;
	
	public Papeleta() {
	}

	public Integer getPapeletaid() {
		return papeletaid;
	}

	public Date getFechaPapeleta() {
		return fechaPapeleta;
	}

	public String getLugar() {
		return lugar;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public String getMotivo() {
		return motivo;
	}

	public String getPolicia() {
		return policia;
	}

	public Integer getIdPiloto() {
		return idPiloto;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setPapeletaid(Integer papeletaid) {
		this.papeletaid = papeletaid;
	}

	public void setFechaPapeleta(Date fechaPapeleta) {
		this.fechaPapeleta = fechaPapeleta;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar.toUpperCase();
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo.toUpperCase();
	}

	public void setPolicia(String policia) {
		this.policia = policia.toUpperCase();
	}

	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public String getFechaString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		fechaString = formato.format(getFechaPapeleta());
		return fechaString;
	}

	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
	}

	public String getNroBus() {
		return nroBus;
	}

	public void setNroBus(String nroBus) {
		this.nroBus = nroBus;
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

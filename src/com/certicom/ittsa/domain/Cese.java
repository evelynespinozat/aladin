package com.certicom.ittsa.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cese {
	
	private int idCese;
	private String motivo;
	private String observacion;
	private int IdTerramoza;
	private int IdPiloto;
	private Date Fecha;
	private Date FechaUltimaActualizacion;
	private String UsuarioUltimaActualizacion;
	
	/***Para Reporte**/
	private String fechaString;
	
	public int getIdCese() {
		return idCese;
	}
	public String getMotivo() {
		return motivo;
	}
	public String getObservacion() {
		return observacion;
	}
	public int getIdTerramoza() {
		return IdTerramoza;
	}
	public int getIdPiloto() {
		return IdPiloto;
	}
	public void setIdCese(int idCese) {
		this.idCese = idCese;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public void setIdTerramoza(int idTerramoza) {
		IdTerramoza = idTerramoza;
	}
	public void setIdPiloto(int idPiloto) {
		IdPiloto = idPiloto;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
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

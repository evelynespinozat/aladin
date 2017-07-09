package com.certicom.ittsa.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Incidencia {
	
	private Integer incidenciaID;
	private String descripcion;
	private Date fecha;
	private String tipo;
	private Integer idPiloto;
	private Integer idTerramoza;
	private Date FechaUltimaActualizacion;
	private String UsuarioUltimaActualizacion;

	/*Para Reportes*/
	private String fechaString;
	
	
	public Incidencia(){
		
	}



	public Integer getIncidenciaID() {
		return incidenciaID;
	}



	public void setIncidenciaID(Integer incidenciaID) {
		this.incidenciaID = incidenciaID;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public Integer getIdPiloto() {
		return idPiloto;
	}



	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}



	public Integer getIdTerramoza() {
		return idTerramoza;
	}



	public void setIdTerramoza(Integer idTerramoza) {
		this.idTerramoza = idTerramoza;
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

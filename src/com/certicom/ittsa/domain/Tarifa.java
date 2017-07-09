package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Tarifa {

	private Integer idTarifa;
	private Integer idOrigen;
	private Integer idDestino;
	private String tipo;
	private Double precioEnvioNormal;
	private BigDecimal precioEnvioCorporativo;
	private BigDecimal rangoInicio;
	private BigDecimal rangoFin;
	private Date fRegistro;
	private Boolean porcentual;
	private Boolean estado;
	private String sdistrito;
	private BigDecimal precioKilo;
	private BigDecimal precioExtraRapido;
	private BigDecimal precioVolumen;

	/******** Otros **********/
	private String precioString;
	private String precioCorporativoString;
	private String porcentualString;
	private String nombOrigen;
	private String nombDestino;
	private String sid_ubigeo;

	public Integer getIdTarifa() {
		return idTarifa;
	}

	public void setIdTarifa(Integer idTarifa) {
		this.idTarifa = idTarifa;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getRangoInicio() {
		return rangoInicio;
	}

	public void setRangoInicio(BigDecimal rangoInicio) {
		this.rangoInicio = rangoInicio;
	}

	public BigDecimal getRangoFin() {
		return rangoFin;
	}

	public void setRangoFin(BigDecimal rangoFin) {
		this.rangoFin = rangoFin;
	}

	public Date getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Boolean getPorcentual() {
		return porcentual;
	}

	public void setPorcentual(Boolean porcentual) {
		this.porcentual = porcentual;
	}

	public String getPorcentualString() {
		if (getPorcentual()) {
			porcentualString = "SI";
		} else {
			porcentualString = "NO";
		}
		return porcentualString;
	}

	public void setPorcentualString(String porcentualString) {
		this.porcentualString = porcentualString;
	}

	public String getNombOrigen() {
		return nombOrigen;
	}

	public void setNombOrigen(String nombOrigen) {
		this.nombOrigen = nombOrigen;
	}

	public String getPrecioString() {
		return precioString;
	}

	public void setPrecioString(String precioString) {
		this.precioString = precioString;
	}

	public String getNombDestino() {
		return nombDestino;
	}

	public void setNombDestino(String nombDestino) {
		this.nombDestino = nombDestino;
	}

	public Double getPrecioEnvioNormal() {
		return precioEnvioNormal;
	}

	public void setPrecioEnvioNormal(Double precioEnvioNormal) {
		this.precioEnvioNormal = precioEnvioNormal;
	}

	public BigDecimal getPrecioEnvioCorporativo() {
		return precioEnvioCorporativo;
	}

	public void setPrecioEnvioCorporativo(BigDecimal precioEnvioCorporativo) {
		this.precioEnvioCorporativo = precioEnvioCorporativo;
	}

	public String getPrecioCorporativoString() {
		return precioCorporativoString;
	}

	public void setPrecioCorporativoString(String precioCorporativoString) {
		this.precioCorporativoString = precioCorporativoString;
	}

	public String getSdistrito() {
		return sdistrito;
	}

	public void setSdistrito(String sdistrito) {
		this.sdistrito = sdistrito;
	}

	public BigDecimal getPrecioKilo() {
		return precioKilo;
	}

	public void setPrecioKilo(BigDecimal precioKilo) {
		this.precioKilo = precioKilo;
	}

	public BigDecimal getPrecioExtraRapido() {
		return precioExtraRapido;
	}

	public void setPrecioExtraRapido(BigDecimal precioExtraRapido) {
		this.precioExtraRapido = precioExtraRapido;
	}

	public BigDecimal getPrecioVolumen() {
		return precioVolumen;
	}

	public void setPrecioVolumen(BigDecimal precioVolumen) {
		this.precioVolumen = precioVolumen;
	}

	public String getSid_ubigeo() {
		return sid_ubigeo;
	}

	public void setSid_ubigeo(String sid_ubigeo) {
		this.sid_ubigeo = sid_ubigeo;
	}
	
}

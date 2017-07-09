package com.certicom.ittsa.domain;

import java.util.Date;

public class NotaCobranza {

	private Integer idNotaCobranza;
	private String serieNotaCobranza;
	private Integer numeroNotaCobranza;
	private Date fRegistro;
	private Date fEmision;
	private Integer idOrigen;
	private Integer idDestino;
	private Integer idEncomienda;
	private String estado; // POR PAGAR Y PAGADO
	private String desOrigen;
	private String desDestino;
	private String razonSocialRemitente;
	private String nombresRemitente;
	private String apellidosRemitente;
	private String rucRemitente;
	private String dniRemitente;
	private String numeroFisico;
	private String tipoDocumento;
	private String comprobante;
	private Double totalCobrado;
	

	public NotaCobranza() {
	}

	public String getRazonSocialRemitente() {
		return razonSocialRemitente;
	}

	public void setRazonSocialRemitente(String razonSocialRemitente) {
		this.razonSocialRemitente = razonSocialRemitente;
	}

	public String getNombresRemitente() {
		return nombresRemitente;
	}

	public void setNombresRemitente(String nombresRemitente) {
		this.nombresRemitente = nombresRemitente;
	}

	public String getApellidosRemitente() {
		return apellidosRemitente;
	}

	public void setApellidosRemitente(String apellidosRemitente) {
		this.apellidosRemitente = apellidosRemitente;
	}

	public String getRucRemitente() {
		return rucRemitente;
	}

	public void setRucRemitente(String rucRemitente) {
		this.rucRemitente = rucRemitente;
	}

	public String getDniRemitente() {
		return dniRemitente;
	}

	public void setDniRemitente(String dniRemitente) {
		this.dniRemitente = dniRemitente;
	}

	public String getDesOrigen() {
		return desOrigen;
	}

	public void setDesOrigen(String desOrigen) {
		this.desOrigen = desOrigen;
	}

	public String getDesDestino() {
		return desDestino;
	}

	public void setDesDestino(String desDestino) {
		this.desDestino = desDestino;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getIdNotaCobranza() {
		return idNotaCobranza;
	}

	public void setIdNotaCobranza(Integer idNotaCobranza) {
		this.idNotaCobranza = idNotaCobranza;
	}

	public String getSerieNotaCobranza() {
		return serieNotaCobranza;
	}

	public void setSerieNotaCobranza(String serieNotaCobranza) {
		this.serieNotaCobranza = serieNotaCobranza;
	}

	public Integer getNumeroNotaCobranza() {
		return numeroNotaCobranza;
	}

	public void setNumeroNotaCobranza(Integer numeroNotaCobranza) {
		this.numeroNotaCobranza = numeroNotaCobranza;
	}

	public Date getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}

	public Date getfEmision() {
		return fEmision;
	}

	public void setfEmision(Date fEmision) {
		this.fEmision = fEmision;
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

	public Integer getIdEncomienda() {
		return idEncomienda;
	}

	public void setIdEncomienda(Integer idEncomienda) {
		this.idEncomienda = idEncomienda;
	}

	public String getNumeroFisico() {
		return numeroFisico;
	}

	public void setNumeroFisico(String numeroFisico) {
		this.numeroFisico = numeroFisico;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public Double getTotalCobrado() {
		return totalCobrado;
	}

	public void setTotalCobrado(Double totalCobrado) {
		this.totalCobrado = totalCobrado;
	}
	
	
	
}

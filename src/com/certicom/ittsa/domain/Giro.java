package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Giro {

	private Integer idGiro;
	private Integer idOrigen;
	private Integer idDestino;
	private Integer idOficina;
	private String tipoPersona;
	private Double montoGiro;
	private String documento;
	private String tipoDocumento;
	private Integer idUsuario;
	private Integer idPuntoVentaOrigen;
	private Double totalCobrado;
	private String dniRemitente;
	private String apellidosRemitente;
	private String nombresRemitente;
	private String telefonoRemitente;
	private String rucRemitente;
	private String razonSocialRemitente;
	private String dniDestinatario1;
	private String apellidosDestinatario1;
	private String nombresDestinatario1;
	private String telefonoDestinatario1;
	private String rucDestinatario1;
	private String razonSocialDestinatario1;
	private String dniDestinatario2;
	private String ApellidosDestinatario2;
	private String nombresDestinatario2;
	private String telefonoDestinatario2;
	private String rucDestinatario2;
	private String razonSocialDestinatario2;
	private String observacionEnvio;
	private Date fEnvio;
	private Date fRecojo;
	private String observacionRecojo;
	private Date fRegistro;
	private String tipoVenta;
	private Integer idPromocion;
	private Integer idAutorizante;
	private String tipoEntrega;
	private Integer estado;
	private String codigo;
	private Integer idPuntoVentaDestino;
	private String guiaRemitente;

	// //Otros
	private String origenString;
	private String destinoString;
	private String fRegistroString;
	private String desOficina;

	private Date fecInicio;
	private Date fecFin;
	
	public Giro() {

	}

	public String getDesOficina() {
		return desOficina;
	}

	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}

	public Integer getIdGiro() {
		return idGiro;
	}

	public void setIdGiro(Integer idGiro) {
		this.idGiro = idGiro;
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

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdPuntoVentaOrigen() {
		return idPuntoVentaOrigen;
	}

	public void setIdPuntoVentaOrigen(Integer idPuntoVentaOrigen) {
		this.idPuntoVentaOrigen = idPuntoVentaOrigen;
	}

	public Double getTotalCobrado() {
		return totalCobrado;
	}

	public void setTotalCobrado(Double totalCobrado) {
		this.totalCobrado = totalCobrado;
	}

	public String getDniRemitente() {
		return dniRemitente;
	}

	public void setDniRemitente(String dniRemitente) {
		this.dniRemitente = dniRemitente;
	}

	public String getTelefonoRemitente() {
		return telefonoRemitente;
	}

	public void setTelefonoRemitente(String telefonoRemitente) {
		this.telefonoRemitente = telefonoRemitente;
	}

	public String getDniDestinatario1() {
		return dniDestinatario1;
	}

	public void setDniDestinatario1(String dniDestinatario1) {
		this.dniDestinatario1 = dniDestinatario1;
	}

	public String getTelefonoDestinatario1() {
		return telefonoDestinatario1;
	}

	public void setTelefonoDestinatario1(String telefonoDestinatario1) {
		this.telefonoDestinatario1 = telefonoDestinatario1;
	}

	public String getDniDestinatario2() {
		return dniDestinatario2;
	}

	public void setDniDestinatario2(String dniDestinatario2) {
		this.dniDestinatario2 = dniDestinatario2;
	}

	public String getTelefonoDestinatario2() {
		return telefonoDestinatario2;
	}

	public void setTelefonoDestinatario2(String telefonoDestinatario2) {
		this.telefonoDestinatario2 = telefonoDestinatario2;
	}

	public String getObservacionEnvio() {
		return observacionEnvio;
	}

	public void setObservacionEnvio(String observacionEnvio) {
		this.observacionEnvio = observacionEnvio;
	}

	public Date getfEnvio() {
		return fEnvio;
	}

	public void setfEnvio(Date fEnvio) {
		this.fEnvio = fEnvio;
	}

	public Date getfRecojo() {
		return fRecojo;
	}

	public void setfRecojo(Date fRecojo) {
		this.fRecojo = fRecojo;
	}

	public String getObservacionRecojo() {
		return observacionRecojo;
	}

	public void setObservacionRecojo(String observacionRecojo) {
		this.observacionRecojo = observacionRecojo;
	}

	public Date getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}

	public String getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public Integer getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromocion(Integer idPromocion) {
		this.idPromocion = idPromocion;
	}

	public Integer getIdAutorizante() {
		return idAutorizante;
	}

	public void setIdAutorizante(Integer idAutorizante) {
		this.idAutorizante = idAutorizante;
	}

	public String getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getIdPuntoVentaDestino() {
		return idPuntoVentaDestino;
	}

	public void setIdPuntoVentaDestino(Integer idPuntoVentaDestino) {
		this.idPuntoVentaDestino = idPuntoVentaDestino;
	}

	public String getGuiaRemitente() {
		return guiaRemitente;
	}

	public void setGuiaRemitente(String guiaRemitente) {
		this.guiaRemitente = guiaRemitente;
	}

	public String getRucRemitente() {
		return rucRemitente;
	}

	public void setRucRemitente(String rucRemitente) {
		this.rucRemitente = rucRemitente;
	}

	public String getRucDestinatario1() {
		return rucDestinatario1;
	}

	public void setRucDestinatario1(String rucDestinatario1) {
		this.rucDestinatario1 = rucDestinatario1;
	}

	public String getRucDestinatario2() {
		return rucDestinatario2;
	}

	public void setRucDestinatario2(String rucDestinatario2) {
		this.rucDestinatario2 = rucDestinatario2;
	}

	public String getRazonSocialRemitente() {
		return razonSocialRemitente;
	}

	public void setRazonSocialRemitente(String razonSocialRemitente) {
		this.razonSocialRemitente = razonSocialRemitente;
	}

	public String getRazonSocialDestinatario1() {
		return razonSocialDestinatario1;
	}

	public void setRazonSocialDestinatario1(String razonSocialDestinatario1) {
		this.razonSocialDestinatario1 = razonSocialDestinatario1;
	}

	public String getRazonSocialDestinatario2() {
		return razonSocialDestinatario2;
	}

	public void setRazonSocialDestinatario2(String razonSocialDestinatario2) {
		this.razonSocialDestinatario2 = razonSocialDestinatario2;
	}

	public String getOrigenString() {
		return origenString;
	}

	public void setOrigenString(String origenString) {
		this.origenString = origenString;
	}

	public String getDestinoString() {
		return destinoString;
	}

	public void setDestinoString(String destinoString) {
		this.destinoString = destinoString;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getfRegistroString() {
		return fRegistroString;
	}

	public void setfRegistroString(String fRegistroString) {
		this.fRegistroString = fRegistroString;
	}

	public Double getMontoGiro() {
		return montoGiro;
	}

	public void setMontoGiro(Double montoGiro) {
		this.montoGiro = montoGiro;
	}

	public String getApellidosRemitente() {
		return apellidosRemitente;
	}

	public void setApellidosRemitente(String apellidosRemitente) {
		this.apellidosRemitente = apellidosRemitente;
	}

	public String getNombresRemitente() {
		return nombresRemitente;
	}

	public void setNombresRemitente(String nombresRemitente) {
		this.nombresRemitente = nombresRemitente;
	}

	public String getNombresDestinatario1() {
		return nombresDestinatario1;
	}

	public void setNombresDestinatario1(String nombresDestinatario1) {
		this.nombresDestinatario1 = nombresDestinatario1;
	}

	public String getNombresDestinatario2() {
		return nombresDestinatario2;
	}

	public void setNombresDestinatario2(String nombresDestinatario2) {
		this.nombresDestinatario2 = nombresDestinatario2;
	}

	public String getApellidosDestinatario1() {
		return apellidosDestinatario1;
	}

	public void setApellidosDestinatario1(String apellidosDestinatario1) {
		this.apellidosDestinatario1 = apellidosDestinatario1;
	}

	public String getApellidosDestinatario2() {
		return ApellidosDestinatario2;
	}

	public void setApellidosDestinatario2(String apellidosDestinatario2) {
		ApellidosDestinatario2 = apellidosDestinatario2;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}
	
	

}

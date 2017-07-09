package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Postergacion {

	private Integer idpostergado;
	private Integer idasientoventa;
	private Date fechapostergacion;
	private Date fechacaducidad;
	private String estado;
	private String documentopersona;
	private String documentoempresa;
	private Integer usuarioactiva;
	private Date fechaactivacion;
	private String serie;
	private String numeroBoleto;
	private Date fecharegistro;
	private Integer usuarioid;
	private Boolean fechaAbierta;
	private Integer origen;
	private Integer destino;
	private BigDecimal monto;
	private Integer servicioid;

	private String origenstr;
	private String destinostr;
	private String serviciostr;

	// filtros
	private Integer idAgencia;
	private Integer idOficina;
	private Date fecInicio;
	private Date fecFin;
	private String nomPersona;
	private String horaPost;
	private Date FSalida;
	private String HSalida;
	private Integer numero;
	private Integer tiempVencer;
	private Integer tipoReserva;

	public Postergacion() {
	}

	public Integer getTiempVencer() {
		return tiempVencer;
	}

	public void setTiempVencer(Integer tiempVencer) {
		this.tiempVencer = tiempVencer;
	}

	public Integer getIdpostergado() {
		return idpostergado;
	}

	public void setIdpostergado(Integer idpostergado) {
		this.idpostergado = idpostergado;
	}

	public Integer getIdasientoventa() {
		return idasientoventa;
	}

	public void setIdasientoventa(Integer idasientoventa) {
		this.idasientoventa = idasientoventa;
	}

	public Date getFechapostergacion() {
		return fechapostergacion;
	}

	public void setFechapostergacion(Date fechapostergacion) {
		this.fechapostergacion = fechapostergacion;
	}

	public Date getFechacaducidad() {
		return fechacaducidad;
	}

	public void setFechacaducidad(Date fechacaducidad) {
		this.fechacaducidad = fechacaducidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDocumentopersona() {
		return documentopersona;
	}

	public void setDocumentopersona(String documentopersona) {
		this.documentopersona = documentopersona;
	}

	public String getDocumentoempresa() {
		return documentoempresa;
	}

	public void setDocumentoempresa(String documentoempresa) {
		this.documentoempresa = documentoempresa;
	}

	public Date getFechaactivacion() {
		return fechaactivacion;
	}

	public void setFechaactivacion(Date fechaactivacion) {
		this.fechaactivacion = fechaactivacion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Integer getUsuarioid() {
		return usuarioid;
	}

	public void setUsuarioid(Integer usuarioid) {
		this.usuarioid = usuarioid;
	}

	public Date getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public void setUsuarioactiva(Integer usuarioactiva) {
		this.usuarioactiva = usuarioactiva;
	}

	public Integer getUsuarioactiva() {
		return usuarioactiva;
	}

	public Boolean getFechaAbierta() {
		return fechaAbierta;
	}

	public void setFechaAbierta(Boolean fechaAbierta) {
		this.fechaAbierta = fechaAbierta;
	}

	public Integer getServicioid() {
		return servicioid;
	}

	public void setServicioid(Integer servicioid) {
		this.servicioid = servicioid;
	}

	public Integer getOrigen() {
		return origen;
	}

	public void setOrigen(Integer origen) {
		this.origen = origen;
	}

	public Integer getDestino() {
		return destino;
	}

	public void setDestino(Integer destino) {
		this.destino = destino;
	}

	public String getOrigenstr() {
		return origenstr;
	}

	public void setOrigenstr(String origenstr) {
		this.origenstr = origenstr;
	}

	public String getServiciostr() {
		return serviciostr;
	}

	public void setServiciostr(String serviciostr) {
		this.serviciostr = serviciostr;
	}

	public String getDestinostr() {
		return destinostr;
	}

	public void setDestinostr(String destinostr) {
		this.destinostr = destinostr;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
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

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public String getNomPersona() {
		return nomPersona;
	}

	public void setNomPersona(String nomPersona) {
		this.nomPersona = nomPersona;
	}

	public String getHoraPost() {
		return horaPost;
	}

	public void setHoraPost(String horaPost) {
		this.horaPost = horaPost;
	}

	public Date getFSalida() {
		return FSalida;
	}

	public void setFSalida(Date fSalida) {
		FSalida = fSalida;
	}

	public String getHSalida() {
		return HSalida;
	}

	public void setHSalida(String hSalida) {
		HSalida = hSalida;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(Integer tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public String getNumeroBoleto() {
		return numeroBoleto;
	}

	public void setNumeroBoleto(String numeroBoleto) {
		this.numeroBoleto = numeroBoleto;
	}
	
	

}

package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Date;

public class HistorialPasajero {

	private Integer IdHistorialPasajero;
	private String dni;
	private String serieBoleto;
	private Integer idOficina;
	private Integer numeroBoleto;
	private Date fRegistro;
	private String nroRuc;
	private Integer asientoventaid;
	private String operacion;
	private Integer nroasiento;
	private Integer piso;
	private Integer idservicio;
	private Integer idprogramacion;
	private Integer idpadre;
	private Integer puntoVentaId;
	private String tipoOperacion;
	private Servicio servicio;
	private Programacion programacion;
	private AsientoVenta asientoVenta;
	private Agencia agenciaOrigen;
	private Agencia agenciaDestino;
	private BigDecimal monto;
	private PuntoVenta puntoVenta;
	private Persona persona;
	private Date fechaCaducidad;
	private String tipoEntrega;
	private Boolean prepagado;
	
	


	public HistorialPasajero() {
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getSerieBoleto() {
		return serieBoleto;
	}

	public void setSerieBoleto(String serieBoleto) {
		this.serieBoleto = serieBoleto;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}
	
	public Integer getNumeroBoleto() {
		return numeroBoleto;
	}

	public void setNumeroBoleto(Integer numeroBoleto) {
		this.numeroBoleto = numeroBoleto;
	}

	public Date getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}

	public String getNroRuc() {
		return nroRuc;
	}

	public void setNroRuc(String nroRuc) {
		this.nroRuc = nroRuc;
	}

	public Integer getAsientoventaid() {
		return asientoventaid;
	}

	public void setAsientoventaid(Integer asientoventaid) {
		this.asientoventaid = asientoventaid;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public Integer getNroasiento() {
		return nroasiento;
	}

	public void setNroasiento(Integer nroasiento) {
		this.nroasiento = nroasiento;
	}

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public Integer getIdservicio() {
		return idservicio;
	}

	public void setIdservicio(Integer idservicio) {
		this.idservicio = idservicio;
	}


	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Programacion getProgramacion() {
		return programacion;
	}

	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}

	public Integer getIdprogramacion() {
		return idprogramacion;
	}

	public void setIdprogramacion(Integer idprogramacion) {
		this.idprogramacion = idprogramacion;
	}

	public AsientoVenta getAsientoVenta() {
		return asientoVenta;
	}

	public void setAsientoVenta(AsientoVenta asientoVenta) {
		this.asientoVenta = asientoVenta;
	}

	public Agencia getAgenciaOrigen() {
		return agenciaOrigen;
	}

	public void setAgenciaOrigen(Agencia agenciaOrigen) {
		this.agenciaOrigen = agenciaOrigen;
	}

	public Agencia getAgenciaDestino() {
		return agenciaDestino;
	}

	public void setAgenciaDestino(Agencia agenciaDestino) {
		this.agenciaDestino = agenciaDestino;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public Integer getIdHistorialPasajero() {
		return IdHistorialPasajero;
	}

	public void setIdHistorialPasajero(Integer idHistorialPasajero) {
		IdHistorialPasajero = idHistorialPasajero;
	}

	public Integer getIdpadre() {
		return idpadre;
	}

	public void setIdpadre(Integer idpadre) {
		this.idpadre = idpadre;
	}

	public Integer getPuntoVentaId() {
		return puntoVentaId;
	}

	public void setPuntoVentaId(Integer puntoVentaId) {
		this.puntoVentaId = puntoVentaId;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public Boolean getPrepagado() {
		return prepagado;
	}

	public void setPrepagado(Boolean prepagado) {
		this.prepagado = prepagado;
	}
	
}

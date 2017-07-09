package com.certicom.ittsa.domain;

import java.util.Date;

public class SalidaAutoparte {

	private Integer idSalidaAutoparte;
	private Integer idBus;
	private Integer idMecanico;
	private Integer idAutoparte;
	private Integer cantidad;
	private Integer idOficina;
	private Integer idAgencia;
	private Integer estado; // 1 peticion; 2 atendido
	private Date fecPedido;
	private String tipoMantenimiento;
	private String accion;
	private Integer idAlmacen;

	// campos alternos
	private String nomMecanico;
	private String descAutoparte;
	private Integer nroBus;
	private boolean seleccionado = false;
	
	private double subtotal;
	private double costo;

	public SalidaAutoparte() {

	}

	public String getNomMecanico() {
		return nomMecanico;
	}

	public void setNomMecanico(String nomMecanico) {
		this.nomMecanico = nomMecanico;
	}

	public String getDescAutoparte() {
		return descAutoparte;
	}

	public void setDescAutoparte(String descAutoparte) {
		this.descAutoparte = descAutoparte;
	}

	public Integer getNroBus() {
		return nroBus;
	}

	public void setNroBus(Integer nroBus) {
		this.nroBus = nroBus;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public Integer getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(Integer idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public Integer getIdSalidaAutoparte() {
		return idSalidaAutoparte;
	}

	public void setIdSalidaAutoparte(Integer idSalidaAutoparte) {
		this.idSalidaAutoparte = idSalidaAutoparte;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public Integer getIdMecanico() {
		return idMecanico;
	}

	public void setIdMecanico(Integer idMecanico) {
		this.idMecanico = idMecanico;
	}

	public Integer getIdAutoparte() {
		return idAutoparte;
	}

	public void setIdAutoparte(Integer idAutoparte) {
		this.idAutoparte = idAutoparte;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getFecPedido() {
		return fecPedido;
	}

	public void setFecPedido(Date fecPedido) {
		this.fecPedido = fecPedido;
	}

	public String getTipoMantenimiento() {
		return tipoMantenimiento;
	}

	public void setTipoMantenimiento(String tipoMantenimiento) {
		this.tipoMantenimiento = tipoMantenimiento;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public double getCosto() {
		return costo;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	
	
}

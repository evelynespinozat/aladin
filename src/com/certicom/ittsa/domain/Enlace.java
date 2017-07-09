package com.certicom.ittsa.domain;

import java.math.BigDecimal;

public class Enlace {

	private Integer idEnlace;
	private Integer servicioOrigen;
	private Integer servicioTramo;
	private String tipo;
	private boolean estado;
	private Integer intCorre;
	private String hora;
	private Integer IdServicioP;
	private Integer origen;
	private Integer destino;

	private String desOrigen;
	private String desDestino;
	private String desServicio;
	private String HSalida;

	// atributos secundarios
	private String ruta;
	private String horaSalida;
	private String tramo;
	private String horaSalidaTramo;
	private Double horasViaje;
	private BigDecimal precio1;
	private BigDecimal precio2;

	public Enlace() {
	}

	public Double getHorasViaje() {
		return horasViaje;
	}

	public void setHorasViaje(Double horasViaje) {
		this.horasViaje = horasViaje;
	}

	public BigDecimal getPrecio1() {
		return precio1;
	}

	public void setPrecio1(BigDecimal precio1) {
		this.precio1 = precio1;
	}

	public BigDecimal getPrecio2() {
		return precio2;
	}

	public void setPrecio2(BigDecimal precio2) {
		this.precio2 = precio2;
	}

	public Integer getIntCorre() {
		return intCorre;
	}

	public void setIntCorre(Integer intCorre) {
		this.intCorre = intCorre;
	}

	public Integer getIdEnlace() {
		return idEnlace;
	}

	public Integer getServicioOrigen() {
		return servicioOrigen;
	}

	public Integer getServicioTramo() {
		return servicioTramo;
	}

	public String getTipo() {
		return tipo;
	}

	public boolean isEstado() {
		return estado;
	}

	public String getRuta() {
		return ruta;
	}

	public String getHoraSalida() {
		return horaSalida;
	}

	public String getTramo() {
		return tramo;
	}

	public String getHoraSalidaTramo() {
		return horaSalidaTramo;
	}

	public void setIdEnlace(Integer idEnlace) {
		this.idEnlace = idEnlace;
	}

	public void setServicioOrigen(Integer servicioOrigen) {
		this.servicioOrigen = servicioOrigen;
	}

	public void setServicioTramo(Integer servicioTramo) {
		this.servicioTramo = servicioTramo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo.toUpperCase();
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta.toUpperCase();
	}

	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida.toUpperCase();
	}

	public void setTramo(String tramo) {
		this.tramo = tramo.toUpperCase();
	}

	public void setHoraSalidaTramo(String horaSalidaTramo) {
		this.horaSalidaTramo = horaSalidaTramo.toUpperCase();
	}

	public String getHora() {
		return hora;
	}

	public Integer getIdServicioP() {
		return IdServicioP;
	}

	public Integer getOrigen() {
		return origen;
	}

	public Integer getDestino() {
		return destino;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public void setIdServicioP(Integer idServicioP) {
		IdServicioP = idServicioP;
	}

	public void setOrigen(Integer origen) {
		this.origen = origen;
	}

	public void setDestino(Integer destino) {
		this.destino = destino;
	}

	public String getDesOrigen() {
		return desOrigen;
	}

	public String getDesDestino() {
		return desDestino;
	}

	public String getDesServicio() {
		return desServicio;
	}

	public void setDesOrigen(String desOrigen) {
		this.desOrigen = desOrigen;
	}

	public void setDesDestino(String desDestino) {
		this.desDestino = desDestino;
	}

	public void setDesServicio(String desServicio) {
		this.desServicio = desServicio;
	}

	public String getHSalida() {
		return HSalida;
	}

	public void setHSalida(String hSalida) {
		HSalida = hSalida;
	}

}

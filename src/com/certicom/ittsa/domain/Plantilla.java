package com.certicom.ittsa.domain;

public class Plantilla {
	public Integer IdPlantilla;
	public String Descripcion;
	public Integer IdClase;
	public Integer Idagencia;
	public Integer IdOficina;
	public Integer IdOrigen;
	public Integer IdDestino;
	public Integer IdServicio;
	
	public String desCServicio;
	public String desAgencia;
	public String desOficina;
	public String desOrigen;
	public String desDestino;
	public String HSalida;
	
	public Integer id;
	
	//atributo auxiliar
	public Integer IdAlmacen;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdPlantilla() {
		return IdPlantilla;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public Integer getIdClase() {
		return IdClase;
	}
	public Integer getIdagencia() {
		return Idagencia;
	}
	public Integer getIdOficina() {
		return IdOficina;
	}
	public Integer getIdOrigen() {
		return IdOrigen;
	}
	public Integer getIdDestino() {
		return IdDestino;
	}
	public void setIdPlantilla(Integer idPlantilla) {
		IdPlantilla = idPlantilla;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion.toUpperCase();
	}
	public void setIdClase(Integer idClase) {
		IdClase = idClase;
	}
	public void setIdagencia(Integer idagencia) {
		Idagencia = idagencia;
	}
	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}
	public void setIdOrigen(Integer idOrigen) {
		IdOrigen = idOrigen;
	}
	public void setIdDestino(Integer idDestino) {
		IdDestino = idDestino;
	}
	public String getDesCServicio() {
		return desCServicio;
	}
	public String getDesAgencia() {
		return desAgencia;
	}
	public String getDesOficina() {
		return desOficina;
	}
	public String getDesOrigen() {
		return desOrigen;
	}
	public String getDesDestino() {
		return desDestino;
	}
	public void setDesCServicio(String desCServicio) {
		this.desCServicio = desCServicio;
	}
	public void setDesAgencia(String desAgencia) {
		this.desAgencia = desAgencia;
	}
	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}
	public void setDesOrigen(String desOrigen) {
		this.desOrigen = desOrigen;
	}
	public void setDesDestino(String desDestino) {
		this.desDestino = desDestino;
	}
	public Integer getIdAlmacen() {
		return IdAlmacen;
	}
	public void setIdAlmacen(Integer idAlmacen) {
		IdAlmacen = idAlmacen;
	}
	public Integer getIdServicio() {
		return IdServicio;
	}
	public void setIdServicio(Integer idServicio) {
		IdServicio = idServicio;
	}
	public String getHSalida() {
		return HSalida;
	}
	public void setHSalida(String hSalida) {
		HSalida = hSalida;
	}
	
	
	
}

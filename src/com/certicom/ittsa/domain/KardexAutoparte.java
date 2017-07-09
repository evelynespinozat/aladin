package com.certicom.ittsa.domain;

import java.util.Date;

public class KardexAutoparte {
	
	public Integer IdKardex;
	public Integer IdAutoparte;
	public Integer  Idagencia;
	public Integer  IdOficina;
	public String  Tipo;
	public Integer  Ingreso;
	public Integer  Salida;
	public Date  FRegistro;
	public String docRef;
	public Integer IdBus;
	public Integer IdMecanico;
	
	public String nomMecanico;
	public Integer nroBus;
	
	public Integer IdProgramacion;
	public String desAgencia;
	public String desOficina;
	public String desProducto;
	public String medida;
	public String desMedida;
	public String desOrigen;
	public String desDestino;
	public String desServicio;
	public double precio;
	public String moneda;
	
	//para el consolidado de productos
	public Integer numBus;
	public Integer idProducto;
	public Integer cantConsumida;
	
	public Integer getIdBus() {
		return IdBus;
	}
	public void setIdBus(Integer idBus) {
		this.IdBus = idBus;
	}
	public Integer getNumBus() {
		return numBus;
	}
	public void setNumBus(Integer numBus) {
		this.numBus = numBus;
	}
	public Integer getCantConsumida() {
		return cantConsumida;
	}
	public void setCantConsumida(Integer cantConsumida) {
		this.cantConsumida = cantConsumida;
	}
	public Integer getIdProgramacion() {
		return IdProgramacion;
	}
	public String getDocRef() {
		return docRef;
	}
	public void setIdProgramacion(Integer idProgramacion) {
		IdProgramacion = idProgramacion;
	}
	public void setDocRef(String docRef) {
		this.docRef = docRef;
	}
	public Integer getIdKardex() {
		return IdKardex;
	}
	public Integer getIdagencia() {
		return Idagencia;
	}
	public String getTipo() {
		return Tipo;
	}

	public Date getFRegistro() {
		return FRegistro;
	}
	public String getDesAgencia() {
		return desAgencia;
	}
	public String getDesOficina() {
		return desOficina;
	}
	public String getDesProducto() {
		return desProducto;
	}
	public void setIdKardex(Integer idKardex) {
		IdKardex = idKardex;
	}
	public void setIdagencia(Integer idagencia) {
		Idagencia = idagencia;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}

	public void setFRegistro(Date fRegistro) {
		FRegistro = fRegistro;
	}
	public void setDesAgencia(String desAgencia) {
		this.desAgencia = desAgencia;
	}
	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}
	public void setDesProducto(String desProducto) {
		this.desProducto = desProducto;
	}
	public Integer getIdOficina() {
		return IdOficina;
	}
	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}
	public String getMedida() {
		return medida;
	}
	public String getDesMedida() {
		return desMedida;
	}
	public void setMedida(String medida) {
		this.medida = medida;
	}
	public void setDesMedida(String desMedida) {
		this.desMedida = desMedida;
	}
	public Integer getIngreso() {
		return Ingreso;
	}
	public void setIngreso(Integer ingreso) {
		Ingreso = ingreso;
	}
	public Integer getSalida() {
		return Salida;
	}
	public void setSalida(Integer salida) {
		Salida = salida;
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
	public Integer getIdAutoparte() {
		return IdAutoparte;
	}
	public void setIdAutoparte(Integer idAutoparte) {
		IdAutoparte = idAutoparte;
	}
	public Integer getIdMecanico() {
		return IdMecanico;
	}
	public String getNomMecanico() {
		return nomMecanico;
	}
	public Integer getNroBus() {
		return nroBus;
	}
	public void setIdMecanico(Integer idMecanico) {
		IdMecanico = idMecanico;
	}
	public void setNomMecanico(String nomMecanico) {
		this.nomMecanico = nomMecanico;
	}
	public void setNroBus(Integer nroBus) {
		this.nroBus = nroBus;
	}
	public double getPrecio() {
		return precio;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
}

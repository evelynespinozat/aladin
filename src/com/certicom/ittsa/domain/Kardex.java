package com.certicom.ittsa.domain;

import java.util.Date;

public class Kardex {
	
	public Integer IdKardex;
	public Integer IdProducto;
	public Integer  Idagencia;
	public Integer  IdOficina;
	public String  Tipo;
	public Integer  Ingreso;
	public Integer  Salida;
	public Date  FRegistro;
	public Integer IdProgramacion;
	public String docRef;
	private double costoUni; 
	private double costo;
	private Integer cantDist;
	private double costoxPasj;
	private String disgregacion;
	
	public String desAgencia;
	public String desOficina;
	public String desProducto;
	public String medida;
	public String desMedida;
	public String desOrigen;
	public String desDestino;
	public String desServicio;
	
	
	//para el consolidado de productos
	public Integer idBus;
	public Integer numBus;
	public Integer idProducto;
	public Integer cantConsumida;
	private String hSalida;
	private Date fSalida;
	
	public Integer getIdBus() {
		return idBus;
	}
	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
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
	public Integer getIdProducto() {
		return IdProducto;
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
	public void setIdProducto(Integer idProducto) {
		IdProducto = idProducto;
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
	public String gethSalida() {
		return hSalida;
	}
	public void sethSalida(String hSalida) {
		this.hSalida = hSalida;
	}
	public Date getfSalida() {
		return fSalida;
	}
	public void setfSalida(Date fSalida) {
		this.fSalida = fSalida;
	}
	public double getCostoUni() {
		return costoUni;
	}
	public void setCostoUni(double costoUni) {
		this.costoUni = costoUni;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public Integer getCantDist() {
		return cantDist;
	}
	public void setCantDist(Integer cantDist) {
		this.cantDist = cantDist;
	}
	public double getCostoxPasj() {
		return costoxPasj;
	}
	public void setCostoxPasj(double costoxPasj) {
		this.costoxPasj = costoxPasj;
	}
	public String getDisgregacion() {
		return disgregacion;
	}
	public void setDisgregacion(String disgregacion) {
		this.disgregacion = disgregacion;
	}
	
	
	
	
}

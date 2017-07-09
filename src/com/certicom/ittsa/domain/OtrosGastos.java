package com.certicom.ittsa.domain;

import java.util.Date;

public class OtrosGastos {
	private Integer IdOtrosGastos;
	private Integer IdOficina;
	private Integer IdAgencia;
	private Date fecha;
	private String motivo;
	private Double monto;
	private Double ultimoAporte;
	private Double saldo;
	private boolean estado;
	
	private String des_agencia;
	private String des_oficina;
	
	public Integer getIdOtrosGastos() {
		return IdOtrosGastos;
	}
	public void setIdOtrosGastos(Integer idOtrosGastos) {
		IdOtrosGastos = idOtrosGastos;
	}
	public Integer getIdOficina() {
		return IdOficina;
	}
	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}
	public Integer getIdAgencia() {
		return IdAgencia;
	}
	public void setIdAgencia(Integer idAgencia) {
		IdAgencia = idAgencia;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public Double getUltimoAporte() {
		return ultimoAporte;
	}
	public void setUltimoAporte(Double ultimoAporte) {
		this.ultimoAporte = ultimoAporte;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public boolean getEstado(){
		return this.estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public String getDes_agencia() {
		return des_agencia;
	}
	public void setDes_agencia(String des_agencia) {
		this.des_agencia = des_agencia;
	}
	public String getDes_oficina() {
		return des_oficina;
	}
	public void setDes_oficina(String des_oficina) {
		this.des_oficina = des_oficina;
	}
	
	
	

}

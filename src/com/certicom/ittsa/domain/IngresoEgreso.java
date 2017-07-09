package com.certicom.ittsa.domain;

import java.util.Date;

public class IngresoEgreso {
	
	private Integer idingresoegreso;
	private Date fechareg;
	private Integer operacion; // 1 = INGRESO ; 2 = EGRESO
	private String usuario;
	private Integer usuarioreg;
	private double monto;
	private Integer idagencia;
	private Integer idoficina;
	private String motivo;
	private Integer estado; // 1 = POR RENDIR ; 2 = RENDIDO

	//cambio
	public IngresoEgreso(){
	}

	public Integer getIdingresoegreso() {
		return idingresoegreso;
	}

	public void setIdingresoegreso(Integer idingresoegreso) {
		this.idingresoegreso = idingresoegreso;
	}

	public Date getFechareg() {
		return fechareg;
	}

	public void setFechareg(Date fechareg) {
		this.fechareg = fechareg;
	}

	public Integer getOperacion() {
		return operacion;
	}

	public void setOperacion(Integer operacion) {
		this.operacion = operacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getUsuarioreg() {
		return usuarioreg;
	}

	public void setUsuarioreg(Integer usuarioreg) {
		this.usuarioreg = usuarioreg;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Integer getIdagencia() {
		return idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public Integer getIdoficina() {
		return idoficina;
	}

	public void setIdoficina(Integer idoficina) {
		this.idoficina = idoficina;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}

package com.certicom.ittsa.domain;

import java.util.Date;

public class CierreCaja {

	private Integer idCierreCaja;
	private Integer idUsuario;
	private Double saldo;
	private Date fCierre;
	private Integer idPuntoVenta;
	private Integer idUsuarioReceptor;
	private Integer idOficina;
	private String tipoUsuario;

	public CierreCaja() {
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getIdUsuarioReceptor() {
		return idUsuarioReceptor;
	}

	public void setIdUsuarioReceptor(Integer idUsuarioReceptor) {
		this.idUsuarioReceptor = idUsuarioReceptor;
	}

	public Integer getIdCierreCaja() {
		return idCierreCaja;
	}

	public void setIdCierreCaja(Integer idCierreCaja) {
		this.idCierreCaja = idCierreCaja;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Date getfCierre() {
		return fCierre;
	}

	public void setfCierre(Date fCierre) {
		this.fCierre = fCierre;
	}

	public Integer getIdPuntoVenta() {
		return idPuntoVenta;
	}

	public void setIdPuntoVenta(Integer idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

}

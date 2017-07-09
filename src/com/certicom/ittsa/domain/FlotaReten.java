package com.certicom.ittsa.domain;

import java.util.Date;

public class FlotaReten {
	public Integer IdBusReten;
	public Integer IdBus;
	public Integer IdAgencia;
	public Integer idPiloto;
	public Integer idCopiloto;
	public String Estado;
	public Date FRegistro;

	public String descAgencia;
	public String namePiloto;
	public String nameCopiloto;
	public String descEstado;
	public String nroBus;
	public String dniPiloto;
	public String dniCopiloto;
	public String placa;
	public String telefono;
	
	public Integer getIdBusReten() {
		return IdBusReten;
	}
	public Integer getIdBus() {
		return IdBus;
	}
	public Integer getIdAgencia() {
		return IdAgencia;
	}
	public Integer getIdPiloto() {
		return idPiloto;
	}
	public Integer getIdCopiloto() {
		return idCopiloto;
	}
	public String getEstado() {
		return Estado;
	}
	public Date getFRegistro() {
		return FRegistro;
	}
	public String getDescAgencia() {
		return descAgencia;
	}
	public String getNamePiloto() {
		return namePiloto;
	}
	public String getNameCopiloto() {
		return nameCopiloto;
	}
	public String getDescEstado() {
		return descEstado;
	}
	public String getNroBus() {
		return nroBus;
	}
	public String getDniPiloto() {
		return dniPiloto;
	}
	public String getDniCopiloto() {
		return dniCopiloto;
	}
	public String getPlaca() {
		return placa;
	}
	public void setIdBusReten(Integer idBusReten) {
		IdBusReten = idBusReten;
	}
	public void setIdBus(Integer idBus) {
		IdBus = idBus;
	}
	public void setIdAgencia(Integer idAgencia) {
		IdAgencia = idAgencia;
	}
	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}
	public void setIdCopiloto(Integer idCopiloto) {
		this.idCopiloto = idCopiloto;
	}
	public void setEstado(String estado) {
		Estado = estado.toUpperCase();
	}
	public void setFRegistro(Date fRegistro) {
		FRegistro = fRegistro;
	}
	public void setDescAgencia(String descAgencia) {
		this.descAgencia = descAgencia.toUpperCase();
	}
	public void setNamePiloto(String namePiloto) {
		this.namePiloto = namePiloto.toUpperCase();
	}
	public void setNameCopiloto(String nameCopiloto) {
		this.nameCopiloto = nameCopiloto.toUpperCase();
	}
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado.toUpperCase();
	}
	public void setNroBus(String nroBus) {
		this.nroBus = nroBus.toUpperCase();
	}
	public void setDniPiloto(String dniPiloto) {
		this.dniPiloto = dniPiloto.toUpperCase();
	}
	public void setDniCopiloto(String dniCopiloto) {
		this.dniCopiloto = dniCopiloto.toUpperCase();
	}
	public void setPlaca(String placa) {
		this.placa = placa.toUpperCase();
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
}

package com.certicom.ittsa.domain;

import java.util.Date;

public class EncomiendaAlmacen {
	
	private Integer id;
	private Integer idEncomienda;
	private Date fRegistro;
	private Date fVencimiento;
	private Integer idAlmacen;
	private Integer idoficina;
	private boolean habido;
	public Integer getId() {
		return id;
	}
	public Integer getIdEncomienda() {
		return idEncomienda;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public Date getfVencimiento() {
		return fVencimiento;
	}
	public Integer getIdAlmacen() {
		return idAlmacen;
	}
	public Integer getIdoficina() {
		return idoficina;
	}
	public boolean isHabido() {
		return habido;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setIdEncomienda(Integer idEncomienda) {
		this.idEncomienda = idEncomienda;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public void setfVencimiento(Date fVencimiento) {
		this.fVencimiento = fVencimiento;
	}
	public void setIdAlmacen(Integer idAlmacen) {
		this.idAlmacen = idAlmacen;
	}
	public void setIdoficina(Integer idoficina) {
		this.idoficina = idoficina;
	}
	public void setHabido(boolean habido) {
		this.habido = habido;
	}
	
	
	
}

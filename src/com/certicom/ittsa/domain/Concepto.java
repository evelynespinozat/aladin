package com.certicom.ittsa.domain;

import java.util.Date;

public class Concepto {

	private int IdConcepto;
	private String Codigo;
	private String Descripcion;
	private Double Importe;
	private Date FRegistro;
	private boolean Estado;

	public int getIdConcepto() {
		return IdConcepto;
	}

	public String getCodigo() {
		return Codigo;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public Double getImporte() {
		return Importe;
	}

	public Date getFRegistro() {
		return FRegistro;
	}

	public boolean isEstado() {
		return Estado;
	}

	public void setIdConcepto(int idConcepto) {
		IdConcepto = idConcepto;
	}

	public void setCodigo(String codigo) {
		Codigo = codigo.toUpperCase();
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion.toUpperCase();
	}

	public void setImporte(Double importe) {
		Importe = importe;
	}

	public void setFRegistro(Date fRegistro) {
		FRegistro = fRegistro;
	}

	public void setEstado(boolean estado) {
		Estado = estado;
	}

}

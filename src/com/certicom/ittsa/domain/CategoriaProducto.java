package com.certicom.ittsa.domain;

public class CategoriaProducto {

	private int idCatProducto;
	private String Descripcion;
	private boolean Estado;
	private String tipo;
	
	public int getIdCatProducto() {
		return idCatProducto;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public boolean isEstado() {
		return Estado;
	}
	public String getTipo() {
		return tipo;
	}
	public void setIdCatProducto(int idCatProducto) {
		this.idCatProducto = idCatProducto;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion.toUpperCase();
	}
	public void setEstado(boolean estado) {
		Estado = estado;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo.toUpperCase();
	}

	

}

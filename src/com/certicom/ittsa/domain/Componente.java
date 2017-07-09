package com.certicom.ittsa.domain;

public class Componente {
	public int Idcomponente;
	public String Nombre;
	public String Descripcion;
	public boolean Estado;
	public int IdPuntoVenta;
	public String namePuntoVenta;
	
	public int getIdcomponente() {
		return Idcomponente;
	}
	
	public String getNombre() {
		return Nombre;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public boolean isEstado() {
		return Estado;
	}
	public int getIdPuntoVenta() {
		return IdPuntoVenta;
	}
	public String getNamePuntoVenta() {
		return namePuntoVenta;
	}
	public void setIdcomponente(int idcomponente) {
		Idcomponente = idcomponente;
	}
	public void setNombre(String nombre) {
		Nombre = nombre.toUpperCase();
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion.toUpperCase();
	}
	public void setEstado(boolean estado) {
		Estado = estado;
	}
	public void setIdPuntoVenta(int idPuntoVenta) {
		IdPuntoVenta = idPuntoVenta;
	}
	public void setNamePuntoVenta(String namePuntoVenta) {
		this.namePuntoVenta = namePuntoVenta.toUpperCase();
	}

	
	

}

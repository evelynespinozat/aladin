package com.certicom.ittsa.domain;

public class Producto {
	private int IdProducto;
	private String Descripcion;
	private String Medida;
	private boolean Estado;
	private double Costo;
	private double CostoUni;
	private Integer idCatProducto;
	private String desgregacion;
	private Integer cantDist;
	private double costoxPasj;
	
	private String desMedida;
	private String desCatProducto;
	
	public int getIdProducto() {
		return IdProducto;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public String getMedida() {
		return Medida;
	}
	public boolean isEstado() {
		return Estado;
	}
	public double getCosto() {
		return Costo;
	}
	public double getCostoUni() {
		return CostoUni;
	}
	public String getDesMedida() {
		return desMedida;
	}
	public void setIdProducto(int idProducto) {
		IdProducto = idProducto;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion.toUpperCase();
	}
	public void setMedida(String medida) {
		Medida = medida;
	}
	public void setEstado(boolean estado) {
		Estado = estado;
	}
	public void setCosto(double costo) {
		Costo = costo;
	}
	public void setCostoUni(double costoUni) {
		CostoUni = costoUni;
	}
	public void setDesMedida(String desMedida) {
		this.desMedida = desMedida.toUpperCase();
	}
	public Integer getIdCatProducto() {
		return idCatProducto;
	}
	public String getDesCatProducto() {
		return desCatProducto;
	}
	public void setIdCatProducto(Integer idCatProducto) {
		this.idCatProducto = idCatProducto;
	}
	public void setDesCatProducto(String desCatProducto) {
		this.desCatProducto = desCatProducto;
	}
	public String getDesgregacion() {
		return desgregacion;
	}
	public void setDesgregacion(String desgregacion) {
		this.desgregacion = desgregacion;
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


	
		

}

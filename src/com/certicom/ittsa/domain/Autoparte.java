package com.certicom.ittsa.domain;

public class Autoparte {
	
	private Integer idAutoparte;
	private Integer idAlmacen;
	private Integer idProveedor;
	private Integer idAutomotor;
	private String descripcion;
	private Double valorizacion;
	private Double stock;
	private Integer kmCambio;
	private boolean estado;
	
	private String medida;
	private String marca;
	private String moneda;
	private double costoIGV;
	private double costo;
	private String codigo;
	
	private String desMedida;
	
	public Autoparte(){
	}

	public Integer getIdAutoparte() {
		return idAutoparte;
	}

	public void setIdAutoparte(Integer idAutoparte) {
		this.idAutoparte = idAutoparte;
	}

	public Integer getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(Integer idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Integer getIdAutomotor() {
		return idAutomotor;
	}

	public void setIdAutomotor(Integer idAutomotor) {
		this.idAutomotor = idAutomotor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getValorizacion() {
		return valorizacion;
	}

	public void setValorizacion(Double valorizacion) {
		this.valorizacion = valorizacion;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Integer getKmCambio() {
		return kmCambio;
	}

	public void setKmCambio(Integer kmCambio) {
		this.kmCambio = kmCambio;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getMedida() {
		return medida;
	}

	public String getMarca() {
		return marca;
	}

	public String getMoneda() {
		return moneda;
	}

	public double getCostoIGV() {
		return costoIGV;
	}

	public double getCosto() {
		return costo;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public void setCostoIGV(double costoIGV) {
		this.costoIGV = costoIGV;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public String getDesMedida() {
		return desMedida;
	}

	public void setDesMedida(String desMedida) {
		this.desMedida = desMedida;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}

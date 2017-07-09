package com.certicom.ittsa.domain;


public class Asiento {	
	
	//datos originales
	private int IdAsiento;
	private int Idclase;
	private String Numero;
	private Integer Piso;
	private Boolean Estado;
	private Integer nroOrden;
	
	private ClaseServicio tipoBus;
	private AsientoVenta asientoVenta;
	private Integer idtipoasiento;
	private Boolean visible;
	
	
	
	// datos de agrupacion
	private String descClase;
	private Integer canPrPiso;
	private Integer canSePiso;
	
	private Integer n_canPrPiso;
	private Integer n_canSePiso;
	
	
	public Asiento(){}
	
	public int getIdAsiento() {
		return IdAsiento;
	}
	public void setIdAsiento(int idAsiento) {
		IdAsiento = idAsiento;
	}
	public int getIdclase() {
		return Idclase;
	}
	public void setIdclase(int idclase) {
		Idclase = idclase;
	}

	public Integer getPiso() {
		return Piso;
	}
	public void setPiso(Integer piso) {
		Piso = piso;
	}
	public String getDescClase() {
		return descClase;
	}
	public void setDescClase(String descClase) {
		this.descClase = descClase;
	}
	public Integer getCanPrPiso() {
		return canPrPiso;
	}
	public void setCanPrPiso(Integer canPrPiso) {
		this.canPrPiso = canPrPiso;
	}
	public Integer getCanSePiso() {
		return canSePiso;
	}
	public void setCanSePiso(Integer canSePiso) {
		this.canSePiso = canSePiso;
	}
	public Integer getN_canPrPiso() {
		return n_canPrPiso;
	}
	public void setN_canPrPiso(Integer n_canPrPiso) {
		this.n_canPrPiso = n_canPrPiso;
	}
	public Integer getN_canSePiso() {
		return n_canSePiso;
	}
	public void setN_canSePiso(Integer n_canSePiso) {
		this.n_canSePiso = n_canSePiso;
	}
	public Integer getNroOrden() {
		return nroOrden;
	}
	public void setNroOrden(Integer nroOrden) {
		this.nroOrden = nroOrden;
	}
	public ClaseServicio getTipoBus() {
		return tipoBus;
	}
	public void setTipoBus(ClaseServicio tipoBus) {
		this.tipoBus = tipoBus;
	}

	public Boolean getEstado() {
		return Estado;
	}

	public void setEstado(Boolean estado) {
		Estado = estado;
	}

	public String getNumero() {
		return Numero;
	}

	public void setNumero(String numero) {
		Numero = numero;
	}

	public AsientoVenta getAsientoVenta() {
		return asientoVenta;
	}

	public void setAsientoVenta(AsientoVenta asientoVenta) {
		this.asientoVenta = asientoVenta;
	}

	public Integer getIdtipoasiento() {
		return idtipoasiento;
	}

	public void setIdtipoasiento(Integer idtipoasiento) {
		this.idtipoasiento = idtipoasiento;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	
}

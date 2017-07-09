package com.certicom.ittsa.domain;

public class OficinaAlmacen {
	
	private Integer id;
	private Integer idOficina;
	private Integer idAlmacen;
	
	private String desAlmacen;
	
	public OficinaAlmacen(){
	}

	public String getDesAlmacen() {
		return desAlmacen;
	}

	public void setDesAlmacen(String desAlmacen) {
		this.desAlmacen = desAlmacen;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public Integer getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(Integer idAlmacen) {
		this.idAlmacen = idAlmacen;
	}


}

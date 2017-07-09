package com.certicom.ittsa.domain;

public class Ubigeo {

	public String sid_ubigeo;
	public String sdepartamento;
	public String sprovincia;
	public String sdistrito;
	public String getSid_ubigeo() {
		return sid_ubigeo;
	}
	public void setSid_ubigeo(String sid_ubigeo) {
		this.sid_ubigeo = sid_ubigeo;
	}
	public String getSdepartamento() {
		return sdepartamento;
	}
	public void setSdepartamento(String sdepartamento) {
		this.sdepartamento = sdepartamento.toUpperCase();
	}
	public String getSprovincia() {
		return sprovincia;
	}
	public void setSprovincia(String sprovincia) {
		this.sprovincia = sprovincia.toUpperCase();
	}
	public String getSdistrito() {
		return sdistrito;
	}
	public void setSdistrito(String sdistrito) {
		this.sdistrito = sdistrito.toUpperCase();
	}
	
	
	
	

}

package com.certicom.ittsa.domain;

public class ProveedorAgencia {
	
	public Integer Id;
	public Integer Idagencia;
	public Integer IdOficina;
	public Integer IdProveedor;
	
	public String desAgencia;
	public String desOficina;
	public String RazonSocial;

	public Integer getId() {
		return Id;
	}
	
	public Integer getIdagencia() {
		return Idagencia;
	}
	public Integer getIdOficina() {
		return IdOficina;
	}
	public Integer getIdProveedor() {
		return IdProveedor;
	}
	public String getDesAgencia() {
		return desAgencia;
	}
	public String getDesOficina() {
		return desOficina;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public void setIdagencia(Integer idagencia) {
		Idagencia = idagencia;
	}
	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}
	public void setIdProveedor(Integer idProveedor) {
		IdProveedor = idProveedor;
	}
	public void setDesAgencia(String desAgencia) {
		this.desAgencia = desAgencia;
	}
	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}

	public String getRazonSocial() {
		return RazonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}
	
	
	
	
	
}

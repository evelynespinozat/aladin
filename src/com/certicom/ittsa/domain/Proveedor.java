package com.certicom.ittsa.domain;

public class Proveedor {

	public Integer IdProveedor;
	public String RUC;
	public String RazonSocial;
	public String Direccion;
	public String Telefono;
	public String Email;
	public String codUbigeo;
	public boolean Estado;
	public String desUbigeo;
	public String Contacto;
	public String telContacto;
	public String tipo;
	
	public Integer getIdProveedor() {
		return IdProveedor;
	}
	public String getRUC() {
		return RUC;
	}
	public String getRazonSocial() {
		return RazonSocial;
	}
	public String getDireccion() {
		return Direccion;
	}
	public String getTelefono() {
		return Telefono;
	}
	public String getEmail() {
		return Email;
	}
	public String getCodUbigeo() {
		return codUbigeo;
	}
	public boolean isEstado() {
		return Estado;
	}
	public String getDesUbigeo() {
		return desUbigeo;
	}
	public void setIdProveedor(Integer idProveedor) {
		IdProveedor = idProveedor;
	}
	public void setRUC(String rUC) {
		RUC = rUC.toUpperCase();
	}
	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial.toUpperCase();
	}
	public void setDireccion(String direccion) {
		Direccion = direccion.toUpperCase();
	}
	public void setTelefono(String telefono) {
		Telefono = telefono.toUpperCase();
	}
	public void setEmail(String email) {
		Email = email.toUpperCase();
	}
	public void setCodUbigeo(String codUbigeo) {
		this.codUbigeo = codUbigeo;
	}
	public void setEstado(boolean estado) {
		Estado = estado;
	}
	public void setDesUbigeo(String desUbigeo) {
		this.desUbigeo = desUbigeo.toUpperCase();
	}
	public String getContacto() {
		return Contacto;
	}
	public String getTelContacto() {
		return telContacto;
	}
	public void setContacto(String contacto) {
		Contacto = contacto;
	}
	public void setTelContacto(String telContacto) {
		this.telContacto = telContacto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}

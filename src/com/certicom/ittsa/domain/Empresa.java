package com.certicom.ittsa.domain;

import java.util.Date;

public class Empresa {
	
	private String RazonSocial;
	private String Direccion;
	private String Telefono;
	private String RUC;
	private String Email;
	private boolean Estado;
	private Date FRegistro;
	//<INICIO> jpiscoya
	private String Telefono2;
	private String PaginaWeb;
	private String PersonaContacto;
	//<FIN> jpiscoya
	
	public Empresa(){;}

	public String getRazonSocial() {
		return RazonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public String getRUC() {
		return RUC;
	}

	public void setRUC(String rUC) {
		RUC = rUC;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public boolean isEstado() {
		return Estado;
	}

	public void setEstado(boolean estado) {
		Estado = estado;
	}

	public Date getFRegistro() {
		return FRegistro;
	}

	public void setFRegistro(Date fRegistro) {
		FRegistro = fRegistro;
	}

	public String getTelefono2() {
		return Telefono2;
	}

	public void setTelefono2(String telefono2) {
		Telefono2 = telefono2;
	}

	public String getPaginaWeb() {
		return PaginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		PaginaWeb = paginaWeb;
	}

	public String getPersonaContacto() {
		return PersonaContacto;
	}

	public void setPersonaContacto(String personaContacto) {
		PersonaContacto = personaContacto;
	}

	

	
	
}

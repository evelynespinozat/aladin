package com.certicom.ittsa.domain;

public class PagadorPrepagado {

	 private String tipoDoc;
	 private String nroDoc;
	 private String nombre;
	 private String direccion;
	 private String referencia;
	 private String telefono;
	 private String email;
	 
	 //delivery
	 
	 public PagadorPrepagado(){;}
	 
	 public String getNombre() {
	  return nombre;
	 }
	 public void setNombre(String nombre) {
	  this.nombre = nombre;
	 }
	 public String getDireccion() {
	  return direccion;
	 }
	 public void setDireccion(String direccion) {
	  this.direccion = direccion;
	 }
	 public String getTelefono() {
	  return telefono;
	 }
	 public void setTelefono(String telefono) {
	  this.telefono = telefono;
	 }
	 public String getEmail() {
	  return email;
	 }
	 public void setEmail(String email) {
	  this.email = email;
	 }

	 public String getTipoDoc() {
	  return tipoDoc;
	 }

	 public void setTipoDoc(String tipoDoc) {
	  this.tipoDoc = tipoDoc;
	 }

	 public String getNroDoc() {
	  return nroDoc;
	 }

	 public void setNroDoc(String nroDoc) {
	  this.nroDoc = nroDoc;
	 }

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	 
	 
	}

package com.certicom.ittsa.domain;

public class DNI {
	
	private String nro_dni;
	private Long id_documento;
	private Long cod_imagen;
	
	
	//Para reportes
	private Integer nro_impresiones;
	private Integer nro_consultas;
	private String nombre_ca;
	
	
	public DNI(){	
	}


	public String getNro_dni() {
		return nro_dni;
	}


	public Long getId_documento() {
		return id_documento;
	}


	public Long getCod_imagen() {
		return cod_imagen;
	}


	public Integer getNro_impresiones() {
		return nro_impresiones;
	}


	public Integer getNro_consultas() {
		return nro_consultas;
	}


	public String getNombre_ca() {
		return nombre_ca;
	}


	public void setNro_dni(String nro_dni) {
		this.nro_dni = nro_dni.toUpperCase();
	}


	public void setId_documento(Long id_documento) {
		this.id_documento = id_documento;
	}


	public void setCod_imagen(Long cod_imagen) {
		this.cod_imagen = cod_imagen;
	}


	public void setNro_impresiones(Integer nro_impresiones) {
		this.nro_impresiones = nro_impresiones;
	}


	public void setNro_consultas(Integer nro_consultas) {
		this.nro_consultas = nro_consultas;
	}


	public void setNombre_ca(String nombre_ca) {
		this.nombre_ca = nombre_ca.toUpperCase();
	}

	

	
}
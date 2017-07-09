package com.certicom.ittsa.domain;

public class ClaseServicio {
	private int idclase;
	private String Descripcion;
	private boolean estado;
	private String grupo;
	private String nombreCorto;
	private String atnBordo;
	private Integer asientos;
	private Integer nroPisos;
	private Integer IdCatServicio;
	private Integer nroColumnasPisoUno;
	private Integer nroColumnasPisoDos;
	private Integer idtipoasientop1;
	private String color;
	private Integer idclasecapacidad;
	private ClaseServicio claseServicioCapacidad;
	
	
	
	private Integer idtipoasientop2;
	
	private CategoriaServicio categoriaSevicio;
	
	

	public ClaseServicio() {
	}

	public int getIdclase() {
		return idclase;
	}

	public void setIdclase(int idclase) {
		this.idclase = idclase;
	}


	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getAtnBordo() {
		return atnBordo;
	}

	public void setAtnBordo(String atnBordo) {
		this.atnBordo = atnBordo;
	}

	public Integer getAsientos() {
		return asientos;
	}

	public void setAsientos(Integer asientos) {
		this.asientos = asientos;
	}

	public CategoriaServicio getCategoriaSevicio() {
		return categoriaSevicio;
	}

	public void setCategoriaSevicio(CategoriaServicio categoriaSevicio) {
		this.categoriaSevicio = categoriaSevicio;
	}

	public Integer getNroPisos() {
		return nroPisos;
	}

	public void setNroPisos(Integer nroPisos) {
		this.nroPisos = nroPisos;
	}

	public Integer getIdCatServicio() {
		return IdCatServicio;
	}

	public void setIdCatServicio(Integer idCatServicio) {
		IdCatServicio = idCatServicio;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Integer getNroColumnasPisoUno() {
		return nroColumnasPisoUno;
	}

	public void setNroColumnasPisoUno(Integer nroColumnasPisoUno) {
		this.nroColumnasPisoUno = nroColumnasPisoUno;
	}

	public Integer getNroColumnasPisoDos() {
		return nroColumnasPisoDos;
	}

	public void setNroColumnasPisoDos(Integer nroColumnasPisoDos) {
		this.nroColumnasPisoDos = nroColumnasPisoDos;
	}
	public Integer getIdtipoasientop1() {
		return idtipoasientop1;
	}

	public void setIdtipoasientop1(Integer idtipoasientop1) {
		this.idtipoasientop1 = idtipoasientop1;
	}

	public Integer getIdtipoasientop2() {
		return idtipoasientop2;
	}

	public void setIdtipoasientop2(Integer idtipoasientop2) {
		this.idtipoasientop2 = idtipoasientop2;
	}

	public Integer getIdclasecapacidad() {
		return idclasecapacidad;
	}

	public void setIdclasecapacidad(Integer idclasecapacidad) {
		this.idclasecapacidad = idclasecapacidad;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public ClaseServicio getClaseServicioCapacidad() {
		return claseServicioCapacidad;
	}

	public void setClaseServicioCapacidad(ClaseServicio claseServicioCapacidad) {
		this.claseServicioCapacidad = claseServicioCapacidad;
	}


}

package com.certicom.ittsa.domain;

public class Destino {

	private int IdDestino;
	private Integer Origen;
	private Integer Destino;
	private String desOrigen;
	private String desDestino;
	private boolean Estado;
	private int Idagencia;
	//agregados
	private Integer kmDistancia;
	

	
	public Integer getKmDistancia() {
		return kmDistancia;
	}

	public void setKmDistancia(Integer kmDistancia) {
		this.kmDistancia = kmDistancia;
	}

	public int getIdDestino() {
		return IdDestino;
	}

	public Integer getOrigen() {
		return Origen;
	}

	public Integer getDestino() {
		return Destino;
	}

	public String getDesOrigen() {
		return desOrigen;
	}

	public String getDesDestino() {
		return desDestino;
	}

	public boolean isEstado() {
		return Estado;
	}

	public int getIdagencia() {
		return Idagencia;
	}

	public void setIdDestino(int idDestino) {
		IdDestino = idDestino;
	}

	public void setOrigen(Integer origen) {
		Origen = origen;
	}

	public void setDestino(Integer destino) {
		Destino = destino;
	}

	public void setDesOrigen(String desOrigen) {
		this.desOrigen = desOrigen.toUpperCase();
	}

	public void setDesDestino(String desDestino) {
		this.desDestino = desDestino.toUpperCase();
	}

	public void setEstado(boolean estado) {
		Estado = estado;
	}

	public void setIdagencia(int idagencia) {
		Idagencia = idagencia;
	}


	
	
}

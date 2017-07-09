package com.certicom.ittsa.domain;

import java.util.Date;

public class VentaDetalle {
	
	String nomAgencia;
	String nomOficina;
	String nomPuntoVenta;
	String nomUsuario;
	Date fInicio;
	Date fFin;
	String nBoleto;
	String nComprobante;
	String estado;
	String monto;
	
	//Agregados por Evelyn
	Date fechaVenta;
	String horaviaje;
	Date fechasalida;
	String ruta;
	String asiento;
	String documentoPersona;
	String tarifa;
	String formapago;
	
	public VentaDetalle(){
				
	}
	
	
	
	public Date getFechasalida() {
		return fechasalida;
	}



	public void setFechasalida(Date fechasalida) {
		this.fechasalida = fechasalida;
	}



	public String getHoraviaje() {
		return horaviaje;
	}


	public void setHoraviaje(String horaviaje) {
		this.horaviaje = horaviaje;
	}


	public String getRuta() {
		return ruta;
	}


	public void setRuta(String ruta) {
		this.ruta = ruta;
	}



	public String getAsiento() {
		return asiento;
	}


	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	public String getDocumentoPersona() {
		return documentoPersona;
	}

	public void setDocumentoPersona(String documentoPersona) {
		this.documentoPersona = documentoPersona;
	}

	public String getTarifa() {
		return tarifa;
	}


	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}


	public String getFormapago() {
		return formapago;
	}


	public void setFormapago(String formapago) {
		this.formapago = formapago;
	}


	/**
	 * @return the nomAgencia
	 */
	public String getNomAgencia() {
		return nomAgencia;
	}


	/**
	 * @param nomAgencia the nomAgencia to set
	 */
	public void setNomAgencia(String nomAgencia) {
		this.nomAgencia = nomAgencia;
	}


	/**
	 * @return the nomOficina
	 */
	public String getNomOficina() {
		return nomOficina;
	}


	/**
	 * @param nomOficina the nomOficina to set
	 */
	public void setNomOficina(String nomOficina) {
		this.nomOficina = nomOficina;
	}


	/**
	 * @return the nomPuntoVenta
	 */
	public String getNomPuntoVenta() {
		return nomPuntoVenta;
	}


	/**
	 * @param nomPuntoVenta the nomPuntoVenta to set
	 */
	public void setNomPuntoVenta(String nomPuntoVenta) {
		this.nomPuntoVenta = nomPuntoVenta;
	}


	/**
	 * @return the nomUsuario
	 */
	public String getNomUsuario() {
		return nomUsuario;
	}


	/**
	 * @param nomUsuario the nomUsuario to set
	 */
	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}


	/**
	 * @return the fInicio
	 */
	public Date getfInicio() {
		return fInicio;
	}


	/**
	 * @param fInicio the fInicio to set
	 */
	public void setfInicio(Date fInicio) {
		this.fInicio = fInicio;
	}


	/**
	 * @return the fFin
	 */
	public Date getfFin() {
		return fFin;
	}


	/**
	 * @param fFin the fFin to set
	 */
	public void setfFin(Date fFin) {
		this.fFin = fFin;
	}


	/**
	 * @return the nBoleto
	 */
	public String getnBoleto() {
		return nBoleto;
	}


	/**
	 * @param nBoleto the nBoleto to set
	 */
	public void setnBoleto(String nBoleto) {
		this.nBoleto = nBoleto;
	}


	/**
	 * @return the nComprobante
	 */
	public String getnComprobante() {
		return nComprobante;
	}


	/**
	 * @param nComprobante the nComprobante to set
	 */
	public void setnComprobante(String nComprobante) {
		this.nComprobante = nComprobante;
	}


	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


	/**
	 * @return the monto
	 */
	public String getMonto() {
		return monto;
	}


	/**
	 * @param monto the monto to set
	 */
	public void setMonto(String monto) {
		this.monto = monto;
	}


	/**
	 * @return the fechaVenta
	 */
	public Date getFechaVenta() {
		return fechaVenta;
	}


	/**
	 * @param fechaVenta the fechaVenta to set
	 */
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	
		
	
	
}

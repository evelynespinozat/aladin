package com.certicom.ittsa.domain;

public class HistorialEncomienda {

	private Integer idHistorial;
	private String dniRemitente;
	private String apellidosRemitente;
	private String nombresRemitente;
	private String telefonoRemitente;
	private String rucRemitente;
	private String dniDestinatario1;
	private String apellidosDestinatario1;
	private String nombresDestinatario1;
	private String telefonoDestinatario1;
	private String rucDestinatario1;
	private String dniDestinatario2;
	private String apellidosDestinatario2;
	private String nombresDestinatario2;
	private String telefonoDestinatario2;
	private String rucDestinatario2;
	private String direccionEnvio;
	private String razonSocialRemitente;
	private String razonSocialDestinatario1;
	private String razonSocialDestinatario2;
	private Integer idEncomienda;
	
	//valores adiconales para envios frecuentes
	private Integer idTarifa;
	private Double precioReparto;
	private Double precioExtraRapido;

	public HistorialEncomienda() {

	}
	
	public Integer getIdTarifa() {
		return idTarifa;
	}

	public void setIdTarifa(Integer idTarifa) {
		this.idTarifa = idTarifa;
	}

	public Double getPrecioReparto() {
		return precioReparto;
	}

	public void setPrecioReparto(Double precioReparto) {
		this.precioReparto = precioReparto;
	}

	public Double getPrecioExtraRapido() {
		return precioExtraRapido;
	}

	public void setPrecioExtraRapido(Double precioExtraRapido) {
		this.precioExtraRapido = precioExtraRapido;
	}

	public Integer getIdEncomienda() {
		return idEncomienda;
	}

	public void setIdEncomienda(Integer idEncomienda) {
		this.idEncomienda = idEncomienda;
	}

	public Integer getIdHistorial() {
		return idHistorial;
	}

	public void setIdHistorial(Integer idHistorial) {
		this.idHistorial = idHistorial;
	}

	public String getDniRemitente() {
		return dniRemitente;
	}

	public void setDniRemitente(String dniRemitente) {
		this.dniRemitente = dniRemitente;
	}

	public String getTelefonoRemitente() {
		return telefonoRemitente;
	}

	public void setTelefonoRemitente(String telefonoRemitente) {
		this.telefonoRemitente = telefonoRemitente;
	}

	public String getRucRemitente() {
		return rucRemitente;
	}

	public void setRucRemitente(String rucRemitente) {
		this.rucRemitente = rucRemitente;
	}

	public String getDniDestinatario1() {
		return dniDestinatario1;
	}

	public void setDniDestinatario1(String dniDestinatario1) {
		this.dniDestinatario1 = dniDestinatario1;
	}

	public String getTelefonoDestinatario1() {
		return telefonoDestinatario1;
	}

	public void setTelefonoDestinatario1(String telefonoDestinatario1) {
		this.telefonoDestinatario1 = telefonoDestinatario1;
	}

	public String getRucDestinatario1() {
		return rucDestinatario1;
	}

	public void setRucDestinatario1(String rucDestinatario1) {
		this.rucDestinatario1 = rucDestinatario1;
	}

	public String getDniDestinatario2() {
		return dniDestinatario2;
	}

	public void setDniDestinatario2(String dniDestinatario2) {
		this.dniDestinatario2 = dniDestinatario2;
	}

	public String getTelefonoDestinatario2() {
		return telefonoDestinatario2;
	}

	public void setTelefonoDestinatario2(String telefonoDestinatario2) {
		this.telefonoDestinatario2 = telefonoDestinatario2;
	}

	public String getRucDestinatario2() {
		return rucDestinatario2;
	}

	public void setRucDestinatario2(String rucDestinatario2) {
		this.rucDestinatario2 = rucDestinatario2;
	}

	public String getDireccionEnvio() {
		return direccionEnvio;
	}

	public void setDireccionEnvio(String direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}

	public String getRazonSocialRemitente() {
		return razonSocialRemitente;
	}

	public void setRazonSocialRemitente(String razonSocialRemitente) {
		this.razonSocialRemitente = razonSocialRemitente;
	}

	public String getRazonSocialDestinatario1() {
		return razonSocialDestinatario1;
	}

	public void setRazonSocialDestinatario1(String razonSocialDestinatario1) {
		this.razonSocialDestinatario1 = razonSocialDestinatario1;
	}

	public String getRazonSocialDestinatario2() {
		return razonSocialDestinatario2;
	}

	public void setRazonSocialDestinatario2(String razonSocialDestinatario2) {
		this.razonSocialDestinatario2 = razonSocialDestinatario2;
	}

	public String getApellidosRemitente() {
		return apellidosRemitente;
	}

	public void setApellidosRemitente(String apellidosRemitente) {
		this.apellidosRemitente = apellidosRemitente;
	}

	public String getNombresRemitente() {
		return nombresRemitente;
	}

	public void setNombresRemitente(String nombresRemitente) {
		this.nombresRemitente = nombresRemitente;
	}

	public String getApellidosDestinatario1() {
		return apellidosDestinatario1;
	}

	public void setApellidosDestinatario1(String apellidosDestinatario1) {
		this.apellidosDestinatario1 = apellidosDestinatario1;
	}

	public String getNombresDestinatario1() {
		return nombresDestinatario1;
	}

	public void setNombresDestinatario1(String nombresDestinatario1) {
		this.nombresDestinatario1 = nombresDestinatario1;
	}

	public String getApellidosDestinatario2() {
		return apellidosDestinatario2;
	}

	public void setApellidosDestinatario2(String apellidosDestinatario2) {
		this.apellidosDestinatario2 = apellidosDestinatario2;
	}

	public String getNombresDestinatario2() {
		return nombresDestinatario2;
	}

	public void setNombresDestinatario2(String nombresDestinatario2) {
		this.nombresDestinatario2 = nombresDestinatario2;
	}

}

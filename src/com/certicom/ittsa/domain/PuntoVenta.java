package com.certicom.ittsa.domain;

public class PuntoVenta {
	
	private Integer idPuntoVenta;
	private Integer IdOficina;
	private String namePuntoVenta;
	private String numeroIP;
	private String NombreImpresora;
	private String serieBoleto;
	private String serieBoleta;
	private Integer ultimoBoleto;
	private Integer ultimaBoleta;
	private String tipo;
	private boolean estado;
	private String serieFactura;
	private Integer ultimaFactura;
	private String serieGuiaRemision;
	private Integer ultimaGuia;
	private String descPtventa;
	private String seriereserva;
	private Integer ultimareserva;
	private String serieNotaCobranza;
	private Integer ultimaNotaCobranza;
	private String seriePrepagado;
	private Integer ultimoPrepagado;

	
	//atributos para la vista 
	private String desOficina;
	private Integer IdAgencia;
	private String desAgencia;
	private String desTipo;
	private String login;
	
	
	private Agencia agencia;
	
	public PuntoVenta(){
		
	}

	public String getSerieNotaCobranza() {
		return serieNotaCobranza;
	}

	public void setSerieNotaCobranza(String serieNotaCobranza) {
		this.serieNotaCobranza = serieNotaCobranza;
	}


	public Integer getUltimaNotaCobranza() {
		return ultimaNotaCobranza;
	}


	public void setUltimaNotaCobranza(Integer ultimaNotaCobranza) {
		this.ultimaNotaCobranza = ultimaNotaCobranza;
	}


	public Integer getIdPuntoVenta() {
		return idPuntoVenta;
	}


	public String getNamePuntoVenta() {
		return namePuntoVenta;
	}

	public String getNumeroIP() {
		return numeroIP;
	}

	public String getSerieBoleto() {
		return serieBoleto;
	}

	public String getSerieBoleta() {
		return serieBoleta;
	}

	public Integer getUltimoBoleto() {
		return ultimoBoleto;
	}

	public Integer getUltimaBoleta() {
		return ultimaBoleta;
	}

	public String getTipo() {
		return tipo;
	}

	public boolean isEstado() {
		return estado;
	}

	public String getSerieFactura() {
		return serieFactura;
	}

	public Integer getUltimaFactura() {
		return ultimaFactura;
	}

	public String getSerieGuiaRemision() {
		return serieGuiaRemision;
	}

	public Integer getUltimaGuia() {
		return ultimaGuia;
	}

	public String getDescPtventa() {
		return descPtventa;
	}

	public String getDesOficina() {
		return desOficina;
	}


	public String getDesAgencia() {
		return desAgencia;
	}

	public String getDesTipo() {
		return desTipo;
	}

	public void setIdPuntoVenta(Integer idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}


	public void setNamePuntoVenta(String namePuntoVenta) {
		this.namePuntoVenta = namePuntoVenta.toUpperCase();
	}

	public void setNumeroIP(String numeroIP) {
		this.numeroIP = numeroIP;
	}

	public void setSerieBoleto(String serieBoleto) {
		this.serieBoleto = serieBoleto.toUpperCase();
	}

	public void setSerieBoleta(String serieBoleta) {
		this.serieBoleta = serieBoleta.toUpperCase();
	}

	public void setUltimoBoleto(Integer ultimoBoleto) {
		this.ultimoBoleto = ultimoBoleto;
	}

	public void setUltimaBoleta(Integer ultimaBoleta) {
		this.ultimaBoleta = ultimaBoleta;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void setSerieFactura(String serieFactura) {
		this.serieFactura = serieFactura.toUpperCase();
	}

	public void setUltimaFactura(Integer ultimaFactura) {
		this.ultimaFactura = ultimaFactura;
	}

	public void setSerieGuiaRemision(String serieGuiaRemision) {
		this.serieGuiaRemision = serieGuiaRemision.toUpperCase();
	}

	public void setUltimaGuia(Integer ultimaGuia) {
		this.ultimaGuia = ultimaGuia;
	}

	public void setDescPtventa(String descPtventa) {
		this.descPtventa = descPtventa.toUpperCase();
	}

	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina.toUpperCase();
	}


	public void setDesAgencia(String desAgencia) {
		this.desAgencia = desAgencia.toUpperCase();
	}

	public void setDesTipo(String desTipo) {
		this.desTipo = desTipo.toUpperCase();
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Integer getIdOficina() {
		return IdOficina;
	}

	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}

	public Integer getIdAgencia() {
		return IdAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		IdAgencia = idAgencia;
	}

	public String getSeriereserva() {
		return seriereserva;
	}

	public void setSeriereserva(String seriereserva) {
		this.seriereserva = seriereserva;
	}

	public Integer getUltimareserva() {
		return ultimareserva;
	}

	public void setUltimareserva(Integer ultimareserva) {
		this.ultimareserva = ultimareserva;
	}

	public String getSeriePrepagado() {
		return seriePrepagado;
	}

	public void setSeriePrepagado(String seriePrepagado) {
		this.seriePrepagado = seriePrepagado;
	}

	public Integer getUltimoPrepagado() {
		return ultimoPrepagado;
	}

	public void setUltimoPrepagado(Integer ultimoPrepagado) {
		this.ultimoPrepagado = ultimoPrepagado;
	}

	public String getNombreImpresora() {
		return NombreImpresora;
	}

	public void setNombreImpresora(String nombreImpresora) {
		NombreImpresora = nombreImpresora;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}

package com.certicom.ittsa.domain;


public class Boleto {
	
	private String fechaEmision;
	private String usuario;
	private String ruc;
	private String pasajero;
	private String empresa;
	private String dni;
	private String origen;
	private String destino;
	private String totalLetras;
	private String fechaViaje;
	private String horaViaje;
	private String valorTotal;
	private String numeroAsiento;
	private String codBoleto;
	private String costoPasaje;
	
	//prepgados
	private String montoPrepagado;
	private String pagador;
	private String tipoDoc;
	private String numeroDoc;
	private String codigoBoleta;



	public Boleto(){;}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getPasajero() {
		return pasajero;
	}

	public void setPasajero(String pasajero) {
		this.pasajero = pasajero;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getTotalLetras() {
		return totalLetras;
	}

	public void setTotalLetras(String totalLetras) {
		this.totalLetras = totalLetras;
	}

	public String getFechaViaje() {
		return fechaViaje;
	}

	public void setFechaViaje(String fechaViaje) {
		this.fechaViaje = fechaViaje;
	}

	public String getHoraViaje() {
		return horaViaje;
	}

	public void setHoraViaje(String horaViaje) {
		this.horaViaje = horaViaje;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getNumeroAsiento() {
		return numeroAsiento;
	}

	public void setNumeroAsiento(String numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getMontoPrepagado() {
		return montoPrepagado;
	}

	public void setMontoPrepagado(String montoPrepagado) {
		this.montoPrepagado = montoPrepagado;
	}

	public String getPagador() {
		return pagador;
	}

	public void setPagador(String pagador) {
		this.pagador = pagador;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getNumeroDoc() {
		return numeroDoc;
	}

	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}

	public String getCodigoBoleta() {
		return codigoBoleta;
	}

	public void setCodigoBoleta(String codigoBoleta) {
		this.codigoBoleta = codigoBoleta;
	}

	public String getCodBoleto() {
		return codBoleto;
	}

	public void setCodBoleto(String codBoleto) {
		this.codBoleto = codBoleto;
	}

	public String getCostoPasaje() {
		return costoPasaje;
	}

	public void setCostoPasaje(String costoPasaje) {
		this.costoPasaje = costoPasaje;
	}
}

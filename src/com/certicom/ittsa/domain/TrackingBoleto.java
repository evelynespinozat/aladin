package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;


public class TrackingBoleto implements Comparator{

	private Integer IdTrackingBoleto;
	private String serieBoleto;
	private String numeroBoleto;
	private Date fregistro;
	private Integer idusuario;
	private Integer idasiento;
	private Integer idasientoventa;
	private String estado;
	private Integer idprogramacion;
	private Integer idservicio;
	private String nrodocumento;
	private Usuario usuario;
	private Asiento asiento;
	private AsientoVenta asientoVenta;
	private Programacion programacion;
	private Servicio servicio;
	private Persona persona;
	private Agencia agenciaOrigen;
	private Agencia agenciaDestino;
	private String nrodocempresa;
	private Integer diastranscurridos;
	private boolean flgdias;
	private PuntoVenta puntoVenta;
	private Integer intsecuencia;
	private Boolean prepagado;
	private Date fechaCaducidad;
	private BigDecimal monto;
	private BigDecimal montoReal;
	private String tipoInh;
	private String motivoInh;
	private String serieRelacionado;
	private String numeroRelacionado;
	private String dniNuevoPasajero;
	private Integer puntoVentaId;
	private Boolean verBoleto;
	
	
	//variabels alternas para visualizar
	private Integer idAgencia;
	private Integer idOficina;
	private String desServicio;
	private String desOrigen;
	private String desDestino;
	private Integer desNroAsiento;
	private String desUsuario;

	public TrackingBoleto(){}
	
	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public Integer getIdTrackingBoleto() {
		return IdTrackingBoleto;
	}
	public void setIdTrackingBoleto(Integer idTrackingBoleto) {
		IdTrackingBoleto = idTrackingBoleto;
	}
	public String getSerieBoleto() {
		return serieBoleto;
	}
	public void setSerieBoleto(String serieBoleto) {
		this.serieBoleto = serieBoleto;
	}
	public String getNumeroBoleto() {
		return numeroBoleto;
	}
	public void setNumeroBoleto(String numeroBoleto) {
		this.numeroBoleto = numeroBoleto;
	}
	public Date getFregistro() {
		return fregistro;
	}
	public void setFregistro(Date fregistro) {
		this.fregistro = fregistro;
	}
	public Integer getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}
	public Integer getIdasiento() {
		return idasiento;
	}
	public void setIdasiento(Integer idasiento) {
		this.idasiento = idasiento;
	}
	public Integer getIdasientoventa() {
		return idasientoventa;
	}
	public void setIdasientoventa(Integer idasientoventa) {
		this.idasientoventa = idasientoventa;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getIdprogramacion() {
		return idprogramacion;
	}
	public void setIdprogramacion(Integer idprogramacion) {
		this.idprogramacion = idprogramacion;
	}
	public Integer getIdservicio() {
		return idservicio;
	}
	public void setIdservicio(Integer idservicio) {
		this.idservicio = idservicio;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Asiento getAsiento() {
		return asiento;
	}
	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}
	public AsientoVenta getAsientoVenta() {
		return asientoVenta;
	}
	public void setAsientoVenta(AsientoVenta asientoVenta) {
		this.asientoVenta = asientoVenta;
	}
	public Programacion getProgramacion() {
		return programacion;
	}
	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Agencia getAgenciaOrigen() {
		return agenciaOrigen;
	}

	public void setAgenciaOrigen(Agencia agenciaOrigen) {
		this.agenciaOrigen = agenciaOrigen;
	}

	public Agencia getAgenciaDestino() {
		return agenciaDestino;
	}

	public void setAgenciaDestino(Agencia agenciaDestino) {
		this.agenciaDestino = agenciaDestino;
	}

	public String getNrodocempresa() {
		return nrodocempresa;
	}

	public void setNrodocempresa(String nrodocempresa) {
		this.nrodocempresa = nrodocempresa;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Integer getDiastranscurridos() {
		return diastranscurridos;
	}

	public void setDiastranscurridos(Integer diastranscurridos) {
		this.diastranscurridos = diastranscurridos;
	}

	public boolean isFlgdias() {
		return flgdias;
	}

	public void setFlgdias(boolean flgdias) {
		this.flgdias = flgdias;
	}

	public String getDesServicio() {
		return desServicio;
	}

	public void setDesServicio(String desServicio) {
		this.desServicio = desServicio;
	}

	public String getDesOrigen() {
		return desOrigen;
	}

	public void setDesOrigen(String desOrigen) {
		this.desOrigen = desOrigen;
	}

	public String getDesDestino() {
		return desDestino;
	}

	public void setDesDestino(String desDestino) {
		this.desDestino = desDestino;
	}

	public Integer getDesNroAsiento() {
		return desNroAsiento;
	}

	public void setDesNroAsiento(Integer desNroAsiento) {
		this.desNroAsiento = desNroAsiento;
	}

	public String getDesUsuario() {
		return desUsuario;
	}

	public void setDesUsuario(String desUsuario) {
		this.desUsuario = desUsuario;
	}
	
	public Integer getIntsecuencia() {
		return intsecuencia;
	}

	public void setIntsecuencia(Integer intsecuencia) {
		this.intsecuencia = intsecuencia;
	}

	public int compare(Object o1, Object o2) { 
        TrackingBoleto persona1 = (TrackingBoleto)o1; 
        TrackingBoleto persona2 = (TrackingBoleto)o2; 
        return persona1.getProgramacion().getfSalida(). 
                compareTo(persona2.getProgramacion().getfSalida()); 

    }

	public Boolean getPrepagado() {
		return prepagado;
	}

	public void setPrepagado(Boolean prepagado) {
		this.prepagado = prepagado;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getMontoReal() {
		return montoReal;
	}

	public void setMontoReal(BigDecimal montoReal) {
		this.montoReal = montoReal;
	}

	public String getTipoInh() {
		return tipoInh;
	}

	public void setTipoInh(String tipoInh) {
		this.tipoInh = tipoInh;
	}

	public String getMotivoInh() {
		return motivoInh;
	}

	public void setMotivoInh(String motivoInh) {
		this.motivoInh = motivoInh;
	}

	public String getSerieRelacionado() {
		return serieRelacionado;
	}

	public void setSerieRelacionado(String serieRelacionado) {
		this.serieRelacionado = serieRelacionado;
	}

	public String getNumeroRelacionado() {
		return numeroRelacionado;
	}

	public void setNumeroRelacionado(String numeroRelacionado) {
		this.numeroRelacionado = numeroRelacionado;
	}

	public String getDniNuevoPasajero() {
		return dniNuevoPasajero;
	}

	public void setDniNuevoPasajero(String dniNuevoPasajero) {
		this.dniNuevoPasajero = dniNuevoPasajero;
	}

	public Integer getPuntoVentaId() {
		return puntoVentaId;
	}

	public void setPuntoVentaId(Integer puntoVentaId) {
		this.puntoVentaId = puntoVentaId;
	}

	public Boolean getVerBoleto() {
		return verBoleto;
	}

	public void setVerBoleto(Boolean verBoleto) {
		this.verBoleto = verBoleto;
	} 
	
}

package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Servicio {

	private Integer IdServicio;
	private Integer IdClase;
	private String Descripcion;
	private BigDecimal Precio1;
	private BigDecimal Precio2;
	private String desOrigen;
	private String desDestino;
	private Integer Origen;
	private Integer Destino;
	private String HSalida;
	private Double HorasViaje;
	private Date HLlegada;
	private Date Hora24;
	private Boolean Estado;
	private String TipoFrecuencia; // 1 = FRECUENTE ; 2 = DEMANDA 
	private String idRutaDestino;
	private Date FRegistro;
	private Integer FrecuenteSabado;
	private String DescripcionClase;
	private String HorasViajeString;
	private String RutaCompartida;
	private String descTipoFrecuencia;
	private Integer GradoServ; // 1 : padre - 2 : hijo

	// campos secundarios
	private String nroHoras24;
	private Integer cantEnlaces;
	private Date fSalidaProgramacion;
	private BigDecimal Tramo2Precio1;
	private BigDecimal Tramo2Precio2;
	private Integer IntCorre;
	private Boolean seleccionado;
	private Integer idServicioPadre;
	private ClaseServicio claseServicio;
	
	private String rutaCompEnCaliente;//servicio creado encaliente o a demanda 

        private Promocion promocion;

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }
        
	public Servicio() {
	}

	public Date getfSalidaProgramacion() {
		return fSalidaProgramacion;
	}

	
	public Integer getIntCorre() {
		return IntCorre;
	}

	public void setIntCorre(Integer intCorre) {
		IntCorre = intCorre;
	}


	public void setfSalidaProgramacion(Date fSalidaProgramacion) {
		this.fSalidaProgramacion = fSalidaProgramacion;
	}



	public BigDecimal getTramo2Precio1() {
		return Tramo2Precio1;
	}

	public void setTramo2Precio1(BigDecimal tramo2Precio1) {
		Tramo2Precio1 = tramo2Precio1;
	}

	public BigDecimal getTramo2Precio2() {
		return Tramo2Precio2;
	}

	public void setTramo2Precio2(BigDecimal tramo2Precio2) {
		Tramo2Precio2 = tramo2Precio2;
	}


	public Integer getCantEnlaces() {
		return cantEnlaces;
	}

	public void setCantEnlaces(Integer cantEnlaces) {
		this.cantEnlaces = cantEnlaces;
	}

	public Integer getIdServicio() {
		return IdServicio;
	}

	public Integer getIdClase() {
		return IdClase;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public BigDecimal getPrecio1() {
		return Precio1;
	}

	public BigDecimal getPrecio2() {
		return Precio2;
	}

	public String getDesOrigen() {
		return desOrigen;
	}

	public String getDesDestino() {
		return desDestino;
	}

	public Integer getOrigen() {
		return Origen;
	}

	public Integer getDestino() {
		return Destino;
	}

	public String getHSalida() {
		return HSalida;
	}

	public Double getHorasViaje() {
		return HorasViaje;
	}

	public Date getHLlegada() {
		return HLlegada;
	}

	public Date getHora24() {
		return Hora24;
	}

	public Boolean getEstado() {
		return Estado;
	}

	public String getTipoFrecuencia() {
		return TipoFrecuencia;
	}

	public String getIdRutaDestino() {
		return idRutaDestino;
	}

	public Date getFRegistro() {
		return FRegistro;
	}

	public Integer getFrecuenteSabado() {
		return FrecuenteSabado;
	}

	public String getDescripcionClase() {
		return DescripcionClase;
	}

	public String getNroHoras24() {
		return nroHoras24;
	}

	public ClaseServicio getClaseServicio() {
		return claseServicio;
	}

	public void setIdServicio(Integer idServicio) {
		IdServicio = idServicio;
	}

	public void setIdClase(Integer idClase) {
		IdClase = idClase;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion.toUpperCase();
	}

	public void setPrecio1(BigDecimal precio1) {
		Precio1 = precio1;
	}

	public void setPrecio2(BigDecimal precio2) {
		Precio2 = precio2;
	}

	public void setDesOrigen(String desOrigen) {
		this.desOrigen = desOrigen.toUpperCase();
	}

	public void setDesDestino(String desDestino) {
		this.desDestino = desDestino.toUpperCase();
	}

	public void setOrigen(Integer origen) {
		Origen = origen;
	}

	public void setDestino(Integer destino) {
		Destino = destino;
	}

	public void setHSalida(String hSalida) {
		HSalida = hSalida;
	}

	public void setHorasViaje(Double horasViaje) {
		HorasViaje = horasViaje;
	}

	public void setHLlegada(Date hLlegada) {
		HLlegada = hLlegada;
	}

	public void setHora24(Date hora24) {
		Hora24 = hora24;
	}

	public void setEstado(Boolean estado) {
		Estado = estado;
	}

	public void setTipoFrecuencia(String tipoFrecuencia) {
		TipoFrecuencia = tipoFrecuencia.toUpperCase();
	}

	public void setIdRutaDestino(String idRutaDestino) {
		this.idRutaDestino = idRutaDestino;
	}

	public void setFRegistro(Date fRegistro) {
		FRegistro = fRegistro;
	}

	public void setFrecuenteSabado(Integer frecuenteSabado) {
		FrecuenteSabado = frecuenteSabado;
	}

	public void setDescripcionClase(String descripcionClase) {
		DescripcionClase = descripcionClase.toUpperCase();
	}

	public void setNroHoras24(String nroHoras24) {
		this.nroHoras24 = nroHoras24.toUpperCase();
	}

	public void setClaseServicio(ClaseServicio claseServicio) {
		this.claseServicio = claseServicio;
	}

	public String getHorasViajeString() {
		return HorasViajeString;
	}

	public void setHorasViajeString(String horasViajeString) {
		HorasViajeString = horasViajeString;
	}

	public String getDescTipoFrecuencia() {

		if (getTipoFrecuencia().equals("1")) {
			descTipoFrecuencia = "FRECUENTE";
		} else
			descTipoFrecuencia = "DEMANDA";
		return descTipoFrecuencia;
	}

	public void setDescTipoFrecuencia(String descTipoFrecuencia) {
		this.descTipoFrecuencia = descTipoFrecuencia;
	}

	public Integer getIdServicioPadre() {
		return idServicioPadre;
	}

	public void setIdServicioPadre(Integer idServicioPadre) {
		this.idServicioPadre = idServicioPadre;
	}

	public Boolean getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public String getRutaCompartida() {
		return RutaCompartida;
	}

	public void setRutaCompartida(String rutaCompartida) {
		RutaCompartida = rutaCompartida;
	}

	public Integer getGradoServ() {
		return GradoServ;
	}

	public void setGradoServ(Integer gradoServ) {
		GradoServ = gradoServ;
	}

	public String getRutaCompEnCaliente() {
		return rutaCompEnCaliente;
	}

	public void setRutaCompEnCaliente(String rutaCompEnCaliente) {
		this.rutaCompEnCaliente = rutaCompEnCaliente;
	}

}

package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Programacion {

	private Integer idProgramacion;
	private Integer idServicio;
	private Integer idBus;
	private Date fLiquidacion;
	private Date fSalida;
	private BigDecimal precioPiso1;
	private BigDecimal precioPiso2;
	private String hSalida;
	private Date fcreacion;
	private Date fconfirmacion;
	private Date fmodificacion;
	private Date hora24;
	private String estado;
	private String estEmbarque;
	private String estDesembarque;
	private Date fSalidaReal;
	private Date fLlegadaReal;
	private Integer origen;
	private Integer destino;
	private Integer estManifiesto;
	private Integer usuarioRegistro;
	private Date fechaRegistro;

	// REPORTES
	private String fSalidaString;
	private String nombOrigen;
	private String nombDestino;
	private String nombPiloto;
	private String nombCopiloto;
	private Integer cantidadSalidasBus;
	private String descCategoriaServ;

	// CAMPOS ALTERNOS
	private String descripEstado;
	private String descEstadoFD;
	private Integer idCatServicio;
	private String descServicio;
	private Integer idClase;
	private Flota flota;
	private Piloto piloto;
	private Piloto copiloto;
	private String numeroBus;
	private String nombUsuarioRegistro;
	private Date fecInicio;
	private Date fecFin;
	private String descEstado;
	private String icono;
	private String etiqueta;
	private Servicio servicio;
	private String desAgencia;
	private String desOficina;
	private boolean asignarBus;
	private boolean asignarPiloto;
	private boolean asignarCopiloto;
	private boolean guardarProgFlota;
	private Boolean seleccionado = Boolean.FALSE;
	private Integer IdAgencia;
	private Integer IdOficina;
	private Integer flagAtnBordo;
	private Integer IdTripulacion;
	private Integer IdPiloto;
	private Integer IdCopiloto;
	private Integer IdTerramoza;
	private Integer IdTerramoza2;
	private String nomPiloto;
	private String nomCopiloto;
	private String nomTerramoza;
	private String nomTerramoza2;
	private String licPiloto;
	private String licCopiloto;
	private ClaseServicio clase;
	private Integer disponibles;
	private Integer disponibleP1;
	private Integer disponibleP2;
	private Integer vendidos;
	private Integer reservados;
	private Integer separados;
	private TipoAsiento tipoAsientoP1;
	private TipoAsiento tipoAsientop2;
	private Integer frecuenteSabado;
	
	private Integer idConCombus;
	private Integer kmRecorridos;
	private double precioComb;
	private double costoTotal;
	private double costoKm;
	
	// aumentar recorrido
	private Integer recorridoBus;
	private Integer nBus;
	private String placaBus;
	private Integer kmAumento;

	// 17-02-2014 jtorres datos tripulacion
	private String desEstDesem;
	private String desOrigen;
	private String desDestino;

	/* Para Liquidacion */
	private boolean bandPorCerrar = Boolean.FALSE;
	private boolean bandCerrado = Boolean.FALSE;
	private List<Detalle_Liquidacion> listaDet;
	private String ubigeoPiloto;
	private String ubigeoCopiloto;
	private String ubigeoTerramoza1;
	private String ubigeoTerramoza2;

	// juan
	private String flagLlegada;
	private Integer cantAsiVen;
	private Boolean activar;
	private Integer estKilometraje;
	private Integer IdSalida;
	private Integer numAsientos;
	private double costoGlobal;
	private double costoUnitario;
	private String varTripulante;
	private Integer desKmInicial;
	private Integer desKmFinal;
	private Integer desKmRecorridos;
	private Integer desGradoServicio;

	// daniel 12/03-2014
	private Date hLlegada;
	private Double horasViaje;
	private String hLlegadaAprox;
	private String desRutaCompartida;
	private String desTipoFrecuencia;
	private Integer aumentarCapacidad;
	private String ruta;
	private Integer asienVendidos;
	private Integer asienReserva;
	private Date fSubm;
	private Integer idSubmanifiesto;
	private boolean selected = false;
	private Integer capacidadVerdadera;
	private Integer IntCorreEnlace;
	private Integer gradoServ;
	private double consumo;
	private Boolean ampliado;
	
	//-datos para hoja ruta
	private String idRutaDestino;
	private Integer terminalSalida;
	private Integer terminalLLegada;
	
	private List<Programacion> listaSalida;
	private List<Programacion> listaRetorno;
	private BigDecimal asignacionViatico;
	private BigDecimal totalGastos;
	
	
	public Programacion() {
	}
	
	public Integer getTerminalSalida() {
		return terminalSalida;
	}

	public void setTerminalSalida(Integer terminalSalida) {
		this.terminalSalida = terminalSalida;
	}

	public Integer getTerminalLLegada() {
		return terminalLLegada;
	}

	public void setTerminalLLegada(Integer terminalLLegada) {
		this.terminalLLegada = terminalLLegada;
	}

	public String getIdRutaDestino() {
		return idRutaDestino;
	}

	public void setIdRutaDestino(String idRutaDestino) {
		this.idRutaDestino = idRutaDestino;
	}

	public Integer getFrecuenteSabado() {
		return frecuenteSabado;
	}

	public void setFrecuenteSabado(Integer frecuenteSabado) {
		this.frecuenteSabado = frecuenteSabado;
	}

	public Integer getGradoServ() {
		return gradoServ;
	}

	public void setGradoServ(Integer gradoServ) {
		this.gradoServ = gradoServ;
	}

	public Integer getAsienVendidos() {
		return asienVendidos;
	}

	public void setAsienVendidos(Integer asienVendidos) {
		this.asienVendidos = asienVendidos;
	}

	public Integer getAsienReserva() {
		return asienReserva;
	}

	public void setAsienReserva(Integer asienReserva) {
		this.asienReserva = asienReserva;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	
	public String getDesTipoFrecuencia() {
		return desTipoFrecuencia;
	}

	public void setDesTipoFrecuencia(String desTipoFrecuencia) {
		this.desTipoFrecuencia = desTipoFrecuencia;
	}
	
	public Integer getAumentarCapacidad() {
		return aumentarCapacidad;
	}

	public void setAumentarCapacidad(Integer aumentarCapacidad) {
		this.aumentarCapacidad = aumentarCapacidad;
	}

	public String getLicPiloto() {
		return licPiloto;
	}

	public void setLicPiloto(String licPiloto) {
		this.licPiloto = licPiloto;
	}

	public String getLicCopiloto() {
		return licCopiloto;
	}

	public void setLicCopiloto(String licCopiloto) {
		this.licCopiloto = licCopiloto;
	}

	public String getPlacaBus() {
		return placaBus;
	}

	public void setPlacaBus(String placaBus) {
		this.placaBus = placaBus;
	}

	public Integer getDesGradoServicio() {
		return desGradoServicio;
	}

	public void setDesGradoServicio(Integer desGradoServicio) {
		this.desGradoServicio = desGradoServicio;
	}

	public String getDesRutaCompartida() {
		return desRutaCompartida;
	}

	public void setDesRutaCompartida(String desRutaCompartida) {
		this.desRutaCompartida = desRutaCompartida;
	}

	public String gethLlegadaAprox() {
		return hLlegadaAprox;
	}

	public void sethLlegadaAprox(String hLlegadaAprox) {
		this.hLlegadaAprox = hLlegadaAprox;
	}

	public Date gethLlegada() {
		return hLlegada;
	}

	public void sethLlegada(Date hLlegada) {
		this.hLlegada = hLlegada;
	}

	public Double getHorasViaje() {
		return horasViaje;
	}

	public void setHorasViaje(Double horasViaje) {
		this.horasViaje = horasViaje;
	}

	public Integer getDesKmInicial() {
		return desKmInicial;
	}

	public void setDesKmInicial(Integer desKmInicial) {
		this.desKmInicial = desKmInicial;
	}

	public Integer getDesKmFinal() {
		return desKmFinal;
	}

	public void setDesKmFinal(Integer desKmFinal) {
		this.desKmFinal = desKmFinal;
	}

	public Integer getDesKmRecorridos() {
		return desKmRecorridos;
	}

	public void setDesKmRecorridos(Integer desKmRecorridos) {
		this.desKmRecorridos = desKmRecorridos;
	}

	public String getVarTripulante() {
		return varTripulante;
	}

	public void setVarTripulante(String varTripulante) {
		this.varTripulante = varTripulante;
	}

	public Integer getIdSalida() {
		return IdSalida;
	}

	public void setIdSalida(Integer idSalida) {
		IdSalida = idSalida;
	}

	public Integer getNumAsientos() {
		return numAsientos;
	}

	public void setNumAsientos(Integer numAsientos) {
		this.numAsientos = numAsientos;
	}

	public double getCostoGlobal() {
		return costoGlobal;
	}

	public void setCostoGlobal(double costoGlobal) {
		this.costoGlobal = costoGlobal;
	}

	public double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public Integer getEstKilometraje() {
		return estKilometraje;
	}

	public void setEstKilometraje(Integer estKilometraje) {
		this.estKilometraje = estKilometraje;
	}

	public Boolean getActivar() {
		return activar;
	}

	public void setActivar(Boolean activar) {
		this.activar = activar;
	}

	public String getUbigeoPiloto() {
		return ubigeoPiloto;
	}

	public void setUbigeoPiloto(String ubigeoPiloto) {
		this.ubigeoPiloto = ubigeoPiloto;
	}

	public String getUbigeoCopiloto() {
		return ubigeoCopiloto;
	}

	public void setUbigeoCopiloto(String ubigeoCopiloto) {
		this.ubigeoCopiloto = ubigeoCopiloto;
	}

	public String getUbigeoTerramoza1() {
		return ubigeoTerramoza1;
	}

	public void setUbigeoTerramoza1(String ubigeoTerramoza1) {
		this.ubigeoTerramoza1 = ubigeoTerramoza1;
	}

	public String getUbigeoTerramoza2() {
		return ubigeoTerramoza2;
	}

	public void setUbigeoTerramoza2(String ubigeoTerramoza2) {
		this.ubigeoTerramoza2 = ubigeoTerramoza2;
	}

	public String getfSalidaString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		fSalidaString = formato.format(getfSalida());
		return fSalidaString;
	}

	public String getFlagLlegada() {
		return flagLlegada;
	}

	public void setFlagLlegada(String flagLlegada) {
		this.flagLlegada = flagLlegada;
	}

	public Integer getCantAsiVen() {
		return cantAsiVen;
	}

	public void setCantAsiVen(Integer cantAsiVen) {
		this.cantAsiVen = cantAsiVen;
	}

	public Boolean getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public String getDescCategoriaServ() {
		return descCategoriaServ;
	}

	public void setDescCategoriaServ(String descCategoriaServ) {
		this.descCategoriaServ = descCategoriaServ;
	}

	public List<Detalle_Liquidacion> getListaDet() {
		return listaDet;
	}

	public void setListaDet(List<Detalle_Liquidacion> listaDet) {
		this.listaDet = listaDet;
	}

	public Integer getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(Integer disponibles) {
		this.disponibles = disponibles;
	}

	public ClaseServicio getClase() {
		return clase;
	}

	public void setClase(ClaseServicio clase) {
		this.clase = clase;
	}

	public boolean isBandPorCerrar() {
		return bandPorCerrar;
	}

	public void setBandPorCerrar(boolean bandPorCerrar) {
		this.bandPorCerrar = bandPorCerrar;
	}

	public boolean isBandCerrado() {
		return bandCerrado;
	}

	public void setBandCerrado(boolean bandCerrado) {
		this.bandCerrado = bandCerrado;
	}

	public Integer getKmAumento() {
		return kmAumento;
	}

	public void setKmAumento(Integer kmAumento) {
		this.kmAumento = kmAumento;
	}

	public Integer getRecorridoBus() {
		return recorridoBus;
	}

	public void setRecorridoBus(Integer recorridoBus) {
		this.recorridoBus = recorridoBus;
	}

	public Integer getIdTripulacion() {
		return IdTripulacion;
	}

	public String getDesEstDesem() {
		if (getEstDesembarque() != null) {
			if (getEstDesembarque().equals("1"))
				setDesEstDesem("SI");
			else
				setDesEstDesem("NO");
		} else {
			setDesEstDesem("NO");
		}
		return desEstDesem;
	}

	public void setDesEstDesem(String desEstDesem) {
		this.desEstDesem = desEstDesem;
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

	public Integer getnBus() {
		return nBus;
	}

	public void setnBus(Integer nBus) {
		this.nBus = nBus;
	}

	public void setIdTripulacion(Integer idTripulacion) {
		IdTripulacion = idTripulacion;
	}

	public Integer getIdPiloto() {
		return IdPiloto;
	}

	public void setIdPiloto(Integer idPiloto) {
		IdPiloto = idPiloto;
	}

	public Integer getIdCopiloto() {
		return IdCopiloto;
	}

	public void setIdCopiloto(Integer idCopiloto) {
		IdCopiloto = idCopiloto;
	}

	public Integer getIdTerramoza() {
		return IdTerramoza;
	}

	public void setIdTerramoza(Integer idTerramoza) {
		IdTerramoza = idTerramoza;
	}

	public Integer getIdTerramoza2() {
		return IdTerramoza2;
	}

	public void setIdTerramoza2(Integer idTerramoza2) {
		IdTerramoza2 = idTerramoza2;
	}

	public String getNomPiloto() {
		return nomPiloto;
	}

	public void setNomPiloto(String nomPiloto) {
		this.nomPiloto = nomPiloto;
	}

	public String getNomCopiloto() {
		return nomCopiloto;
	}

	public void setNomCopiloto(String nomCopiloto) {
		this.nomCopiloto = nomCopiloto;
	}

	public String getNomTerramoza() {
		return nomTerramoza;
	}

	public void setNomTerramoza(String nomTerramoza) {
		this.nomTerramoza = nomTerramoza;
	}

	public String getNomTerramoza2() {
		return nomTerramoza2;
	}

	public void setNomTerramoza2(String nomTerramoza2) {
		this.nomTerramoza2 = nomTerramoza2;
	}

	public void setfSalidaString(String fSalidaString) {
		this.fSalidaString = fSalidaString;
	}

	public String getNombOrigen() {
		return nombOrigen;
	}

	public void setNombOrigen(String nombOrigen) {
		this.nombOrigen = nombOrigen;
	}

	public String getNombDestino() {
		return nombDestino;
	}

	public void setNombDestino(String nombDestino) {
		this.nombDestino = nombDestino;
	}

	public String getNombPiloto() {
		return nombPiloto;
	}

	public void setNombPiloto(String nombPiloto) {
		this.nombPiloto = nombPiloto;
	}

	public String getNombCopiloto() {
		return nombCopiloto;
	}

	public void setNombCopiloto(String nombCopiloto) {
		this.nombCopiloto = nombCopiloto;
	}

	public Integer getIdAgencia() {
		return IdAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		IdAgencia = idAgencia;
	}

	public Integer getIdOficina() {
		return IdOficina;
	}

	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}

	public Integer getFlagAtnBordo() {
		return flagAtnBordo;
	}

	public void setFlagAtnBordo(Integer flagAtnBordo) {
		this.flagAtnBordo = flagAtnBordo;
	}

	public String getDesAgencia() {
		return desAgencia;
	}

	public void setDesAgencia(String desAgencia) {
		this.desAgencia = desAgencia;
	}

	public String getDesOficina() {
		return desOficina;
	}

	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}

	public Integer getIdProgramacion() {
		return idProgramacion;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public Date getfLiquidacion() {
		return fLiquidacion;
	}

	public Date getfSalida() {
		return fSalida;
	}

	public BigDecimal getPrecioPiso1() {
		return precioPiso1;
	}

	public BigDecimal getPrecioPiso2() {
		return precioPiso2;
	}

	public String gethSalida() {
		return hSalida;
	}

	public Date getFcreacion() {
		return fcreacion;
	}

	public Date getFconfirmacion() {
		return fconfirmacion;
	}

	public Date getFmodificacion() {
		return fmodificacion;
	}

	public Date getHora24() {
		return hora24;
	}

	public String getEstado() {
		return estado;
	}

	public String getEstEmbarque() {
		return estEmbarque;
	}

	public String getEstDesembarque() {
		return estDesembarque;
	}

	public Date getfSalidaReal() {
		return fSalidaReal;
	}

	public Date getfLlegadaReal() {
		return fLlegadaReal;
	}

	public Integer getOrigen() {
		return origen;
	}

	public Integer getDestino() {
		return destino;
	}

	public Integer getEstManifiesto() {
		return estManifiesto;
	}

	public String getDescripEstado() {
		return descripEstado;
	}

	public Integer getIdCatServicio() {
		return idCatServicio;
	}

	public String getDescServicio() {
		return descServicio;
	}

	public Integer getIdClase() {
		return idClase;
	}

	public Flota getFlota() {
		return flota;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public Piloto getCopiloto() {
		return copiloto;
	}

	public String getNumeroBus() {
		return numeroBus;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public String getDescEstado() {
		return descEstado;
	}

	public String getIcono() {
		return icono;
	}

	public boolean isAsignarBus() {
		return asignarBus;
	}

	public boolean isAsignarPiloto() {
		return asignarPiloto;
	}

	public boolean isAsignarCopiloto() {
		return asignarCopiloto;
	}

	public boolean isGuardarProgFlota() {
		return guardarProgFlota;
	}

	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public void setfLiquidacion(Date fLiquidacion) {
		this.fLiquidacion = fLiquidacion;
	}

	public void setfSalida(Date fSalida) {
		this.fSalida = fSalida;
	}

	public void setPrecioPiso1(BigDecimal precioPiso1) {
		this.precioPiso1 = precioPiso1;
	}

	public void setPrecioPiso2(BigDecimal precioPiso2) {
		this.precioPiso2 = precioPiso2;
	}

	public void sethSalida(String hSalida) {
		this.hSalida = hSalida;
	}

	public void setFcreacion(Date fcreacion) {
		this.fcreacion = fcreacion;
	}

	public void setFconfirmacion(Date fconfirmacion) {
		this.fconfirmacion = fconfirmacion;
	}

	public void setFmodificacion(Date fmodificacion) {
		this.fmodificacion = fmodificacion;
	}

	public void setHora24(Date hora24) {
		this.hora24 = hora24;
	}

	public void setEstado(String estado) {
		this.estado = estado.toUpperCase();
	}

	public void setEstEmbarque(String estEmbarque) {
		this.estEmbarque = estEmbarque.toUpperCase();
	}

	public void setEstDesembarque(String estDesembarque) {
		this.estDesembarque = estDesembarque.toUpperCase();
	}

	public void setfSalidaReal(Date fSalidaReal) {
		this.fSalidaReal = fSalidaReal;
	}

	public void setfLlegadaReal(Date fLlegadaReal) {
		this.fLlegadaReal = fLlegadaReal;
	}

	public void setOrigen(Integer origen) {
		this.origen = origen;
	}

	public void setDestino(Integer destino) {
		this.destino = destino;
	}

	public void setEstManifiesto(Integer estManifiesto) {
		this.estManifiesto = estManifiesto;
	}

	public void setDescripEstado(String descripEstado) {
		this.descripEstado = descripEstado.toUpperCase();
	}

	public void setIdCatServicio(Integer idCatServicio) {
		this.idCatServicio = idCatServicio;
	}

	public void setDescServicio(String descServicio) {
		this.descServicio = descServicio.toUpperCase();
	}

	public void setIdClase(Integer idClase) {
		this.idClase = idClase;
	}

	public void setFlota(Flota flota) {
		this.flota = flota;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public void setCopiloto(Piloto copiloto) {
		this.copiloto = copiloto;
	}

	public void setNumeroBus(String numeroBus) {
		this.numeroBus = numeroBus.toUpperCase();
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado.toUpperCase();
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public void setAsignarBus(boolean asignarBus) {
		this.asignarBus = asignarBus;
	}

	public void setAsignarPiloto(boolean asignarPiloto) {
		this.asignarPiloto = asignarPiloto;
	}

	public void setAsignarCopiloto(boolean asignarCopiloto) {
		this.asignarCopiloto = asignarCopiloto;
	}

	public void setGuardarProgFlota(boolean guardarProgFlota) {
		this.guardarProgFlota = guardarProgFlota;
	}

	public Integer getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(Integer usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombUsuarioRegistro() {
		return nombUsuarioRegistro;
	}

	public void setNombUsuarioRegistro(String nombUsuarioRegistro) {
		this.nombUsuarioRegistro = nombUsuarioRegistro;
	}

	public String getDescEstadoFD() {
		if (getEstado().equals("0")) {
			descEstadoFD = "Demanda";
		} else
			descEstadoFD = "Frecuente";
		return descEstadoFD;
	}

	public void setDescEstadoFD(String descEstadoFD) {
		this.descEstadoFD = descEstadoFD;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Integer getCantidadSalidasBus() {
		return cantidadSalidasBus;
	}

	public void setCantidadSalidasBus(Integer cantidadSalidasBus) {
		this.cantidadSalidasBus = cantidadSalidasBus;
	}

	public JRDataSource getLiquidacionesDS() {
		return new JRBeanCollectionDataSource(this.listaDet, false);
	}

	public Integer getDisponibleP1() {
		return disponibleP1;
	}

	public void setDisponibleP1(Integer disponibleP1) {
		this.disponibleP1 = disponibleP1;
	}

	public Integer getDisponibleP2() {
		return disponibleP2;
	}

	public void setDisponibleP2(Integer disponibleP2) {
		this.disponibleP2 = disponibleP2;
	}


	public Date getfSubm() {
		return fSubm;
	}

	public void setfSubm(Date fSubm) {
		this.fSubm = fSubm;
	}

	public Integer getIdSubmanifiesto() {
		return idSubmanifiesto;
	}

	public void setIdSubmanifiesto(Integer idSubmanifiesto) {
		this.idSubmanifiesto = idSubmanifiesto;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public TipoAsiento getTipoAsientoP1() {
		return tipoAsientoP1;
	}

	public void setTipoAsientoP1(TipoAsiento tipoAsientoP1) {
		this.tipoAsientoP1 = tipoAsientoP1;
	}

	public TipoAsiento getTipoAsientop2() {
		return tipoAsientop2;
	}

	public Integer getCapacidadVerdadera() {
		return capacidadVerdadera;
	}

	public void setCapacidadVerdadera(Integer capacidadVerdadera) {
		this.capacidadVerdadera = capacidadVerdadera;
	}

	public void setTipoAsientop2(TipoAsiento tipoAsientop2) {
		this.tipoAsientop2 = tipoAsientop2;
	}

	public Integer getVendidos() {
		return vendidos;
	}

	public void setVendidos(Integer vendidos) {
		this.vendidos = vendidos;
	}

	public Integer getReservados() {
		return reservados;
	}

	public void setReservados(Integer reservados) {
		this.reservados = reservados;
	}


	public Integer getIntCorreEnlace() {
		return IntCorreEnlace;
	}


	public void setIntCorreEnlace(Integer intCorreEnlace) {
		IntCorreEnlace = intCorreEnlace;
	}

	public Integer getIdConCombus() {
		return idConCombus;
	}

	public void setIdConCombus(Integer idConCombus) {
		this.idConCombus = idConCombus;
	}

	public Integer getKmRecorridos() {
		return kmRecorridos;
	}

	public void setKmRecorridos(Integer kmRecorridos) {
		this.kmRecorridos = kmRecorridos;
	}

	public double getPrecioComb() {
		return precioComb;
	}

	public void setPrecioComb(double precioComb) {
		this.precioComb = precioComb;
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public double getCostoKm() {
		return costoKm;
	}

	public void setCostoKm(double costoKm) {
		this.costoKm = costoKm;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	public List<Programacion> getListaSalida() {
		return listaSalida;
	}

	public void setListaSalida(List<Programacion> listaSalida) {
		this.listaSalida = listaSalida;
	}

	public List<Programacion> getListaRetorno() {
		return listaRetorno;
	}

	public void setListaRetorno(List<Programacion> listaRetorno) {
		this.listaRetorno = listaRetorno;
	}
	
	public JRBeanCollectionDataSource getSalidaDS() {
		return new JRBeanCollectionDataSource(this.listaSalida, false);
	}
	
	public JRBeanCollectionDataSource getRetornoDS() {
		return new JRBeanCollectionDataSource(this.listaRetorno, false);
	}

	public Integer getSeparados() {
		return separados;
	}

	public void setSeparados(Integer separados) {
		this.separados = separados;
	}

	public Boolean getAmpliado() {
		return ampliado;
	}

	public void setAmpliado(Boolean ampliado) {
		this.ampliado = ampliado;
	}

	public BigDecimal getAsignacionViatico() {
		return asignacionViatico;
	}

	public void setAsignacionViatico(BigDecimal asignacionViatico) {
		this.asignacionViatico = asignacionViatico;
	}

	public BigDecimal getTotalGastos() {
		return totalGastos;
	}

	public void setTotalGastos(BigDecimal totalGastos) {
		this.totalGastos = totalGastos;
	}


}

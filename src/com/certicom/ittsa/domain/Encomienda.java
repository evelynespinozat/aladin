package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Encomienda {

	private Integer idEncomienda;
	private Integer idOrigen;
	private Integer idDestino;
	private String tipoPersona;
	private String comprobante;
	private String tipoDocumento;
	private Integer idUsuario;
	private Integer idPuntoVentaOrigen;
	private Double totalCobrado;
	private String dniRemitente;
	private String apellidosRemitente;
	private String nombresRemitente;
	private String telefonoRemitente;
	private String rucRemitente;
	private String razonSocialRemitente;
	private String dniDestinatario1;
	private String apellidosDestinatario1;
	private String nombresDestinatario1;
	private String telefonoDestinatario1;
	private String rucDestinatario1;
	private String razonSocialDestinatario1;
	private String dniDestinatario2;
	private String apellidosDestinatario2;
	private String nombresDestinatario2;
	private String telefonoDestinatario2;
	private String rucDestinatario2;
	private String razonSocialDestinatario2;
	private String observacionEnvio;
	private Date fEnvio;
	private Date fRecojo;
	private String observacionRecojo;
	private Date fRegistro;
	private String tipoVenta;
	private Integer idPromocion;
	private Integer idAutorizante;
	private String tipoEntrega;
	private Integer estado;
	private BigDecimal pesoTotal;
	private String codigo;
	private Integer idPuntoVentaDestino;
	private String guiaRemitente;
	private String direccionEnvio;
	private Integer idTarifa;
	private Double precioReparto;
	private Double precioExtraRapido;
	private String servicioEntrega;
	private String numeroDocRecoge;
	private String nombreRecoge;
	private Integer idUsuarioEntrega;
	private String ubicacionAlmacen;

	// //Otros
	private String origenString;
	private String destinoString;
	private String fRegistroString;
	private Integer importe;
	private Integer idDetalle;
	private String desOrigen;
	private String desDestino;
	private String desEncomienda;
	private Date fecIni;
	private Date fecFin;

	private String remitcompleto;
	private String desTipoDoc;
	private String desEstado;

	private boolean seleccionado = false;
	private Integer cantidad;
	private Integer nroBultos;

	private String hSalida;
	private Integer idBus;
	private Integer nroBus;
	private String remitente;
	private String destinatario1;
	private String destinatario2;
	private String detalle;

	// 27-03-2014
	private Integer idProgramacion;

	// 31-03-2014
	private String tipoDocAbr;
	private String piloto;
	private String copiloto;
	private String dest1;
	private String dest2;
	private Integer idPersonalReparto;
	private Integer idAlmacen;
	private String sdistrito;
	
	private Integer idAgencia;
	private Integer idOficina;
	
	private Date fRegAlmacen;
	private Date fVencimiento;
	private Integer diasVencimiento;
	private String usuarioEntrega;
	private String desEstadoTrack;
	private Integer estadoActual;
	private String codigoBarrasStringPD;
	private Integer idProducto_detalleEnc;
	private String desProducto;
	
	private String obsReparto;
	private String condicionReparto;
	private boolean devuelto = false;
	private Integer idSubmanifiesto;
	private String desOficinaDestino;
	private String desOficinaOrigen;
	
	private String DireccionDestinatario1;
	private String DireccionDestinatario2;
	private String DireccionRemitente;
	
	private String formapago;
	
	public Encomienda() {
	}
	
	public String gethSalida() {
		return hSalida;
	}

	public void sethSalida(String hSalida) {
		this.hSalida = hSalida;
	}

	public String getUbicacionAlmacen() {
		return ubicacionAlmacen;
	}

	public void setUbicacionAlmacen(String ubicacionAlmacen) {
		this.ubicacionAlmacen = ubicacionAlmacen;
	}

	public String getNumeroDocRecoge() {
		return numeroDocRecoge;
	}

	public void setNumeroDocRecoge(String numeroDocRecoge) {
		this.numeroDocRecoge = numeroDocRecoge;
	}

	public String getNombreRecoge() {
		return nombreRecoge;
	}

	public void setNombreRecoge(String nombreRecoge) {
		this.nombreRecoge = nombreRecoge;
	}

	public Integer getIdUsuarioEntrega() {
		return idUsuarioEntrega;
	}

	public void setIdUsuarioEntrega(Integer idUsuarioEntrega) {
		this.idUsuarioEntrega = idUsuarioEntrega;
	}

	public String getServicioEntrega() {
		return servicioEntrega;
	}

	public void setServicioEntrega(String servicioEntrega) {
		this.servicioEntrega = servicioEntrega;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public Integer getNroBus() {
		return nroBus;
	}

	public void setNroBus(Integer nroBus) {
		this.nroBus = nroBus;
	}

	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	public String getDestinatario1() {
		return destinatario1;
	}

	public void setDestinatario1(String destinatario1) {
		this.destinatario1 = destinatario1;
	}

	public String getDestinatario2() {
		return destinatario2;
	}

	public void setDestinatario2(String destinatario2) {
		this.destinatario2 = destinatario2;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Integer getImporte() {
		return importe;
	}

	public void setImporte(Integer importe) {
		this.importe = importe;
	}

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
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

	public String getDesEncomienda() {
		return desEncomienda;
	}

	public void setDesEncomienda(String desEncomienda) {
		this.desEncomienda = desEncomienda;
	}

	public Date getFecIni() {
		return fecIni;
	}

	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public String getRemitcompleto() {
		return remitcompleto;
	}

	public void setRemitcompleto(String remitcompleto) {
		this.remitcompleto = remitcompleto;
	}

	public String getDesTipoDoc() {
		return desTipoDoc;
	}

	public void setDesTipoDoc(String desTipoDoc) {
		this.desTipoDoc = desTipoDoc;
	}

	public String getDesEstado() {
		return desEstado;
	}

	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getNroBultos() {
		return nroBultos;
	}

	public void setNroBultos(Integer nroBultos) {
		this.nroBultos = nroBultos;
	}

	public Integer getIdEncomienda() {
		return idEncomienda;
	}

	public void setIdEncomienda(Integer idEncomienda) {
		this.idEncomienda = idEncomienda;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdPuntoVentaOrigen() {
		return idPuntoVentaOrigen;
	}

	public void setIdPuntoVentaOrigen(Integer idPuntoVentaOrigen) {
		this.idPuntoVentaOrigen = idPuntoVentaOrigen;
	}

	public Double getTotalCobrado() {
		return totalCobrado;
	}

	public void setTotalCobrado(Double totalCobrado) {
		this.totalCobrado = totalCobrado;
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

	public String getObservacionEnvio() {
		return observacionEnvio;
	}

	public void setObservacionEnvio(String observacionEnvio) {
		this.observacionEnvio = observacionEnvio;
	}

	public Date getfEnvio() {
		return fEnvio;
	}

	public void setfEnvio(Date fEnvio) {
		this.fEnvio = fEnvio;
	}

	public Date getfRecojo() {
		return fRecojo;
	}

	public void setfRecojo(Date fRecojo) {
		this.fRecojo = fRecojo;
	}

	public String getObservacionRecojo() {
		return observacionRecojo;
	}

	public void setObservacionRecojo(String observacionRecojo) {
		this.observacionRecojo = observacionRecojo;
	}

	public Date getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}

	public String getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public Integer getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromocion(Integer idPromocion) {
		this.idPromocion = idPromocion;
	}

	public Integer getIdAutorizante() {
		return idAutorizante;
	}

	public void setIdAutorizante(Integer idAutorizante) {
		this.idAutorizante = idAutorizante;
	}

	public String getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public BigDecimal getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(BigDecimal pesoTotal) {
		this.pesoTotal = pesoTotal;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getIdPuntoVentaDestino() {
		return idPuntoVentaDestino;
	}

	public void setIdPuntoVentaDestino(Integer idPuntoVentaDestino) {
		this.idPuntoVentaDestino = idPuntoVentaDestino;
	}

	public String getGuiaRemitente() {
		return guiaRemitente;
	}

	public void setGuiaRemitente(String guiaRemitente) {
		this.guiaRemitente = guiaRemitente;
	}

	public String getRucRemitente() {
		return rucRemitente;
	}

	public void setRucRemitente(String rucRemitente) {
		this.rucRemitente = rucRemitente;
	}

	public String getRucDestinatario1() {
		return rucDestinatario1;
	}

	public void setRucDestinatario1(String rucDestinatario1) {
		this.rucDestinatario1 = rucDestinatario1;
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

	public String getOrigenString() {
		return origenString;
	}

	public void setOrigenString(String origenString) {
		this.origenString = origenString;
	}

	public String getDestinoString() {
		return destinoString;
	}

	public void setDestinoString(String destinoString) {
		this.destinoString = destinoString;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getfRegistroString() {
		return fRegistroString;
	}

	public void setfRegistroString(String fRegistroString) {
		this.fRegistroString = fRegistroString;
	}

	public Double getPrecioReparto() {
		return precioReparto;
	}

	public void setPrecioReparto(Double precioReparto) {
		this.precioReparto = precioReparto;
	}

	public Integer getIdTarifa() {
		return idTarifa;
	}

	public void setIdTarifa(Integer idTarifa) {
		this.idTarifa = idTarifa;
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

	public Integer getIdProgramacion() {
		return idProgramacion;
	}

	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
	}

	public String getTipoDocAbr() {
		return tipoDocAbr;
	}

	public void setTipoDocAbr(String tipoDocAbr) {
		this.tipoDocAbr = tipoDocAbr;
	}

	public String getPiloto() {
		return piloto;
	}

	public String getCopiloto() {
		return copiloto;
	}

	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}

	public void setCopiloto(String copiloto) {
		this.copiloto = copiloto;
	}

	public String getDest1() {
		return dest1;
	}

	public String getDest2() {
		return dest2;
	}

	public void setDest1(String dest1) {
		this.dest1 = dest1;
	}

	public void setDest2(String dest2) {
		this.dest2 = dest2;
	}

	public Integer getIdPersonalReparto() {
		return idPersonalReparto;
	}

	public void setIdPersonalReparto(Integer idPersonalReparto) {
		this.idPersonalReparto = idPersonalReparto;
	}

	public Integer getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(Integer idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public String getSdistrito() {
		return sdistrito;
	}

	public void setSdistrito(String sdistrito) {
		this.sdistrito = sdistrito;
	}

	public Double getPrecioExtraRapido() {
		return precioExtraRapido;
	}

	public void setPrecioExtraRapido(Double precioExtraRapido) {
		this.precioExtraRapido = precioExtraRapido;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public Date getfRegAlmacen() {
		return fRegAlmacen;
	}

	public void setfRegAlmacen(Date fRegAlmacen) {
		this.fRegAlmacen = fRegAlmacen;
	}

	public Date getfVencimiento() {
		return fVencimiento;
	}

	public Integer getDiasVencimiento() {
		return diasVencimiento;
	}

	public void setfVencimiento(Date fVencimiento) {
		this.fVencimiento = fVencimiento;
	}

	public void setDiasVencimiento(Integer diasVencimiento) {
		this.diasVencimiento = diasVencimiento;
	}

	public String getUsuarioEntrega() {
		return usuarioEntrega;
	}

	public void setUsuarioEntrega(String usuarioEntrega) {
		this.usuarioEntrega = usuarioEntrega;
	}

	public String getDesEstadoTrack() {
		return desEstadoTrack;
	}

	public void setDesEstadoTrack(String desEstadoTrack) {
		this.desEstadoTrack = desEstadoTrack;
	}

	public Integer getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(Integer estadoActual) {
		this.estadoActual = estadoActual;
	}

	public String getCodigoBarrasStringPD() {
		return codigoBarrasStringPD;
	}

	public void setCodigoBarrasStringPD(String codigoBarrasStringPD) {
		this.codigoBarrasStringPD = codigoBarrasStringPD;
	}

	public Integer getIdProducto_detalleEnc() {
		return idProducto_detalleEnc;
	}

	public void setIdProducto_detalleEnc(Integer idProducto_detalleEnc) {
		this.idProducto_detalleEnc = idProducto_detalleEnc;
	}

	public String getDesProducto() {
		return desProducto;
	}

	public void setDesProducto(String desProducto) {
		this.desProducto = desProducto;
	}

	public String getObsReparto() {
		return obsReparto;
	}

	public void setObsReparto(String obsReparto) {
		this.obsReparto = obsReparto;
	}

	public String getCondicionReparto() {
		return condicionReparto;
	}

	public void setCondicionReparto(String condicionReparto) {
		this.condicionReparto = condicionReparto;
	}

	public boolean isDevuelto() {
		return devuelto;
	}

	public void setDevuelto(boolean devuelto) {
		this.devuelto = devuelto;
	}

	public Integer getIdSubmanifiesto() {
		return idSubmanifiesto;
	}

	public void setIdSubmanifiesto(Integer idSubmanifiesto) {
		this.idSubmanifiesto = idSubmanifiesto;
	}

	public String getDesOficinaDestino() {
		return desOficinaDestino;
	}

	public void setDesOficinaDestino(String desOficinaDestino) {
		this.desOficinaDestino = desOficinaDestino;
	}

	public String getDesOficinaOrigen() {
		return desOficinaOrigen;
	}

	public void setDesOficinaOrigen(String desOficinaOrigen) {
		this.desOficinaOrigen = desOficinaOrigen;
	}

	public String getDireccionDestinatario1() {
		return DireccionDestinatario1;
	}

	public void setDireccionDestinatario1(String direccionDestinatario1) {
		DireccionDestinatario1 = direccionDestinatario1;
	}

	public String getDireccionDestinatario2() {
		return DireccionDestinatario2;
	}

	public void setDireccionDestinatario2(String direccionDestinatario2) {
		DireccionDestinatario2 = direccionDestinatario2;
	}

	public String getDireccionRemitente() {
		return DireccionRemitente;
	}

	public void setDireccionRemitente(String direccionRemitente) {
		DireccionRemitente = direccionRemitente;
	}

	public String getFormapago() {
		return formapago;
	}

	public void setFormapago(String formapago) {
		this.formapago = formapago;
	}
	
	

	
	
}

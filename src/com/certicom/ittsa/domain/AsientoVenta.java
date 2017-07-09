package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.primefaces.model.StreamedContent;

public class AsientoVenta {

	private Integer idasientoventa;
	private String estado;
	private Date fechaventa;
	private BigDecimal monto;
	private String tipoventa; // DIRECTA Y ONLINE
	private Integer IdAsiento;
	private Integer IdProgramacion;
	private String numero;
	private String sexo;
	private String documentoPersona;
	private String documentoEmpresa;
	private Integer idPostergado;
	private String observacion;
	private Boolean externo;
	private Integer oficinaId;
	private Boolean prepagado;
	private String tipdocprepagado;
	private String nrodocprepagado;
	private Boolean delivery;
	private String tipdocdelivery;
	private String nrodocdelivery;
	private Boolean abordo;
	private Integer idusuarioventa;
	private Boolean promocional;
	private Integer idpromocion;
	private Integer idusuarioautorizante;
	private Date horareserva;
	private String estado_delivery;
	private Date fechaEntregaDelivery;
	private String referenDelivery;
	private String telefDelivery;
	private String personaRecibeDeliv;
	private BigDecimal montoReal;
	private Integer idAsientoVentaInhabiltd;
	

	// Campos alternos
	private Integer piso;
	private Persona persona;
	private Empresa empresa;
	private String nombres;
	private String aPaterno;
	private String aMaterno;
	private Boolean rucBoolean;
	private String razon;
	private String serieBoleto;
	private Integer ultimoBoleto;
	private String serieBoletoMulti;
	private Integer numeroBoletoMulti;
	private String codigoReserva;
	private String inhabilita;
	
	private Asiento asiento;
	private String codigoBoleta;
	private String direccion;
	
	private String desOrigen;
	private String desDestino;
	private String desServicio;
	private Date desFSalida;
	private String desHsalida;
	private Integer nroAsiento;
	private Integer edadPersona;
	private Integer diasRestantes;
	private Integer horasRestantes;
	private Persona personaPagador;
	private Persona personaCliente;
	private Usuario usuario;
	private Boolean Visible;
	private String completoVendedor;
	private String desOficina;
	private Date fSalida;
	private Date fechaPostergacion;
	private Postergacion postergacion;
	
	private String serieNumeroBoleto;
	
	private String transferenciaBanco;
	private String transferenciaNumero;
	private BigDecimal costoRealAsiento;
	private Integer idAsientoVentaOrigenTransbordo;
	
	private String nombreImagen;
	private Integer numeroImagen;
	
	
	private StreamedContent fotoHuella;
	
	private Boolean ventaOrigen;
	
	private Integer nropedido;
	
	private String turno_entrega;
	private String motivo;
	
	//variable de salida del store procedure
	private int retorno;

	public Date getHorareserva() {
		return horareserva;
	}

	public void setHorareserva(Date horareserva) {
		this.horareserva = horareserva;
	}

	public Date getfSalida() {
		return fSalida;
	}

	public void setfSalida(Date fSalida) {
		this.fSalida = fSalida;
	}

	public String getCompletoVendedor() {
		return completoVendedor;
	}
	
	public String getDesOficina() {
		return desOficina;
	}

	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}

	public void setCompletoVendedor(String completoVendedor) {
		this.completoVendedor = completoVendedor;
	}

	public String getDesServicio() {
		return desServicio;
	}

	public void setDesServicio(String desServicio) {
		this.desServicio = desServicio;
	}

	public Persona getPersonaPagador() {
		return personaPagador;
	}

	public void setPersonaPagador(Persona personaPagador) {
		this.personaPagador = personaPagador;
	}

	public Persona getPersonaCliente() {
		return personaCliente;
	}

	public void setPersonaCliente(Persona personaCliente) {
		this.personaCliente = personaCliente;
	}

	public String getDesHsalida() {
		return desHsalida;
	}

	public void setDesHsalida(String desHsalida) {
		this.desHsalida = desHsalida;
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

	public Date getDesFSalida() {
		return desFSalida;
	}

	public void setDesFSalida(Date desFSalida) {
		this.desFSalida = desFSalida;
	}

	public Integer getNroAsiento() {
		return nroAsiento;
	}

	public void setNroAsiento(Integer nroAsiento) {
		this.nroAsiento = nroAsiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getDelivery() {
		return delivery;
	}

	public void setDelivery(Boolean delivery) {
		this.delivery = delivery;
	}

	public String getTipdocdelivery() {
		return tipdocdelivery;
	}

	public void setTipdocdelivery(String tipdocdelivery) {
		this.tipdocdelivery = tipdocdelivery;
	}

	public String getNrodocdelivery() {
		return nrodocdelivery;
	}

	public void setNrodocdelivery(String nrodocdelivery) {
		this.nrodocdelivery = nrodocdelivery;
	}

	public String getSerieBoletoMulti() {
		return serieBoletoMulti;
	}

	public void setSerieBoletoMulti(String serieBoletoMulti) {
		this.serieBoletoMulti = serieBoletoMulti;
	}

	public Integer getNumeroBoletoMulti() {
		return numeroBoletoMulti;
	}

	public void setNumeroBoletoMulti(Integer numeroBoletoMulti) {
		this.numeroBoletoMulti = numeroBoletoMulti;
	}

	public String getSerieBoleto() {
		return serieBoleto;
	}

	public void setSerieBoleto(String serieBoleto) {
		this.serieBoleto = serieBoleto;
	}

	public Integer getUltimoBoleto() {
		return ultimoBoleto;
	}

	public void setUltimoBoleto(Integer ultimoBoleto) {
		this.ultimoBoleto = ultimoBoleto;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public Boolean getRucBoolean() {
		return rucBoolean;
	}

	public void setRucBoolean(Boolean rucBoolean) {
		this.rucBoolean = rucBoolean;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getaPaterno() {
		return aPaterno;
	}

	public void setaPaterno(String aPaterno) {
		this.aPaterno = aPaterno;
	}

	public String getaMaterno() {
		return aMaterno;
	}

	public void setaMaterno(String aMaterno) {
		this.aMaterno = aMaterno;
	}

	public String getDocumentoPersona() {
		return documentoPersona;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public void setDocumentoPersona(String documentoPersona) {
		this.documentoPersona = documentoPersona;
	}

	public String getDocumentoEmpresa() {
		return documentoEmpresa;
	}

	public void setDocumentoEmpresa(String documentoEmpresa) {
		this.documentoEmpresa = documentoEmpresa;
	}

	public AsientoVenta() {
		;
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

	public Date getFechaventa() {
		return fechaventa;
	}

	public void setFechaventa(Date fechaventa) {
		this.fechaventa = fechaventa;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getTipoventa() {
		return tipoventa;
	}

	public void setTipoventa(String tipoventa) {
		this.tipoventa = tipoventa;
	}

	public Integer getIdAsiento() {
		return IdAsiento;
	}

	public void setIdAsiento(Integer idAsiento) {
		IdAsiento = idAsiento;
	}

	public Integer getIdProgramacion() {
		return IdProgramacion;
	}

	public void setIdProgramacion(Integer idProgramacion) {
		IdProgramacion = idProgramacion;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getIdPostergado() {
		return idPostergado;
	}

	public void setIdPostergado(Integer idPostergado) {
		this.idPostergado = idPostergado;
	}

	public String getCodigoReserva() {
		return codigoReserva;
	}

	public void setCodigoReserva(String codigoReserva) {
		this.codigoReserva = codigoReserva;
	}

	public Boolean getExterno() {
		return externo;
	}

	public void setExterno(Boolean externo) {
		this.externo = externo;
	}

	public Integer getOficinaId() {
		return oficinaId;
	}

	public void setOficinaId(Integer oficinaId) {
		this.oficinaId = oficinaId;
	}

	public Boolean getPrepagado() {
		return prepagado;
	}

	public void setPrepagado(Boolean prepagado) {
		this.prepagado = prepagado;
	}

	public String getTipdocprepagado() {
		return tipdocprepagado;
	}

	public void setTipdocprepagado(String tipdocprepagado) {
		this.tipdocprepagado = tipdocprepagado;
	}

	public String getNrodocprepagado() {
		return nrodocprepagado;
	}

	public void setNrodocprepagado(String nrodocprepagado) {
		this.nrodocprepagado = nrodocprepagado;
	}

	public String getInhabilita() {
		return inhabilita;
	}

	public void setInhabilita(String inhabilita) {
		this.inhabilita = inhabilita;
	}

	public String getCodigoBoleta() {
		return codigoBoleta;
	}

	public void setCodigoBoleta(String codigoBoleta) {
		this.codigoBoleta = codigoBoleta;
	}

	public Boolean getAbordo() {
		return abordo;
	}

	public void setAbordo(Boolean abordo) {
		this.abordo = abordo;
	}

	public Integer getEdadPersona() {
		return edadPersona;
	}

	public void setEdadPersona(Integer edadPersona) {
		this.edadPersona = edadPersona;
	}

	public Integer getDiasRestantes() {
		return diasRestantes;
	}

	public void setDiasRestantes(Integer diasRestantes) {
		this.diasRestantes = diasRestantes;
	}

	public Integer getHorasRestantes() {
		return horasRestantes;
	}

	public void setHorasRestantes(Integer horasRestantes) {
		this.horasRestantes = horasRestantes;
	}

	public Integer getIdusuarioventa() {
		return idusuarioventa;
	}

	public void setIdusuarioventa(Integer idusuarioventa) {
		this.idusuarioventa = idusuarioventa;
	}

	public Boolean getVisible() {
		return Visible;
	}

	public void setVisible(Boolean visible) {
		Visible = visible;
	}

	public Boolean getPromocional() {
		return promocional;
	}

	public void setPromocional(Boolean promocional) {
		this.promocional = promocional;
	}

	public Integer getIdpromocion() {
		return idpromocion;
	}

	public void setIdpromocion(Integer idpromocion) {
		this.idpromocion = idpromocion;
	}

	public Integer getIdusuarioautorizante() {
		return idusuarioautorizante;
	}

	public void setIdusuarioautorizante(Integer idusuarioautorizante) {
		this.idusuarioautorizante = idusuarioautorizante;
	}

	public String getEstado_delivery() {
		return estado_delivery;
	}

	public void setEstado_delivery(String estado_delivery) {
		this.estado_delivery = estado_delivery;
	}

	public Date getFechaEntregaDelivery() {
		return fechaEntregaDelivery;
	}

	public void setFechaEntregaDelivery(Date fechaEntregaDelivery) {
		this.fechaEntregaDelivery = fechaEntregaDelivery;
	}

	public String getReferenDelivery() {
		return referenDelivery;
	}

	public void setReferenDelivery(String referenDelivery) {
		this.referenDelivery = referenDelivery;
	}

	public String getTelefDelivery() {
		return telefDelivery;
	}

	public void setTelefDelivery(String telefDelivery) {
		this.telefDelivery = telefDelivery;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getSerieNumeroBoleto() {
		return serieNumeroBoleto;
	}

	public void setSerieNumeroBoleto(String serieNumeroBoleto) {
		this.serieNumeroBoleto = serieNumeroBoleto;
	}

	public String getPersonaRecibeDeliv() {
		return personaRecibeDeliv;
	}

	public void setPersonaRecibeDeliv(String personaRecibeDeliv) {
		this.personaRecibeDeliv = personaRecibeDeliv;
	}

	public Date getFechaPostergacion() {
		return fechaPostergacion;
	}

	public void setFechaPostergacion(Date fechaPostergacion) {
		this.fechaPostergacion = fechaPostergacion;
	}

	public Postergacion getPostergacion() {
		return postergacion;
	}

	public void setPostergacion(Postergacion postergacion) {
		this.postergacion = postergacion;
	}

	public String getTransferenciaBanco() {
		return transferenciaBanco;
	}

	public void setTransferenciaBanco(String transferenciaBanco) {
		this.transferenciaBanco = transferenciaBanco;
	}

	public String getTransferenciaNumero() {
		return transferenciaNumero;
	}

	public void setTransferenciaNumero(String transferenciaNumero) {
		this.transferenciaNumero = transferenciaNumero;
	}

	public BigDecimal getCostoRealAsiento() {
		return costoRealAsiento;
	}

	public void setCostoRealAsiento(BigDecimal costoRealAsiento) {
		this.costoRealAsiento = costoRealAsiento;
	}

	public Integer getIdAsientoVentaOrigenTransbordo() {
		return idAsientoVentaOrigenTransbordo;
	}

	public void setIdAsientoVentaOrigenTransbordo(
			Integer idAsientoVentaOrigenTransbordo) {
		this.idAsientoVentaOrigenTransbordo = idAsientoVentaOrigenTransbordo;
	}

	public StreamedContent getFotoHuella() {
		return fotoHuella;
	}

	public void setFotoHuella(StreamedContent fotoHuella) {
		this.fotoHuella = fotoHuella;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public Integer getNumeroImagen() {
		return numeroImagen;
	}

	public void setNumeroImagen(Integer numeroImagen) {
		this.numeroImagen = numeroImagen;
	}

	public int getRetorno() {
		return retorno;
	}

	public void setRetorno(int retorno) {
		this.retorno = retorno;
	}

	public Boolean getVentaOrigen() {
		return ventaOrigen;
	}

	public void setVentaOrigen(Boolean ventaOrigen) {
		this.ventaOrigen = ventaOrigen;
	}

	public BigDecimal getMontoReal() {
		return montoReal;
	}

	public void setMontoReal(BigDecimal montoReal) {
		this.montoReal = montoReal;
	}

	public Integer getIdAsientoVentaInhabiltd() {
		return idAsientoVentaInhabiltd;
	}

	public void setIdAsientoVentaInhabiltd(Integer idAsientoVentaInhabiltd) {
		this.idAsientoVentaInhabiltd = idAsientoVentaInhabiltd;
	}

	public Integer getNropedido() {
		return nropedido;
	}

	public void setNropedido(Integer nropedido) {
		this.nropedido = nropedido;
	}

	public String getTurno_entrega() {
		return turno_entrega;
	}

	public void setTurno_entrega(String turno_entrega) {
		this.turno_entrega = turno_entrega;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
	
	
}

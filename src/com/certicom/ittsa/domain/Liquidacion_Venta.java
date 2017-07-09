package com.certicom.ittsa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Liquidacion_Venta {

	private Integer idLiquidacion_venta;
	private String documento;
	private Integer idPuntoVenta;
	private Date fDocumento;
	private String Area;
	private Double subTotal;
	private Double igv;
	private Double total;
	private Integer estado;
	private Double totalEfectivo;
	private Double totalTarjeta;
	private Double totalCredito;
	private String proceso;
	private String formaPago;
	private String movimientoCaja;
	public List<Liquidacion_Venta> fPagos;
	public double totalxFPago;
	public double totalxProceso;
	private String estLiq;
	private String idpventa;
	private String nroipPV;
	private String userPV;
	private String namePV;
	private String tipoDocumento;
	private Integer idUsuario;
	private String origenPago;
	private String formaEntrega;
	private String tipoTarjeta;

	// alternos
	private String minDocumento;
	private String maxDocumento;

	public List<Liquidacion_Venta> listaIngresos;
	public List<Liquidacion_Venta> listaEgresos;
	public List<Liquidacion_Venta> listaMinMaxSeries;
	
	//filtros
	private Integer idAgencia;
	private Integer idOficina;

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public String getMinDocumento() {
		return minDocumento;
	}

	public void setMinDocumento(String minDocumento) {
		this.minDocumento = minDocumento;
	}

	public String getMaxDocumento() {
		return maxDocumento;
	}

	public void setMaxDocumento(String maxDocumento) {
		this.maxDocumento = maxDocumento;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getOrigenPago() {
		return origenPago;
	}

	public void setOrigenPago(String origenPago) {
		this.origenPago = origenPago;
	}

	public String getFormaEntrega() {
		return formaEntrega;
	}

	public void setFormaEntrega(String formaEntrega) {
		this.formaEntrega = formaEntrega;
	}

	public String getEstLiq() {
		return estLiq;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public void setEstLiq(String estLiq) {
		this.estLiq = estLiq;
	}

	public String getIdpventa() {
		return idpventa;
	}

	public void setIdpventa(String idpventa) {
		this.idpventa = idpventa;
	}

	public String getNroipPV() {
		return nroipPV;
	}

	public void setNroipPV(String nroipPV) {
		this.nroipPV = nroipPV;
	}

	public String getUserPV() {
		return userPV;
	}

	public void setUserPV(String userPV) {
		this.userPV = userPV;
	}

	public String getNamePV() {
		return namePV;
	}

	public void setNamePV(String namePV) {
		this.namePV = namePV;
	}

	public Liquidacion_Venta() {
		this.fPagos = new ArrayList<Liquidacion_Venta>();
	}

	public List<Liquidacion_Venta> getfPagos() {
		return fPagos;
	}

	public void setfPagos(List<Liquidacion_Venta> fPagos) {
		this.fPagos = fPagos;
	}

	public double getTotalxFPago() {
		return totalxFPago;
	}

	public void setTotalxFPago(double totalxFPago) {
		this.totalxFPago = totalxFPago;
	}

	public double getTotalxProceso() {
		return totalxProceso;
	}

	public void setTotalxProceso(double totalxProceso) {
		this.totalxProceso = totalxProceso;
	}

	public Integer getIdLiquidacion_venta() {
		return idLiquidacion_venta;
	}

	public void setIdLiquidacion_venta(Integer idLiquidacion_venta) {
		this.idLiquidacion_venta = idLiquidacion_venta;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getIdPuntoVenta() {
		return idPuntoVenta;
	}

	public void setIdPuntoVenta(Integer idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

	public Date getfDocumento() {
		return fDocumento;
	}

	public void setfDocumento(Date fDocumento) {
		this.fDocumento = fDocumento;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getIgv() {
		return igv;
	}

	public void setIgv(Double igv) {
		this.igv = igv;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Double getTotalEfectivo() {
		return totalEfectivo;
	}

	public void setTotalEfectivo(Double totalEfectivo) {
		this.totalEfectivo = totalEfectivo;
	}

	public Double getTotalTarjeta() {
		return totalTarjeta;
	}

	public void setTotalTarjeta(Double totalTarjeta) {
		this.totalTarjeta = totalTarjeta;
	}

	public Double getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(Double totalCredito) {
		this.totalCredito = totalCredito;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getMovimientoCaja() {
		return movimientoCaja;
	}

	public void setMovimientoCaja(String movimientoCaja) {
		this.movimientoCaja = movimientoCaja;
	}

	public List<Liquidacion_Venta> getListaIngresos() {
		return listaIngresos;
	}

	public void setListaIngresos(List<Liquidacion_Venta> listaIngresos) {
		this.listaIngresos = listaIngresos;
	}

	public List<Liquidacion_Venta> getListaEgresos() {
		return listaEgresos;
	}

	public void setListaEgresos(List<Liquidacion_Venta> listaEgresos) {
		this.listaEgresos = listaEgresos;
	}
	
	

	public List<Liquidacion_Venta> getListaMinMaxSeries() {
		return listaMinMaxSeries;
	}

	public void setListaMinMaxSeries(List<Liquidacion_Venta> listaMinMaxSeries) {
		this.listaMinMaxSeries = listaMinMaxSeries;
	}

	public JRBeanCollectionDataSource getIngresosDS() {
		return new JRBeanCollectionDataSource(this.listaIngresos, false);
	}

	public JRBeanCollectionDataSource getEgresosDS() {
		return new JRBeanCollectionDataSource(this.listaEgresos, false);
	}

	public JRBeanCollectionDataSource getMinMaxSeriesDS() {
		return new JRBeanCollectionDataSource(this.listaMinMaxSeries, false);
	}

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
	
	

}

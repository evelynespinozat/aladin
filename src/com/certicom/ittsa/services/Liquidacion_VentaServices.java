package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Giro;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.Liquidacion_VentaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class Liquidacion_VentaServices implements Liquidacion_VentaMapper{
	
	Liquidacion_VentaMapper liquidacion_VentaMapper = (Liquidacion_VentaMapper)ServiceFinder.findBean("liquidacion_VentaMapper");

	@Override
	public List<Liquidacion_Venta> findAll() throws Exception {
		return liquidacion_VentaMapper.findAll();
	}

	@Override
	public Liquidacion_Venta findById(Integer idLiquidacion_venta)
			throws Exception {
		return liquidacion_VentaMapper.findById(idLiquidacion_venta); 
	}

	@Override
	public void eliminarLiquidacion_Venta(Integer idLiquidacion_venta)
			throws Exception {
		liquidacion_VentaMapper.eliminarLiquidacion_Venta(idLiquidacion_venta); 
	}

	@Override
	public void crearLiquidacion_Venta(Liquidacion_Venta liquidacion_Venta)
			throws Exception {
		liquidacion_VentaMapper.crearLiquidacion_Venta(liquidacion_Venta); 
	}

	@Override
	public void actualizaLiquidacion_Venta(Liquidacion_Venta liquidacion_Venta)
			throws Exception {
		liquidacion_VentaMapper.actualizaLiquidacion_Venta(liquidacion_Venta); 
	}

	@Override
	public List<Liquidacion_Venta> listarProcesos() throws Exception {
		return liquidacion_VentaMapper.listarProcesos();
	}

	@Override
	public List<Liquidacion_Venta> listarLiquidacionxFPago(Date fecha,
			String proceso) throws Exception {
		return liquidacion_VentaMapper.listarLiquidacionxFPago(fecha, proceso);
	}

	@Override
	public List<Liquidacion_Venta> listarLiquidacionxFPagoPV(Date fecha,
			String proceso, Integer IdPuntoVenta) throws Exception {
		return liquidacion_VentaMapper.listarLiquidacionxFPagoPV(fecha, proceso, IdPuntoVenta);
	}

	@Override
	public List<Liquidacion_Venta> listarLiquidacionxFPagoOFI(Date fecha,
			String proceso, Integer idOficina) throws Exception {
		return liquidacion_VentaMapper.listarLiquidacionxFPagoOFI(fecha, proceso, idOficina);
	}

	@Override
	public void preliquidarxPuntoVentaCargo(Date fecha, Integer IdPuntoVenta)
			throws Exception {
		liquidacion_VentaMapper.preliquidarxPuntoVentaCargo(fecha, IdPuntoVenta);
	}

	@Override
	public List<Liquidacion_Venta> listarPreLiquidaciones(Date fecha,
			Integer idOficina) throws Exception {
		return liquidacion_VentaMapper.listarPreLiquidaciones(fecha, idOficina);
	}

	@Override
	public void liquidarxOficina(Date fecha, Integer idOficina)
			throws Exception {
		liquidacion_VentaMapper.liquidarxOficina(fecha, idOficina);
	}

	@Override
	public void anularLiquidacion_Venta(Liquidacion_Venta liquidacion_Venta)
			throws Exception {
		liquidacion_VentaMapper.anularLiquidacion_Venta(liquidacion_Venta);
	}

	@Override
	public Liquidacion_Venta findByDocumento(String documento) throws Exception {
		return liquidacion_VentaMapper.findByDocumento(documento);
	}

	@Override
	public Double calcularEfectivoxPuntoVenta(Date fecha, Integer idOficina)
			throws Exception {
		return liquidacion_VentaMapper.calcularEfectivoxPuntoVenta(fecha, idOficina);
	}

	@Override
	public Double calcularEfectivoxOficina(Date fecha, Integer idPuntoVenta)
			throws Exception {
		return liquidacion_VentaMapper.calcularEfectivoxOficina(fecha, idPuntoVenta);
	}

	@Override
	public List<Liquidacion_Venta> listarLiquidacionxFPagoPVEgreso(Date fecha,
			String proceso, Integer IdPuntoVenta) throws Exception {
		return liquidacion_VentaMapper.listarLiquidacionxFPagoPVEgreso(fecha, proceso, IdPuntoVenta);
	}

	@Override
	public List<Liquidacion_Venta> listarLiquidacionxFPagoOFIEgreso(Date fecha,
			String proceso, Integer idOficina) throws Exception {
		return liquidacion_VentaMapper.listarLiquidacionxFPagoOFIEgreso(fecha, proceso, idOficina);
	}

	@Override
	public List<Liquidacion_Venta> listarSeriesLiquidacion(Date fecha,
			Integer idPuntoVenta) throws Exception {
		return liquidacion_VentaMapper.listarSeriesLiquidacion(fecha, idPuntoVenta);
	}

	@Override
	public List<Liquidacion_Venta> listaLiquidacionIngresoDetxOficina(
			Date fecha, Integer idOficina) throws Exception {
		return liquidacion_VentaMapper.listaLiquidacionIngresoDetxOficina(fecha, idOficina);
	}

	@Override
	public List<Liquidacion_Venta> listaLiquidacionEgresoDetxOficina()
			throws Exception {
		return liquidacion_VentaMapper.listaLiquidacionEgresoDetxOficina();
	}

	@Override
	public List<Liquidacion_Venta> rptLiquidacionxFPagoOFI(Date fecha,
			Integer idOficina) throws Exception {
		return liquidacion_VentaMapper.rptLiquidacionxFPagoOFI(fecha, idOficina);
	}

	@Override
	public List<Liquidacion_Venta> rptLiquidacionxFPagoOFIEgreso(Date fecha,
			Integer idOficina) throws Exception {
		return liquidacion_VentaMapper.rptLiquidacionxFPagoOFIEgreso(fecha, idOficina);
	}

	@Override
	public List<Liquidacion_Venta> listarLiquidacionVentasxFPagoPV(Date fecha,
			String proceso, Integer IdPuntoVenta) throws Exception {
		return liquidacion_VentaMapper.listarLiquidacionVentasxFPagoPV(fecha, proceso, IdPuntoVenta);
	}

	@Override
	public Double calcularEfectivoVentasxPuntoVenta(Date fecha,
			Integer idPuntoVenta) throws Exception {
		return liquidacion_VentaMapper.calcularEfectivoVentasxPuntoVenta(fecha, idPuntoVenta);
	}

	@Override
	public List<Liquidacion_Venta> listarLiquidacionVentasxFPagoOFI(Date fecha,
			String proceso, Integer idOficina) throws Exception {
		return liquidacion_VentaMapper.listarLiquidacionVentasxFPagoOFI(fecha, proceso, idOficina);
	}

	@Override
	public Double calcularEfectivoVentasxOficina(Date fecha, Integer idOficina)
			throws Exception {
		return liquidacion_VentaMapper.calcularEfectivoVentasxOficina(fecha, idOficina);
	}

	@Override
	public List<Liquidacion_Venta> listarProcesosVenta() throws Exception {
		return liquidacion_VentaMapper.listarProcesosVenta();
	}

	@Override
	public List<Liquidacion_Venta> listarSeriesLiquidacionVentas(Date fecha,
			Integer idPuntoVenta) throws Exception {
		return liquidacion_VentaMapper.listarSeriesLiquidacionVentas(fecha, idPuntoVenta);
	}

	@Override
	public List<Liquidacion_Venta> listarLiquidacionVentasxFPagoPVEgreso(
			Date fecha, String proceso, Integer IdPuntoVenta) throws Exception {
		return liquidacion_VentaMapper.listarLiquidacionVentasxFPagoPVEgreso(fecha, proceso, IdPuntoVenta);
	}

	@Override
	public List<Liquidacion_Venta> listarLiquidacionVentasxFPagoOFIEgreso(
			Date fecha, String proceso, Integer idOficina) throws Exception {
		return liquidacion_VentaMapper.listarLiquidacionVentasxFPagoOFIEgreso(fecha, proceso, idOficina);
	}

	@Override
	public void preliquidarVentasxPuntoVentaCargo(Date fecha,
			Integer IdPuntoVenta) throws Exception {
		liquidacion_VentaMapper.preliquidarVentasxPuntoVentaCargo(fecha, IdPuntoVenta);
	}

	@Override
	public List<Liquidacion_Venta> listarPreLiquidacionesVenta(Date fecha,
			Integer idOficina) throws Exception {
		return liquidacion_VentaMapper.listarPreLiquidacionesVenta(fecha, idOficina);
	}

	@Override
	public void liquidarVentasxOficina(Date fecha, Integer idOficina)
			throws Exception {
		liquidacion_VentaMapper.liquidarVentasxOficina(fecha, idOficina);
	}

	@Override
	public List<Liquidacion_Venta> rptReporteMecanizado(
			Liquidacion_Venta liquidacion_Venta) throws Exception {
		return liquidacion_VentaMapper.rptReporteMecanizado(liquidacion_Venta);
	}

	@Override
	public List<Giro> listarDisgregadoGiroPuntoVenta(Date fecha,
			Integer idPVenta) throws Exception {
		return liquidacion_VentaMapper.listarDisgregadoGiroPuntoVenta(fecha, idPVenta);
	}

	@Override
	public List<Giro> listarDisgregadoGiroOficina(Date fecha, Integer idOficina)
			throws Exception {
		// TODO Auto-generated method stub
		return liquidacion_VentaMapper.listarDisgregadoGiroOficina(fecha, idOficina);
	}

	
}

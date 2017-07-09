package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.SalidaDetalle;
import com.certicom.ittsa.mapper.SalidaDetalleMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class SalidaDetalleService implements SalidaDetalleMapper {

	SalidaDetalleMapper salidaDetalleMapper = (SalidaDetalleMapper)ServiceFinder.findBean("salidaDetalleMapper");

	@Override
	public void registrarSalidaDetalle(SalidaDetalle salidaDetalle) {
		salidaDetalleMapper.registrarSalidaDetalle(salidaDetalle);
	}

	@Override
	public List<SalidaDetalle> listLLegadaProductosxProg(Integer IdProgramacion) {
		return salidaDetalleMapper.listLLegadaProductosxProg(IdProgramacion);
	}

	@Override
	public void actualizarEstadoEntrega(Integer IDSalDet, String flagEntrega,Integer cantConsumida) {
		salidaDetalleMapper.actualizarEstadoEntrega(IDSalDet, flagEntrega,cantConsumida);
	}

	@Override
	public List<SalidaDetalle> listaProductosxSalida(Integer IdSalida) {
		return salidaDetalleMapper.listaProductosxSalida(IdSalida);
	}
	
	
	
}

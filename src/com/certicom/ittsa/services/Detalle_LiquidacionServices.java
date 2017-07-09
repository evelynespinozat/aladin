package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Detalle_Liquidacion;
import com.certicom.ittsa.mapper.Detalle_LiquidacionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class Detalle_LiquidacionServices implements Detalle_LiquidacionMapper{
	
	Detalle_LiquidacionMapper detalle_LiquidacionMapper = (Detalle_LiquidacionMapper)ServiceFinder.findBean("detalle_LiquidacionMapper");

	@Override
	public List<Detalle_Liquidacion> findAll() throws Exception {
		// TODO Auto-generated method stub
		return detalle_LiquidacionMapper.findAll();
	}

	@Override
	public Detalle_Liquidacion findById(Integer idDetalleLiq) throws Exception {
		// TODO Auto-generated method stub
		return detalle_LiquidacionMapper.findById(idDetalleLiq); 
	}

	@Override
	public void eliminarDetalleLiq(Integer idDetalleLiq) throws Exception {
		// TODO Auto-generated method stub
		detalle_LiquidacionMapper.eliminarDetalleLiq(idDetalleLiq); 
	}

	@Override
	public void crearDetalleLiq(Detalle_Liquidacion detalleLiq)
			throws Exception {
		// TODO Auto-generated method stub
		detalle_LiquidacionMapper.crearDetalleLiq(detalleLiq); 
	}

	@Override
	public void actualizarDetalleLiq(Detalle_Liquidacion detalleLiq)
			throws Exception {
		// TODO Auto-generated method stub
		detalle_LiquidacionMapper.actualizarDetalleLiq(detalleLiq); 
	}

	@Override
	public List<Detalle_Liquidacion> findByIdLiquidacion(Integer idLiquidacion)
			throws Exception {
		// TODO Auto-generated method stub
		return detalle_LiquidacionMapper.findByIdLiquidacion(idLiquidacion); 
	}

	@Override
	public void actualizarEstado(Detalle_Liquidacion detalleLiq)
			throws Exception {
		// TODO Auto-generated method stub
		detalle_LiquidacionMapper.actualizarEstado(detalleLiq); 
	}

	@Override
	public Integer countByEstado(Integer idLiquidacion) throws Exception {
		// TODO Auto-generated method stub
		return detalle_LiquidacionMapper.countByEstado(idLiquidacion); 
	}



	
}

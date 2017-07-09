package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Detalle_Liquidacion;
import com.certicom.ittsa.domain.Liquidacion;
import com.certicom.ittsa.mapper.Detalle_LiquidacionMapper;
import com.certicom.ittsa.mapper.LiquidacionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class LiquidacionServices implements LiquidacionMapper{
	
	LiquidacionMapper liquidacionMapper = (LiquidacionMapper)ServiceFinder.findBean("liquidacionMapper");

	@Override
	public List<Liquidacion> findAll() throws Exception {
		// TODO Auto-generated method stub
		return liquidacionMapper.findAll();
	}

	@Override
	public Liquidacion findById(Integer idLiquidacion) throws Exception {
		// TODO Auto-generated method stub
		return liquidacionMapper.findById(idLiquidacion);
	}

	@Override
	public void eliminarLiquidacion(Integer idLiquidacion) throws Exception {
		// TODO Auto-generated method stub
		liquidacionMapper.eliminarLiquidacion(idLiquidacion); 
	}

	@Override
	public void crearLiquidacion(Liquidacion Liquidacion) throws Exception {
		// TODO Auto-generated method stub
		liquidacionMapper.crearLiquidacion(Liquidacion); 
	}

	@Override
	public void actualizarLiquidacion(Liquidacion Liquidacion) throws Exception {
		// TODO Auto-generated method stub
		liquidacionMapper.actualizarLiquidacion(Liquidacion); 
	}

	@Override
	public Liquidacion findByIdProgramacion(Integer idProgramacion)
			throws Exception {
		// TODO Auto-generated method stub
		return liquidacionMapper.findByIdProgramacion(idProgramacion); 
	}

	
	
}

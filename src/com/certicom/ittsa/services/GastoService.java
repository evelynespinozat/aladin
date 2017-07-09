package com.certicom.ittsa.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.certicom.ittsa.domain.Gasto;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.GastoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;


public class GastoService  implements GastoMapper, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	GastoMapper gastoMapper = (GastoMapper)ServiceFinder.findBean("gastoMapper");


	@Override
	public List<Gasto> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarGasto(Integer idgasto) throws Exception {
		gastoMapper.eliminarGasto(idgasto);
	}

	@Override
	public void crearGasto(Gasto gasto) throws Exception 
	{
		gastoMapper.crearGasto(gasto);	
	}

	@Override
	public void actualizarGasto(Gasto gasto) throws Exception {
		// TODO Auto-generated method stub
		gastoMapper.actualizarGasto(gasto);
	}

	@Override
	public List<Gasto> findByProgramacion(Integer programacionId)
			throws Exception {
		return gastoMapper.findByProgramacion(programacionId);
	}

	@Override
	public BigDecimal sumaGastosByProgramacion(Integer programacionId)
			throws Exception {
		return gastoMapper.sumaGastosByProgramacion(programacionId);
	}

}

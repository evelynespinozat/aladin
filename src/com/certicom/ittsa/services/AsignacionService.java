package com.certicom.ittsa.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.certicom.ittsa.domain.Asignacion;
import com.certicom.ittsa.mapper.AsignacionMapper;
import com.certicom.ittsa.mapper.GastoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;


public class AsignacionService  implements AsignacionMapper, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AsignacionMapper asignacionMapper = (AsignacionMapper)ServiceFinder.findBean("asignacionMapper");

	@Override
	public List<Asignacion> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarAsignacion(Integer idAsignacion) throws Exception {
		// TODO Auto-generated method stub
		asignacionMapper.eliminarAsignacion(idAsignacion);
	}

	@Override
	public void crearAsignacion(Asignacion asignacion) throws Exception {
		// TODO Auto-generated method stub
		asignacionMapper.crearAsignacion(asignacion);
	}

	@Override
	public void actualizarAsignacion(Asignacion asignacion) throws Exception {
		// TODO Auto-generated method stub
		asignacionMapper.actualizarAsignacion(asignacion);
	}

	@Override
	public Asignacion findByProgramacion(Integer idProgramacion)
			throws Exception {
		return asignacionMapper.findByProgramacion(idProgramacion);
	}

	@Override
	public BigDecimal sumaAsignacionByProgramacion(Integer programacionId)
			throws Exception {
		// TODO Auto-generated method stub
		return asignacionMapper.sumaAsignacionByProgramacion(programacionId);
	}

}

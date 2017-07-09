package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.GuiaRemision;
import com.certicom.ittsa.mapper.GuiaRemisionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class GuiaRemisionService implements GuiaRemisionMapper{
	
	GuiaRemisionMapper guiaRemisionMapper = (GuiaRemisionMapper)ServiceFinder.findBean("guiaRemisionMapper");

	@Override
	public List<GuiaRemision> findAll() throws Exception {
		return guiaRemisionMapper.findAll();
	}

	@Override
	public GuiaRemision findById(Integer idGuiaRemision) throws Exception {
		return guiaRemisionMapper.findById(idGuiaRemision);
	}

	@Override
	public void eliminarGuiaRemision(Integer idGuiaRemision) throws Exception {
		guiaRemisionMapper.eliminarGuiaRemision(idGuiaRemision);
	}

	@Override
	public void crearGuiaRemision(GuiaRemision guiaRemision) throws Exception {
		guiaRemisionMapper.crearGuiaRemision(guiaRemision);
	}

	@Override
	public void actualizarGuiaRemision(GuiaRemision guiaRemision)
			throws Exception {
		guiaRemisionMapper.actualizarGuiaRemision(guiaRemision);
	}

	@Override
	public Integer findLast() throws Exception {
		return guiaRemisionMapper.findLast();
	}

}

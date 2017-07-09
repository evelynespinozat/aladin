package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Cese;
import com.certicom.ittsa.mapper.CeseMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CeseService implements CeseMapper{
	
	CeseMapper ceseMapper = (CeseMapper)ServiceFinder.findBean("ceseMapper");

	@Override
	public Cese findById(Integer idCese) throws Exception {
		return ceseMapper.findById(idCese);
	}

	@Override
	public void eliminarCese(Integer idCese) throws Exception {
		ceseMapper.eliminarCese(idCese);
	}

	@Override
	public void crearCese(Cese cese) throws Exception {
		ceseMapper.crearCese(cese);
	}

	@Override
	public List<Cese> listxPiloto(Integer IdPiloto) throws Exception {
		return ceseMapper.listxPiloto(IdPiloto);
	}

	@Override
	public List<Cese> listxTerramoza(Integer IdTerramoza) throws Exception {
		return ceseMapper.listxTerramoza(IdTerramoza);
	}

	
}

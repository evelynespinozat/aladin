package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Evaluacion;
import com.certicom.ittsa.domain.Papeleta;
import com.certicom.ittsa.mapper.EvaluacionMapper;
import com.certicom.ittsa.mapper.PapeletaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PapeletaService implements PapeletaMapper{

	PapeletaMapper papeletaMapper = (PapeletaMapper)ServiceFinder.findBean("papeletaMapper");
	
	@Override
	public List<Papeleta> findAll() throws Exception {
		return papeletaMapper.findAll();
	}

	@Override
	public Integer findLast() throws Exception {
		return papeletaMapper.findLast();
	}

	@Override
	public Papeleta findById(Integer papeletaid) throws Exception {
		return papeletaMapper.findById(papeletaid);
	}

	@Override
	public void eliminarPapeleta(Integer papeletaid) throws Exception {
		papeletaMapper.eliminarPapeleta(papeletaid);
	}

	@Override
	public void crearPapeleta(Papeleta papeleta) throws Exception {
		papeletaMapper.crearPapeleta(papeleta);
	}

	@Override
	public List<Papeleta> findByPilotoId(Integer idPiloto) throws Exception {
		return papeletaMapper.findByPilotoId(idPiloto);
	}


}

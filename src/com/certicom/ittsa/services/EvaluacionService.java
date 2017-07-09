package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Evaluacion;
import com.certicom.ittsa.mapper.EvaluacionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class EvaluacionService implements EvaluacionMapper{

	EvaluacionMapper evaluacionMapper = (EvaluacionMapper)ServiceFinder.findBean("evaluacionMapper");
	
	@Override
	public List<Evaluacion> findAll() throws Exception {
		return evaluacionMapper.findAll();
	}

	@Override
	public Integer findLast() throws Exception {
		return evaluacionMapper.findLast();
	}

	@Override
	public Evaluacion findById(Integer evaluacionId) throws Exception {
		return evaluacionMapper.findById(evaluacionId);
	}

	@Override
	public void eliminarEvaluacion(Integer evaluacionId) throws Exception {
		System.out.println("el formulario tomo el siguiente valor " + evaluacionId);
		evaluacionMapper.eliminarEvaluacion(evaluacionId);
	}

	@Override
	public List<Evaluacion> findByPilotoId(Integer idPiloto) throws Exception {
		return evaluacionMapper.findByPilotoId(idPiloto);
	}

	@Override
	public List<Evaluacion> findByTerramozaId(Integer idTerramoza)
			throws Exception {
		// TODO Auto-generated method stub
		return evaluacionMapper.findByTerramozaId(idTerramoza); 
	}

	@Override
	public void crearEvaluacion_Piloto(Evaluacion evaluacion) throws Exception {
		// TODO Auto-generated method stub
		evaluacionMapper.crearEvaluacion_Piloto(evaluacion); 
	}

	@Override
	public void crearEvaluacion_Terramoza(Evaluacion evaluacion)
			throws Exception {
		// TODO Auto-generated method stub
		evaluacionMapper.crearEvaluacion_Terramoza(evaluacion); 
	}


}

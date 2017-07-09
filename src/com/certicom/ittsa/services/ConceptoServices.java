package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Concepto;
import com.certicom.ittsa.mapper.ConceptoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ConceptoServices implements ConceptoMapper{
	
	ConceptoMapper conceptoMapper = (ConceptoMapper)ServiceFinder.findBean("conceptoMapper");

	@Override
	public List<Concepto> findAll() throws Exception {
		return conceptoMapper.findAll();
	}

	@Override
	public Concepto findById(Integer IdConcepto) throws Exception {
		return conceptoMapper.findById(IdConcepto);
	}

	@Override
	public void registrarConcepto(Concepto concepto) throws Exception {
		conceptoMapper.registrarConcepto(concepto);
	}

	@Override
	public void actualizarConcepto(Concepto concepto) throws Exception {
		conceptoMapper.actualizarConcepto(concepto);
		
	}

	@Override
	public void eliminarConcepto(Integer IdConcepto) throws Exception {
		conceptoMapper.eliminarConcepto(IdConcepto);
	}



	





}

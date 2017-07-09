package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Embarque;
import com.certicom.ittsa.mapper.EmbarqueMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class EmbarqueService implements EmbarqueMapper{
	
	EmbarqueMapper embarqueMapper = (EmbarqueMapper)ServiceFinder.findBean("embarqueMapper");

	@Override
	public List<Embarque> findAll() throws Exception {

		return embarqueMapper.findAll();
	}

	@Override
	public Embarque findById(Integer embarqueId) throws Exception {
		// TODO Auto-generated method stub
		return embarqueMapper.findById(embarqueId);
	}

	@Override
	public void eliminarEmbarque(Integer embarqueId) throws Exception {
		// TODO Auto-generated method stub
		embarqueMapper.eliminarEmbarque(embarqueId);
	}

	@Override
	public void crearEmbarque(Embarque embarque) throws Exception {
		// TODO Auto-generated method stub
		embarqueMapper.crearEmbarque(embarque);
	}

	@Override
	public void actualizarEmbarque(Embarque embarque) throws Exception {
		// TODO Auto-generated method stub
		embarqueMapper.actualizarEmbarque(embarque);
	}

	
	

}

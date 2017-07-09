package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Medida;
import com.certicom.ittsa.mapper.MedidaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class MedidaService implements MedidaMapper{
	
	MedidaMapper medidaMapper = (MedidaMapper)ServiceFinder.findBean("medidaMapper");

	@Override
	public List<Medida> findAll() throws Exception {
		return medidaMapper.findAll();
	}

	@Override
	public Medida findById(String CodigoMedida) throws Exception {
		return medidaMapper.findById(CodigoMedida);
	}

	@Override
	public void eliminarMedida(String CodigoMedida) throws Exception {
		medidaMapper.eliminarMedida(CodigoMedida);
	}

	@Override
	public void crearMedida(Medida medida) throws Exception {
		medidaMapper.crearMedida(medida);
	}

	@Override
	public void actualizarMedida(Medida medida) throws Exception {
		medidaMapper.actualizarMedida(medida);
	}

	@Override
	public List<Medida> listaMedidasActivas() throws Exception {
		return medidaMapper.listaMedidasActivas();
	}

	

}

package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Mecanico;
import com.certicom.ittsa.mapper.MecanicoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class MecanicoService implements MecanicoMapper{
	
	MecanicoMapper mecanicoMapper = (MecanicoMapper)ServiceFinder.findBean("mecanicoMapper");

	@Override
	public List<Mecanico> findAll() throws Exception {

		return mecanicoMapper.findAll();
	}

	@Override
	public Mecanico findById(Integer agenciaID) throws Exception {

		return mecanicoMapper.findById(agenciaID);
	}

	@Override
	public void eliminarMecanico(Integer agenciaID) throws Exception {

		mecanicoMapper.eliminarMecanico(agenciaID);
	}

	@Override
	public void crearMecanico(Mecanico agencia) throws Exception {

		mecanicoMapper.crearMecanico(agencia);
	}

	@Override
	public void actualizarMecanico(Mecanico agencia) throws Exception {
		mecanicoMapper.actualizarMecanico(agencia);
		
	}

	@Override
	public List<Mecanico> listaMecanicosActivas() throws Exception {

		return mecanicoMapper.listaMecanicosActivas();
	}

}

package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.ConsumoCombustible;
import com.certicom.ittsa.mapper.ConsumoCombustibleMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ConsumoCombustibleService implements ConsumoCombustibleMapper{
	
	ConsumoCombustibleMapper consumoCombustibleMapper = (ConsumoCombustibleMapper)ServiceFinder.findBean("consumoCombustibleMapper");

	@Override
	public List<ConsumoCombustible> findAll() throws Exception {

		return consumoCombustibleMapper.findAll();
	}

	@Override
	public ConsumoCombustible findById(Integer agenciaID) throws Exception {

		return consumoCombustibleMapper.findById(agenciaID);
	}

	@Override
	public void eliminarConsumoCombustible(Integer agenciaID) throws Exception {

		consumoCombustibleMapper.eliminarConsumoCombustible(agenciaID);
	}

	@Override
	public void crearConsumoCombustible(ConsumoCombustible agencia) throws Exception {

		consumoCombustibleMapper.crearConsumoCombustible(agencia);
	}

	@Override
	public void actualizarConsumoCombustible(ConsumoCombustible agencia) throws Exception {
		consumoCombustibleMapper.actualizarConsumoCombustible(agencia);
		
	}

	@Override
	public int verificarProgramacion(Integer idProgramacion) throws Exception {
		// TODO Auto-generated method stub
		return consumoCombustibleMapper.verificarProgramacion(idProgramacion);
	}

	@Override
	public List<ConsumoCombustible> listarProgramacionescnKilometro(Integer idBus)
			throws Exception {
		// TODO Auto-generated method stub
		return consumoCombustibleMapper.listarProgramacionescnKilometro(idBus);
	}

	@Override
	public List<ConsumoCombustible> listarConsumoCombustiblexFechas(
			Date fecInicio, Date fecFin, Integer idBus) {
		// TODO Auto-generated method stub
		return consumoCombustibleMapper.listarConsumoCombustiblexFechas(fecInicio, fecFin, idBus);
	}


}

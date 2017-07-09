package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Tarifa;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.TarifaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TarifaServices implements TarifaMapper{
	
	TarifaMapper tarifaMapper = (TarifaMapper)ServiceFinder.findBean("tarifaMapper");

	@Override
	public List<Tarifa> findAll() throws Exception {
		// TODO Auto-generated method stub
		return tarifaMapper.findAll();
	}

	@Override
	public Tarifa findById(Integer idTarifa) throws Exception {
		// TODO Auto-generated method stub
		return tarifaMapper.findById(idTarifa);
	}

	@Override
	public void eliminarTarifa(Integer IdTarifa) throws Exception {
		// TODO Auto-generated method stub
		tarifaMapper.eliminarTarifa(IdTarifa);
	}

	@Override
	public void crearTarifaGiro(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		tarifaMapper.crearTarifaGiro(tarifa);
	}

	@Override
	public void actualizarTarifaGiro(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		tarifaMapper.actualizarTarifaGiro(tarifa); 
	}

	@Override
	public List<Tarifa> findAllGiros() throws Exception {
		// TODO Auto-generated method stub
		return tarifaMapper.findAllGiros();
	}

	@Override
	public void actualizarEstado(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		tarifaMapper.actualizarEstado(tarifa); 
	}

	@Override
	public List<Tarifa> findGirosByTipo(Integer idAgencia,String tipo) throws Exception {
		// TODO Auto-generated method stub
		return tarifaMapper.findGirosByTipo(idAgencia,tipo); 
	}

	@Override
	public void crearTarifaEncomienda(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		tarifaMapper.crearTarifaEncomienda(tarifa); 
	}

	@Override
	public void actualizarTarifaEncomienda(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		tarifaMapper.actualizarTarifaEncomienda(tarifa);
	}

	@Override
	public List<Tarifa> findRepartoByTipo(Integer idAgencia, Integer idDestino,
			String tipo) throws Exception {
		// TODO Auto-generated method stub
		return tarifaMapper.findRepartoByTipo(idAgencia, idDestino, tipo);
	}

	@Override
	public Tarifa findByDestProvincia(Integer idDestino, String sdistrito)
			throws Exception {
		// TODO Auto-generated method stub
		return tarifaMapper.findByDestProvincia(idDestino, sdistrito);
	}

	@Override
	public void crearTarifaReparto(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		tarifaMapper.crearTarifaReparto(tarifa);
	}

	@Override
	public Tarifa findTarifaEncomiendaByOrigenDestino(Integer idOrigen,
			Integer idDestino) throws Exception {
		// TODO Auto-generated method stub
		return tarifaMapper.findTarifaEncomiendaByOrigenDestino(idOrigen, idDestino); 
	}

	@Override
	public Tarifa findByOriDestProvincia(Integer IdOrigen, Integer idDestino,
			String sdistrito) throws Exception {
		return tarifaMapper.findByOriDestProvincia(IdOrigen, idDestino, sdistrito);
	}


}

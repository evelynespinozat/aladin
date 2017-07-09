package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.mapper.DestinoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class DestinoServices implements DestinoMapper{
	
	DestinoMapper destinoMapper = (DestinoMapper)ServiceFinder.findBean("destinoMapper");

	@Override
	public List<Destino> findAll() throws Exception {
		return destinoMapper.findAll();
	}

	@Override
	public Destino findById(Integer IdDestino) throws Exception {
		return destinoMapper.findById(IdDestino);
	}

	@Override
	public void registrarDestino(Destino destino) throws Exception {
		destinoMapper.registrarDestino(destino);
	}

	@Override
	public void actualizarDestino(Destino destino) throws Exception {
		destinoMapper.actualizarDestino(destino);
	}

	@Override
	public void eliminarDestino(Integer IdDestino) throws Exception {
		destinoMapper.eliminarDestino(IdDestino);
	}

	@Override
	public List<Destino> obtenerDestino(Integer idOrigen) throws Exception {
		return destinoMapper.obtenerDestino(idOrigen);
	}

	@Override
	public Destino obtenerRecorridoAproximado(Integer origen, Integer destino)
			throws Exception {
		// TODO Auto-generated method stub
		return destinoMapper.obtenerRecorridoAproximado(origen, destino);
	}

	@Override
	public Integer validarDestinoRepetido(Integer origen, Integer destino)
			throws Exception {
		return destinoMapper.validarDestinoRepetido(origen, destino);
	}


}

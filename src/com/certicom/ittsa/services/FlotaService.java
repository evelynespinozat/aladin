package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.mapper.FlotaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class FlotaService implements FlotaMapper{
	
	FlotaMapper flotaMapper = (FlotaMapper)ServiceFinder.findBean("flotaMapper");

	@Override
	public List<Flota> findAll() throws Exception {

		return flotaMapper.findAll();
	}

	@Override
	public Flota findById(Integer idBus) throws Exception {

		return flotaMapper.findById(idBus);
	}

	@Override
	public void eliminarFlota(Integer idBus) throws Exception {

		flotaMapper.eliminarFlota(idBus);
	}

	@Override
	public void crearFlota(Flota flota) throws Exception {

		flotaMapper.crearFlota(flota);
	}

	@Override
	public void actualizarFlota(Flota flota) throws Exception {
		flotaMapper.actualizarFlota(flota);
		
	}

	@Override
	public List<Flota> listaFlotaActivas() throws Exception {

		return flotaMapper.listaFlotaActivas();
	}

	@Override
	public List<Flota> findByIdClase(Integer idClase) throws Exception {
		return flotaMapper.findByIdClase(idClase); 
	}

	@Override
	public List<Flota> findAll2() throws Exception {
		return flotaMapper.findAll2(); 
	}

	@Override
	public void actualizarPiloto_Copiloto(Flota flota) throws Exception {
		flotaMapper.actualizarPiloto_Copiloto(flota); 
	}

	@Override
	public void actualizarPiloto(Integer idPiloto, Integer idBus)
			throws Exception {
		flotaMapper.actualizarPiloto(idPiloto, idBus); 
	}

	@Override
	public void actualizarCopiloto(Integer idCopiloto, Integer idBus)
			throws Exception {
		flotaMapper.actualizarCopiloto(idCopiloto, idBus); 
	}

	@Override
	public void aumentarKilometraje(Flota flota) throws Exception {
		flotaMapper.aumentarKilometraje(flota);
	}

	@Override
	public void aumentarKilometrajeAproximado(Integer idBus, Integer kmDistancia)
			throws Exception {
		flotaMapper.aumentarKilometrajeAproximado(idBus, kmDistancia);
	}

	@Override
	public void retirarKilometrajeAproximado(Integer idBus, Integer kmDistancia)
			throws Exception {
		flotaMapper.retirarKilometrajeAproximado(idBus, kmDistancia);
	}

	@Override
	public void actualizarKmAproximado(Integer idBus, Integer editKm)
			throws Exception {
		flotaMapper.actualizarKmAproximado(idBus, editKm);
	}

	@Override
	public List<Flota> busesxCambio(FlotaAutoparte flotaAutoparte)
			throws Exception {
		return flotaMapper.busesxCambio(flotaAutoparte);
	}

	@Override
	public List<Flota> busesxCambioAprox(FlotaAutoparte flotaAutoparte)
			throws Exception {
		return flotaMapper.busesxCambioAprox(flotaAutoparte);
	}

	@Override
	public List<Flota> busesxCambioAll() throws Exception {
		return flotaMapper.busesxCambioAll();
	}

	@Override
	public List<Flota> listaFlotaActivasOrderNum() throws Exception {
		return flotaMapper.listaFlotaActivasOrderNum();
	}

	@Override
	public List<Flota> obtenerFlotaAsociadas(Integer idClase, Integer Origen, Integer Destino) throws Exception {
		return flotaMapper.obtenerFlotaAsociadas(idClase,Origen,Destino);
	}

	@Override
	public void actualizarRecorrido(Integer idBus, Integer recorrido)
			throws Exception {
		flotaMapper.actualizarRecorrido(idBus, recorrido);
	}

}

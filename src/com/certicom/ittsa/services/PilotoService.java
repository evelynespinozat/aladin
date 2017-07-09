package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.PilotoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PilotoService implements PilotoMapper{
	
	PilotoMapper pilotoMapper = (PilotoMapper)ServiceFinder.findBean("pilotoMapper");

	@Override
	public List<Piloto> findAll() throws Exception {
		return pilotoMapper.findAll();
	}

	@Override
	public Piloto findById(Integer idPiloto) throws Exception {
		return pilotoMapper.findById(idPiloto);
	}

	@Override
	public void eliminarPiloto(Integer idPiloto) throws Exception {
		pilotoMapper.eliminarPiloto(idPiloto);
	}

	@Override
	public void crearPiloto(Piloto piloto) throws Exception {
		pilotoMapper.crearPiloto(piloto);
	}

	@Override
	public void actualizarPiloto(Piloto piloto) throws Exception {
		pilotoMapper.actualizarPiloto(piloto);
	}

	@Override
	public List<Piloto> listaPilotoActivas() throws Exception {

		return pilotoMapper.listaPilotoActivas();
	}

	@Override
	public void actualizarPilotoLaboral(Piloto piloto) throws Exception {
		pilotoMapper.actualizarPilotoLaboral(piloto); 
	}

	@Override
	public void actualizarPilotoFamiliar(Piloto piloto) throws Exception {
		pilotoMapper.actualizarPilotoFamiliar(piloto);
	}

	@Override
	public Integer findLast() throws Exception {
		return pilotoMapper.findLast();
	}

	@Override
	public Piloto findByDNI(String dni) throws Exception {
		return pilotoMapper.findByDNI(dni);
	}

	@Override
	public void actualizarPilotoSnFoto(Piloto piloto) throws Exception {
		pilotoMapper.actualizarPilotoSnFoto(piloto);
	}

	@Override
	public List<Piloto> findByEstado() throws Exception {
		return pilotoMapper.findByEstado(); 
	}

	@Override
	public Integer verificarDniCarnetRepetidos(String dni) throws Exception {
		return pilotoMapper.verificarDniCarnetRepetidos(dni);
	}

	@Override
	public void actualizarAsignacion(Integer idPiloto, Boolean asignado ) throws Exception {
		pilotoMapper.actualizarAsignacion(idPiloto, asignado);
	}

	@Override
	public List<Piloto> pilotosDisponibles() throws Exception {
		return pilotoMapper.pilotosDisponibles();
	}

	@Override
	public List<Piloto> copilotosDisponibles() throws Exception {
		// TODO Auto-generated method stub
		return pilotoMapper.copilotosDisponibles();
	}
	
}

package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Turno;
import com.certicom.ittsa.mapper.TurnoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TurnoService implements TurnoMapper{
	
	TurnoMapper turnoMapper = (TurnoMapper)ServiceFinder.findBean("turnoMapper");

	@Override
	public List<Turno> findAll() throws Exception {

		return turnoMapper.findAll();
	}

	@Override
	public Turno findById(Integer idTurno) throws Exception {
		return turnoMapper.findById(idTurno);
	}

	@Override
	public void eliminarTurno(Integer idTurno) throws Exception {
		turnoMapper.eliminarTurno(idTurno);
	}

	@Override
	public void crearTurno(Turno turno) throws Exception {
		turnoMapper.crearTurno(turno);
	}

	@Override
	public void actualizarTurno(Turno turno) throws Exception {
		turnoMapper.actualizarTurno(turno);
		
	}

	@Override
	public List<Turno> listarTurnoActivos() throws Exception {
		// TODO Auto-generated method stub
		return turnoMapper.listarTurnoActivos();
	}





}

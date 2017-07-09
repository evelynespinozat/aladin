package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.OficinaTurno;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.OficinaTurnoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class OficinaTurnoService implements OficinaTurnoMapper{
	
	OficinaTurnoMapper oficinaTurnoMapper = (OficinaTurnoMapper)ServiceFinder.findBean("oficinaTurnoMapper");

	@Override
	public List<OficinaTurno> findAll() throws Exception {

		return oficinaTurnoMapper.findAll();
	}

	@Override
	public OficinaTurno findById(Integer agenciaID) throws Exception {

		return oficinaTurnoMapper.findById(agenciaID);
	}

	@Override
	public void eliminarOficinaTurno(Integer agenciaID) throws Exception {

		oficinaTurnoMapper.eliminarOficinaTurno(agenciaID);
	}

	@Override
	public void crearOficinaTurno(OficinaTurno oficinaTurno) throws Exception {

		oficinaTurnoMapper.crearOficinaTurno(oficinaTurno);
	}

	@Override
	public void actualizarOficinaTurno(OficinaTurno oficinaTurno) throws Exception {
		
		oficinaTurnoMapper.actualizarOficinaTurno(oficinaTurno);		
	}

	@Override
	public List<OficinaTurno> listaOficinaTurno() throws Exception {
	
		return oficinaTurnoMapper.listaOficinaTurno();
	}

	@Override
	public Integer cantTurnAgencia(Integer idTurno) throws Exception {
		return oficinaTurnoMapper.cantTurnAgencia(idTurno);
	}

}

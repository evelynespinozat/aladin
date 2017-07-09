package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.mapper.TripulacionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TripulacionServices implements TripulacionMapper{
	
	TripulacionMapper tripulacionMapper = (TripulacionMapper)ServiceFinder.findBean("tripulacionMapper");

	@Override
	public List<Tripulacion> findAll() throws Exception {
		// TODO Auto-generated method stub
		return tripulacionMapper.findAll();
	}

	@Override
	public Tripulacion findById(Integer idTripulacion) throws Exception {
		// TODO Auto-generated method stub
		return tripulacionMapper.findById(idTripulacion);
	}

	@Override
	public void eliminarTripulacion(Integer p_idTripulacion) throws Exception {
		// TODO Auto-generated method stub
		tripulacionMapper.eliminarTripulacion(p_idTripulacion);
	}

	@Override
	public void crearTripulacion(Tripulacion tripulacion) throws Exception {
		// TODO Auto-generated method stub
		tripulacionMapper.crearTripulacion(tripulacion); 
	}

	@Override
	public Tripulacion findByIdProgramacion(Integer idProgramacion)
			throws Exception {
		// TODO Auto-generated method stub
		return tripulacionMapper.findByIdProgramacion(idProgramacion); 
	}

	@Override
	public void actualizarIdBus(Integer p_idTripulacion, Integer p_idBus)
			throws Exception {
		// TODO Auto-generated method stub
		tripulacionMapper.actualizarIdBus(p_idTripulacion, p_idBus); 
	}

	@Override
	public void actualizarIdPiloto(Integer p_idTripulacion, Integer p_idPiloto,boolean flag)
			throws Exception {
		// TODO Auto-generated method stub
		tripulacionMapper.actualizarIdPiloto(p_idTripulacion, p_idPiloto,flag); 
	}

	@Override
	public void actualizarIdCopiloto(Integer p_idTripulacion, Integer p_idPiloto,boolean flag)
			throws Exception {
		// TODO Auto-generated method stub
		tripulacionMapper.actualizarIdCopiloto(p_idTripulacion, p_idPiloto,flag); 
	}

	@Override
	public void actualizarTerramoza1(Integer IdTripulacion, Integer IdTerramoza)
			throws Exception {
		tripulacionMapper.actualizarTerramoza1(IdTripulacion, IdTerramoza);
	}

	@Override
	public void actualizarTerramoza2(Integer IdTripulacion, Integer IdTerramoza2)
			throws Exception {
		tripulacionMapper.actualizarTerramoza2(IdTripulacion, IdTerramoza2);
	}

	@Override
	public void eliminarTripulacionXProgramacion(Integer idprogramacion)
			throws Exception {
		// TODO Auto-generated method stub
		tripulacionMapper.eliminarTripulacionXProgramacion(idprogramacion);
	}

	@Override
	public Tripulacion listarTripulacionxProg(Integer idprogramacion)
			throws Exception {
		return tripulacionMapper.listarTripulacionxProg(idprogramacion);
	}

	
	

}

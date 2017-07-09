package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.HistorialMantenimiento;
import com.certicom.ittsa.mapper.HistorialMantenimientoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class HistorialMantenimientoService implements HistorialMantenimientoMapper{
	
	HistorialMantenimientoMapper historialMantenimientoMapper = (HistorialMantenimientoMapper)ServiceFinder.findBean("historialMantenimientoMapper");

	@Override
	public List<HistorialMantenimiento> findAll() throws Exception {

		return historialMantenimientoMapper.findAll();
	}

	@Override
	public HistorialMantenimiento findById(Integer idHistoMant) throws Exception {

		return historialMantenimientoMapper.findById(idHistoMant);
	}

	@Override
	public void eliminarHistorialMantenimiento(Integer idHistoMant) throws Exception {

		historialMantenimientoMapper.eliminarHistorialMantenimiento(idHistoMant);
	}

	@Override
	public void crearHistorialMantenimiento(HistorialMantenimiento historialMantenimiento) throws Exception {

		historialMantenimientoMapper.crearHistorialMantenimiento(historialMantenimiento);
	}

	@Override
	public void actualizarHistorialMantenimiento(HistorialMantenimiento historialMantenimiento) throws Exception {
		historialMantenimientoMapper.actualizarHistorialMantenimiento(historialMantenimiento);
	}

	@Override
	public Integer findByLastId() throws Exception {
		// TODO Auto-generated method stub
		return historialMantenimientoMapper.findByLastId();
	}
	
	@Override
	public void actualizarObsHistorialMantenimiento(HistorialMantenimiento historialMantenimiento) throws Exception {
		historialMantenimientoMapper.actualizarObsHistorialMantenimiento(historialMantenimiento);
	}

	@Override
	public List<HistorialMantenimiento> obtenerAutopartesConMecanico(Flota flota)
			throws Exception {
		return historialMantenimientoMapper.obtenerAutopartesConMecanico(flota);
	}

	@Override
	public List<HistorialMantenimiento> obtenerAutoConMecanicoCorrectivo(
			Flota flota) throws Exception {
		// TODO Auto-generated method stub
		return historialMantenimientoMapper.obtenerAutoConMecanicoCorrectivo(flota);
	}

	@Override
	public List<HistorialMantenimiento> obtenerHistorialFlota(Flota flota)
			throws Exception {
		// TODO Auto-generated method stub
		return historialMantenimientoMapper.obtenerHistorialFlota(flota);
	}

	@Override
	public List<HistorialMantenimiento> obtenerMecanicoAsignado(
			HistorialMantenimiento historialMantenimiento) throws Exception {
		// TODO Auto-generated method stub
		return historialMantenimientoMapper.obtenerMecanicoAsignado(historialMantenimiento);
	}
	

}

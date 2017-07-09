package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.PlantillaDetalle;
import com.certicom.ittsa.mapper.PlantillaDetalleMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PlantillaDetalleService implements PlantillaDetalleMapper{
	
	PlantillaDetalleMapper plantillaDetalleMapper = (PlantillaDetalleMapper)ServiceFinder.findBean("plantillaDetalleMapper");

	@Override
	public void registarPlantillaDetalle(PlantillaDetalle plantillaDetalle) {
		plantillaDetalleMapper.registarPlantillaDetalle(plantillaDetalle);
	}

	@Override
	public List<PlantillaDetalle> listarPlantillaDetalle(Integer IdPlantilla) {
		return plantillaDetalleMapper.listarPlantillaDetalle(IdPlantilla);
	}

	@Override
	public void eliminarPlantillaDetalle(Integer IdPlanDet) {
		plantillaDetalleMapper.eliminarPlantillaDetalle(IdPlanDet);
	}

	@Override
	public Integer cantProductoxPlantilla(PlantillaDetalle plantillaDetalle) {
		return plantillaDetalleMapper.cantProductoxPlantilla(plantillaDetalle);
	}

	@Override
	public List<PlantillaDetalle> listarProductosxPlantilla(
			PlantillaDetalle plantillaDetalle) {
		return plantillaDetalleMapper.listarProductosxPlantilla(plantillaDetalle);
	}

	@Override
	public void actualizarCantidad(Integer IdPlanDet, Integer cantDefecto) {
		plantillaDetalleMapper.actualizarCantidad(IdPlanDet, cantDefecto);
	}
	



	

}

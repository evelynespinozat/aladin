package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Plantilla;
import com.certicom.ittsa.mapper.PlantillaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PlantillaService implements PlantillaMapper{
	
	PlantillaMapper plantillaMapper = (PlantillaMapper)ServiceFinder.findBean("plantillaMapper");

	@Override
	public List<Plantilla> findAll() throws Exception {
		return plantillaMapper.findAll();
	}

	@Override
	public Plantilla findById(Integer IdPlantilla) throws Exception {
		return plantillaMapper.findById(IdPlantilla);
	}

	@Override
	public void eliminarPlantilla(Integer IdPlantilla) throws Exception {
		plantillaMapper.eliminarPlantilla(IdPlantilla);
	}

	@Override
	public void crearPlantilla(Plantilla plantilla) throws Exception {
		plantillaMapper.crearPlantilla(plantilla);
	}

	@Override
	public void actualizarPlantilla(Plantilla plantilla) throws Exception {
		plantillaMapper.actualizarPlantilla(plantilla);
	}
	
	@Override
	public Integer cantProdxPlantilla(Integer idPlantilla) {
		return plantillaMapper.cantProdxPlantilla(idPlantilla);
	}

	
	
	

}

package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Asiento;
import com.certicom.ittsa.mapper.AsientoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class AsientoServices implements AsientoMapper{
	
	AsientoMapper asientoMapper = (AsientoMapper)ServiceFinder.findBean("asientoMapper");

	@Override
	public List<Asiento> findAll() throws Exception {
		return asientoMapper.findAll();
	}

	@Override
	public Asiento findById(Integer p_IdAsiento) throws Exception {
		return asientoMapper.findById(p_IdAsiento);
	}

	@Override
	public void registrarAsientos(Asiento asiento) throws Exception {
		asientoMapper.registrarAsientos(asiento);
	}

	@Override
	public void actualizarAsientos(Asiento asiento) throws Exception {
		asientoMapper.actualizarAsientos(asiento);
	}

	@Override
	public void eliminarAsientos(Integer IdAsiento) throws Exception {
		asientoMapper.eliminarAsientos(IdAsiento);
	}

	@Override
	public void eliminarAsientosxClase(Integer IdClase) throws Exception {
		asientoMapper.eliminarAsientosxClase(IdClase);
		
	}

	@Override
	public Integer findByClase(Integer p_IdClase) throws Exception {
		return asientoMapper.findByClase(p_IdClase);
	}

	@Override
	public List<Asiento> findAsientoByClase(Integer p_IdClase,Integer piso) throws Exception {
		return asientoMapper.findAsientoByClase(p_IdClase, piso);
	}

	
	@Override
	public void actualizarVisibilidad(Boolean visible, Boolean estado,
			Integer IdAsiento) throws Exception {
		asientoMapper.actualizarVisibilidad(visible, estado, IdAsiento);
	}

	@Override
	public void actualizarAsiento32(Integer numero,Boolean visible, Integer nroOrden,
			Integer piso, Integer idClase) throws Exception {
		asientoMapper.actualizarAsiento32(numero,visible, nroOrden, piso, idClase);
	}

	@Override
	public void actualizarOrdenAsiento(Integer numeroOrden, Integer idAsiento)
			throws Exception {
		asientoMapper.actualizarOrdenAsiento(numeroOrden, idAsiento);
		
	}

    public List<Asiento> findAsientoByProgramacion(Integer idProgramacion, Integer piso) throws Exception {
        return asientoMapper.findAsientoByProgramacion(idProgramacion, piso);
    }


}

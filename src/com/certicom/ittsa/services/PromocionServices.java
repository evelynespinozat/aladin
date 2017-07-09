package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Promocion;
import com.certicom.ittsa.mapper.PromocionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PromocionServices implements PromocionMapper {

	PromocionMapper promocionMapper = (PromocionMapper) ServiceFinder.findBean("promocionMapper");

	@Override
	public List<Promocion> findAll() throws Exception {
		return promocionMapper.findAll();
	}

	@Override
	public Promocion findById(Integer IdPromocion) throws Exception {
		return promocionMapper.findById(IdPromocion);
	}

	@Override
	public void registrarPromocion(Promocion promocion) throws Exception {
		promocionMapper.registrarPromocion(promocion);

	}

	@Override
	public void actualizarPromocion(Promocion promocion) throws Exception {
		promocionMapper.actualizarPromocion(promocion);
	}

	@Override
	public void eliminarPromocion(Integer IdPromocion) throws Exception {
		promocionMapper.eliminarPromocion(IdPromocion);

	}

	@Override
	public Promocion findByServicioIda(Integer idservicioida) throws Exception {
		return promocionMapper.findByServicioIda(idservicioida);
	}

	@Override
	public Promocion findByServicioIdaVuelta(Integer idservicioida)
			throws Exception {
		return promocionMapper.findByServicioIdaVuelta(idservicioida);
	}

	@Override
	public List<Promocion> listPromocionesByFiltros(Integer origen,
			Integer destino) {
		// TODO Auto-generated method stub
		return promocionMapper.listPromocionesByFiltros(origen, destino);
	}

}

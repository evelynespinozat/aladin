package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.mapper.FlotaAutoparteMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class FlotaAutoparteService implements FlotaAutoparteMapper{
	
	FlotaAutoparteMapper flotaAutoparteMapper = (FlotaAutoparteMapper)ServiceFinder.findBean("flotaAutoparteMapper");

	@Override
	public void eliminarFlotaAutoparte(Integer idFlotaAuto) throws Exception {

		flotaAutoparteMapper.eliminarFlotaAutoparte(idFlotaAuto);
	}

	@Override
	public void crearFlotaAutoparte(FlotaAutoparte flotaAutoparte) throws Exception {

		flotaAutoparteMapper.crearFlotaAutoparte(flotaAutoparte);
	}

	@Override
	public List<FlotaAutoparte> listaAutopartesxAutomotor(Integer idBus, Integer idAutomotor) throws Exception {
		// TODO Auto-generated method stub
		return flotaAutoparteMapper.listaAutopartesxAutomotor(idBus, idAutomotor);
	}

	@Override
	public void incrementarKm(FlotaAutoparte flotaAutoparte) throws Exception {
		// TODO Auto-generated method stub
		flotaAutoparteMapper.incrementarKm(flotaAutoparte);
	}

	@Override
	public List<FlotaAutoparte> autopartesxCambio(FlotaAutoparte floAutoFilter) throws Exception {
		// TODO Auto-generated method stub
		return flotaAutoparteMapper.autopartesxCambio(floAutoFilter);
	}

	@Override
	public void incrementarKmAproximado(FlotaAutoparte flotaAutoparte)
			throws Exception {
		flotaAutoparteMapper.incrementarKmAproximado(flotaAutoparte);
	}

	@Override
	public void retirarincrementarKmAproximado(FlotaAutoparte flotaAutoparte)
			throws Exception {
		flotaAutoparteMapper.retirarincrementarKmAproximado(flotaAutoparte);
	}

	@Override
	public List<FlotaAutoparte> autopartesxCambioAprox(
			FlotaAutoparte flotaAutoparte) throws Exception {
		// TODO Auto-generated method stub
		return flotaAutoparteMapper.autopartesxCambioAprox(flotaAutoparte);
	}

	@Override
	public List<FlotaAutoparte> autopartesxBus(Flota flota)
			throws Exception {

		return flotaAutoparteMapper.autopartesxBus(flota);
	}

	@Override
	public FlotaAutoparte findById(Integer idFlotaAuto) throws Exception {
		// TODO Auto-generated method stub
		return flotaAutoparteMapper.findById(idFlotaAuto);
	}

	@Override
	public void kmCero(FlotaAutoparte flotaAutoparte) {
		// TODO Auto-generated method stub
		flotaAutoparteMapper.kmCero(flotaAutoparte);
	}

	@Override
	public List<FlotaAutoparte> autopartesxBusAll(Flota flota) throws Exception {
		// TODO Auto-generated method stub
		return flotaAutoparteMapper.autopartesxBusAll(flota);
	}

}

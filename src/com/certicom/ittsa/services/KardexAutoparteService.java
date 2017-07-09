package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.AgenciaAutoparte;
import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.KardexAutoparte;
import com.certicom.ittsa.mapper.KardexAutoparteMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class KardexAutoparteService implements KardexAutoparteMapper{
	
	KardexAutoparteMapper kardexAutoparteMapper = (KardexAutoparteMapper)ServiceFinder.findBean("kardexAutoparteMapper");

	@Override
	public void registrarKardex(KardexAutoparte kardex) {
		System.out.println("el id automotor es " + kardex.getIdAutoparte());
		kardexAutoparteMapper.registrarKardex(kardex);
	}

	@Override
	public void deleteKardexbyProducto(KardexAutoparte kardex) {
		kardexAutoparteMapper.deleteKardexbyProducto(kardex);
	}

	@Override
	public List<KardexAutoparte> getCantKardexxProducto(KardexAutoparte kardex) {
		return kardexAutoparteMapper.getCantKardexxProducto(kardex);
	}

	@Override
	public List<KardexAutoparte> listarKardexporProducto(AgenciaAutoparte ap) {
		return kardexAutoparteMapper.listarKardexporProducto(ap);
	}

	@Override
	public List<KardexAutoparte> obtenerListaProductosxFecha(Date fecInicial,
			Date fecFinal, Integer idProducto) {
		// TODO Auto-generated method stub
		return kardexAutoparteMapper.obtenerListaProductosxFecha(fecInicial, fecFinal, idProducto);
	}

	
	

}

package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.Kardex;
import com.certicom.ittsa.mapper.KardexMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class KardexService implements KardexMapper{
	
	KardexMapper kardexMapper = (KardexMapper)ServiceFinder.findBean("kardexMapper");

	@Override
	public void registrarKardex(Kardex kardex) {
		kardexMapper.registrarKardex(kardex);
	}

	@Override
	public void deleteKardexbyProducto(Kardex kardex) {
		kardexMapper.deleteKardexbyProducto(kardex);
	}

	@Override
	public List<Kardex> getCantKardexxProducto(Kardex kardex) {
		return kardexMapper.getCantKardexxProducto(kardex);
	}

	@Override
	public List<Kardex> listarKardexporProducto(AgenciaProducto ap) {
		return kardexMapper.listarKardexporProducto(ap);
	}

	@Override
	public List<Kardex> obtenerListaProductosxFecha(Date fecInicial,
			Date fecFinal, Integer idProducto) {
		// TODO Auto-generated method stub
		return kardexMapper.obtenerListaProductosxFecha(fecInicial, fecFinal, idProducto);
	}

	
	

}

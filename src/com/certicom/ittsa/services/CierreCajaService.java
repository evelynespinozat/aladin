package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.CierreCaja;
import com.certicom.ittsa.mapper.CierreCajaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CierreCajaService implements CierreCajaMapper{
	
	CierreCajaMapper cierreCajaMapper = (CierreCajaMapper)ServiceFinder.findBean("cierreCajaMapper");

	@Override
	public List<CierreCaja> findAll() throws Exception {
		return cierreCajaMapper.findAll();
	}

	@Override
	public CierreCaja findById(Integer idCierreCaja) throws Exception {
		return cierreCajaMapper.findById(idCierreCaja);
	}

	@Override
	public void crearCierreCaja(CierreCaja cierreCaja) throws Exception {
		cierreCajaMapper.crearCierreCaja(cierreCaja);
	}

	@Override
	public void deleteCierreCaja(Integer idCierreCaja) {
		cierreCajaMapper.deleteCierreCaja(idCierreCaja);
	}

	@Override
	public List<CierreCaja> verificarCierrexUsuarioFechaCounter(Integer idUsuario,
			Date fCierre) throws Exception {
		return cierreCajaMapper.verificarCierrexUsuarioFechaCounter(idUsuario, fCierre);
	}

	@Override
	public List<CierreCaja> verificarCierrexUsuarioFechaJCounter(
			Integer idUsuario, Date fCierre) throws Exception {
		// TODO Auto-generated method stub
		return cierreCajaMapper.verificarCierrexUsuarioFechaJCounter(idUsuario, fCierre);
	}

}

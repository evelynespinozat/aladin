package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Empresa;
import com.certicom.ittsa.mapper.EmpresaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class EmpresaService implements EmpresaMapper{
	EmpresaMapper empresaMapper = (EmpresaMapper)ServiceFinder.findBean("empresaMapper");

	@Override
	public Empresa findByNroRUC(String RUC) throws Exception {
		return empresaMapper.findByNroRUC(RUC);
	}
	
	@Override
	public List<Empresa> findByRazSoc(String razSoc) throws Exception {
		return empresaMapper.findByRazSoc(razSoc);
	}

	@Override
	public void crearEmpresa(Empresa empresa) throws Exception {
		empresaMapper.crearEmpresa(empresa);
		
	}

	@Override
	public void actualizarEmpresa(Empresa empresa) throws Exception {
		// TODO Auto-generated method stub
		empresaMapper.actualizarEmpresa(empresa);
	}

	@Override
	public void updateEmpresa(Empresa empresa) throws Exception {
		// TODO Auto-generated method stub
		empresaMapper.updateEmpresa(empresa);
	}

	@Override
	public void updateEstadoEmpresa(Empresa empresa) throws Exception {
		// TODO Auto-generated method stub
		empresaMapper.updateEstadoEmpresa(empresa);
	}

	@Override
	public List<Empresa> findAllEmpresas() throws Exception  {
		// TODO Auto-generated method stub
		return empresaMapper.findAllEmpresas();
	}

	@Override
	public void deleteEmpresa(String ruc) throws Exception {
		// TODO Auto-generated method stub
		empresaMapper.deleteEmpresa(ruc);
	}
	
	
}

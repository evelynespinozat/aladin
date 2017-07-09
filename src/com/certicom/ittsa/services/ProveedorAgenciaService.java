package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.ProveedorAgencia;
import com.certicom.ittsa.mapper.ProveedorAgenciaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ProveedorAgenciaService implements ProveedorAgenciaMapper{
	
	ProveedorAgenciaMapper proveedorAgenciaMapper = (ProveedorAgenciaMapper)ServiceFinder.findBean("proveedorAgenciaMapper");

	@Override
	public List<ProveedorAgencia> findAll() throws Exception {
		return proveedorAgenciaMapper.findAll();
	}

	@Override
	public ProveedorAgencia findById(Integer Id) throws Exception {
		return proveedorAgenciaMapper.findById(Id);
	}

	@Override
	public void crearProveedorAgencia(ProveedorAgencia proveedorAgencia)
			throws Exception {
		proveedorAgenciaMapper.crearProveedorAgencia(proveedorAgencia);
	}

	@Override
	public List<ProveedorAgencia> listOfixProveedor(Integer IdProveedor) {
		return proveedorAgenciaMapper.listOfixProveedor(IdProveedor);
	}

	@Override
	public void deleteOficinaFromProveedor(Integer Id) {
		proveedorAgenciaMapper.deleteOficinaFromProveedor(Id);
	}

	@Override
	public Integer getCantProvAgencia(ProveedorAgencia proveedorAgencia) {
		return proveedorAgenciaMapper.getCantProvAgencia(proveedorAgencia);
	}

	@Override
	public List<ProveedorAgencia> listarProveedorxOficina(Integer IdOficina) {
		return proveedorAgenciaMapper.listarProveedorxOficina(IdOficina);
	}


	

	
	

}

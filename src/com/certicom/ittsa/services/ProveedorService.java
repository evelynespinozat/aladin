package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Proveedor;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.ProveedorMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ProveedorService implements ProveedorMapper{
	
	ProveedorMapper proveedorMapper = (ProveedorMapper)ServiceFinder.findBean("proveedorMapper");

	@Override
	public List<Proveedor> findAll() throws Exception {
		return proveedorMapper.findAll();
	}

	@Override
	public Proveedor findById(Integer IdProveedor) throws Exception {
		return proveedorMapper.findById(IdProveedor);
	}

	@Override
	public void eliminarProveedor(Integer IdProveedor) throws Exception {
		proveedorMapper.eliminarProveedor(IdProveedor);
	}

	@Override
	public void crearProveedor(Proveedor proveedor) throws Exception {
		proveedorMapper.crearProveedor(proveedor);
	}

	@Override
	public void actualizarProveedor(Proveedor proveedor) throws Exception {
		proveedorMapper.actualizarProveedor(proveedor);
	}

	@Override
	public List<Proveedor> listaProveedoresActivos() throws Exception {
		return proveedorMapper.listaProveedoresActivos();
	}

	@Override
	public Integer cantProveedoresxProducto(Integer IdProveedor) throws Exception {
		return proveedorMapper.cantProveedoresxProducto(IdProveedor);
	}

	@Override
	public List<Proveedor> listarProveedorATB() throws Exception {
		// TODO Auto-generated method stub
		return proveedorMapper.listarProveedorATB();
	}

	@Override
	public List<Proveedor> listarProveedorMAN() throws Exception {
		// TODO Auto-generated method stub
		return proveedorMapper.listarProveedorMAN();
	}

	@Override
	public List<Proveedor> listaProveedoresActivosATB() throws Exception {
		// TODO Auto-generated method stub
		return proveedorMapper.listaProveedoresActivosATB();
	}

	@Override
	public List<Proveedor> listaProveedoresActivosMAN() throws Exception {
		// TODO Auto-generated method stub
		return proveedorMapper.listaProveedoresActivosMAN();
	}

	@Override
	public Integer cantProveedoresxAutoparte(Integer IdProveedor)
			throws Exception {
		return proveedorMapper.cantProveedoresxAutoparte(IdProveedor);
	}

	
	

}

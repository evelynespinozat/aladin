package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Tarifa_Producto;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.Tarifa_ProductoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class Tarifa_ProductoServices implements Tarifa_ProductoMapper{
	
	Tarifa_ProductoMapper tarifa_ProductoMapper = (Tarifa_ProductoMapper)ServiceFinder.findBean("tarifa_ProductoMapper");

	@Override
	public List<Tarifa_Producto> findAll() throws Exception {
		// TODO Auto-generated method stub
		return tarifa_ProductoMapper.findAll();
	}

	@Override
	public Tarifa_Producto findById(Integer idTarifa_Producto) throws Exception {
		// TODO Auto-generated method stub
		return tarifa_ProductoMapper.findById(idTarifa_Producto); 
	}

	@Override
	public Agencia findByDescripcion(String descripcion) throws Exception {
		// TODO Auto-generated method stub
		return tarifa_ProductoMapper.findByDescripcion(descripcion); 
	}

	@Override
	public void eliminarTarifa_Producto(Integer idTarifa_Producto)
			throws Exception {
		// TODO Auto-generated method stub
		tarifa_ProductoMapper.eliminarTarifa_Producto(idTarifa_Producto);
	}

	@Override
	public void crearTarifa_Producto(Tarifa_Producto tarifa_Producto)
			throws Exception {
		// TODO Auto-generated method stub
		tarifa_ProductoMapper.crearTarifa_Producto(tarifa_Producto);
	}

	@Override
	public void actualizarTarifa_Producto(Tarifa_Producto tarifa_Producto)
			throws Exception {
		// TODO Auto-generated method stub
		tarifa_ProductoMapper.actualizarTarifa_Producto(tarifa_Producto); 
	}

	@Override
	public List<Tarifa_Producto> findByIdAgencia(Integer idAgencia)
			throws Exception {
		// TODO Auto-generated method stub
		return tarifa_ProductoMapper.findByIdAgencia(idAgencia); 
	}

	

	
}

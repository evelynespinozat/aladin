package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.PlantillaDetalle;
import com.certicom.ittsa.mapper.AgenciaProductoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class AgenciaProductoService implements AgenciaProductoMapper{
	
	AgenciaProductoMapper agenciaProductoMapper = (AgenciaProductoMapper)ServiceFinder.findBean("agenciaProductoMapper");

	@Override
	public List<AgenciaProducto> findAll() throws Exception {
		return agenciaProductoMapper.findAll();
	}

	@Override
	public AgenciaProducto findById(Integer Id) throws Exception {
		return agenciaProductoMapper.findById(Id);
	}

	@Override
	public void crearAgenciaProducto(AgenciaProducto agenciaProducto)
			throws Exception {
		agenciaProductoMapper.crearAgenciaProducto(agenciaProducto);
	}

	@Override
	public List<AgenciaProducto> listOfixProducto(Integer IdProducto) {
		return agenciaProductoMapper.listOfixProducto(IdProducto);
	}

	@Override
	public void deleteOficinaFromProduct(Integer Id) {
		agenciaProductoMapper.deleteOficinaFromProduct(Id);
	}

	@Override
	public Integer getCantProdAgeAlmacen(AgenciaProducto agenciaProducto) {
		return agenciaProductoMapper.getCantProdAgeAlmacen(agenciaProducto);
	}

	@Override
	public List<AgenciaProducto> listarAgenProductos(
			AgenciaProducto agenciaProducto) throws Exception {
		return agenciaProductoMapper.listarAgenProductos(agenciaProducto);
	}

	@Override
	public void actualizarStockProducto(PlantillaDetalle plantillaDetalle) {
		agenciaProductoMapper.actualizarStockProducto(plantillaDetalle);
	}

	@Override
	public void agregarStockProducto(Integer Idagencia, Integer IdOficina,
			Integer IdProducto, Integer IdAlmacen, Integer cantIngreso) {
		agenciaProductoMapper.agregarStockProducto(Idagencia, IdOficina, IdProducto, IdAlmacen, cantIngreso);
	}

	@Override
	public Integer cantProductoxAgeOfi(Integer idAgencia, Integer idOficina,
			Integer idAlmacen, Integer idProducto) throws Exception {
		return agenciaProductoMapper.cantProductoxAgeOfi(idAgencia,idOficina,idAlmacen,idProducto);
	}

	@Override
	public List<AgenciaProducto> listOfixProductoxAgencia(Integer IdProducto,
			Integer idAgencia) {
		return agenciaProductoMapper.listOfixProductoxAgencia(IdProducto, idAgencia);
	}



	
	

}

package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.AgenciaAutoparte;
import com.certicom.ittsa.domain.PlantillaDetalle;
import com.certicom.ittsa.mapper.AgenciaAutoparteMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class AgenciaAutoParteService implements AgenciaAutoparteMapper{
	
	AgenciaAutoparteMapper agenciaAutoparteMapper = (AgenciaAutoparteMapper)ServiceFinder.findBean("agenciaAutoparteMapper");

	@Override
	public List<AgenciaAutoparte> findAll() throws Exception {
		return agenciaAutoparteMapper.findAll();
	}

	@Override
	public AgenciaAutoparte findById(Integer Id) throws Exception {
		return agenciaAutoparteMapper.findById(Id);
	}

	@Override
	public void crearAgenciaAutoparte(AgenciaAutoparte agenciaProducto)
			throws Exception {
		agenciaAutoparteMapper.crearAgenciaAutoparte(agenciaProducto);
	}

	@Override
	public List<AgenciaAutoparte> listOfixProducto(Integer IdProducto) {
		return agenciaAutoparteMapper.listOfixProducto(IdProducto);
	}

	@Override
	public void deleteOficinaFromProduct(Integer Id) {
		agenciaAutoparteMapper.deleteOficinaFromProduct(Id);
	}

	@Override
	public Integer getCantProdAgeAlmacen(AgenciaAutoparte agenciaProducto) {
		return agenciaAutoparteMapper.getCantProdAgeAlmacen(agenciaProducto);
	}

	@Override
	public List<AgenciaAutoparte> listarAgenProductos(
			AgenciaAutoparte agenciaProducto) throws Exception {
		return agenciaAutoparteMapper.listarAgenProductos(agenciaProducto);
	}

	@Override
	public void actualizarStockProducto(PlantillaDetalle plantillaDetalle) {
		agenciaAutoparteMapper.actualizarStockProducto(plantillaDetalle);
	}

	@Override
	public void agregarStockProducto(Integer Idagencia, Integer IdOficina,
			Integer IdProducto, Integer IdAlmacen, Integer cantIngreso,Double precioCompra) {
		agenciaAutoparteMapper.agregarStockProducto(Idagencia, IdOficina, IdProducto, IdAlmacen, cantIngreso, precioCompra);
	}

	@Override
	public Integer cantProductoxAgeOfi(Integer idAgencia, Integer idOficina,
			Integer idAlmacen, Integer idProducto) throws Exception {
		return agenciaAutoparteMapper.cantProductoxAgeOfi(idAgencia,idOficina,idAlmacen,idProducto);
	}


	
	

}

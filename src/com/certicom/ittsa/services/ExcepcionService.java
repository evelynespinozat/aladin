package com.certicom.ittsa.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.Excepcion;
import com.certicom.ittsa.mapper.ExcepcionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ExcepcionService implements ExcepcionMapper{
	
	ExcepcionMapper excepcionMapper = (ExcepcionMapper)ServiceFinder.findBean("excepcionMapper");

	@Override
	public List<Excepcion> findAll() throws Exception {
		return excepcionMapper.findAll();
	}

	@Override
	public Excepcion findById(Integer Idexcepcion) throws Exception {
		return excepcionMapper.findById(Idexcepcion);
	}

	@Override
	public void eliminarExcepcion(Integer Idexcepcion) throws Exception {
		excepcionMapper.eliminarExcepcion(Idexcepcion);
		
	}

	@Override
	public void crearExcepcion(Excepcion excepcion) throws Exception {
		excepcion.setfRegistro(new Date());
		excepcionMapper.crearExcepcion(excepcion);
	}

	@Override
	public void actualizarExcepcion(Excepcion excepcion) throws Exception {
		excepcionMapper.actualizarExcepcion(excepcion);
	}

	@Override
	public List<Excepcion> listaExcepcionActivas() throws Exception {
		return excepcionMapper.listaExcepcionActivas();
	}

	@Override
	public List<Excepcion> findByAnio(String anio) throws Exception {
		return excepcionMapper.findByAnio(anio+"%");
	}
	
	@Override
	public Excepcion findExcepcionByFecha(Date  fecha) throws Exception {
		return excepcionMapper.findExcepcionByFecha(fecha);
	}


	
	public Boolean crearExcepcionPorDefecto(String anio)
	{
		
		Boolean resultado = Boolean.FALSE;
		
		//listando: tomando base un anio anterior
		Integer anioAnterior = Integer.parseInt(anio) - 1;
		List<Excepcion> lista = null;
		try {
			
			//validando
			if(this.findByAnio(anioAnterior+"").size() == 0)
			{
				resultado = Boolean.FALSE;
				return resultado;
			}else{
			
				List<Excepcion> listaExc = this.findByAnio(anio);
				if(listaExc.size() == 0)
				{
					lista = this.findByAnio(anioAnterior.toString());
					
					Excepcion exec = null;
					//creando:
					for(Excepcion ex : lista)
					{
						//convert fecha:
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date today = ex.getFecha();        
						String fechaStr = df.format(today);
						
						//setetando nueva fecha:
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date nuevoDate = format.parse(fechaStr.replaceFirst(anioAnterior.toString(),anio));
						
						exec = new Excepcion();
						exec.setDescripcion(ex.getDescripcion());
						exec.setFecha(nuevoDate);
						exec.setfRegistro(new Date());
						
						this.crearExcepcion(exec);
	
					}
					
					resultado = Boolean.TRUE;
				}else{
					resultado = Boolean.FALSE;
				}
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return resultado;
		
	}
	

}

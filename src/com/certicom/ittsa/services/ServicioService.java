package com.certicom.ittsa.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.Transbordo;
import com.certicom.ittsa.mapper.ServicioMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ServicioService implements ServicioMapper{

	ServicioMapper servicioMapper = (ServicioMapper)ServiceFinder.findBean("servicioMapper");
	
	@Override
	public List<Servicio> findAll() throws Exception {
		return servicioMapper.findAll();
	}

	@Override
	public List<Servicio> listaCServiciosActivos() throws Exception {
		return servicioMapper.listaCServiciosActivos();
	}

	@Override
	public Servicio findById(Integer idServicio) throws Exception {
		return servicioMapper.findById(idServicio);
	}

	@Override
	public List<Servicio> findByOrigenDestino(Integer origen, Integer destino)
			throws Exception {
		return servicioMapper.findByOrigenDestino(origen, destino);
	}

	@Override
	public void actualizarServicio(Servicio servicio) throws Exception {
		servicioMapper.actualizarServicio(servicio);
		
	}

	@Override
	public void eliminarServicio(Integer idServicio) throws Exception {
		servicioMapper.eliminarServicio(idServicio);
		
	}



	
	/**
	 * Desc:metodo que suma las horas de viaje a la hora de salida para obtener la hora de llegada
	 * Auth:Will
	 * */
	public Date sumaHoras(Date horaSalida24, Double horasViaje)
	{
		//sumando horas a hora salida
		
		System.out.println("suamndo horas");
		
		//Date to String
		DateFormat df = new SimpleDateFormat("HH:mm");
		String reportDate = df.format(horaSalida24);
		
		//extraigo las horas:
	    Integer hora = Integer.parseInt(reportDate.substring(0,2));
		Integer salida;
		String res;

		salida = (int) (hora+horasViaje);
		
		if(salida >= 24 && salida < 48)
		{
			salida = salida - 24;
		}else if(salida >=48 && salida < 72)
		{
			salida = salida - 24*2;
		}
		
		res = salida +":"+reportDate.substring(3,5);
		//convirtiendo  String a Time:
		

		DateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = null;
		try {
			date = sdf.parse(res);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("Time logrado: " + sdf.format(date));
		
		//salida
		return date;
	}
	
	public Integer convertir24(Integer hora)
	{
		Integer horaFinal = 0;
		
		switch(hora)
		{
		  case 12 :  horaFinal = 12;break;  
		  case 1 :  horaFinal = 13;break;
		  case 2 :  horaFinal = 14;break;
		  case 3 :  horaFinal = 15;break;
		  case 4 :  horaFinal = 16;break;
		  case 5 :  horaFinal = 17;break;
		  case 6 :  horaFinal = 18;break;
		  case 7 :  horaFinal = 19;break;
		  case 8 :  horaFinal = 20;break;
		  case 9 :  horaFinal = 21;break;
		  case 10 :  horaFinal = 22;break;
		  case 11 :  horaFinal = 23;break;
		  
		}
		
		return horaFinal;
	}
	
	
	public Date convertirHSalida24(String horaSalida)
	{
		
		//extraigo las horas:
	    Integer hora = Integer.parseInt(horaSalida.substring(0,2));
	    System.out.println("**** hora de Salida es "+hora);
	    System.out.println("*** hora de Salida 2 es "+horaSalida);
		String res = null;
		//extraemos el sufijo AM/PM
		if(horaSalida.substring(6).equals("PM"))
		{
			hora = convertir24(hora);
			res = hora +":"+horaSalida.substring(3,5);
			System.out.println("Res ---"+res);
		}else{
			//tratamiento para la media noche
			if(hora.intValue() == 12)
			{
				//consultando el ultimo registro de las 11:
				String ultimaHora = this.obtenerUltimoRegistroPorHorasalida().substring(0,5);
				

				if(ultimaHora.equals("23:50"))
				{
					res = "23" +":"+"51";
				}
				else if(ultimaHora.equals("23:51"))
				{
					res = "23" +":"+"52";
				}else if(ultimaHora.equals("23:52"))
				{
					res = "23" +":"+"53";
				}else if(ultimaHora.equals("23:53"))
				{
					res = "23" +":"+"54";
				}else if(ultimaHora.equals("23:54"))
				{
					res = "23" +":"+"55";
				}else if(ultimaHora.equals("23:55"))
				{
					res = "23" +":"+"56";
				}else if(ultimaHora.equals("23:56"))
				{
					res = "23" +":"+"57";
				}else if(ultimaHora.equals("23:57"))
				{
					res = "23" +":"+"58";
				}else if(ultimaHora.equals("23:58"))
				{
					res = "23" +":"+"59";
				}else
				{
					res = "23" +":"+"51";
				}

			}else{
				
				res = hora +":"+horaSalida.substring(3,5);
				
			}
			
			
			
		}
		
		//convirtiendo  String a Time:
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = null;
	try {
			System.out.println("Res--date ---"+res);
			date = sdf.parse(res);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//salida
		return date;
	}

	@Override
	public List<Servicio> serviciosFilter(Programacion programacion) throws Exception {
		return servicioMapper.serviciosFilter(programacion);
	}

	@Override
	public List<Servicio> obtenerEnlaces(Servicio servicio) throws Exception {
		return servicioMapper.obtenerEnlaces(servicio);
	}

	@Override
	public int obtenerServicioClase(Integer idclase) throws Exception {
		return servicioMapper.obtenerServicioClase(idclase);
	}

	@Override
	public List<Servicio> obtenerServicioByIdClase(Integer idclase)
			throws Exception {
		return servicioMapper.obtenerServicioByIdClase(idclase); 
	}

	@Override
	public List<Servicio> obtenerServicioporClase(Integer idclase)
			throws Exception {
		return servicioMapper.obtenerServicioporClase(idclase);
	}

	@Override
	public Servicio serviciobyProgramacion(String fSalida, Integer origen,
			Integer destino, String hsalida,Integer idProgramacion) throws Exception {
		return servicioMapper.serviciobyProgramacion(fSalida, origen, destino, hsalida, idProgramacion);
	}

	@Override
	public List<Servicio> getServiciosOriDesClase(Integer Origen,
			Integer Destino, Integer IdClase) throws Exception {
		return servicioMapper.getServiciosOriDesClase(Origen, Destino, IdClase);
	}

	@Override
	public List<Servicio> findByOrigenDestinoRutaCompartida(Integer origen,
			Integer destino) throws Exception {
		return servicioMapper.findByOrigenDestinoRutaCompartida(origen, destino);
	}

	@Override
	public void actualizarServicioCorrelativo(Servicio servicio)
			throws Exception {
		servicioMapper.actualizarServicioCorrelativo(servicio);
	}
	
	@Override
	public void actualizarServicioPadre(Integer idServicio) throws Exception {
		servicioMapper.actualizarServicioPadre(idServicio);
	}

	@Override
	public void eliminarServiciosHijos(Integer intCorre) throws Exception {
		servicioMapper.eliminarServiciosHijos(intCorre);
	}

	@Override
	public List<Servicio> obtenerServiciosHijos(Integer idServicio)
			throws Exception {
		return servicioMapper.obtenerServiciosHijos(idServicio);
	}

	@Override
	public List<Servicio> obtenerServiciosPadre(Integer idServicio)
			throws Exception {
		return servicioMapper.obtenerServiciosPadre(idServicio);
	}

	@Override
	public Integer obtenerIdUltimoRegistro() {
		// TODO Auto-generated method stub
		return servicioMapper.obtenerIdUltimoRegistro();
	}

	@Override
	public List<Servicio> listaServiciosPadres(Integer frecuencia,Integer origen, Integer destino)
			throws Exception {
		return servicioMapper.listaServiciosPadres(frecuencia,origen, destino);
	}

	@Override
	public List<Servicio> listaHijosServiciosCompartidos(Integer correlativo)
			throws Exception {
		return servicioMapper.listaHijosServiciosCompartidos(correlativo);
	}

	@Override
	public void crearServicio(Servicio servicio) throws Exception {
		servicioMapper.crearServicio(servicio);
	}

	@Override
	public Integer validarServicioxHora(Servicio servicio) throws Exception {
		return servicioMapper.validarServicioxHora(servicio);
	}

	@Override
	public Servicio serviciobyIdProgramacion(Integer idProgramacion)
			throws Exception {
		return servicioMapper.serviciobyIdProgramacion(idProgramacion);
	}

	@Override
	public List<Servicio> obtenerServicioporClaseDemanda(Integer idclase)
			throws Exception {
		return servicioMapper.obtenerServicioporClaseDemanda(idclase);
	}

	@Override
	public void actualizarServicioAmpliar(Integer idClase, String pdescripcion,
			Integer idServicio) throws Exception {
		servicioMapper.actualizarServicioAmpliar(idClase, pdescripcion, idServicio);
		
	}

	@Override
	public String obtenerUltimoRegistroPorHorasalida() {
		return servicioMapper.obtenerUltimoRegistroPorHorasalida();
	}

}

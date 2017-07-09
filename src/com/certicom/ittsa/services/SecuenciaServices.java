package com.certicom.ittsa.services; 

import java.util.List; 

import org.apache.ibatis.annotations.Param;

import com.certicom.ittsa.domain.Parametro;
import com.certicom.ittsa.domain.Secuencia;
import com.certicom.ittsa.mapper.SecuenciaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class SecuenciaServices implements SecuenciaMapper{

	SecuenciaMapper secuenciaMapper = (SecuenciaMapper)ServiceFinder.findBean("secuenciaMapper");

	public Secuencia findbyDescripcion(String secuenciaTrackingBoleto) throws Exception {
		// TODO Auto-generated method stub
		return secuenciaMapper.findbyDescripcion(secuenciaTrackingBoleto);
	}
	
	public void actualizarSecuencia(Secuencia sec) throws Exception{
		// TODO Auto-generated method stub
		secuenciaMapper.actualizarSecuencia(sec) ;
	}


} 

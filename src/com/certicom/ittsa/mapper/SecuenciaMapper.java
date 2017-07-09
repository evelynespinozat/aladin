package com.certicom.ittsa.mapper; 

import java.util.List; 

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Parametro;
import com.certicom.ittsa.domain.Secuencia;

public interface SecuenciaMapper{

	@Select("select * from t_secuencia where descripcion = #{secdescripcion}")
	public Secuencia findbyDescripcion(@Param("secdescripcion") String secuenciaTrackingBoleto) throws Exception;
	
	@Update("update t_secuencia set secuencia=#{secuencia} where descripcion=#{descripcion}")
	@Options(flushCache = true, useCache = true)
	public void actualizarSecuencia(Secuencia sec) throws Exception; 

	
	
	/*@Select("select valor from t_parametro where nombre_parametro = #{p_nombre_parametro}")
	public String findParametro_byNombre(@Param("p_nombre_parametro") String nombre_parametro) throws Exception;*/
} 

package com.certicom.ittsa.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Salida;

public interface SalidaMapper {
	
	public void registrarSalidaProductos(Salida salida) throws Exception;
	
	@Select("select IdSalida from t_salida where IdProgramacion = #{IdProgramacion}")
	public Integer getIDbyProgramacion(Integer IdProgramacion);
	
	@Update("update t_salida set flagLlegada = #{flagLlegada} where IdProgramacion=#{IdProgramacion} ")
	@Options(flushCache=true)
	public void actualizarFlagLLegada(@Param("IdProgramacion")Integer IdProgramacion, @Param("flagLlegada")String flagLlegada);
	
}

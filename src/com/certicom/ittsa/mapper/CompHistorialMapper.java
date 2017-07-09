package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.CompHistorial;

public interface CompHistorialMapper {


	@Select("select * from t_componente_historial where IdHistComp = #{IdHistComp}")
	public CompHistorial findById(@Param("IdHistComp") Integer IdHistComp) throws Exception;
	
	@Delete("delete  from t_componente_historial  where IdHistComp = #{IdHistComp}")
	@Options(flushCache=true)
	public void eliminarCompHistorial(@Param("IdHistComp") Integer IdHistComp) throws Exception;
	
	@Insert("insert into t_componente_historial (Descripcion,Observacion,Tecnico,Fecha,FRegistro,EstadoHis,Idcomponente)"
			+ " values (#{Descripcion},#{Observacion},#{Tecnico},#{Fecha},#{FRegistro},#{EstadoHis},#{Idcomponente})")
	public void crearCompHistorial(CompHistorial compHistorial) throws Exception;
	
	
	@Update("update t_componente_historial set Descripcion = #{Descripcion}, Observacion = #{Observacion},Tecnico=#{Tecnico},  "
			+ "Fecha=#{Fecha}, FRegistro=#{FRegistro}, EstadoHis=#{EstadoHis} where IdHistComp= #{IdHistComp}")
	@Options(flushCache=true,useCache=true)
    public void actualizarCompHistorial(CompHistorial compHistorial) throws Exception;
	
	
	@Select("select * from t_componente_historial where Idcomponente = #{Idcomponente} order by Fecha desc ")
	public List<CompHistorial> listaHistorialxComPV(Integer Idcomponente) throws Exception;

	@Select("select count(*) from t_componente_historial where Idcomponente = #{Idcomponente} ")
	public Integer cantCompxHistorial(Integer Idcomponente) throws Exception;
	
	
}

package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Excepcion;

public interface ExcepcionMapper {

	
	@Select("select * from t_excepcion order by descripcion asc")
	public List<Excepcion> findAll() throws Exception;
	

	@Select("select * from t_excepcion where Idexcepcion = #{Idexcepcion}")
	public Excepcion findById(@Param("Idexcepcion") Integer Idexcepcion) throws Exception;
	
	@Select("select * from t_excepcion where Fecha like #{anio}")
	public List<Excepcion> findByAnio(@Param("anio") String anio) throws Exception;
	
	@Delete("delete  from t_excepcion  where Idexcepcion = #{Idexcepcion}")
	@Options(flushCache=true)
	public void eliminarExcepcion(@Param("Idexcepcion") Integer Idexcepcion) throws Exception;
	
	@Insert("insert into t_excepcion (Descripcion,Estado,Fecha,FRegistro) values (#{descripcion},#{estado},#{fecha},#{fRegistro})") 
	public void crearExcepcion(Excepcion excepcion) throws Exception;
	
	
	@Update("update t_excepcion set Descripcion = #{descripcion}, Estado = #{estado}, Fecha = #{fecha}, FRegistro = #{fRegistro} where Idexcepcion = #{idexcepcion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarExcepcion(Excepcion excepcion) throws Exception;
	
	
	@Select("select * from t_excepcion where Estado = 1 order by descripcion asc")
	public List<Excepcion> listaExcepcionActivas() throws Exception;
	
	@Select("select * from t_excepcion where fecha = #{fecha}")
	public Excepcion findExcepcionByFecha(Date  fecha) throws Exception; 
	
	
}

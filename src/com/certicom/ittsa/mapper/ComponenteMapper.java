package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Componente;

public interface ComponenteMapper {


	@Select("select * from t_componente where Idcomponente = #{Idcomponente}")
	public Componente findById(@Param("Idcomponente") Integer Idcomponente) throws Exception;
	
	@Delete("delete  from t_componente  where Idcomponente = #{Idcomponente}")
	@Options(flushCache=true)
	public void eliminarComponente(@Param("Idcomponente") Integer Idcomponente) throws Exception;
	
	@Insert("insert into t_componente (Nombre,Descripcion, Estado, IdPuntoVenta) values (#{Nombre},#{Descripcion},#{Estado},#{IdPuntoVenta})")
	public void crearComponente(Componente componente) throws Exception;
	
	
	@Update("update t_componente set Nombre = #{Nombre}, Descripcion = #{Descripcion},Estado=#{Estado}  where Idcomponente= #{Idcomponente}")
	@Options(flushCache=true,useCache=true)
    public void actualizarComponente(Componente Componente) throws Exception;
	
	
	@Select("select * from t_componente where IdPuntoVenta = #{IdPuntoVenta} ")
	public List<Componente> listaComponentesxPV(Integer IdPuntoVenta) throws Exception;
	
	
}

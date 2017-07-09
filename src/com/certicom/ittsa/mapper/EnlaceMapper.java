package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Enlace;
import com.certicom.ittsa.domain.Oficina;

public interface EnlaceMapper {

	@Select("select * from t_enlace order by idEnlace asc")
	public List<Enlace> findAll() throws Exception;

	@Select("select * from t_enlace where IdEnlace = #{idEnlace}")
	public Enlace findById(@Param("idEnlace") Integer idEnlace)
			throws Exception;

	@Delete("delete  from t_enlace  where IdEnlace = #{idEnlace}")
	@Options(flushCache = true)
	public void eliminarEnlace(@Param("idEnlace") Integer idEnlace)
			throws Exception;

	@Insert("insert into t_enlace (Tipo, Estado, hora,IdServicioP,origen,destino,intCorre) "
			+ "values (#{tipo},#{estado},#{hora},#{IdServicioP},#{origen},#{destino},#{intCorre})")
	public void crearEnlace(Enlace enlace) throws Exception;

	@Update("update t_enlace set Tipo=#{tipo}, Estado = #{estado} where IdEnlace= #{idEnlace}")
	@Options(flushCache = true, useCache = true)
	public void actualizarEnlace(Enlace enlace) throws Exception;

	@Select("select * from t_enlace where Estado = 1 order by idEnlace asc")
	public List<Enlace> listaEnlacesActivas() throws Exception;

	public List<Enlace> listaEnlaces() throws Exception;

	public List<Enlace> listarTramosServicio(Integer IdServicio)throws Exception;

	@Delete("DELETE from t_enlace where IdServicioP = #{idServicio}")
	public void eliminarEnlacesByServicio(
			@Param("idServicio") Integer idServicio) throws Exception;
	
	@Select("select IntCorrelativo from t_correlativo where IdCorrelativo = 1")
	public Integer obtenerCorrelativo() throws Exception;
	
	@Update("update t_correlativo set IntCorrelativo = #{intCorrelativo} where IdCorrelativo = #{idCorrelativo}")
	public void actualizarCorrelativo(@Param("idCorrelativo")Integer idCorrelativo, @Param("intCorrelativo")Integer intCorrelativo) throws Exception;
	
	
}

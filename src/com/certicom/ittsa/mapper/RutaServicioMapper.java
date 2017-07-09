package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.RutaServicio;


public interface RutaServicioMapper {

	
	@Select("select * from t_rutaServicio order by RutaDestino asc")
	public List<RutaServicio> findAll() throws Exception;
	

	@Select("select * from t_rutaServicio where idRutaDestino = #{p_rutaDestinoId}")
	public RutaServicio findById(@Param("p_rutaDestinoId") String rutaServicioId) throws Exception;
	
	@Delete("delete  from t_rutaServicio  where idRutaDestino = #{p_rutaDestinoId}")
	@Options(flushCache=true)
	public void eliminarRutaServicio(@Param("p_rutaDestinoId") Integer rutaServicioId) throws Exception;
	
	@Insert("insert into t_rutaServicio (rutaDestino, tipoViaje, estado) values (#{RutaDestino},#{TipoViaje},#{Estado})")
	public void crearRutaServicio(RutaServicio rutaServicio) throws Exception;
	
	
	@Update("update t_rutaServicio set Descripcion = #{Descripcion},Direccion=#{Direccion} ,Telefono1=#{Telefono1}, Telefono2 =#{Telefono2},Email = #{Email},Grupo = #{Grupo},Estado = #{Estado} where Idagencia= #{Idagencia}")
	//@Update("update t_cliente set razonsocial_cliente=#{razonsocial_cliente},direccion_cliente=#{direccion_cliente} ,contacto_cliente=#{direccion_cliente},telefono_cliente=#{telefono_cliente}")
	@Options(flushCache=true,useCache=true)
    public void actualizarRutaServicio(RutaServicio rutaServicio) throws Exception;
	
	
	@Select("select * from t_rutaServicio where Estado = 1 order by descripcion asc")
	public List<RutaServicio> listaRutaServicioActivas() throws Exception;
}

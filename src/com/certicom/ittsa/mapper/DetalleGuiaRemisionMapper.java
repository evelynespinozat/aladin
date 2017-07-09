package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.DetalleGuiaRemision;

public interface DetalleGuiaRemisionMapper {
	
	@Select("select * from t_detalleguiaremision order by Descripcion asc")
	public List<DetalleGuiaRemision> findAll() throws Exception;
	
	@Select("select * from t_detalleguiaremision where IdDetGuiaRemision = #{idDetGuiaRemision}")
	public DetalleGuiaRemision findById(@Param("idDetGuiaRemision") Integer idDetGuiaRemision) throws Exception;
	
	@Delete("delete  from t_detalleguiaremision  where IdDetGuiaRemision = #{idDetGuiaRemision}")
	@Options(flushCache=true)
	public void eliminarDetalleGuiaRemision(@Param("idDetGuiaRemision") Integer idDetGuiaRemision) throws Exception;
	
	@Insert("insert into t_detalleguiaremision (idGuiaRemision, descripcion, peso, cantidad, precioEnvio) values (#{idGuiaRemision},#{descripcion},#{peso},#{cantidad},#{precioEnvio})")
	public void crearDetalleGuiaRemision(DetalleGuiaRemision detalleGuiaRemision) throws Exception;
	
	@Update("update t_detalleguiaremision set IdGuiaRemision = #{idGuiaRemision}, Descripcion = #{descripcion}, Peso=#{peso}, Cantidad =#{cantidad}, PrecioEnvio = #{precioEnvio} where IdDetGuiaRemision= #{idDetGuiaRemision}")
	@Options(flushCache=true,useCache=true)
    public void actualizarDetalleGuiaRemision(DetalleGuiaRemision detalleGuiaRemision) throws Exception;
	
}

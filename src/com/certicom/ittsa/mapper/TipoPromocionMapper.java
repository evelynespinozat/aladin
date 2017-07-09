package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.TipoPromocion;

public interface TipoPromocionMapper {

	
	@Select("select * from t_tipopromocion order by descripcion asc")
	public List<TipoPromocion> findAll() throws Exception;
	
	@Select("select * from t_tipopromocion where IdTipoPromocion = #{idTipoPromocion}")
	public TipoPromocion findById(@Param("idTipoPromocion") Integer idTipoPromocion) throws Exception;
	
	@Delete("delete  from t_tipopromocion  where IdTipoPromocion = #{idTipoPromocion}")
	@Options(flushCache=true)
	public void eliminarTipoPromocion(@Param("idTipoPromocion") Integer agenciaID) throws Exception;
	
	@Insert("insert into t_tipopromocion (Descripcion, direccion, telefono1, telefono2, Email, grupo,estado) values (#{Descripcion},#{Direccion},#{Telefono1},#{Telefono2},#{Email},#{Grupo},#{Estado})")
	public void crearTipoPromocion(TipoPromocion agencia) throws Exception;
	
	
	@Update("update t_tipopromocion set Descripcion = #{Descripcion},Direccion=#{Direccion} ,Telefono1=#{Telefono1}, Telefono2 =#{Telefono2},Email = #{Email},Grupo = #{Grupo},Estado = #{Estado} where IdTipoPromocion= #{IdTipoPromocion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTipoPromocion(TipoPromocion agencia) throws Exception;
	

	
}

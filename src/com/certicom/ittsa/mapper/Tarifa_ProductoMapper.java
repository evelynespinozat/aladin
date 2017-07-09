package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Tarifa_Producto;

public interface Tarifa_ProductoMapper {

	
	@Select("select * from t_tarifa_productos order by descripcion asc")
	public List<Tarifa_Producto> findAll() throws Exception;
	

	@Select("select * from t_tarifa_productos where IdTarifa_Producto = #{idTarifa_Producto}")
	public Tarifa_Producto findById(@Param("idTarifa_Producto") Integer idTarifa_Producto) throws Exception;
	
	@Select("select * from t_tarifa_productos where IdAgencia = #{idAgencia} order by descripcion asc")
	public List<Tarifa_Producto> findByIdAgencia(@Param("idAgencia") Integer idAgencia) throws Exception;
	
	
	@Select("select * from t_tarifa_productos where Descripcion = #{descripcion}")
	public Agencia findByDescripcion(@Param("descripcion") String descripcion) throws Exception;
	
	
	@Delete("delete  from t_tarifa_productos  where IdTarifa_Producto = #{idTarifa_Producto}")
	@Options(flushCache=true)
	public void eliminarTarifa_Producto(@Param("idTarifa_Producto") Integer idTarifa_Producto) throws Exception;
	
	@Insert("insert into t_tarifa_productos (Descripcion,IdAgencia,PrecioEnvio,PrecioEmbalaje,Peso,PrecioKilo) values (#{descripcion},#{idAgencia},Cast(#{precioEnvio} as decimal),Cast(#{precioEmbalaje} as decimal),Cast(#{peso} as decimal),Cast(#{precioKilo} as decimal))")
	public void crearTarifa_Producto(Tarifa_Producto tarifa_Producto) throws Exception;
	
	
	@Update("update t_tarifa_productos set Descripcion = #{descripcion},IdAgencia=#{idAgencia} ,PrecioEnvio=#{precioEnvio}, PrecioEmbalaje =#{precioEmbalaje}, Peso =#{peso}  where IdTarifa_Producto = #{idTarifa_Producto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTarifa_Producto(Tarifa_Producto tarifa_Producto) throws Exception;

	
}

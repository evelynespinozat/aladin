package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.OficinaAlmacen;

public interface OficinaAlmacenMapper {

	@Select("select * from t_oficina_almacen where Id = #{id}")
	public OficinaAlmacen findById(@Param("id") Integer id) throws Exception;
	
	@Delete("delete  from t_oficina_almacen  where Id = #{id}")
	@Options(flushCache=true)
	public void eliminarOficinaAlmacen(@Param("id") Integer id) throws Exception;
	
	@Insert("insert into t_oficina_almacen (IdOficina, IdAlmacen) values (#{idOficina},#{idAlmacen})")
	public void crearOficinaAlmacen(OficinaAlmacen oficinaAlmacen) throws Exception;
	
	@Select("select o.IdOficina as idOficina, o.IdAlmacen as idAlmacen, o.Id as id, a.Descripcion as desAlmacen from t_oficina_almacen o inner join t_almacen a on a.IdAlmacen = o.IdAlmacen where o.IdOficina = #{idOficina}")
	public List<OficinaAlmacen> listaOficinasxAlmacen(@Param("idOficina") Integer idOficina) throws Exception;
	
	@Select("select count(*) from t_oficina_almacen where IdOficina = #{idOficina} and IdAlmacen = #{idAlmacen} ")
	public int verificarAlmacenxOficina(@Param("idOficina") Integer idOficina,@Param("idAlmacen") Integer idAlmacen) throws Exception;
}

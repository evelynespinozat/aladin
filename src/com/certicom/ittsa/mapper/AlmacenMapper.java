package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Almacen;

public interface AlmacenMapper {

	
	@Select("select * from t_almacen order by descripcion asc")
	public List<Almacen> findAll() throws Exception;
	

	@Select("select * from t_almacen where IdAlmacen = #{IdAlmacen}")
	public Almacen findById(@Param("IdAlmacen") Integer IdAlmacen) throws Exception;
	
	@Delete("delete  from t_almacen  where IdAlmacen = #{IdAlmacen}")
	@Options(flushCache=true)
	public void eliminarAlmacen(@Param("IdAlmacen") Integer IdAlmacen) throws Exception;
	
	@Insert("insert into t_almacen (Descripcion, Estado) values (#{Descripcion},#{Estado})")
	public void crearAlmacen(Almacen almacen) throws Exception;
	
	
	@Update("update t_almacen set Descripcion = #{Descripcion},Estado=#{Estado}  where IdAlmacen= #{IdAlmacen}")
	@Options(flushCache=true,useCache=true)
    public void actualizarAlmacen(Almacen almacen) throws Exception;

	@Update("update t_almacen set asignado = #{asignado} where IdAlmacen= #{IdAlmacen}")
	@Options(flushCache=true,useCache=true)
	public void actualizarAsignacionAlmacen(@Param("asignado") boolean asignado,@Param("IdAlmacen") Integer IdAlmacen) throws Exception;
	
	
	@Select("select * from t_almacen where Estado = 1 order by descripcion asc")
	public List<Almacen> listaAlmacensActivas() throws Exception;
	
	@Select("select * from t_almacen where Estado = 1 and asignado = 0 order by descripcion asc")
	public List<Almacen> listaAlmacenNoAsignados() throws Exception;
	
	@Select("select oa.IdAlmacen,a.Descripcion from t_oficina_almacen oa, t_almacen a where oa.IdAlmacen = a.IdAlmacen "
				+ " and oa.IdOficina = #{IdOficina}")
	public List<Almacen> listAlmacenxOficina(Integer IdOficina);
	
	@Select("select COUNT(*) from t_almacen where Descripcion = #{descripcion}")
	public Integer cantAlmacen(@Param("descripcion") String descripcion)throws Exception;
	
	
}

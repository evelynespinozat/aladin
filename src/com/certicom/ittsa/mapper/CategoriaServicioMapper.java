package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.CategoriaServicio;

public interface CategoriaServicioMapper {

	
	@Select("select * from t_categoriaservicio order by Descripcion asc")
	public List<CategoriaServicio> findAll() throws Exception;
	
	@Select("select * from t_categoriaservicio where Descripcion =#{descripcion}")
	public CategoriaServicio findByNombre(@Param("descripcion") String  descripcion) throws Exception;
	

	@Select("select * from t_categoriaservicio where IdCatServicio = #{idCatServicio}")
	public CategoriaServicio findById(@Param("idCatServicio") Integer idCatServicio) throws Exception;
	
	@Delete("delete  from t_categoriaservicio  where IdCatServicio = #{idCatServicio}")
	@Options(flushCache=true)
	public void eliminarCategoriaServicio(@Param("idCatServicio") Integer idCatServicio) throws Exception;
	
	@Insert("insert into t_categoriaservicio (Descripcion,Estado) values (#{descripcion},#{estado})")
	public void crearCategoriaServicio(CategoriaServicio categoriaServicio) throws Exception;
	
	
	@Update("update t_categoriaservicio set Descripcion=#{descripcion},Estado=#{estado} where IdCatServicio= #{idCatServicio}")
	@Options(flushCache=true,useCache=true)
    public void actualizarCategoriaServicio(CategoriaServicio categoriaServicio) throws Exception;
	
	@Select("select * from t_categoriaservicio where Estado = 1 order by Descripcion asc")
	public List<CategoriaServicio> listaCatServicioActivos() throws Exception;
	
}

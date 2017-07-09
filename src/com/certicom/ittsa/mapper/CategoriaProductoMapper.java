package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.CategoriaProducto;
import com.certicom.ittsa.domain.Oficina;

public interface CategoriaProductoMapper {

	
	@Select("select * from t_categoria_producto order by descripcion asc")
	public List<CategoriaProducto> findAll() throws Exception;
	

	@Select("select * from t_categoria_producto where idCatProducto = #{idCatProducto}")
	public CategoriaProducto findById(@Param("idCatProducto") Integer idCatProducto) throws Exception;
	
	@Delete("delete  from t_categoria_producto  where idCatProducto = #{idCatProducto}")
	@Options(flushCache=true)
	public void eliminarCategoriaProducto(@Param("idCatProducto") Integer idCatProducto) throws Exception;
	
	@Insert("insert into t_categoria_producto (Descripcion,Estado,tipo) values (#{Descripcion},#{Estado},#{tipo})")
	public void crearCategoriaProducto(CategoriaProducto agencia) throws Exception;
	
	
	@Update("update t_categoria_producto set Descripcion = #{Descripcion},Estado=#{Estado} ,tipo=#{tipo}  where idCatProducto= #{idCatProducto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarCategoriaProducto(CategoriaProducto categoriaProducto) throws Exception;
	
	
	@Select("select * from t_categoria_producto where Estado = 1 order by descripcion asc")
	public List<CategoriaProducto> listaCategoriaProductosActivas() throws Exception;

	@Select("select count(*) from t_producto where idCatProducto = #{idCatProducto}")
	public Integer cantCategoriaProducto(@Param("idCatProducto") Integer idCatProducto) throws Exception;
	
}

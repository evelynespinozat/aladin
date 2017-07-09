package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Producto;

public interface ProductoMapper {
	
	public List<Producto> findAll() throws Exception;

	@Select("select * from t_producto where IdProducto = #{IdProducto}")
	public Producto findById(@Param("IdProducto") Integer IdProducto) throws Exception;
	
	@Delete("delete  from t_producto  where IdProducto = #{IdProducto}")
	@Options(flushCache=true)
	public void eliminarProducto(@Param("IdProducto") Integer IdProducto) throws Exception;
	
	@Insert("insert into t_producto (Descripcion, Medida, Estado ,Costo, CostoUni ,idCatProducto,desgregacion,cantDist,costoxPasj) "
			+ " values (#{Descripcion},#{Medida},#{Estado},#{Costo},#{CostoUni},#{idCatProducto},#{desgregacion},#{cantDist},#{costoxPasj})")
	public void crearProducto(Producto producto) throws Exception;
	
	
	@Update("update t_producto set Descripcion = #{Descripcion},Medida=#{Medida} , Estado =#{Estado},Costo = #{Costo},CostoUni = #{CostoUni}, idCatProducto= #{idCatProducto}, desgregacion=#{desgregacion}, "
			+ "cantDist = #{cantDist}, costoxPasj = #{costoxPasj}  where IdProducto= #{IdProducto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarProducto(Producto producto) throws Exception;
	
	
	@Select("select * from t_producto where Estado = 1 ")
	public List<Producto> listaProductosActivos() throws Exception;
	
	@Select(" ")
	public Integer cantProductoAsignados(Integer IdProducto) throws Exception;
	
	@Update("update t_producto set costoUni = #{costoUni} where IdProducto= #{idProducto} ")
	@Options(flushCache=true,useCache=true)
	public void actualizarCostoProducto(@Param("idProducto")Integer idProducto, @Param("costoUni")double costoUni)throws Exception;
	
	
}

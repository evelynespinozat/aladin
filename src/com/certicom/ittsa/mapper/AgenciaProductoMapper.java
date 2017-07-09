package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.PlantillaDetalle;

public interface AgenciaProductoMapper {

	@Select("select * from t_agencia_producto")
	public List<AgenciaProducto> findAll() throws Exception;
	

	@Select("select * from t_agencia_producto where Id = #{Id}")
	public AgenciaProducto findById(@Param("Id") Integer Id) throws Exception;

	
	@Insert("insert into t_agencia_producto (Idagencia, IdOficina, IdAlmacen, IdProducto, Stock, Fecha,costoUni,costo,cantDist,costoxPasj,disgregacion) "
			+ " values (#{Idagencia},#{IdOficina},#{IdAlmacen},#{IdProducto},#{Stock},#{Fecha},#{costoUni},#{costo},#{cantDist},#{costoxPasj},#{disgregacion})")
	public void crearAgenciaProducto(AgenciaProducto agenciaProducto) throws Exception;
	
	
	public List<AgenciaProducto> listOfixProducto(Integer IdProducto);
	
	@Select("select ap.Id, ap.Idagencia, a.Descripcion as desAgencia, ap.IdOficina, o.Nombre as desOficina, ap.IdAlmacen, "
			+ " al.Descripcion as desAlmacen,ap.Stock, ap.Fecha, p.IdProducto, p.Descripcion as desProducto, p.Medida,"
			+ " ap.costoUni, ap.costo, ap.cantDist, ap.costoxPasj, ap.disgregacion "
			+ " from t_agencia_producto ap, t_agencia a, t_agenciaoficina o, t_almacen al, t_producto p "
			+ " where ap.Idagencia = a.Idagencia and ap.IdOficina = o.IdOficina and ap.IdAlmacen = al.IdAlmacen "
			+ "	and ap.IdProducto = p.IdProducto and ap.IdProducto = #{IdProducto} and ap.Idagencia = #{idAgencia}")
	public List<AgenciaProducto> listOfixProductoxAgencia(@Param("IdProducto")Integer IdProducto,@Param("idAgencia")Integer idAgencia);
	
	@Delete("delete from t_agencia_producto where Id = #{Id}")
	public void deleteOficinaFromProduct(Integer Id);
	
	public Integer getCantProdAgeAlmacen(AgenciaProducto agenciaProducto);

	public List<AgenciaProducto> listarAgenProductos(AgenciaProducto agenciaProducto) throws Exception;
	
	@Update("update t_agencia_producto set Stock = (Stock - #{cantSalida}) where Idagencia = #{Idagencia} "
					+ "	and IdOficina = #{IdOficina} and IdProducto =#{IdProducto}")
	@Options(flushCache=true,useCache=true)
	public void actualizarStockProducto(PlantillaDetalle plantillaDetalle);
	
	@Update("update t_agencia_producto set Stock = (Stock + #{cantLLegada}) where Idagencia = #{Idagencia} "
			+ "	and IdOficina = #{IdOficina} and IdProducto =#{IdProducto} and IdAlmacen=#{IdAlmacen} ")
	@Options(flushCache=true,useCache=true)
	public void agregarStockProducto(@Param("Idagencia")Integer Idagencia,@Param("IdOficina")Integer IdOficina,
									 @Param("IdProducto")Integer IdProducto,@Param("IdAlmacen")Integer IdAlmacen,
									 @Param("cantLLegada")Integer cantLLegada) ;


	@Select("select count(*) from t_agencia_producto where Idagencia = #{Idagencia} "
			+ "	and IdOficina = #{IdOficina} and IdProducto =#{IdProducto} and IdAlmacen=#{IdAlmacen}")
	public Integer cantProductoxAgeOfi(@Param("Idagencia")Integer Idagencia,@Param("IdOficina") Integer IdOficina,
									   @Param("IdAlmacen")Integer IdAlmacen,@Param("IdProducto") Integer IdProducto) throws Exception;
	
	
	
}

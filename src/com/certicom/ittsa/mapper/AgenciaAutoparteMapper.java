package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.AgenciaAutoparte;
import com.certicom.ittsa.domain.PlantillaDetalle;

public interface AgenciaAutoparteMapper {

	@Select("select * from t_agencia_autoparte")
	public List<AgenciaAutoparte> findAll() throws Exception;
	

	@Select("select * from t_agencia_autoparte where Id = #{Id}")
	public AgenciaAutoparte findById(@Param("Id") Integer Id) throws Exception;

	
	@Insert("insert into t_agencia_autoparte (Idagencia, IdOficina, IdAlmacen, IdAutoparte, Stock, Fecha) "
			+ " values (#{Idagencia},#{IdOficina},#{IdAlmacen},#{IdAutoparte},#{Stock},#{Fecha})")
	public void crearAgenciaAutoparte(AgenciaAutoparte agenciaProducto) throws Exception;
	
	
	public List<AgenciaAutoparte> listOfixProducto(Integer IdAutoparte);
	
	@Delete("delete from t_agencia_autoparte where Id = #{Id}")
	public void deleteOficinaFromProduct(Integer Id);
	
	public Integer getCantProdAgeAlmacen(AgenciaAutoparte agenciaProducto);

	public List<AgenciaAutoparte> listarAgenProductos(AgenciaAutoparte agenciaProducto) throws Exception;
	
	@Update("update t_agencia_autoparte set Stock = (Stock - #{cantSalida}) where Idagencia = #{Idagencia} "
					+ "	and IdOficina = #{IdOficina} and IdAutoparte =#{idAutoparte}")
	@Options(flushCache=true,useCache=true)
	public void actualizarStockProducto(PlantillaDetalle plantillaDetalle);
	
	@Update("update t_agencia_autoparte set Stock = (Stock + #{cantLLegada}), PrecioCompra = #{precioCompra} where Idagencia = #{Idagencia} "
			+ "	and IdOficina = #{IdOficina} and IdAutoparte =#{IdAutoparte} and IdAlmacen=#{IdAlmacen} ")
	@Options(flushCache=true,useCache=true)
	public void agregarStockProducto(@Param("Idagencia")Integer Idagencia,@Param("IdOficina")Integer IdOficina,
									 @Param("IdAutoparte")Integer IdAutoparte,@Param("IdAlmacen")Integer IdAlmacen,
									 @Param("cantLLegada")Integer cantLLegada,@Param("precioCompra") Double precioCompra) ;


	@Select("select count(*) from t_agencia_autoparte where Idagencia = #{Idagencia} "
			+ "	and IdOficina = #{IdOficina} and IdAutoparte =#{IdAutoparte} and IdAlmacen=#{IdAlmacen}")
	public Integer cantProductoxAgeOfi(@Param("Idagencia")Integer Idagencia,@Param("IdOficina") Integer IdOficina,
									   @Param("IdAlmacen")Integer IdAlmacen,@Param("IdAutoparte") Integer IdAutoparte) throws Exception;
	
	
	
}

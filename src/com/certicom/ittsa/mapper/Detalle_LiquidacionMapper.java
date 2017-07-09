package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Detalle_Liquidacion;

public interface Detalle_LiquidacionMapper {

	
	@Select("select * from t_detalle_liquidacion order by Costo desc")
	public List<Detalle_Liquidacion> findAll() throws Exception;
	
	@Select("select * from t_detalle_liquidacion where IdLiquidacion = #{idLiquidacion} order by Costo desc")
	public List<Detalle_Liquidacion> findByIdLiquidacion(@Param("idLiquidacion") Integer idLiquidacion) throws Exception;
	

	@Select("select * from t_detalle_liquidacion where IdDetalleLiq = #{idDetalleLiq}")
	public Detalle_Liquidacion findById(@Param("idDetalleLiq") Integer idDetalleLiq) throws Exception;

	@Select("select count(*) from t_detalle_liquidacion where Estado = 1 and IdLiquidacion = #{idLiquidacion}")
	public Integer countByEstado(@Param("idLiquidacion") Integer idLiquidacion) throws Exception;
	
	@Delete("delete  from t_detalle_liquidacion  where IdDetalleLiq = #{idDetalleLiq}")
	@Options(flushCache=true)
	public void eliminarDetalleLiq(@Param("idDetalleLiq") Integer idDetalleLiq) throws Exception;
	
	@Insert("insert into t_detalle_liquidacion (Concepto,Costo,Estado,IdLiquidacion) values (#{concepto},#{costo},#{estado},#{idLiquidacion})")
	public void crearDetalleLiq(Detalle_Liquidacion detalleLiq) throws Exception;
	
	
	@Update("update t_detalle_liquidacion set Concepto = #{concepto}, Costo=#{costo} ,Estado = #{estado}, IdLiquidacion=#{idLiquidacion} where IdDetalleLiq= #{idDetalleLiq}")
	@Options(flushCache=true,useCache=true)
    public void actualizarDetalleLiq(Detalle_Liquidacion detalleLiq) throws Exception;
	
	@Update("update t_detalle_liquidacion set Estado = #{estado} where IdDetalleLiq= #{idDetalleLiq}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEstado(Detalle_Liquidacion detalleLiq) throws Exception;

	
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Detalle_Liquidacion;
import com.certicom.ittsa.domain.Liquidacion;

public interface LiquidacionMapper {

	
	@Select("select * from t_liquidacion order by IdProgramacion asc")
	public List<Liquidacion> findAll() throws Exception;
	

	@Select("select * from t_liquidacion where IdLiquidacion = #{idLiquidacion}")
	public Liquidacion findById(@Param("idLiquidacion") Integer idLiquidacion) throws Exception;

	@Select("select * from t_liquidacion where IdProgramacion = #{idProgramacion}")
	public Liquidacion findByIdProgramacion(@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	@Delete("delete  from t_liquidacion  where IdLiquidacion = #{idLiquidacion}")
	@Options(flushCache=true)
	public void eliminarLiquidacion(@Param("idLiquidacion") Integer idLiquidacion) throws Exception;
	
	@Insert("insert into t_liquidacion (IdProgramacion,Total,Estado) values (#{idProgramacion},#{total},#{estado})")
	public void crearLiquidacion(Liquidacion Liquidacion) throws Exception;
	
	
	@Update("update t_liquidacion set IdProgramacion = #{idProgramacion}, Total=#{total} ,Estado = #{estado} where IdLiquidacion= #{idLiquidacion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarLiquidacion(Liquidacion Liquidacion) throws Exception;
	
}

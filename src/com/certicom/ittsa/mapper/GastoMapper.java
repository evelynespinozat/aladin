package com.certicom.ittsa.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Gasto;
import com.certicom.ittsa.domain.Liquidacion;


public interface GastoMapper {

	
	@Insert("insert into t_gasto (programacionid,monto,concepto,fecha,observaciones) values (#{programacionId},#{monto},#{concepto},#{fecha},#{observaciones})")
	public void crearGasto(Gasto gasto) throws Exception;
	
	@Update("update t_gasto set programacionid = #{programacionId}, monto=#{monto} ,concepto = #{concepto},fecha=#{fecha} ,observaciones=#{observaciones}  where idGasto= #{idGasto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarGasto(Gasto gasto) throws Exception;
	
	
	@Delete("delete  from t_gasto  where idGasto = #{idGasto}")
	@Options(flushCache=true)
	public void eliminarGasto(@Param("idGasto") Integer idgasto) throws Exception;
	
	
	@Select("select * from t_gasto where programacionId = #{programacionId}")
	public List<Gasto> findByProgramacion(@Param("programacionId") Integer programacionId) throws Exception;
	
	
	@Select("select sum(gas.monto) from t_gasto gas where programacionId = #{programacionId}")
	public BigDecimal sumaGastosByProgramacion(@Param("programacionId") Integer programacionId) throws Exception;
	
	
	@Select("select * from t_gasto where programacionId = #{programacionId}")
	public List<Gasto> findAll() throws Exception;
	
	
	
	
	
	

	
	
	
	
}

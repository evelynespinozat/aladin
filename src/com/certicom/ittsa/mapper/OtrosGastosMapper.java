package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import com.certicom.ittsa.domain.OtrosGastos;

public interface OtrosGastosMapper {
	

	@Select("select  a.Descripcion as des_agencia,ao.Nombre as des_oficina,og.IdOtrosGastos, og.IdOficina, og.IdAgencia, og.Fecha, "
			+ " og.motivo, og.monto, og.ultimoAporte, og.saldo, og.saldo from t_otros_gastos as og "
			+ " inner join t_agencia as a on og.Idagencia =a.Idagencia "
			+ " inner join t_agenciaoficina as ao on ao.IdOficina=og.IdOficina where og.estado = 1")
	public List<OtrosGastos> findAll() throws Exception;
	
	@Select("select * from t_otros_gastos where IdOtrosGastos = #{IdOtrosGastos}")
	public OtrosGastos findById(@Param("IdOtrosGastos") Integer IdOtrosGastos) throws Exception;
	
	@Delete("delete  from t_otros_gastos  where IdOtrosGastos = #{IdOtrosGastos}")
	@Options(flushCache=true)
	public void eliminarOtrosGastos(@Param("IdOtrosGastos") Integer IdOtrosGastos) throws Exception;
	
	@Insert("insert into t_otros_gastos (IdOficina,IdAgencia, fecha, motivo, monto, ultimoAporte, saldo, estado) values (#{IdOficina},#{IdAgencia},#{fecha},#{motivo}, #{monto}, #{ultimoAporte}, #{saldo}, #{estado})")
	public void crearOtrosGastos(OtrosGastos otrosGastos) throws Exception;
	
	
	@Update("update t_otros_gastos set IdOficina = #{IdOficina},IdAgencia =#{IdAgencia},"
			+ " fecha=#{fecha},motivo=#{motivo}, monto=#{monto}, ultimoAporte=#{ultimoAporte}, saldo=#{saldo}, estado=#{estado} "
			+ " where IdOtrosGastos= #{IdOtrosGastos}")
	@Options(flushCache=true,useCache=true)
    public void actualizarOtrosGastos(OtrosGastos otrosGastos) throws Exception;
	
	
	@Select("select * from t_otros_gastos where estado = 1 order by descripcion asc")
	public List<OtrosGastos> listaOtrosGastosActivas() throws Exception;
	



}

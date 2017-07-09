package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.FlotaReten;

public interface FlotaRetenMapper {

	@Select("select * from t_flotareten where IdBusReten = #{IdBusReten}")
	public FlotaReten findById(@Param("IdBusReten") Integer IdBusReten) throws Exception;
	
	@Delete("delete  from t_flotareten  where IdBusReten = #{IdBusReten}")
	@Options(flushCache=true)
	public void eliminarFlotaReten(@Param("IdBusReten") Integer IdBusReten) throws Exception;
	
	@Insert("insert into t_flotareten (IdBus, IdAgencia, idPiloto, idCopiloto, Estado, FRegistro) values (#{IdBus},#{IdAgencia},#{idPiloto},#{idCopiloto},#{Estado},#{FRegistro})")
	public void crearFlotaReten(FlotaReten flotaReten) throws Exception;
	
	
	@Update("update t_flotareten set IdBus = #{IdBus},IdAgencia=#{IdAgencia} ,idPiloto=#{idPiloto}, idCopiloto =#{idCopiloto},Estado = #{Estado} where IdBusReten= #{IdBusReten}")
	@Options(flushCache=true,useCache=true)
    public void actualizarFlotaReten(FlotaReten flotaReten) throws Exception;
	
	
	public List<FlotaReten> listaFlotaRetensActivas() throws Exception;
	
	
}

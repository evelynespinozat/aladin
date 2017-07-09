package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.TrackingEncomienda;

public interface TrackingEncomiendaMapper {

	
	@Select("select * from t_trackingEncomienda order by IdEncomienda asc")
	public List<TrackingEncomienda> findAll() throws Exception;
	

	@Select("select * from t_trackingEncomienda where IdEncomienda = #{idEncomienda}")
	public TrackingEncomienda findById(@Param("idEncomienda") Integer idEncomienda) throws Exception;

	
	
	@Delete("delete  from t_trackingEncomienda  where IdEncomienda = #{idEncomienda}")
	@Options(flushCache=true)
	public void eliminarTrackingEncomienda(@Param("idEncomienda") Integer idEncomienda) throws Exception;
	
	@Insert("insert into t_trackingEncomienda (IdEncomienda,IdEstado,EstadoActual,IdBus,idProgramacion) values (#{idEncomienda},#{idEstado},#{estadoActual},#{idBus},#{idProgramacion})")
	public void crearTrackingEncomienda(TrackingEncomienda trackingEncomienda) throws Exception;
	
	
	@Update("update t_trackingEncomienda set IdEncomienda = #{idEncomienda}, IdEstado=#{idEstado} ,EstadoActual=#{estadoActual} where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTrackingEncomienda(TrackingEncomienda trackingEncomienda) throws Exception;
	
	// 19/03/2014
	@Update("update t_trackingEncomienda set EstadoActual= 0 where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEstadoAnterior(@Param("idEncomienda")Integer idEncomienda) throws Exception;
	
}

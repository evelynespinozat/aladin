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
import com.certicom.ittsa.domain.TrackingGiro;

public interface TrackingGiroMapper {

	
	@Select("select * from t_trackingGiro order by IdGiro asc")
	public List<TrackingGiro> findAll() throws Exception;
	

	@Select("select * from t_trackingGiro where IdGiro = #{idGiro}")
	public TrackingGiro findById(@Param("idGiro") Integer idGiro) throws Exception;

	
	
	@Delete("delete  from t_trackingGiro  where IdGiro = #{idGiro}")
	@Options(flushCache=true)
	public void eliminarTrackingGiro(@Param("idEncomienda") Integer idEncomienda) throws Exception;
	
	@Insert("insert into t_trackingGiro (IdGiro,IdEstado,EstadoActual) values (#{idGiro},#{idEstado},#{estadoActual})")
	public void crearTrackingGiro(TrackingGiro trackingGiro) throws Exception;
	
	
	@Update("update t_trackingGiro set IdGiro = #{idGiro}, IdEstado=#{idEstado} ,EstadoActual=#{estadoActual} where IdGiro= #{idGiro}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTrackingGiro(TrackingGiro trackingGiro) throws Exception;
	
	
}

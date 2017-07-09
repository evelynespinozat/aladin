package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.Destino;

public interface DestinoMapper {

	
//	@Select("select * from dbo.t_destino  order by origen asc")
	public List<Destino> findAll() throws Exception;
	
	@Select("select * from t_destino where IdDestino=#{idDestino}")
	public Destino findById(@Param("idDestino") Integer idDestino) throws Exception;
	
	public void registrarDestino(Destino destino) throws Exception;
	
	@Options(flushCache=true,useCache=true)
	public void actualizarDestino(Destino destino) throws Exception;
	
	@Delete("delete from dbo.t_destino where IdDestino=#{idDestino}")
	public void eliminarDestino(@Param("idDestino") Integer idDestino) throws Exception;
	
	@Select("select d.IdDestino, a.Idagencia as Destino, a.Descripcion as desDestino, d.Origen from t_destino d inner join t_agencia a on a.Idagencia = d.Destino where d.Origen = #{idOrigen} and d.Estado = 1")
	public List<Destino> obtenerDestino(@Param("idOrigen") Integer idOrigen) throws Exception;
	
	@Select("select * from t_destino where Origen = #{origen} and Destino = #{destino} and Estado = 1")
	public Destino obtenerRecorridoAproximado(@Param("origen")Integer origen ,@Param("destino") Integer destino) throws Exception;
	
	@Select("select count(*) from t_destino where Origen = #{origen} and Destino = #{destino} ")
	public Integer validarDestinoRepetido(@Param("origen")Integer origen ,@Param("destino") Integer destino) throws Exception;
}

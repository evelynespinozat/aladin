package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.GuiaRemision;

public interface GuiaRemisionMapper {

	
	@Select("select * from t_guiaremision order by NumeroGuia asc")
	public List<GuiaRemision> findAll() throws Exception;
	
	@Select("select * from t_guiaremision where IdGuiaRemision = #{idGuiaRemision}")
	public GuiaRemision findById(@Param("idGuiaRemision") Integer idGuiaRemision) throws Exception;

	
	@Delete("delete  from t_guiaremision  where IdGuiaRemision = #{idGuiaRemision}")
	@Options(flushCache=true)
	public void eliminarGuiaRemision(@Param("idGuiaRemision") Integer idGuiaRemision) throws Exception;
	
	@Insert("insert into t_guiaremision (SerieGuia, NumeroGuia, IdBus, FRegistro, FEmision, IdOrigen, IdDestino, IdEncomienda,numeroGuiaCliente) "+
			"values (#{serieGuia},#{numeroGuia},#{idBus},#{fRegistro},#{fEmision},#{idOrigen},#{idDestino},#{idEncomienda},#{numeroGuiaCliente})")
	public void crearGuiaRemision(GuiaRemision guiaRemision) throws Exception;
	
	
	@Update("update t_guiaremision set SerieGuia = #{serieGuia}, NumeroGuia=#{numeroGuia} ,IdBus=#{idBus}, FRegistro =#{fRegistro}, "+
			"FEmision = #{fEmision}, IdOrigen = #{idOrigen}, IdDestino = #{idDestino}, IdEncomienda = #{idEncomienda}, numeroGuiaCliente = #{numeroGuiaCliente} "
			+ " where IdGuiaRemision= #{idGuiaRemision}")
	@Options(flushCache=true,useCache=true)
    public void actualizarGuiaRemision(GuiaRemision guiaRemision) throws Exception;
	
	
	@Select("SELECT MAX(IdGuiaRemision) from t_guiaremision")
	public Integer findLast() throws Exception;
	
}

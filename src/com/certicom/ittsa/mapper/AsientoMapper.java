package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Asiento;

public interface AsientoMapper {

	
	//@Select("select * from dbo.t_clase  order by descripcion asc")
	public List<Asiento> findAll() throws Exception;
	
	@Select("select * from t_asiento where idAsiento=#{p_IdAsiento}")
	public Asiento findById(@Param("p_IdAsiento") Integer p_IdAsiento) throws Exception;
	
	@Select("select count(*) from t_asiento where IdClase=#{p_IdClase}")
	public Integer findByClase(@Param("p_IdClase") Integer p_IdClase) throws Exception;
	
	@Select("select * from t_asiento where IdClase=#{p_IdClase}  and Piso = #{p_piso} order by  nroOrden  ASC")
	public List<Asiento> findAsientoByClase(@Param("p_IdClase") Integer p_IdClase, @Param("p_piso") Integer p_piso) throws Exception;
	
        public List<Asiento> findAsientoByProgramacion(@Param("p_idprogramacion") Integer p_IdProgramacion, @Param("p_piso") Integer p_piso) throws Exception;
	
	public void registrarAsientos(Asiento asiento) throws Exception;
	
	@Options(flushCache=true,useCache=true)
	public void actualizarAsientos(Asiento asiento) throws Exception;
	
	
	@Delete("update dbo.t_asiento set visible =#{p_visible},estado =#{p_estado}  where IdAsiento=#{p_IdAsiento}")
	@Options(flushCache=true,useCache=true)
	public void actualizarVisibilidad(@Param("p_visible") Boolean visible,@Param("p_estado") Boolean estado,@Param("p_IdAsiento") Integer IdAsiento) throws Exception;
	
	
	@Delete("delete from dbo.t_asiento where IdAsiento=#{p_IdAsiento}")
	public void eliminarAsientos(@Param("p_IdAsiento") Integer IdAsiento) throws Exception;
	
	@Delete("delete from t_asiento where IdClase = #{IdClase}")
	public void eliminarAsientosxClase(Integer IdClase) throws Exception;
	
	
	
	@Update("update dbo.t_asiento set numero =#{p_numero},visible =#{p_visible}   where nroOrden=#{p_nroOrden} and piso=#{p_piso} and IdClase = #{p_idClase}")
	@Options(flushCache=true,useCache=true)
	public void actualizarAsiento32(@Param("p_numero") Integer  numero,@Param("p_visible") Boolean  visible,@Param("p_nroOrden") Integer nroOrden,@Param("p_piso") Integer piso,@Param("p_idClase") Integer idClase) throws Exception;
	
	
	
	@Update("update dbo.t_asiento set  nroOrden=#{p_nroOrden} where IdAsiento = #{p_idasiento}")
	@Options(flushCache=true,useCache=true)
	public void actualizarOrdenAsiento(@Param("p_nroOrden") Integer  numeroOrden,@Param("p_idasiento") Integer idAsiento) throws Exception;
	
	
}

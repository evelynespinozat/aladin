package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Servicio;

public interface ClaseServicioMapper {

	
	@Select("select * from dbo.t_clase  order by descripcion asc")
	public List<ClaseServicio> findAll() throws Exception;
	
	@Select("SELECT IDENT_CURRENT('t_clase')")
	public Integer findLast() throws Exception;
	
	@Select("select * from dbo.t_clase where Estado = 1  order by descripcion asc")
	public List<ClaseServicio> listaCServiciosActivos() throws Exception;
	
	@Select("select * from t_clase where IdClase=#{p_idclase}")
	public ClaseServicio findById(@Param("p_idclase") Integer idclase) throws Exception;
	
	
	@Select("select * from dbo.t_clase  where asientos = #{p_nroAsientos}")
	public ClaseServicio findByNroAsientos(@Param("p_nroAsientos") Integer nroAsientos) throws Exception;
	
	public void registrarClaseServicio(ClaseServicio claseServicio) throws Exception;
	
	@Options(flushCache=true,useCache=true)
	public void actualizarClaseServicio(ClaseServicio claseServicio) throws Exception;
	
	
	@Update("update t_clase set nroColumnasPisoUno=#{p_nroColumnasPisoUno},nroColumnasPisoDos=#{p_nroColumnasPisoDos},asientos=#{p_nroAsientos}  where IdClase=#{p_idclase}")
	@Options(flushCache=true,useCache=true)
	public void actualizarColumnasClaseServicio(
			@Param("p_nroColumnasPisoUno") Integer nroColumnasPisoUno,
			@Param("p_nroColumnasPisoDos") Integer nroColumnasPisoDos,
			@Param("p_nroAsientos") Integer asientos,
			@Param("p_idclase") Integer idclase ) throws Exception;
	
	
	@Update("update t_clase set asientos=#{p_nroAsientos}  where IdClase=#{p_idclase}")
	@Options(flushCache=true,useCache=true)
	public void actualizarNroAsientos(@Param("p_nroAsientos") Integer asientos,	@Param("p_idclase") Integer idclase ) throws Exception;
	
	@Delete("delete from dbo.t_clase where IdClase=#{p_idclase}")
	public void eliminarClaseServicio(@Param("p_idclase") Integer idclase) throws Exception;
	
	@Select("select count(*) from t_clase where IdCatServicio=#{IdCatServicio}")
	public Integer cantClasexCServicio(Integer IdCatServicio) throws Exception;
	
	@Select("SELECT  COUNT(*) FROM t_asiento WHERE IdClase = #{idClase} AND Estado = 1")
	public Integer obtenerCantidadAsientos(@Param("idClase") Integer idClase) throws Exception;
	
	@Select("SELECT COUNT(*) FROM t_clase WHERE IdTipoAsientoP1 = #{idTipoAsiento}  or IdTipoAsientoP2 = #{idTipoAsiento} ")
	public Integer cantClaseServicioxTipoAsiento(@Param("idTipoAsiento") Integer idTipoAsiento)throws Exception;
	
	@Select("select cls.atnbordo,cls.asientos,cls.nombrecorto,cls.Descripcion,cls.nroPisos,cls.color,cls.IdTipoAsientoP1,cls.IdTipoAsientoP2 from t_servicio  srv, t_clase cls where srv.IdClase = cls.IdClase and idcatservicio = 4 and  srv.IdServicio = #{p_idservicio}")
	public ClaseServicio findByServicio(@Param("p_idservicio") Integer IdServicio) throws Exception;
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.HistorialMantenimiento;

public interface HistorialMantenimientoMapper {

	
	@Select("select * from t_historialmantenimiento order by IdHistoMant asc")
	public List<HistorialMantenimiento> findAll() throws Exception;
	
	@Select("select * from t_historialmantenimiento where IdHistoMant = #{idHistoMant}")
	public HistorialMantenimiento findById(@Param("idHistoMant") Integer idHistoMant) throws Exception;
	
	@Delete("delete  from t_historialmantenimiento  where IdHistoMant = #{idHistoMant}")
	@Options(flushCache=true)
	public void eliminarHistorialMantenimiento(@Param("idHistoMant") Integer idHistoMant) throws Exception;
	
	@Insert("insert into t_historialmantenimiento (IdMecanico, FecInicio, FecFin, TipMantenimiento, TipMantEfectuado, Cantidad, Observacion, IdFlotaAuto, EstadoMecanico, FecAsignaMecanico, kmRecibido, TipoServicio) values (#{idMecanico},#{fecInicio},#{fecFin},#{tipMantenimiento},#{tipMantEfectuado},#{cantidad},#{observacion},#{idFlotaAuto},#{estadoMecanico},#{fecAsignaMecanico},#{kmRecibido},#{tipoServicio})")
	public void crearHistorialMantenimiento(HistorialMantenimiento historialMantenimiento) throws Exception;
	
	
	@Update("update t_historialmantenimiento set IdMecanico = #{idMecanico}, FecInicio=#{fecInicio} , FecFin=#{fecFin}, TipMantenimiento =#{tipMantenimiento}, TipMantEfectuado = #{tipMantEfectuado}, Cantidad = #{cantidad}, Observacion = #{observacion}, IdFlotaAuto = #{idFlotaAuto} where IdHistoMant= #{idHistoMant}")
	@Options(flushCache=true,useCache=true)
    public void actualizarHistorialMantenimiento(HistorialMantenimiento historialMantenimiento) throws Exception;
	
	@Select("select MAX(IdHistoMant) from t_historialmantenimiento ")
	public Integer findByLastId() throws Exception;
	
	@Update("update t_historialmantenimiento set TipMantEfectuado = #{tipMantEfectuado}, Cantidad = #{cantidad}, Observacion = #{observacion}, FecMantenimiento = #{fecMantenimiento}, EstadoMecanico = #{estadoMecanico}, TipoAccion = #{tipoAccion}  where IdHistoMant= #{idHistoMant}")
	@Options(flushCache=true,useCache=true)
    public void actualizarObsHistorialMantenimiento(HistorialMantenimiento historialMantenimiento) throws Exception;
	
	
	@Select("select h.idHistoMant, m.Nombre +' '+ m.ApePaterno +' '+ m.ApeMaterno as nombreMeca, au.IdAutomotor as idAutomotor,au.Descripcion as desAutomotor, a.Descripcion as desAutoparte, a.IdAutoparte as idAutoparte, a.KmCambio as kmCambio, f.KmActual as kmActual "+    
			"from (((t_historialmantenimiento h "+
					 "inner join t_mecanico m on h.IdMecanico = m.IdMecanico) "+
					 "inner join t_flotaautoparte f on h.IdFlotaAuto = f.IdFlotaAuto) "+
					 "inner join t_autoparte a on  f.IdAutoparte = a.IdAutoparte) "+
					 "inner join t_automotor au on au.IdAutomotor = f.IdAutomotor "+ 
					 "where f.IdBus = #{idBus} "+
					 "and h.TipoServicio = 'PREVENTIVO' "+
					 "and h.EstadoMecanico = 1")
	public List<HistorialMantenimiento> obtenerAutopartesConMecanico(Flota flota) throws Exception;
	
	@Select("select h.idHistoMant, m.Nombre +' '+ m.ApePaterno +' '+ m.ApeMaterno as nombreMeca, au.IdAutomotor as idAutomotor,au.Descripcion as desAutomotor, a.Descripcion as desAutoparte, a.IdAutoparte as idAutoparte, a.KmCambio as kmCambio, f.KmActual as kmActual "+    
			"from (((t_historialmantenimiento h "+
					 "inner join t_mecanico m on h.IdMecanico = m.IdMecanico) "+
					 "inner join t_flotaautoparte f on h.IdFlotaAuto = f.IdFlotaAuto) "+
					 "inner join t_autoparte a on  f.IdAutoparte = a.IdAutoparte) "+
					 "inner join t_automotor au on au.IdAutomotor = f.IdAutomotor "+ 
					 "where f.IdBus = #{idBus} "+
					 "and h.TipoServicio = 'CORRECTIVO' "+
					 "and h.EstadoMecanico = 1")
	public List<HistorialMantenimiento> obtenerAutoConMecanicoCorrectivo(Flota flota) throws Exception;
	
	public List<HistorialMantenimiento> obtenerHistorialFlota(Flota flota)throws Exception;
	
	public List<HistorialMantenimiento> obtenerMecanicoAsignado(HistorialMantenimiento historialMantenimiento) throws Exception;
}

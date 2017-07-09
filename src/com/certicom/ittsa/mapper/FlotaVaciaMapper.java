package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.FlotaVacia;

public interface FlotaVaciaMapper {

	
	/*
	@Select("select fv.*, f.Numero as numeroBus, o.Descripcion as desOrigen, d.Descripcion as desDestino  from t_flota_vacia fv inner join t_flota f "
			+ "on fv.idBus = f.IdBus inner join t_agencia o on fv.idOrigen = o.Idagencia "
			+ "inner join t_agencia d on d.Idagencia = fv.idDestino order by fv.fechaSalida desc")
			*/
	@Select("select fl.idFlotaVacia,fl.idBus,fl.idOrigen,fl.idDestino, (select numero from t_flota flt where flt.IdBus = fl.idBus) as numeroBus, (select ag.descripcion from t_agencia ag where ag.Idagencia = fl.idOrigen) as desOrigen,(select ag.descripcion from t_agencia ag where ag.Idagencia = fl.idDestino) as desDestino,(select pl.Nombres +' '+pl.ApellidoPaterno + ' '+pl.ApellidoMaterno from dbo.t_piloto pl where pl.IdPiloto = fl.idpiloto) as namePiloto,(select pl.Nombres +' '+pl.ApellidoPaterno + ' '+pl.ApellidoMaterno from dbo.t_piloto pl where pl.IdPiloto = fl.idcopiloto) as nameCopiloto,fl.fechaSalida, fl.motivo,fl.observacion,fl.idpiloto,fl.idcopiloto from dbo.t_flota_vacia fl ")
	public List<FlotaVacia> findAll() throws Exception;

	@Select("select fv.*, f.Numero as numeroBus, o.Descripcion as desOrigen, d.Descripcion as desDestino,(select pl.Nombres +' '+pl.ApellidoPaterno + ' '+pl.ApellidoMaterno from dbo.t_piloto pl where pl.IdPiloto = fv.idpiloto) as namePiloto,(select pl.Nombres +' '+pl.ApellidoPaterno + ' '+pl.ApellidoMaterno from dbo.t_piloto pl where pl.IdPiloto = fv.idcopiloto) as nameCopiloto  from t_flota_vacia fv inner join t_flota f "
			+ "on fv.idBus = f.IdBus inner join t_agencia o on fv.idOrigen = o.Idagencia "
			+ "inner join t_agencia d on d.Idagencia = fv.idDestino "
			+ "where fv.idBus = #{idBus} and fv.fechaSalida >=#{fecIni} and fv.fechaSalida<=#{fecFin} "
			+ "order by fv.fechaSalida desc")
	public List<FlotaVacia> rptUnidadVaciasxFiltros(FlotaVacia flotaVacia) throws Exception;
	

	@Select("select * from t_flota_vacia where idFlotaVacia = #{idFlotaVacia}")
	public FlotaVacia findById(@Param("idFlotaVacia") Integer idFlotaVacia) throws Exception;
	
	
	@Delete("delete  from t_flota_vacia  where idFlotaVacia = #{idFlotaVacia}")
	@Options(flushCache=true)
	public void eliminarFlotaVacia(@Param("idFlotaVacia") Integer idFlotaVacia) throws Exception;
	
	@Insert("insert into t_flota_vacia (idBus, idOrigen, idDestino, fechaSalida, fRegistro, motivo,observacion,pilotCopilotReasignado,idpiloto,idcopiloto) "
			+ "values (#{idBus},#{idOrigen},#{idDestino},#{fechaSalida},#{fRegistro},#{motivo},#{observacion},#{pilotCopilotReasignado},#{idpiloto},#{idcopiloto})")
	public void crearFlotaVacia(FlotaVacia flotaVacia) throws Exception;
	
	
	@Update("update t_flota_vacia set idBus = #{idBus},idOrigen=#{idOrigen} ,idDestino=#{idDestino}, fechaSalida =#{fechaSalida},fRegistro = #{fRegistro},motivo = #{motivo},observacion = #{observacion},pilotCopilotReasignado=#{pilotCopilotReasignado},idpiloto=#{idpiloto},idcopiloto=#{idcopiloto} where idFlotaVacia= #{idFlotaVacia}")
	@Options(flushCache=true,useCache=true)
    public void actualizarFlotaVacia(FlotaVacia flotaVacia) throws Exception;
	
	@Select("select * from t_flota_vacia where Estado = 1 ")
	public List<FlotaVacia> listaFlotaVaciasActivas() throws Exception;
	
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.certicom.ittsa.domain.Historial_Laboral;

public interface Historial_LaboralMapper {

	@Select("select * from t_historial_laboral ")
	public List<Historial_Laboral> findAll() throws Exception;

	@Select("SELECT IDENT_CURRENT('t_historial_laboral')")
	public Integer findLast() throws Exception;

	@Select("select * from t_historial_laboral where IdHistorial = #{idHistorial}")
	public Historial_Laboral findById(@Param("idHistorial") Integer idHistorial)
			throws Exception;

//	@Select("select * from t_historial_laboral where IdPiloto = #{pilotoId}")
	public List<Historial_Laboral> findByPilotoId(@Param("pilotoId") Integer idPiloto) throws Exception;

//	@Select("select * from t_historial_laboral where IdTerramoza = #{idTerramoza}")
	public List<Historial_Laboral> findByTerramozaId(@Param("idTerramoza") Integer idTerramoza) throws Exception;

	@Delete("delete  from t_historial_laboral  where IdHistorial = #{idHistorial}")
	@Options(flushCache = true)
	public void eliminarHistorial(@Param("idHistorial") Integer idHistorial)
			throws Exception;

	@Insert("insert into t_historial_laboral (IdPiloto,UltimaEmpresa,UltimoCargo,MotivoCese,AnioUltimaEmpresa,ModalidadContrato,NombreUltimoJefe,RemuneracionUltimaEmpresa,TelefonoUltimaEmpresa,Observacion,MesIniUltimaEmpresa, AnioFinUltimaEmpresa, MesFinUltimaEmpresa,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{idPiloto},#{ultimaEmpresa},#{ultimoCargo},#{motivoCese},#{anioUltimaEmpresa},#{modalidadContrato},#{nombreUltimoJefe},#{remuneracionUltimaEmpresa},#{telefonoUltimaEmpresa},#{observacion},#{mesIniUltimaEmpresa},#{anioFinUltimaEmpresa}, #{mesFinUltimaEmpresa},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearHistorial_Piloto(Historial_Laboral historial)
			throws Exception;

	@Insert("insert into t_historial_laboral (IdTerramoza,UltimaEmpresa,UltimoCargo,MotivoCese,AnioUltimaEmpresa,ModalidadContrato,NombreUltimoJefe,RemuneracionUltimaEmpresa,TelefonoUltimaEmpresa, AnioFinUltimaEmpresa, MesFinUltimaEmpresa, MesIniUltimaEmpresa,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{idTerramoza},#{ultimaEmpresa},#{ultimoCargo},#{motivoCese},#{anioUltimaEmpresa},#{modalidadContrato},#{nombreUltimoJefe},#{remuneracionUltimaEmpresa},#{telefonoUltimaEmpresa},#{anioFinUltimaEmpresa}, #{mesFinUltimaEmpresa}, #{mesIniUltimaEmpresa},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearHistorial_Terramoza(Historial_Laboral historial)
			throws Exception;

}

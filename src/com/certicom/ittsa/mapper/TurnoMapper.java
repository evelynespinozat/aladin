package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Turno;

public interface TurnoMapper {

	
	@Select("select * from t_turno order by descripcion asc")
	public List<Turno> findAll() throws Exception;
	

	@Select("select * from t_turno where IdTurno = #{idTurno}")
	public Turno findById(@Param("idTurno") Integer idTurno) throws Exception;
	
	@Delete("delete  from t_turno  where IdTurno = #{idTurno}")
	@Options(flushCache=true)
	public void eliminarTurno(@Param("idTurno") Integer idTurno) throws Exception;
	
	@Insert("insert into t_turno (Descripcion, HInicio, HFin, horaInicio, horaFin, Estado) values (#{descripcion},#{hInicio},#{hFin},#{horaInicio},#{horaFin},#{estado})")
	public void crearTurno(Turno turno) throws Exception;
	
	@Select("select * from t_turno where Estado = 1 order by descripcion asc")
	public List<Turno> listarTurnoActivos() throws Exception;
	
	//@Update("update t_turno set Descripcion = #{descripcion},HInicio=#{hInicio},HFin=#{hFin},Estado=#{estado} where IdTurno= #{idTurno}")
	//@Options(flushCache=true,useCache=true)
    public void actualizarTurno(Turno turno) throws Exception;
	
}

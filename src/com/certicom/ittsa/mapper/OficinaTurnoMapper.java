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
import com.certicom.ittsa.domain.OficinaTurno;

public interface OficinaTurnoMapper {

	
	@Select("select * from t_oficinaturno order by IdAgenciaTurno asc")
	public List<OficinaTurno> findAll() throws Exception;
	

	@Select("select * from t_oficinaturno where IdAgenciaTurno = #{idAgenciaTurno}")
	public OficinaTurno findById(@Param("idAgenciaTurno") Integer idAgenciaTurno) throws Exception;
	
	@Delete("delete  from t_oficinaturno  where IdAgenciaTurno = #{idAgenciaTurno}")
	@Options(flushCache=true)
	public void eliminarOficinaTurno(@Param("idAgenciaTurno") Integer idAgenciaTurno) throws Exception;
	
	@Insert("insert into t_oficinaturno (IdOficina, IdTurno, Estado) values (#{idOficina},#{idTurno},#{estado})")
	public void crearOficinaTurno(OficinaTurno oficinaTurno) throws Exception;
	
	
	@Update("update t_oficinaturno set IdOficina = #{idOficina},IdTurno=#{idTurno},Estado = #{estado} where IdAgenciaTurno= #{idAgenciaTurno}")
	@Options(flushCache=true,useCache=true)
    public void actualizarOficinaTurno(OficinaTurno oficinaTurno) throws Exception;
	
	public List<OficinaTurno> listaOficinaTurno() throws Exception;
	
	@Select("select COUNT(*) from t_oficinaturno where IdTurno = #{idTurno}")
	public Integer cantTurnAgencia(@Param("idTurno") Integer idTurno) throws Exception;
	
	
}

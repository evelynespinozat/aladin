package com.certicom.ittsa.mapper;

import org.apache.ibatis.annotations.Insert;

import com.certicom.ittsa.domain.EncomiendaAlmacen;
import com.certicom.ittsa.domain.EncomiendaIncidencia;

public interface EncomiendaIncidenciaMapper {
	
	@Insert("insert into t_encomienda_incidencia(IdEncomienda,IdUsuario,observacion,IdAgencia,IdOficina,tipo) values(#{idEncomienda},#{idUsuario},#{observacion},#{idAgencia},#{idOficina},#{tipo})")
	public void registrarEncomiendaIncidencia(EncomiendaIncidencia encomiendaIncidencia) throws Exception;
	
}

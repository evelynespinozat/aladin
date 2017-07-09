package com.certicom.ittsa.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.EncomiendaAlmacen;

public interface EncomiendaAlmacenMapper {
	
	@Insert("insert into t_encomienda_almacen(idEncomienda,fRegistro,fVencimiento,idAlmacen,idoficina,habido) "
			+ "values(#{idEncomienda},#{fRegistro},#{fVencimiento},#{idAlmacen},#{idoficina},#{habido})")
	public void registrarEncomiendaAlmacen(EncomiendaAlmacen encomiendaAlmacen) throws Exception;
	
	@Update("update t_encomienda_almacen set habido = 0 where idEncomienda=#{idEncomienda}")
	@Options(flushCache=true)
	public void actualizarExistenciaEncomienda(@Param("idEncomienda")Integer idEncomienda) throws Exception;
	
}

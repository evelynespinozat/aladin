package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.CierreCaja;

public interface CierreCajaMapper {

	@Select("select * from t_cierrecaja")
	public List<CierreCaja> findAll() throws Exception;

	@Select("select * from t_cierrecaja where IdCierreCaja = #{idCierreCaja}")
	public CierreCaja findById(@Param("idCierreCaja") Integer idCierreCaja) throws Exception;

	@Insert("insert into t_cierrecaja (IdUsuario, Saldo, FCierre, IdPuntoVenta, IdUsuarioReceptor, TipoUsuario) values (#{idUsuario},#{saldo},#{fCierre},#{idPuntoVenta},#{idUsuarioReceptor},#{tipoUsuario})")
	public void crearCierreCaja(CierreCaja cierreCaja) throws Exception;
	
	@Delete("delete from t_cierrecaja where IdCierreCaja = #{idCierreCaja}")
	public void deleteCierreCaja(@Param("idCierreCaja")Integer idCierreCaja);
	
	@Select("select * from t_cierrecaja where IdUsuario = #{idUsuario} and FCierre = #{fCierre} and TipoUsuario = 'COUNTER' ")
	public List<CierreCaja> verificarCierrexUsuarioFechaCounter(@Param("idUsuario") Integer idUsuario,@Param("fCierre") Date fCierre) throws Exception;
    
	@Select("select * from t_cierrecaja where IdUsuario = #{idUsuario} and FCierre = #{fCierre} and TipoUsuario = 'JEFE COUNTER' ")
	public List<CierreCaja> verificarCierrexUsuarioFechaJCounter(@Param("idUsuario") Integer idUsuario,@Param("fCierre") Date fCierre) throws Exception;

}

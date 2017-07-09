package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.IngresoEgreso;
import com.certicom.ittsa.domain.Oficina;

public interface IngresoEgresoMapper {

	
	@Select("select * from t_ingresoegreso order by descripcion asc")
	public List<IngresoEgreso> findAll() throws Exception;
	
	@Select("select * from t_ingresoegreso where idingresoegreso = #{idingresoegreso}")
	public IngresoEgreso findById(@Param("idingresoegreso") Integer idingresoegreso) throws Exception;
	
	@Delete("delete  from t_ingresoegreso  where idingresoegreso = #{idingresoegreso}")
	@Options(flushCache=true)
	public void eliminarIngresoEgreso(@Param("idingresoegreso") Integer idingresoegreso) throws Exception;
	
	@Insert("insert into t_ingresoegreso (fechareg, operacion, usuario, usuarioreg, monto, idagencia, idoficina, motivo, estado) values (#{fechareg},#{operacion},#{usuario},#{usuarioreg},#{monto},#{idagencia},#{idoficina},#{motivo},#{estado})")
	public void crearIngresoEgreso(IngresoEgreso ingresoEgreso) throws Exception;
	
	@Update("update t_ingresoegreso set Descripcion = #{Descripcion},Direccion=#{Direccion} ,Telefono1=#{Telefono1}, Telefono2 =#{Telefono2},Email = #{Email},Grupo = #{Grupo},Estado = #{Estado} where idingresoegreso= #{idingresoegreso}")
	@Options(flushCache=true,useCache=true)
    public void actualizarIngresoEgreso(IngresoEgreso ingresoEgreso) throws Exception;
	
	public List<IngresoEgreso> listaIngresoEgresoxFechasOperacion(IngresoEgreso ingresoEgreso) throws Exception;
}

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

public interface AgenciaMapper {

	
	@Select("select * from t_agencia order by descripcion asc")
	public List<Agencia> findAll() throws Exception;
	

	@Select("select * from t_agencia where Idagencia = #{idagencia}")
	public Agencia findById(@Param("idagencia") Integer idagencia) throws Exception;
	
	
	@Select("select * from t_agencia where descripcion = #{p_descripcion}")
	public Agencia findByDescripcion(@Param("p_descripcion") String Descripcion) throws Exception;
	
	
	@Delete("delete  from t_agencia  where Idagencia = #{p_agenciaId}")
	@Options(flushCache=true)
	public void eliminarAgencia(@Param("p_agenciaId") Integer agenciaID) throws Exception;
	
	@Insert("insert into t_agencia (Descripcion, direccion, telefono1, telefono2, Email, grupo,estado) values (#{Descripcion},#{Direccion},#{Telefono1},#{Telefono2},#{Email},#{Grupo},#{Estado})")
	public void crearAgencia(Agencia agencia) throws Exception;
	
	
	@Update("update t_agencia set Descripcion = #{Descripcion},Direccion=#{Direccion} ,Telefono1=#{Telefono1}, Telefono2 =#{Telefono2},Email = #{Email},Grupo = #{Grupo},Estado = #{Estado} where Idagencia= #{Idagencia}")
	@Options(flushCache=true,useCache=true)
    public void actualizarAgencia(Agencia agencia) throws Exception;
	
	
	@Update("update t_agencia set diascroquis = #{diascroquis} where Idagencia= #{Idagencia}")
	@Options(flushCache=true,useCache=true)
    public void actualizarAgenciaNroDias(Agencia agencia) throws Exception;
	
	
	@Select("select * from t_agencia where Estado = 1 order by descripcion asc")
	public List<Agencia> listaAgenciasActivas() throws Exception;
	
	@Update("update t_agencia set Combustible = #{combustible} where Idagencia = #{idagencia}")
	@Options(flushCache=true,useCache=true)
	public void actualizarCombustible(Agencia agencia) throws Exception;
	
	@Select("select * from t_agencia ag inner join t_destino ds on ag.Idagencia=ds.Destino  where ds.Origen=#{Origen} order by ag.descripcion ASC ")
	public List<Agencia> listarAsociados(@Param("Origen")Integer Origen)throws Exception;

}

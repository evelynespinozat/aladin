package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Oficina;

public interface OficinaMapper {

	
	@Select("select * from t_agenciaoficina order by Nombre asc")
	public List<Oficina> findAll() throws Exception;
	

	@Select("select * from t_agenciaoficina where IdOficina = #{idOficina}")
	public Oficina findById(@Param("idOficina") Integer idOficina) throws Exception;
	
	@Delete("delete  from t_agenciaoficina  where IdOficina = #{idOficina}")
	@Options(flushCache=true)
	public void eliminarOficina(@Param("idOficina") Integer idOficina) throws Exception;
	
	@Insert("insert into t_agenciaoficina (IdAgencia,Tipo,Nombre, Direccion,Telefono2,Telefono1,email,SerieBoletoManual,UltimoNumero,Estado,Prefijo,UltimoGiroTel,IdUsuarioAdm,EsTerminal) values (#{idAgencia},#{tipo},#{nombre},#{direccion},#{telefono2},#{telefono1},#{email},#{serieBoletoManual},#{ultimoNumero},#{estado},#{prefijo},#{ultimoGiroTel},#{idUsuarioAdm},#{esTerminal})")
	public void crearOficina(Oficina oficina) throws Exception;
	
	
	@Update("update t_agenciaoficina set IdAgencia=#{idAgencia},Tipo=#{tipo},Nombre=#{nombre}, Direccion =#{direccion},Telefono2 = #{telefono2},Telefono1 = #{telefono1},Email = #{email},SerieBoletoManual = #{serieBoletoManual},UltimoNumero = #{ultimoNumero},Estado = #{estado},Prefijo = #{prefijo},UltimoGiroTel = #{ultimoGiroTel},IdUsuarioAdm = #{idUsuarioAdm},EsTerminal = #{esTerminal} where IdOficina= #{idOficina}")
	@Options(flushCache=true,useCache=true)
    public void actualizarOficina(Oficina oficina) throws Exception;
	
	public List<Oficina> listaOficinas() throws Exception;
	
	
	@Select("select o.IdOficina, a.Idagencia as IdAgencia, a.Descripcion as desAgencia, o.Tipo,o.Nombre,o.Direccion,o.Telefono1,o.Telefono2,o.Email,o.SerieBoletoManual,o.UltimoNumero,o.Estado,o.EsTerminal from t_agenciaoficina o ,t_agencia a where o.IdAgencia = a.Idagencia and a.Idagencia = #{p_idAgencia} and o.Estado = 1 ORDER BY desAgencia, O.Nombre")
	public List<Oficina> listaOficinasXAgencia(@Param("p_idAgencia") Integer IdAgencia  ) throws Exception;
	
	
	@Select("select * from t_agenciaoficina where Estado = 1")
	public List<Oficina> listaOficinasActivas() throws Exception;
	
	@Select("select * from t_agenciaoficina where IdAgencia= #{IdAgencia} and Estado = 1")
	public List<Oficina> getOficinasxAgencia(@Param("IdAgencia") Integer IdAgencia);

	@Select("select count(*) from t_agenciaoficina where IdAgencia= #{IdAgencia} and Estado = 1")
	public Integer cantOfixAgencia(@Param("IdAgencia") Integer IdAgencia);
	
	
	@Select("select * from t_agenciaoficina where  Nombre =#{nombre} and idagencia=#{idagencia}")
	public Oficina findByNombre(@Param("nombre") String nombre,@Param("idagencia") Integer idagencia) throws Exception;

	
}

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
import com.certicom.ittsa.domain.Transbordo;

public interface TransbordoMapper {

	
	@Select("select * from t_transbordo order by idTransbordo asc")
	public List<Transbordo> findAll() throws Exception;
	

	@Select("select * from t_transbordo where IdTransbordo = #{idTransbordo}")
	public Transbordo findById(@Param("idTransbordo") Integer idTransbordo) throws Exception;
	
	@Delete("delete  from t_transbordo  where IdTransbordo = #{idTransbordo}")
	@Options(flushCache=true)
	public void eliminarTransbordo(@Param("IdTransbordo") Integer idTransbordo) throws Exception;
	
	@Insert("insert into t_transbordo (Descripcion, direccion, telefono1, telefono2, Email, grupo,estado) values (#{Descripcion},#{Direccion},#{Telefono1},#{Telefono2},#{Email},#{Grupo},#{Estado})")
	public void crearTransbordo(Transbordo transbordo) throws Exception;
	
	
	@Update("update t_transbordo set Descripcion = #{Descripcion},Direccion=#{Direccion} ,Telefono1=#{Telefono1}, Telefono2 =#{Telefono2},Email = #{Email},Grupo = #{Grupo},Estado = #{Estado} where Idagencia= #{Idagencia}")
	//@Update("update t_cliente set razonsocial_cliente=#{razonsocial_cliente},direccion_cliente=#{direccion_cliente} ,contacto_cliente=#{direccion_cliente},telefono_cliente=#{telefono_cliente}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTransbordo(Transbordo transbordo) throws Exception;
	
	public List<Transbordo> listaTransbordos();

}

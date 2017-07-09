package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.UsuarioPerfil;


public interface UsuarioPerfilMapper {
	
	public List<UsuarioPerfil> listarPerfilesPorUsuario(Integer usuarioId) throws Exception;
	
	public void insertUsuarioPeril(UsuarioPerfil usuarioPerfil) throws Exception;
	
	@Select("select * from t_usuarioxperfil p where p.idusuario = #{p_usuarioId} and p.cod_perfil = #{p_perfilId}")
	public UsuarioPerfil buscarPerfilUsuario(@Param("p_usuarioId") Integer idusuario,@Param("p_perfilId") Integer idperfil) throws Exception;
	
	@Delete("delete  from t_usuarioxperfil  where idusuario = #{p_usuarioId} and cod_perfil = #{p_perfilId}")
	@Options(flushCache=true)
	public void eliminarPerfilUsuario(@Param("p_usuarioId") Integer idusuario,@Param("p_perfilId") Integer idperfil) throws Exception;
	
	@Select("select COUNT(cod_perfil) from t_usuarioxperfil where cod_perfil =  #{cod_perfil}")
	public Integer countPerfilxId(@Param("cod_perfil") Integer cod_perfil) throws Exception;
	
}

	
	

	



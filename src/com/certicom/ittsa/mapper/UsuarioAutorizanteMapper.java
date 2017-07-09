package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.UsuarioAutorizante;

public interface UsuarioAutorizanteMapper {

	
//	@Select("select * from dbo.t_usuarioautorizante  order by Nombres asc")
	public List<UsuarioAutorizante> findAll() throws Exception;
	
	@Select("select * from t_usuarioautorizante  where IdAutorizante=#{p_IdAutorizante}")
	public UsuarioAutorizante findById(@Param("p_IdAutorizante") Integer IdAutorizante) throws Exception;
	
	public void registrarUsuarioAutorizante(UsuarioAutorizante usuarioAutorizante) throws Exception;
	
	@Options(flushCache=true,useCache=true)
	public void actualizarUsuarioAutorizante(UsuarioAutorizante usuarioAutorizante) throws Exception;
	
	@Delete("delete from dbo.t_usuarioautorizante where IdAutorizante=#{p_IdAutorizante}")
	public void eliminarUsuarioAutorizante(@Param("p_IdAutorizante") Integer IdAutorizante) throws Exception;
	
	@Select("select * from dbo.t_usuarioautorizante where Estado = 1 order by Nombres asc")
	public List<UsuarioAutorizante> listaUsuarioAutorizanteActivos() throws Exception;
	
}

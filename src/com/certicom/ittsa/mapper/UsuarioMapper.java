package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.Usuario;

public interface UsuarioMapper {
    public List<Usuario> getlistaUsuario();
	public List<Usuario> buscarPorLoginClave(Usuario usuario);
	public List<Usuario> buscarPorDNI(String dni);
	public List<Usuario> buscarPorNombre(String nombre);
	public List<Usuario> buscarPorApellido(String apellido);
	public List<Usuario> buscarusuario(Usuario usuario);
	public Usuario buscarPorId(int idusuario);
	
	public void insertUsuario(Usuario usuario);
	
	public void actualizarEstado(Usuario usuario) throws Exception;
	public void actualizar(Usuario usuario);
	public void actualizarLogin(Usuario usuario);
	public void actualizarPassword(Usuario usuario);
	public void actualizarFechaAcceso(Usuario usuario);

	public List<Usuario> getlistaUsuario_byCentroAtencion(Integer id_centro_atencion);
	
	public List<Usuario> getlistaUsuario_Perfil(Usuario usuario);
	
	public Usuario buscarPorLogin(String string) throws Exception;
	
	public void deleteUsuario(Integer idusuario) throws Exception;
	
	public List<Usuario> listarUsuariosXPerfilProceso(String proceso) throws Exception;
	public Usuario getUsuarioxDni(String dni);
	
	public List<Usuario> listarUsuariosActivos();
	
	@Select("select count(*) from t_usuario where idpuntoventa =  #{idPuntoVenta}")
	public int obtenerPuntoVentaxUsu(@Param("idPuntoVenta") Integer idPuntoVenta) throws Exception;
	
	@Select("select * from t_usuario where idusuario =  #{idusuario}")
	public Usuario getUsuario_byIdUsuario(Integer idusuario);
	
	@Select("select u.idusuario, u.dni, u.apellido_paterno, u.apellido_materno, u.nombre from t_usuario u inner join t_usuarioxperfil p on u.idusuario = p.idusuario where p.cod_perfil = #{codPerfil} and idoficina = #{idOficina}")
	public List<Usuario> buscarPorPerfil(@Param("idOficina")Integer idOficina,@Param("codPerfil") Integer codPerfil);
	
	@Select("select COUNT(*) from t_usuario where dni = #{dniUsuario}")
	public int verificarDniCarnetRepetidos(@Param("dniUsuario")String dniUsuario)throws Exception;
	
	@Select("select COUNT(*) from t_usuario where idpuntoventa = #{idPuentoVenta}")
	public int verificarPuntoVentaRepetidos(@Param("idPuentoVenta")Integer idPuentoVenta)throws Exception;
	
	@Select("select * from t_usuario where  idoficina = #{idOficina} order by apellido_paterno , apellido_materno, nombre;")
	public List<Usuario> buscarPorOficina(@Param("idOficina")Integer idOficina);
	
	@Select("select u.* from t_usuario u, t_asientoventa av where u.idusuario = av.idusuarioventa and av.idasientoventa =  #{idasientoventa}")
	public Usuario findByIdUsuarioVenta(@Param("idasientoventa")Integer idasientoventa);
	
	
}

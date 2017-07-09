package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.ListaNegra;

public interface ListaNegraMapper {
	
	@Insert("insert into t_listanegra (DNI,nombreCompleto,motivo,fRegistro,usuarioAutorizante,flagAutorizacion,fActualizacion,estado,usuarioRegistro,rutaOrigen,rutaDestino,fAproxIncidencia) "
			+ " values(#{DNI},#{nombreCompleto},#{motivo},#{fRegistro},#{usuarioAutorizante},#{flagAutorizacion},#{fActualizacion},#{estado},#{usuarioRegistro},#{rutaOrigen},#{rutaDestino},#{fAproxIncidencia})")
	public void registrarListaNegra(ListaNegra listaNegra) throws Exception;
	
	@Update("update t_listanegra set DNI = #{DNI}, nombreCompleto =#{nombreCompleto}, motivo =#{motivo},fRegistro =#{fRegistro}, usuarioAutorizante=#{usuarioAutorizante}, "
			+ "	flagAutorizacion = #{flagAutorizacion}, fActualizacion =#{fActualizacion}, estado=#{estado}, usuarioRegistro = #{usuarioRegistro}, rutaOrigen = #{rutaOrigen}, rutaDestino = #{rutaDestino},fAproxIncidencia = #{fAproxIncidencia} where idListaNegra=#{idListaNegra} ")
	@Options(flushCache=true)
	public void actualizarListaNegra(ListaNegra listaNegra) throws Exception;
	
	@Select("select l.idListaNegra,l.DNI,l.nombreCompleto,l.motivo,l.fRegistro,l.usuarioAutorizante,l.flagAutorizacion,l.fActualizacion,l.estado,l.usuarioRegistro, "
			+ "(select Nombres from t_usuarioautorizante where IdAutorizante = l.usuarioAutorizante) as nomUsuAutoizante  "
			+ "	from t_listanegra l order by fRegistro desc")
	public List<ListaNegra> findAll() throws Exception;
	
	@Select("select * from dbo.t_listanegra where dni=#{dni}")
	public ListaNegra findByIndividuo(@Param("dni") String dni) throws Exception;
		
	
	@Update("update t_listanegra set estado = #{estado} where idListaNegra =#{idListaNegra} ")
	@Options(flushCache=true)
	public void actualizarEstado(@Param("idListaNegra")Integer idListaNegra,@Param("estado") boolean estado)throws Exception;
	
	public void eliminarListaNegra(@Param("idListaNegra")Integer idListaNegra)throws Exception;
	
	

}

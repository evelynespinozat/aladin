package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Giro;
import com.certicom.ittsa.domain.Oficina;

public interface GiroMapper {

	@Select("select * from t_giro order by FRegistro desc")
	public List<Giro> findAll() throws Exception;

	@Select("select * from t_giro where IdGiro = #{idGiro}")
	public Giro findById(@Param("idGiro") Integer idGiro)
			throws Exception;

	@Select("SELECT TOP 1 * FROM t_giro where IdPuntoVentaOrigen=#{idPuntoVenta} ORDER BY IdGiro DESC ")
	public Giro findLastGiroByPV(@Param("idPuntoVenta") Integer idPuntoVenta) throws Exception;

	@Delete("delete  from t_giro  where IdGiro = #{idGiro}")
	@Options(flushCache = true)
	public void eliminarGiro(@Param("idGiro") Integer idGiro)
			throws Exception;

	@Insert("insert into t_giro (IdOrigen, IdDestino, IdUsuario, IdPuntoVentaOrigen,TipoPersona,TotalCobrado,"
			+ "RucRemitente,DniRemitente,ApellidosRemitente,TelefonoRemitente,RazonSocialRemitente,"
			+ "ApellidosDestinatario1,DniDestinatario1,TelefonoDestinatario1,RucDestinatario1,RazonSocialDestinatario1,"
			+ "DniDestinatario2,ApellidosDestinatario2,TelefonoDestinatario2,RucDestinatario2,RazonSocialDestinatario2,"
			+ "ObservacionEnvio,FRegistro,Estado,Documento,TipoDocumento,MontoGiro,NombresRemitente,NombresDestinatario1,NombresDestinatario2,IdOficina) "

			+ "values (#{idOrigen},#{idDestino},#{idUsuario},#{idPuntoVentaOrigen},#{tipoPersona},#{totalCobrado},"
			+ "#{rucRemitente},#{dniRemitente},#{apellidosRemitente},#{telefonoRemitente},#{razonSocialRemitente},"
			+ "#{apellidosDestinatario1},#{dniDestinatario1},#{telefonoDestinatario1},#{rucDestinatario1},#{razonSocialDestinatario1},"
			+ "#{dniDestinatario2},#{apellidosDestinatario2},#{telefonoDestinatario2},#{rucDestinatario2},#{razonSocialDestinatario2},"
			+ "#{observacionEnvio},#{fRegistro},#{estado},#{documento},#{tipoDocumento},#{montoGiro},#{nombresRemitente},#{nombresDestinatario1},#{nombresDestinatario2},#{idOficina})")
	public void crearGiro(Giro giro) throws Exception;

	@Update("update t_giro set IdOrigen=#{idOrigen},IdDestino=#{idDestino} ,TipoPersona=#{tipoPersona}, "
			+ "IdUsuario =#{idUsuario},IdPuntoVentaOrigen = #{idPuntoVentaOrigen}, TotalCobrado = #{totalCobrado},"
			+ "RucRemitente = #{rucRemitente},DniRemitente = #{dniRemitente},ApellidosRemitente = #{apellidosRemitente},TelefonoRemitente=#{telefonoRemitente},"
			+ "ApellidosDestinatario1 = #{apellidosDestinatario1},DniDestinatario1 = #{dniDestinatario1},TelefonoDestinatario1 = #{telefonoDestinatario1},RucDestinatario1 = #{rucDestinatario1},"
			+ "DniDestinatario2 = #{dniDestinatario2},ApellidosDestinatario2 = #{apellidosDestinatario2},TelefonoDestinatario2 = #{telefonoDestinatario2},RucDestinatario2 = #{rucDestinatario2},"
			+ "ObservacionEnvio = #{observacionEnvio},ObservacionRecojo = #{observacionRecojo},FRegistro = #{fRegistro},"
			+ "TipoVenta = #{tipoVenta},Estado = #{estado}, Documento = #{documento},NombresRemitente = #{nombresRemitente}, NombresDestinatario1 = #{nombresDestinatario1}, NombresDestinatario2 = #{nombresDestinatario2},"
			+ "RazonSocialRemitente = #{razonSocialRemitente},RazonSocialDestinatario1 = #{razonSocialDestinatario1},RazonSocialDestinatario2 = #{razonSocialDestinatario2},IdOficina = #{idOficina} "
			+ "where IdGiro= #{idGiro}")
	@Options(flushCache = true, useCache = true)
	public void actualizarGiro(Giro giro) throws Exception;

	@Update("update t_giro set  Estado = #{estado} where IdGiro= #{idGiro}")
	@Options(flushCache = true, useCache = true)
	public void actualizarEstadoGiro(Giro giro) throws Exception;

	@Select("select * from t_giro where IdOrigen=#{idOrigen} and IdDestino=#{idDestino} and FRegistro between #{fechaInicio} and #{fechaFin} and Estado!=10 order by FRegistro desc")
	public List<Giro> findGirosxCobrarByFecha(@Param("idOrigen") Integer idOrigen,@Param("idDestino") Integer idDestino,@Param("fechaInicio") Date fechaInicio,@Param("fechaFin") Date fechaFin) throws Exception;
	
	@Select("select * from t_giro where IdOrigen=#{idOrigen} and IdDestino=#{idDestino} and Estado!=10 order by FRegistro desc")
	public List<Giro> findGirosxCobrarByOrig_Dest(@Param("idOrigen") Integer idOrigen,@Param("idDestino") Integer idDestino) throws Exception;

	@Select("Select * from t_giro where Estado = 1 order by FRegistro desc")
	public List<Giro> girosxCobrar() throws Exception;
	
	public List<Giro> buscarGirosxCobrar(Giro giro) throws Exception;
}

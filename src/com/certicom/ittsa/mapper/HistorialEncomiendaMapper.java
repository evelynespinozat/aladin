package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.HistorialEncomienda;
import com.certicom.ittsa.domain.Oficina;

public interface HistorialEncomiendaMapper {

	
	@Select("select * from t_historialEncomienda")
	public List<HistorialEncomienda> findAll() throws Exception;
	

	@Select("select * from t_historialEncomienda where IdHistorial = #{idHistorial}")
	public HistorialEncomienda findById(@Param("idHistorial") Integer idHistorial) throws Exception;
	
	@Select("select * from t_historialEncomienda where IdEncomienda = #{idEncomienda}")
	public HistorialEncomienda findByIdEncomienda(@Param("idEncomienda") Integer idEncomienda) throws Exception;
	
	//@Select("select * from t_historialEncomienda where DniRemitente = #{dniRemitente}")
	@Select("select h.IdHistorial, h.DniRemitente, h.ApellidosRemitente, h.NombresRemitente, h.TelefonoRemitente, h.RucRemitente, h.RazonSocialRemitente, "+
			"h.DniDestinatario1, h.ApellidosDestinatario1, h.NombresDestinatario1, h.TelefonoDestinatario1, h.RucDestinatario1, h.RazonSocialDestinatario1, "+
			"h.DniDestinatario2, h.ApellidosDestinatario2, h.NombresDestinatario2, h.TelefonoDestinatario2, h.RucDestinatario2, h.RazonSocialDestinatario2, "+
			"h.DireccionEnvio, h.IdEncomienda, e.IdTarifa, e.PrecioReparto, e.precioExtraRapido  from t_historialEncomienda h inner join t_encomienda e on h.IdEncomienda = e.IdEncomienda where h.DniRemitente = #{dniRemitente} ")
	public List<HistorialEncomienda> findByDNIRemitente(@Param("dniRemitente") String dniRemitente) throws Exception;
	
	
	@Delete("delete  from t_historialEncomienda  where IdHistorial = #{idHistorial}")
	@Options(flushCache=true)
	public void eliminarHistorialEncomienda(@Param("idHistorial") Integer idHistorial) throws Exception;
	
	@Insert("insert into t_historialEncomienda (DniRemitente, ApellidosRemitente,NombresRemitente, TelefonoRemitente, RucRemitente, DniDestinatario1, ApellidosDestinatario1, NombresDestinatario1,TelefonoDestinatario1,RucDestinatario1, DniDestinatario2, ApellidosDestinatario2, NombresDestinatario2,TelefonoDestinatario2,RucDestinatario2,DireccionEnvio,razonSocialRemitente,razonSocialDestinatario1,razonSocialDestinatario2,IdEncomienda) "
			+ "values (#{dniRemitente},#{apellidosRemitente},#{nombresRemitente},#{telefonoRemitente},#{rucRemitente},#{dniDestinatario1},#{apellidosDestinatario1},#{nombresDestinatario1},#{telefonoDestinatario1},#{rucDestinatario1},#{dniDestinatario2},#{apellidosDestinatario2},#{nombresDestinatario2},#{telefonoDestinatario2},#{rucDestinatario2},#{direccionEnvio},#{razonSocialRemitente},#{razonSocialDestinatario1},#{razonSocialDestinatario2},#{idEncomienda})")
	public void crearHistorialEncomienda(HistorialEncomienda historialEncomienda) throws Exception;
	
	
	@Update("update t_historialEncomienda set DniRemitente = #{dniRemitente},Remitente=#{remitente} ,TelefonoRemitente=#{telefonoRemitente}, RucRemitente =#{rucRemitente},"
			+ "DniDestinatario1 = #{dniDestinatario1},Destinatario1 = #{destinatario1},TelefonoDestinatario1 = #{telefonoDestinatario1}, RucDestinatario1 =#{rucDestinatario1}, "
			+ "DniDestinatario2 = #{dniDestinatario2},Destinatario2 = #{destinatario2},TelefonoDestinatario2 = #{telefonoDestinatario2}, RucDestinatario2 =#{rucDestinatario2}, "
			+ "DireccionEnvio = #{direccionEnvio} where Idagencia= #{Idagencia}")
	@Options(flushCache=true,useCache=true)
    public void actualizarHistorialEncomienda(HistorialEncomienda historialEncomienda) throws Exception;
	
	
}

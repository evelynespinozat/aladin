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
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Programacion;

public interface EncomiendaMapper {

	
	@Select("select *,(select descripcion from t_estado where valor=Estado  and  modulo = 'ENCOMIENDA') as desEstado from t_encomienda order by FRegistro desc")
	public List<Encomienda> findAll() throws Exception;
	

	@Select("select * from t_encomienda where IdEncomienda = #{idEncomienda}")
	public Encomienda findById(@Param("idEncomienda") Integer idEncomienda) throws Exception;
	
	@Select("SELECT TOP 1 * FROM t_encomienda where IdPuntoVentaOrigen=#{idPuntoVenta} ORDER BY IdEncomienda DESC ")
	public Encomienda findLastEncomiendaByPV(@Param("idPuntoVenta") Integer idPuntoVenta) throws Exception;
	
	
	@Delete("delete  from t_encomienda  where IdEncomienda = #{idEncomienda}")
	@Options(flushCache=true)
	public void eliminarEncomienda(@Param("idEncomienda") Integer idEncomienda) throws Exception;
	
	@Insert("insert into t_encomienda (IdOrigen, IdDestino, IdUsuario, IdPuntoVentaOrigen,TipoPersona,TotalCobrado,"
			+ "RucRemitente,DniRemitente,ApellidosRemitente,TelefonoRemitente,DireccionRemitente, RazonSocialRemitente,"
			+ "ApellidosDestinatario1,DniDestinatario1,TelefonoDestinatario1,DireccionDestinatario1, RucDestinatario1,RazonSocialDestinatario1,"
			+ "DniDestinatario2,ApellidosDestinatario2,TelefonoDestinatario2, DireccionDestinatario2, RucDestinatario2,RazonSocialDestinatario2,"
			+ "ObservacionEnvio,FRegistro,DireccionEnvio,Estado,Comprobante,TipoDocumento,IdTarifa,PrecioReparto,NombresRemitente,"
			+ "NombresDestinatario1,NombresDestinatario2,PesoTotal,ServicioEntrega,precioExtraRapido,idOficina) "
			
			+ "values (#{idOrigen},#{idDestino},#{idUsuario},#{idPuntoVentaOrigen},#{tipoPersona},#{totalCobrado},"
			+ "#{rucRemitente},#{dniRemitente},#{apellidosRemitente},#{telefonoRemitente},#{DireccionRemitente}, #{razonSocialRemitente},"
			+ "#{apellidosDestinatario1},#{dniDestinatario1},#{telefonoDestinatario1},#{DireccionDestinatario1}, #{rucDestinatario1},#{razonSocialDestinatario1},"
			+ "#{dniDestinatario2},#{apellidosDestinatario2},#{telefonoDestinatario2},#{DireccionDestinatario2}, #{rucDestinatario2},#{razonSocialDestinatario2},"
			+ "#{observacionEnvio},#{fRegistro},#{direccionEnvio},#{estado},#{comprobante},#{tipoDocumento},#{idTarifa},#{precioReparto}"
			+ ",#{nombresRemitente},#{nombresDestinatario1},#{nombresDestinatario2},#{pesoTotal},#{servicioEntrega},#{precioExtraRapido},#{idOficina})")
	public void crearEncomienda(Encomienda encomienda) throws Exception;
	
	@Update("update t_encomienda set IdOrigen=#{idOrigen},IdDestino=#{idDestino} ,TipoPersona=#{tipoPersona}, "
			+ "IdUsuario =#{idUsuario},IdPuntoVentaOrigen = #{idPuntoVentaOrigen}, TotalCobrado = #{totalCobrado},"
			+ "RucRemitente = #{rucRemitente},DniRemitente = #{dniRemitente},ApellidosRemitente = #{apellidosRemitente},NombresRemitente = #{nombresRemitente},TelefonoRemitente=#{telefonoRemitente}, DireccionRemitente=#{DireccionRemitente}, "
			+ "ApellidosDestinatario1 = #{apellidosDestinatario1},NombresDestinatario1 = #{nombresDestinatario1},DniDestinatario1 = #{dniDestinatario1},TelefonoDestinatario1 = #{telefonoDestinatario1}, DireccionDestinatario1 = #{DireccionDestinatario1}, RucDestinatario1 = #{rucDestinatario1},"
			+ "DniDestinatario2 = #{dniDestinatario2},ApellidosDestinatario2 = #{apellidosDestinatario2},NombresDestinatario2 = #{nombresDestinatario2},TelefonoDestinatario2 = #{telefonoDestinatario2}, DireccionDestinatario2 = #{DireccionDestinatario2}, RucDestinatario2 = #{rucDestinatario2},"
			+ "ObservacionEnvio = #{observacionEnvio},ObservacionRecojo = #{observacionRecojo},FRegistro = #{fRegistro},"
			+ "TipoVenta = #{tipoVenta},Estado = #{estado},PesoTotal = #{pesoTotal}, DireccionEnvio = #{direccionEnvio}, Comprobante = #{comprobante}, "
			+ "RazonSocialRemitente = #{razonSocialRemitente},RazonSocialDestinatario1 = #{razonSocialDestinatario1},RazonSocialDestinatario2 = #{razonSocialDestinatario2},"
			+ "IdTarifa = #{idTarifa}, PrecioReparto = #{precioReparto} "
			+ "where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEncomienda(Encomienda encomienda) throws Exception;
	
	@Update("update t_encomienda set  Estado = #{estado} where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEstadoEncomienda(Encomienda encomienda) throws Exception;
	
	public List<Encomienda> listarEncomiendasporFiltros(Encomienda encomienda);
	
	public List<Encomienda> listarEncomiendasRecibidas(Encomienda encomienda) throws Exception;
	
	@Update("update t_encomienda set Estado = #{estado},idProgramacion = #{idProgramacion} where IdEncomienda= #{idencomienda}")
	@Options(flushCache=true,useCache=true)
	public void actualizarEstadoEncomienda2(@Param("idencomienda")Integer idencomienda,@Param("estado")Integer estado,@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	@Select("select * from t_encomienda where IdOrigen=#{idOrigen} and IdDestino=#{idDestino} and FRegistro between #{fechaInicio} and #{fechaFin} and Estado in (4,5) order by FRegistro desc")
	public List<Encomienda> findEncomiendaxCobrarByFecha(@Param("idOrigen") Integer idOrigen,@Param("idDestino") Integer idDestino,@Param("fechaInicio") Date fechaInicio,@Param("fechaFin") Date fechaFin) throws Exception;
	
	@Select("select * from t_encomienda where IdOrigen=#{idOrigen} and IdDestino=#{idDestino} and Estado in (4,5) order by FRegistro desc")
	public List<Encomienda> findEncomiendaxCobrarByOrig_Dest(@Param("idOrigen") Integer idOrigen,@Param("idDestino") Integer idDestino) throws Exception;
	
	// 20/03/2014
	public List<Encomienda> listarEncomiendasEmbarcadas(Encomienda encomienda) throws Exception;

	public List<Encomienda> listarEncomiendasDesembarcadasReparto(Encomienda encomienda) throws Exception;
	
	@Update("update t_encomienda set idPersonalReparto =#{idPersonalReparto} where IdEncomienda = #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
	public void actualizarRepartidor(@Param("idEncomienda") Integer idEncomienda,@Param("idPersonalReparto") Integer idPersonalReparto) throws Exception ;
	
	public List<Encomienda> listarEncomiendasDesembarcadas(Encomienda encomienda) throws Exception;
	
	@Update("update t_encomienda set IdUsuarioEntrega = #{idUsuarioEntrega}, NombreRecoge = #{nombreRecoge}, NumeroDocRecoge = #{numeroDocRecoge},FRecojo =#{fRecojo},observacionRecojo= #{observacionRecojo} where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
	public void actualizarValoresRecepcion(Encomienda encomienda)throws Exception;
	
	public List<Encomienda> rptListarEncomiendasEmbarcadas(Encomienda encomienda);
	
	@Update("update t_encomienda set UbicacionAlmacen = #{ubicacionAlmacen} where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
	public void actualizarUbicacionAlmacenEncomienda(Encomienda encomienda)throws Exception;

	public List<Encomienda> encomiendasxRepartidor(Encomienda encomienda);
	
	public List<Encomienda> listarTrackingEncomiendas(Encomienda encomienda);

	public Encomienda obtenerDatosEncomienda(Integer idEncomienda) throws Exception;

	public List<Encomienda> listarEncomiendasInventario(Encomienda encomienda);
	
	
	@Update("update t_encomienda set  Estado = #{estado},obsReparto= #{obsReparto},condicionReparto=#{condicionReparto} where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEstadoDevueltoEncomienda(Encomienda encomienda) throws Exception;

	@Update("update t_encomienda set  ServicioEntrega = null where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
	public void actualizarTipoEntregaEncomienda(Encomienda encomienda) throws Exception;

	public List<Encomienda> listarEncomiendaSubManifiesto(Programacion programacion) throws Exception;
	
	@Update("update t_encomienda set  idSubmanifiesto = #{idSubmanifiesto} where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
	public void actualizarSubManEncomienda(@Param("idSubmanifiesto")Integer idSubmanifiesto,@Param("idEncomienda") Integer idEncomienda) throws Exception;

	@Update("update t_encomienda set  obsReparto = #{obsReparto} where idEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
	public void actualizarObsReparto(@Param("obsReparto")String obsReparto,@Param("idEncomienda") Integer idEncomienda) throws Exception;
	
	public List<Encomienda> rptEncomiendasContraEntrega(Encomienda filter) throws Exception;
	
	public List<Encomienda> listaEncomiendasxOficinas(Encomienda filter) throws Exception;
	
	public List<Encomienda> listaEncomiendasxOficinasxPersona(Encomienda filter) throws Exception;
	

}

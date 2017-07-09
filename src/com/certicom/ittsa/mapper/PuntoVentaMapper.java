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
import com.certicom.ittsa.domain.PuntoVenta;

public interface PuntoVentaMapper {

	
	@Select("select * from t_puntoventa order by NamePuntoVenta asc")
	public List<PuntoVenta> findAll() throws Exception;
	
	
	@Select("select * from t_puntoventa where IdPuntoVenta = #{idPuntoVenta}")
	public PuntoVenta findById(@Param("idPuntoVenta") Integer idPuntoVenta) throws Exception;
	
	@Delete("delete  from t_puntoventa  where IdPuntoVenta = #{idPuntoVenta}")
	@Options(flushCache=true)
	public void eliminarPuntoVenta(@Param("idPuntoVenta") Integer idPuntoVenta) throws Exception;
	
	@Insert("insert into t_puntoventa (IdOficina, NamePuntoVenta, NumeroIP, SerieBoleto, SerieBoleta, UltimoBoleto, UltimaBoleta, Tipo, Estado, SerieFactura, UltimaFactura, SerieGuiaRemision, UltimaGuia,seriereserva,nombreImpresora) "
			+ "values (#{idOficina},#{namePuntoVenta},#{numeroIP},#{serieBoleto},#{serieBoleta},#{ultimoBoleto},#{ultimaBoleta},#{tipo},#{estado},#{serieFactura},#{ultimaFactura},#{serieGuiaRemision},#{ultimaGuia},#{seriereserva},#{nombreImpresora})")
	public void crearPuntoVenta(PuntoVenta puntoVenta) throws Exception;
	
	
	@Update("update t_puntoventa set IdOficina = #{idOficina},"
			+ "NamePuntoVenta=#{namePuntoVenta} ,"
			+ "NumeroIP=#{numeroIP}, "
			+ "SerieBoleto =#{serieBoleto},"
			+ "SerieBoleta =#{serieBoleta},"
			+ "ultimoBoleto = #{ultimoBoleto},"
			+ "UltimaBoleta = #{ultimaBoleta},"
			+ "Tipo = #{tipo},"
			+ "Estado = #{estado},"
			+ "SerieFactura = #{serieFactura},"
			+ "UltimaFactura = #{ultimaFactura},"
			+ "SerieGuiaRemision = #{serieGuiaRemision},"
			+ "UltimaGuia = #{ultimaGuia},"
			+ "nombreImpresora = #{nombreImpresora},"
			+ "seriereserva = #{seriereserva} "
			+ "where IdPuntoVenta= #{idPuntoVenta}")
	@Options(flushCache=true,useCache=true)
    public void actualizarPuntoVenta(PuntoVenta puntoVenta) throws Exception;
	
	@Update("update t_puntoventa set "
			+ "SerieBoleto =#{serieBoleto},"
			+ "SerieBoleta =#{serieBoleta},"
			+ "ultimoBoleto = #{ultimoBoleto},"
			+ "UltimaBoleta = #{ultimaBoleta},"
			+ "SerieFactura = #{serieFactura},"
			+ "UltimaFactura = #{ultimaFactura},"
			+ "SerieGuiaRemision = #{serieGuiaRemision},"
			+ "UltimaGuia = #{ultimaGuia},"
			+ "seriereserva = #{seriereserva}, "
			+ "ultimareserva = #{ultimareserva}, "
			+ "SerieNotaCobranza = #{serieNotaCobranza}, "
			+ "UltimaNotaCobranza = #{ultimaNotaCobranza}, "
			+ "SeriePrepagado = #{seriePrepagado}, "
			+ "UltimoPrepagado = #{ultimoPrepagado} "
			+ "where IdPuntoVenta= #{idPuntoVenta}")
	@Options(flushCache=true,useCache=true)
    public void actualizarSeriePuntoVenta(PuntoVenta puntoVenta) throws Exception;
	
	@Update("update t_puntoventa set "
			+ "UltimaBoleta = #{ultimaBoleta} "
			+ "where IdPuntoVenta= #{idPuntoVenta} ")
	@Options(flushCache=true,useCache=true)
    public void actualizarUltimaBoletaPuntoVenta(PuntoVenta puntoVenta) throws Exception;
	
	@Update("update t_puntoventa set "
			+ "UltimaFactura = #{ultimaFactura} "
			+ "where IdPuntoVenta= #{idPuntoVenta}")
	@Options(flushCache=true,useCache=true)
    public void actualizarUltimaFacturaPuntoVenta(PuntoVenta puntoVenta) throws Exception;
	
	
	@Update("update t_puntoventa set "
			+ "UltimaGuia = #{ultimaGuia} "
			+ "where IdPuntoVenta= #{idPuntoVenta}")
	@Options(flushCache=true,useCache=true)
    public void actualizarUltimaGuiaRemisionPuntoVenta(PuntoVenta puntoVenta) throws Exception;
		
	public List<PuntoVenta> getPventasxOficina(@Param("idoficina") Integer idoficina) throws Exception;
	
	public List<PuntoVenta> listaPuntoVenta() throws Exception;

	public PuntoVenta obtenerPuntoVenta(@Param("idPuntoVenta") Integer idPuntoVenta) throws Exception;
	
	@Update("Update t_puntoventa set UltimoBoleto=#{ultimoBoleto} where IdPuntoVenta =#{idPuntoVenta}")
	@Options(flushCache=true,useCache=true)
	public void actualizarUltimoBoleto(@Param("idPuntoVenta")Integer idPuntoVenta, @Param("ultimoBoleto")Integer ultimoBoleto) throws Exception;

	//reserva
	@Update("Update t_puntoventa set ultimareserva=#{ultimareserva} where IdPuntoVenta =#{idPuntoVenta}")
	@Options(flushCache=true,useCache=true)
	public void actualizarUltimaReserva(@Param("idPuntoVenta")Integer idPuntoVenta, @Param("ultimareserva")Integer ultimareserva) throws Exception;
	
	
	//reserva
	@Update("Update t_puntoventa set ultimaboleta=#{ultimaboleta} where IdPuntoVenta =#{idPuntoVenta}")
	@Options(flushCache=true,useCache=true)
	public void actualizarUltimaBoleta(@Param("idPuntoVenta")Integer idPuntoVenta, @Param("ultimaboleta")Integer ultimaboleta) throws Exception;
	

	@Update("Update t_puntoventa set UltimaNotaCobranza=#{ultimaNotaCobranza} where IdPuntoVenta =#{idPuntoVenta}")
	@Options(flushCache=true,useCache=true)
	public void actualizarUltimaNotaCobranzaPuntoVenta(PuntoVenta puntoVenta)throws Exception;

	//prepagado
	@Update("Update t_puntoventa set UltimoPrepagado=#{ultimoPrepagado} where IdPuntoVenta =#{idPuntoVenta}")
	@Options(flushCache=true,useCache=true)
	public void actualizarUltimaPrepagadoPuntoVenta(PuntoVenta puntoVenta)throws Exception;
	
	@Select("select pv.* from t_usuario  u inner join t_puntoventa pv on u.idpuntoventa = pv.IdPuntoVenta where u.idusuario = #{idusuario}")
	public PuntoVenta getPuntoVentaxUsuario(@Param("idusuario")Integer idusuario) throws Exception;

	@Select("select pv.* from t_puntoventa  pv where pv.NumeroIP = #{numIP}")
	public List<PuntoVenta> getIP(@Param("numIP")String numeroIP); 
	
	@Select("select pv.* from t_puntoventa  pv where pv.nombreImpresora = #{nombreImpresora}")
	public PuntoVenta getImpresora(@Param("nombreImpresora")String nombreImpresora); 
	
	@Select("select * from t_puntoventa where NumeroIP=#{numeroIP} AND IdPuntoVenta = #{idPuntoVenta}")
	public PuntoVenta validarIp(PuntoVenta puntoventa)throws Exception;
	
	@Select("select * from t_puntoventa where nombreImpresora = #{nombreImpresora} AND IdPuntoVenta = #{idPuntoVenta}")
	public PuntoVenta validarImpresora(PuntoVenta puntoventa)throws Exception;
	
	@Select("select * from t_puntoventa where IdPuntoVenta != #{IdPuntoVenta} and NumeroIP = #{NumeroIP}")
	public List<PuntoVenta> buscarIPDiferentes(@Param("IdPuntoVenta")Integer IdPuntoVenta,@Param("NumeroIP")String NumeroIP );
	
	@Select("select pv.* from t_puntoventa  pv where pv.IdPuntoVenta != #{IdPuntoVenta} and pv.nombreImpresora = #{nombreImpresora}")
	public List<PuntoVenta> buscarImpresoraDiferentes(@Param("IdPuntoVenta")Integer IdPuntoVenta,@Param("nombreImpresora")String nombreImpresora); 
	
	@Select("select pv.* from t_puntoventa  pv where pv.nombreImpresora = #{nombreImpresora}")
	public List<PuntoVenta> buscarImpresora(@Param("nombreImpresora")String nombreImpresora); 
	
	
	@Select("select count(*) from t_oficina_almacen where idoficina =#{idOficina}")
	public int obtenerAlmacen(@Param("idOficina")Integer idOficina)throws Exception;
	
	@Select("select count(*) from t_componente c inner join t_puntoventa pv on c.IdPuntoVenta=pv.IdPuntoVenta where pv.IdPuntoVenta=#{IdPuntoVenta};")
	public int obtenerComponentesAsociados(@Param("IdPuntoVenta")Integer IdPuntoVenta)throws Exception;

	@Select("select pv.* from t_puntoventa  pv where pv.IdOficina = #{IdOficina}")
	public List<PuntoVenta> obtenerPuntoVentaxOficina(Integer IdOficina)throws Exception;

	
}
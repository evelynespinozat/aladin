package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.primefaces.extensions.renderkit.widget.Option;

import com.certicom.ittsa.domain.NotaCobranza;

public interface NotaCobranzaMapper {

	
	@Select("select * from t_notacobranza order by NumeroNotaCobranza asc")
	public List<NotaCobranza> findAll() throws Exception;
	
	@Select("select * from t_notacobranza where IdNotaCobranza = #{idNotaCobranza}")
	public NotaCobranza findById(@Param("idNotaCobranza") Integer idNotaCobranza) throws Exception;
	
	@Delete("delete from t_notacobranza where IdNotaCobranza = #{idNotaCobranza}")
	@Options(flushCache=true)
	public void eliminarNotaCobranza(@Param("idNotaCobranza") Integer idNotaCobranza) throws Exception;
	
	@Insert("insert into t_notacobranza (SerieNotaCobranza, NumeroNotaCobranza, FRegistro, FEmision, IdOrigen, IdDestino, IdEncomienda, Estado) values (#{serieNotaCobranza},#{numeroNotaCobranza},#{fRegistro},#{fEmision},#{idOrigen},#{idDestino},#{idEncomienda},#{estado})")
	public void crearNotaCobranza(NotaCobranza notaCobranza) throws Exception;
	
	@Update("update t_notacobranza set SerieNotaCobranza = #{serieNotaCobranza},NumeroNotaCobranza=#{numeroNotaCobranza} ,FRegistro=#{fRegistro}, FEmision =#{fEmision},IdOrigen = #{idOrigen},IdDestino = #{idDestino},IdEncomienda = #{idEncomienda},Estado = #{estado} where IdNotaCobranza= #{idNotaCobranza}")
	@Options(flushCache=true,useCache=true)
    public void actualizarNotaCobranza(NotaCobranza notaCobranza) throws Exception;
	
	@Select("select MAX(IdNotaCobranza) from t_notacobranza")
	public Integer findLast()throws Exception;
	
	@Select("select n.IdNotaCobranza, n.SerieNotaCobranza, n.FEmision, n.NumeroNotaCobranza, n.Estado, o.Descripcion as desOrigen, "
			+ " d.Descripcion as desDestino, e.RazonSocialRemitente, e.NombresRemitente, e.ApellidosRemitente, e.RucRemitente, e.DniRemitente, "
			+ "e.TipoDocumento as tipoDocumento, e.Comprobante as comprobante,e.TotalCobrado as totalCobrado,n.numeroFisico   "+    
			"from ((t_notacobranza n inner join t_agencia o on n.IdOrigen = o.Idagencia) "+ 
			"inner join t_agencia d on n.IdDestino = d.Idagencia) "+
			"inner join t_encomienda e on n.IdEncomienda = e.IdEncomienda  "+
			"where  n.IdOrigen = #{idOrigen} and n.IdDestino = #{idDestino} order by n.FRegistro desc")
	public List<NotaCobranza> buscarNotaCobranzaPorCobrar(@Param("idOrigen")Integer idOrigen,@Param("idDestino")Integer idDestino)throws Exception;
	
	@Update("Update t_notacobranza set Estado='PAGADO', numeroFisico=#{numeroFisico} where IdNotaCobranza = #{idNotaCobranza}")
	@Options(flushCache=true,useCache=true)
	public void pagarNotaCobranza(@Param("idNotaCobranza")Integer idNotaCobranza, @Param("numeroFisico")String numeroFisico)throws Exception;

}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.Promocion;

public interface PromocionMapper {

	
	@Select("select * from dbo.t_promocion  order by descripcion asc")
	public List<Promocion> findAll() throws Exception;
	
	@Select("select * from t_promocion where IdPromocion=#{IdPromocion}")
	public Promocion findById(@Param("IdPromocion") Integer IdPromocion) throws Exception;
	
	@Select("select * from t_promocion where idservicioida=#{idservicioida} and estado = 'true' and TipoViaje = 'IDA'")
	public Promocion findByServicioIda(@Param("idservicioida") Integer idservicioida) throws Exception;
	
	@Select("select * from t_promocion where idservicioida=#{idservicioida}  and estado = 'true' and TipoViaje = 'IDA-VUELTA'")
	public Promocion findByServicioIdaVuelta(@Param("idservicioida") Integer idservicioida) throws Exception;
	
	
	public void registrarPromocion(Promocion promocion) throws Exception;
	
	@Options(flushCache=true,useCache=true)
	public void actualizarPromocion(Promocion promocion) throws Exception;
	
	@Delete("delete from dbo.t_promocion where IdPromocion=#{IdPromocion}")
	public void eliminarPromocion(@Param("IdPromocion") Integer IdPromocion) throws Exception;
	
	@Select("select * from t_promocion where FInicio <= cast(GETDATE()as date) and cast(GETDATE()as date)<= FFin "
			+ " and (isTodos = 2 or (IdOrigen = #{origen} and IdDestino = #{destino})) and Estado = 1 order by FRegistro desc ")
	public List<Promocion> listPromocionesByFiltros(@Param("origen") Integer origen, @Param("destino") Integer destino);
	
	
}

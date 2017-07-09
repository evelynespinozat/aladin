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
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutoparte;

public interface FlotaAutoparteMapper {
	
	@Delete("delete  from t_flotaautoparte  where IdFlotaAuto = #{idFlotaAuto}")
	@Options(flushCache=true)
	public void eliminarFlotaAutoparte(@Param("idFlotaAuto") Integer idFlotaAuto) throws Exception;
	
	@Select("select * from t_flotaautoparte where IdFlotaAuto = #{idFlotaAuto}")
	public FlotaAutoparte findById(@Param("idFlotaAuto") Integer idFlotaAuto) throws Exception;
	
	@Insert("insert into t_flotaautoparte (IdBus, IdAutomotor, IdAutoparte, KmActual) values (#{idBus},#{idAutomotor},#{idAutoparte},#{kmActual})")
	public void crearFlotaAutoparte(FlotaAutoparte agencia) throws Exception;
	
	//modificado por daniel
	@Select("select fa.IdFlotaAuto, fa.IdBus, fa.IdAutomotor, fa.IdAutoparte, fa.KmActual, fa.KmAproximado, fa.FecCambio, fa.FecKmIncremento, fa.FecKmIncrementoAprox, a.Descripcion as desAutoparte, a.KmCambio as kmCambio "+
			"from t_flotaautoparte fa inner join t_autoparte a on a.IdAutoparte = fa.IdAutoparte "+
			"where fa.IdBus = #{idBus} and fa.IdAutomotor = #{idAutomotor} ")
	public List<FlotaAutoparte> listaAutopartesxAutomotor(@Param("idBus") Integer idBus,@Param("idAutomotor") Integer idAutomotor) throws Exception;
	
	@Update("update t_flotaautoparte set KmActual = KmActual + #{kmAdherir}, FecKmIncremento = #{fecKmIncremento} where IdBus = #{idBus} and IdAutomotor = #{idAutomotor} and IdAutoparte = #{idAutoparte}")
	@Options(flushCache=true)
	public void incrementarKm(FlotaAutoparte flotaAutoparte) throws Exception;
	
	public List<FlotaAutoparte> autopartesxCambio(FlotaAutoparte flotaAutoparte) throws Exception;
	
	public List<FlotaAutoparte> autopartesxCambioAprox(FlotaAutoparte flotaAutoparte) throws Exception;
	
	@Update("update t_flotaautoparte set KmAproximado = KmAproximado + #{kmAdherir}, FecKmIncrementoAprox = #{fecKmIncrementoAprox} where IdBus = #{idBus} ")
	@Options(flushCache=true)
	public void incrementarKmAproximado(FlotaAutoparte flotaAutoparte) throws Exception;
	
	@Update("update t_flotaautoparte set KmAproximado = KmAproximado - #{kmAdherir}, FecKmIncrementoAprox = #{fecKmIncrementoAprox} where IdBus = #{idBus} ")
	@Options(flushCache=true)
	public void retirarincrementarKmAproximado(FlotaAutoparte flotaAutoparte) throws Exception;
	
	public List<FlotaAutoparte> autopartesxBus(Flota flota) throws Exception;
	
	@Update("update t_flotaautoparte set KmActual = 0, KmAproximado = 0, KmUltimoCambio = #{kmUltimoCambio} where IdFlotaAuto = #{idFlotaAuto} ")
	@Options(flushCache=true)
	public void kmCero(FlotaAutoparte flotaAutoparte);
	
	public List<FlotaAutoparte> autopartesxBusAll(Flota flota) throws Exception;
}

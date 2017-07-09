package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutoparte;

public interface FlotaMapper {

	
	@Select("select f.IdBus, f.IdClase, f.Asientos, f.Numero, f.Placa, f.Marca, f.Modelo, f.Fabricacion, f.Recorrido, f.TPropiedad, f.InscritoMTC, f.Piloto, f.Copiloto, " +
			"f.TipoCombustible, f.Estado, f.idTipoFlota, f.IdCertiSat, f.Disponibilidad, c.Descripcion as descClase, t.Descripcion as desTipoFlota, f.KmAproximado, f.NroChasis as nroChasis, f.telefono " +
			"from (t_flota f inner join t_clase c on f.IdClase = c.IdClase) inner join t_tipoflota t on f.idTipoFlota = t.idTipoFlota " +
			"order by Numero asc")
	public List<Flota> findAll() throws Exception;
	
	
	
	@Select("select f.IdBus,f.IdClase,f.Numero,f.Asientos,f.Placa,f.Marca,f.Modelo, f.Fabricacion, f.Piloto, f.Copiloto, f.EStado, c.Descripcion as descClase "
			+ " from t_flota f, t_clase c where f.IdClase = c.IdClase order by f.numero asc")
	public List<Flota> findAll2() throws Exception;
	

	@Select("select * from t_flota where IdBus = #{idBus}")
	public Flota findById(@Param("idBus") Integer idBus) throws Exception;
	
	@Select("select c.Descripcion as descClase, f.IdBus, f.IdClase, f.Asientos, f.Numero, f.Marca, f.Piloto as piloto, f.Copiloto as copiloto, f.Estado, f.Modelo, f.Fabricacion, f.Placa from t_flota f, t_clase c where f.IdClase = #{idClase} and f.IdClase = c.IdClase "
			+ " and (f.Piloto is not null) and (f.Copiloto is not null)")
	public List<Flota> findByIdClase(@Param("idClase") Integer idClase) throws Exception;
	
	@Delete("delete  from t_flota  where IdBus = #{idBus}")
	@Options(flushCache=true)
	public void eliminarFlota(@Param("idBus") Integer idBus) throws Exception;
	
	public void crearFlota(Flota flota) throws Exception;
	
	@Options(flushCache=true,useCache=true)
    public void actualizarFlota(Flota flota) throws Exception;
	
	@Update("update t_flota set Piloto = #{piloto},Copiloto=#{copiloto} where IdBus= #{idBus}")
	@Options(flushCache=true,useCache=true)
    public void actualizarPiloto_Copiloto(Flota flota) throws Exception;
	
	@Update("update t_flota set Piloto = #{idPiloto} where IdBus= #{idBus}")
	@Options(flushCache=true,useCache=true)
    public void actualizarPiloto(@Param("idPiloto") Integer idPiloto,@Param("idBus") Integer idBus) throws Exception;
	
	@Update("update t_flota set Copiloto=#{idCopiloto} where IdBus= #{idBus}")
	@Options(flushCache=true,useCache=true)
    public void actualizarCopiloto(@Param("idCopiloto") Integer idCopiloto,@Param("idBus") Integer idBus) throws Exception;
	
	
	@Select("select * from t_flota where Estado = 1  and piloto is not null and Copiloto is not null order by Numero asc")
	public List<Flota> listaFlotaActivas() throws Exception;
	
	@Select("select * from t_flota where Estado = 1 order by Numero asc")
	public List<Flota> listaFlotaActivasOrderNum() throws Exception;
	
	@Update("Update t_flota set Recorrido = Recorrido + #{kmAdherir} where IdBus = #{idBus} ")
	@Options(flushCache=true, useCache=true)
	public void aumentarKilometraje(Flota flota) throws Exception;
	
	@Update("Update t_flota set kmAproximado = kmAproximado + #{kmDistancia} where IdBus = #{idBus} ")
	@Options(flushCache=true, useCache=true)
	public void aumentarKilometrajeAproximado(@Param("idBus")Integer idBus,@Param("kmDistancia") Integer kmDistancia)throws Exception;
	
	@Update("Update t_flota set kmAproximado = kmAproximado - #{kmDistancia} where IdBus = #{idBus}")
	@Options(flushCache=true, useCache=true)
	public void retirarKilometrajeAproximado(@Param("idBus")Integer idBus,@Param("kmDistancia") Integer kmDistancia)throws Exception;
	
	@Update("Update t_flota set kmAproximado = #{editKm} where IdBus = #{idBus}")
	@Options(flushCache=true, useCache=true)
	public void actualizarKmAproximado(@Param("idBus")Integer idBus,@Param("editKm")Integer editKm) throws Exception;
	
	public List<Flota> busesxCambio(FlotaAutoparte flotaAutoparte) throws Exception;
	
	public List<Flota> busesxCambioAprox(FlotaAutoparte flotaAutoparte) throws Exception;
	
	public List<Flota> busesxCambioAll() throws Exception;
	
	@Select("select f.IdBus, f.IdClase, f.Numero, f.Recorrido, f.Piloto, f.Copiloto, f.KmAproximado, s.HSalida, s.Descripcion as desServicio, s.IdServicio as idServicio " 
			+" from ((t_flota f left join t_clase c on f.IdClase = c.IdClase) "
	        +" inner join t_servicio s on s.IdClase = f.IdClase) "
	        +" where f.Piloto is not null and f.Copiloto is not null " 
			+" and f.IdClase = (select idclasecapacidad from t_clase a where a.IdClase = #{idClase} "
			+" and s.Origen = #{Origen} and s.Destino = #{Destino} ) ")
	public List<Flota> obtenerFlotaAsociadas(@Param("idClase") Integer idClase, @Param("Origen") Integer Origen, @Param("Destino") Integer Destino)throws Exception;
	
	@Update("Update t_flota set Recorrido = #{recorrido} where IdBus = #{idBus}")
	@Options(flushCache=true, useCache=true)
	public void actualizarRecorrido(@Param("idBus")Integer idBus,@Param("recorrido")Integer recorrido) throws Exception;
}

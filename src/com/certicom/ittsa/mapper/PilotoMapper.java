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
import com.certicom.ittsa.domain.Piloto;

public interface PilotoMapper {

	
	@Select("select * from t_piloto order by ApellidoPaterno asc")
	public List<Piloto> findAll() throws Exception;
	
	//@Select("select * from t_piloto where Asignado = 0 and cargo = 'PILOTO' and Estado = 1 order by ApellidoPaterno asc")
	@Select("select * from t_piloto where Asignado = 0  and Estado = 1 order by ApellidoPaterno asc")
	public List<Piloto> pilotosDisponibles()throws Exception;
	
	//@Select("select * from t_piloto where Asignado = 0 and cargo = 'COPILOTO' and Estado = 1 order by ApellidoPaterno asc")
	@Select("select * from t_piloto where Asignado = 0  and Estado = 1 order by ApellidoPaterno asc")
	public List<Piloto> copilotosDisponibles()throws Exception;
	
	@Select("SELECT IDENT_CURRENT('t_piloto')")
	public Integer findLast() throws Exception;
	
	@Select("select * from t_piloto where Estado=1 order by ApellidoPaterno asc")
	public List<Piloto> findByEstado() throws Exception;

	
	@Select("select * from t_piloto where IdPiloto = #{idPiloto}")
	public Piloto findById(@Param("idPiloto") Integer idPiloto) throws Exception;
	
	@Select("select * from t_piloto where DNI = #{dNI}")
	public Piloto findByDNI(@Param("dNI") String dni) throws Exception;
	
	
	@Delete("delete  from t_piloto  where IdPiloto = #{idPiloto}")
	@Options(flushCache=true)
	public void eliminarPiloto(@Param("idPiloto") Integer idPiloto) throws Exception;
	
	@Insert("insert into t_piloto (DNI, ApellidoPaterno,ApellidoMaterno, Nombres, Licencia, FNacimiento, CodigoAnterior,Telefono, Estado, Fingreso, Residencia,Cargo,Terminal,Ocupacion,GrdoInstruccion,NroCell,Correo,Sexo,Condicion,Imagen,sid_ubigeo,Disponibilidad,FechaCreacion,usuarioCreacion) "
			+ "values (#{dNI},#{apellidoPaterno},#{apellidoMaterno},#{nombres},#{licencia},#{fNacimiento},#{codigoAnterior},#{telefono},#{estado},#{fingreso},#{residencia},#{cargo},#{terminal},#{ocupacion},#{grdoInstruccion},#{nroCell},#{correo},#{sexo},#{condicion},#{imagen},#{sid_ubigeo},#{disponibilidad},#{FechaCreacion},#{usuarioCreacion})")
	public void crearPiloto(Piloto piloto) throws Exception;
	
	
	@Update("update t_piloto set DNI = #{dNI},ApellidoPaterno=#{apellidoPaterno},ApellidoMaterno=#{apellidoMaterno} ,Nombres=#{nombres}, Licencia =#{licencia},FNacimiento = #{fNacimiento},CodigoAnterior = #{codigoAnterior},Telefono = #{telefono},Estado = #{estado},Fingreso = #{fingreso},Residencia = #{residencia},Cargo = #{cargo},Terminal = #{terminal},Ocupacion = #{ocupacion},GrdoInstruccion = #{grdoInstruccion},NroCell= #{nroCell},Correo= #{correo},Sexo= #{sexo},Condicion= #{condicion},Imagen = #{imagen},sid_ubigeo = #{sid_ubigeo} where IdPiloto= #{idPiloto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarPiloto(Piloto piloto) throws Exception;
	
	@Update("update t_piloto set DNI = #{dNI},ApellidoPaterno=#{apellidoPaterno},ApellidoMaterno=#{apellidoMaterno} ,Nombres=#{nombres}, Licencia =#{licencia},FNacimiento = #{fNacimiento},CodigoAnterior = #{codigoAnterior},Telefono = #{telefono},Estado = #{estado},Fingreso = #{fingreso},Residencia = #{residencia},Cargo = #{cargo},Terminal = #{terminal},Ocupacion = #{ocupacion},GrdoInstruccion = #{grdoInstruccion},NroCell= #{nroCell},Correo= #{correo},Sexo= #{sexo},Condicion= #{condicion},sid_ubigeo = #{sid_ubigeo} where IdPiloto= #{idPiloto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarPilotoSnFoto(Piloto piloto) throws Exception;

	@Update("update t_piloto set UltimaEmpresa = #{ultimaEmpresa},UltimoCargo=#{ultimoCargo},MotivoCese=#{motivoCese},AnioUltimaEmpresa=#{anioUltimaEmpresa},ModalidadContrato=#{modalidadContrato},NombreUltimoJefe=#{nombreUltimoJefe},RemuneracionUltimaEmpresa=#{remuneracionUltimaEmpresa},TelefonoUltimaEmpresa=#{telefonoUltimaEmpresa} where IdPiloto= #{idPiloto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarPilotoLaboral(Piloto piloto) throws Exception;
	
	@Update("update t_piloto set EstCivil = #{estCivil},NombreEsposa=#{nombreEsposa},DNIESposa=#{dniEsposa} ,FechaNacEsposa=#{fechaNacEsposa},NombresHijo1=#{nombresHijo1},NombresHijo2=#{nombresHijo2},EmergenciaContacto=#{emergenciaContacto}  where IdPiloto= #{idPiloto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarPilotoFamiliar(Piloto piloto) throws Exception;
	
	@Select("select * from t_piloto where Estado = 1 order by ApellidoPaterno asc")
	public List<Piloto> listaPilotoActivas() throws Exception;
	
	@Select("select count(*) from t_piloto where dni = #{dni}")
	public Integer verificarDniCarnetRepetidos(@Param("dni")String dni)throws Exception;
	
	@Update("update t_piloto set asignado = #{asignado} where IdPiloto = #{idPiloto}")
	@Options(flushCache=true,useCache=true)
	public void actualizarAsignacion(@Param("idPiloto") Integer idPiloto, @Param("asignado") Boolean asignado)throws Exception;
	
	
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;



import com.certicom.ittsa.domain.UmbralCaptura;

public interface UmbralCapturaMapper {
	
	@Select("select *from t_umbral_captura ")
	public List<UmbralCaptura> findAll() throws Exception;

	@Select("select * from t_umbral_captura where id_captura = #{id_captura}")
	public UmbralCaptura findById(@Param("id_captura") Integer id_captura) throws Exception;
	
	@Delete("delete  from t_umbral_captura  where id_captura = #{id_captura}")
	@Options(flushCache=true)
	public void eliminarUmbralCaptura(@Param("id_captura") Integer id_captura) throws Exception;
	
	@Insert("insert into t_umbral_captura (id_captura,descripcion,estado) "
			+ " values (#{id_captura},#{descripcion},#{estado})")
	public void crearUmbralCaptura(UmbralCaptura umbralCaptura) throws Exception;
	
	
	@Update("update t_umbral_captura set estado = #{estado} where id_captura= #{id_captura}")
	@Options(flushCache=true,useCache=true)
    public void actualizarUmbralCaptura(UmbralCaptura umbralCaptura) throws Exception;
	
	
	@Select("select * from t_umbral_captura where Estado = 1 ")
	public List<UmbralCaptura> listaUmbralCapturaActivos() throws Exception;
	
	@Select("select * from t_umbral_captura where estado = #{estado}")
	public UmbralCaptura findByEstado(@Param("estado") Boolean estado)throws Exception;;

}

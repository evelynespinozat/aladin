package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Medida;

public interface MedidaMapper {
	
	@Select("select * from t_medida order by Descripcion asc")
	public List<Medida> findAll() throws Exception;
	

	@Select("select * from t_medida where CodigoMedida = #{CodigoMedida}")
	public Medida findById(@Param("CodigoMedida") String CodigoMedida) throws Exception;
	
	
	@Delete("delete  from t_medida  where CodigoMedida = #{CodigoMedida}")
	@Options(flushCache=true)
	public void eliminarMedida(@Param("CodigoMedida") String CodigoMedida) throws Exception;
	
	@Insert("insert into t_medida (CodigoMedida, Descripcion, Estado) values (#{CodigoMedida},#{Descripcion},#{Estado})")
	public void crearMedida(Medida medida) throws Exception;
	
	
	@Update("update t_medida set Descripcion = #{Descripcion},Estado=#{Estado} where CodigoMedida= #{CodigoMedida}")
	@Options(flushCache=true,useCache=true)
    public void actualizarMedida(Medida medida) throws Exception;
	
	
	@Select("select * from t_medida where Estado = 1 order by Descripcion asc")
	public List<Medida> listaMedidasActivas() throws Exception;
	
}

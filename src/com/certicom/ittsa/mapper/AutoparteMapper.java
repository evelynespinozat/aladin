package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Autoparte;

public interface AutoparteMapper {

	
	@Select("select * from t_autoparte order by descripcion asc")
	public List<Autoparte> findAll() throws Exception;
	

	@Select("select * from t_autoparte where IdAutoparte = #{idAutoparte}")
	public Autoparte findById(@Param("idAutoparte") Integer idAutoparte) throws Exception;
	
	@Delete("delete  from t_autoparte  where IdAutoparte = #{idAutoparte}")
	@Options(flushCache=true)
	public void eliminarAutoparte(@Param("idAutoparte") Integer idAutoparte) throws Exception;
	
	@Insert("insert into t_autoparte (IdAlmacen, IdProveedor, IdAutomotor, Descripcion, Valorizacion, Stock, KmCambio, Estado, codigo) values (#{idAlmacen},#{idProveedor},#{idAutomotor},#{descripcion},#{valorizacion},#{stock},#{kmCambio},#{estado},#{codigo})")
	public void crearAutoparte(Autoparte autoparte) throws Exception;
	
	
	@Update("update t_autoparte set IdAlmacen = #{idAlmacen},IdProveedor=#{idProveedor} ,IdAutomotor=#{idAutomotor}, Descripcion =#{descripcion},Valorizacion = #{valorizacion},Stock = #{stock},KmCambio = #{kmCambio},Estado = #{estado},codigo = #{codigo} where IdAutoparte= #{idAutoparte}")
	@Options(flushCache=true,useCache=true)
    public void actualizarAutoparte(Autoparte autoparte) throws Exception;
	
	
	@Select("select * from t_autoparte where Estado = 1 order by descripcion asc")
	public List<Autoparte> listaAutopartesActivas() throws Exception;
	
	@Select("Select count(*) from t_autoparte where IdAutomotor = #{idAutomotor}")
	public int countxAutomotor(@Param("idAutomotor") Integer idAutomotor) throws Exception;
	
}

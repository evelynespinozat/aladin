package com.certicom.ittsa.mapper; 

import java.util.List; 

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Menu;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.domain.UsuarioPerfil;

public interface MenuMapper{ 

	public List<Menu> listMenu() throws Exception;
	public void deleteMenu(Menu menu) throws Exception;
	public void updateMenu(Menu menu) throws Exception;
	public void insertMenu(Menu menu) throws Exception;
	public Menu findMenu(int id) throws Exception;
	public Menu findMenuxNombre(String nombreMenu) throws Exception;
	public List<Menu> findMenus(Menu menu) throws Exception;
	public List<Menu> listMenuxSistema(Perfil perfil) throws Exception;
	
	//recupera menus por sistema
	@Select(" select cod_menu,cod_sistema,cod_menu_padre,nombre,descripcion,ind_activo,fecha_registro,fecha_modif,usuario_registro,usuario_modif,icono,action from t_opcion_menu where cod_sistema = #{p_sistemaid} and ind_activo=1 " //piscoya
			+ "order by nombre asc ")
	public List<Menu> listMenuxSistemaId(@Param("p_sistemaid") Long sistemaid) throws Exception;
	
	//busca todos los sistemas/modulos implicados 
	@Select("select sis.cod_sistema, sis.nombre_sistema,sis.descripcion,sis.ind_activo from t_sistema sis where sis.cod_sistema in ( select distinct cod_sistema from  t_opcion_menu  menu ) and sis.ind_activo=1")
	public List<Sistema> listSistemas() throws Exception;
	
	public int opcionMenuByPretty(String pretty) throws Exception;
	
	@Select("select * from t_opcion_menu where cod_sistema = #{cod_sistema} and ind_activo=1" ) //piscoya
	public List<Menu> listaMenusxId(@Param("cod_sistema") Long cod_sistema);
	
	@Update("update t_opcion_menu set nombre=#{nombre}, descripcion =#{descripcion}, icono = #{icono} where cod_sistema = #{cod_sistema} ")
	@Options(useCache=true, flushCache=true)
	public void actualizarMenu(Menu menu)throws Exception;
} 

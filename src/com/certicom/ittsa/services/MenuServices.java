package com.certicom.ittsa.services; 

import java.util.List; 

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.Menu;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.domain.UsuarioPerfil;
import com.certicom.ittsa.mapper.MenuMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class MenuServices implements MenuMapper{

	MenuMapper menuMapper = (MenuMapper)ServiceFinder.findBean("menuMapper");

	public List<Menu> listMenu() throws Exception{
	    return menuMapper.listMenu();
	}

	public void deleteMenu(Menu menu)  throws Exception{
	    menuMapper.deleteMenu(menu);
	}

	public void updateMenu(Menu menu) throws Exception{
	    menuMapper.updateMenu(menu);
	}

	public void insertMenu(Menu menu) throws Exception{
	    menuMapper.insertMenu(menu);
	}

	public Menu findMenu(int id)  throws Exception{
	    return menuMapper.findMenu(id);
	}
	
	public Menu findMenuxNombre(String nombreMenu)  throws Exception{
	    return menuMapper.findMenuxNombre(nombreMenu);
	}


	public List<Menu>  findMenus(Menu menu)  throws Exception{
	    return menuMapper.findMenus(menu);
	}
	
	public List<Menu> listMenuxSistema(Perfil perfil) throws Exception{
	    return menuMapper.listMenuxSistema(perfil);
	}
	
	//ww
	@Override
	public List<Menu> listMenuxSistemaId(Long sistemaid)	throws Exception {
		return menuMapper.listMenuxSistemaId(sistemaid);
	}
	
	//ww
	@Override
	public List<Sistema> listSistemas() throws Exception {
		return menuMapper.listSistemas();
	}
	
	
	@Override
	public int opcionMenuByPretty(String pretty) throws Exception {
		return menuMapper.opcionMenuByPretty(pretty); 
	}

	@Override
	public List<Menu> listaMenusxId(Long cod_sistema) {
		return menuMapper.listaMenusxId(cod_sistema);
	}

	@Override
	public void actualizarMenu(Menu menu) throws Exception {
		menuMapper.actualizarMenu(menu);
	}

} 

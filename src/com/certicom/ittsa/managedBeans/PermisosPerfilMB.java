package com.certicom.ittsa.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.model.MenuModel;

import com.certicom.ittsa.domain.Menu;
import com.certicom.ittsa.domain.MenuXPerfil;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.MenuPerfilServices;
import com.certicom.ittsa.services.MenuServices;

@ManagedBean(name="permisosPerfilMB")
@ViewScoped
public class PermisosPerfilMB {
	
	private List<Sistema> listaModulos;
	private Perfil perfil;
	private MenuServices menuServices; 
	
	private MenuPerfilServices menuPerfilServices;
	
	

	@ManagedProperty(value="#{loginMB}")
	private LoginMB loginMB;
	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}

	public PermisosPerfilMB (){
		
	}
	
	@PostConstruct
	public void inicia(){
		this.menuPerfilServices = new MenuPerfilServices();
		this.menuServices = new MenuServices();
		
		System.out.println("iniciando mb asignar perfil");
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.perfil=(Perfil)flash.get("perfil");
		
		this.crearTablaSubtabla();
		//para pasarlo a service:
		/*
		try {
			MenuXPerfil menuPerfil=null; 
			this.listaModulos=this.menuServices.listSistemas();
			for(Sistema sis : this.listaModulos){
				sis.setListaMenu(this.menuServices.listMenuxSistemaId(sis.getCod_sistema()));
				for(Menu menu : sis.getListaMenu()){
					//consultar
					menuPerfil = this.menuPerfilServices.buscarMenuPerfil(menu.getCod_menu(),this.perfil.getCod_perfil());
					if(menuPerfil!=null){
						menu.setBanderin(Boolean.TRUE);
					}else{
						menu.setBanderin(Boolean.FALSE);
					}
				}
			}
		}catch(Exception e){
			System.out.println("Error :"+e.getMessage());
			e.printStackTrace();
		}
		*/
		//lista para pintar: listaModulos
		
		//vamos acreando:
		//1 recuperar los sietmas dentro de  t_opcion_men: Menu
		//2 a esa lista por cada sistema recuperara sus menus asociados
		//3 volver a iterar la lista de sistema y pro cada menu seterar su estado consultado ala bd
		//4 pintar el subtavble
		
		
		
		
		
		/*
		//listando un domain MenuxPerfil;
		try {
			this.listaMenuPerfil = menuPerfilServices.listarMenuXPerfil();
			System.out.println("tamano de lista :"+this.listaMenuPerfil.size());
		} catch (Exception e) {
			System.out.println("Error :"+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

	/**
	 * desc:crea una tabla con subtabla
	 * @return
	 */
	public void crearTablaSubtabla(){
		
		try {
			MenuXPerfil menuPerfil=null; 
			this.listaModulos=this.menuServices.listSistemas();
			for(Sistema sis : this.listaModulos){
				sis.setListaMenu(this.menuServices.listMenuxSistemaId(sis.getCod_sistema()));
				for(Menu menu : sis.getListaMenu()){
					//consultar
					menuPerfil = this.menuPerfilServices.buscarMenuPerfil(menu.getCod_menu(),this.perfil.getCod_perfil());
					if(menuPerfil!=null){
						menu.setBanderin(Boolean.TRUE);
					}else{
						menu.setBanderin(Boolean.FALSE);
					}
				}
			}
		}catch(Exception e){
			System.out.println("Error :"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Desc: elimina/inserta en la tabla t_opcion_menuxperfil
	 * @param menu 
	 */
	 public String cambiarEstado(Menu menu)
	 {
		 String outcome = null;
		 System.out.println("menu seleccionado :"+menu.getDescripcion());
		 if(menu.getBanderin()){
			 //eliminar
			 menu.setBanderin(Boolean.FALSE);
			 try {
				this.menuPerfilServices.eliminarPerfilUsuario(menu.getCod_menu(),this.perfil.getCod_perfil());
			} catch (Exception e) {
				System.out.println("Error eliminando menuPerfil"+e.getMessage());
				e.printStackTrace();
			}
		 }else{
			 //crear
			 menu.setBanderin(Boolean.TRUE);
			 try {
				this.menuPerfilServices.crearMenuPerfil(menu.getCod_menu(),this.perfil.getCod_perfil(), Boolean.TRUE);
			} catch (Exception e) {
				System.out.println("Eliminando error menuPerfil:"+e.getMessage());
				e.printStackTrace();
			}
		 }
			 
		 //llamando a service para recarcar datatable
		 this.crearTablaSubtabla();
		 
		 this.loginMB.efectuarLogin();

		 return outcome; 
	 }
	
	
	
	/*#########--------setters y getters-------##############*/
	
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Sistema> getListaModulos() {
		return listaModulos;
	}

	public void setListaModulos(List<Sistema> listaModulos) {
		this.listaModulos = listaModulos;
	}

}

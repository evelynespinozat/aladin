package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Menu;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.services.MenuPerfilServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PerfilServices;
import com.certicom.ittsa.services.SistemaServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="menuModuloMB")
@ViewScoped
public class MenuModuloMB extends GenericBeans implements Serializable{

	private TreeNode raiz;
	private List<Menu> listaMenu;
	private Sistema sistema;
	private Integer sistemaId;
	private TreeNode nodoSelec;
	private Menu menu;
	private Menu menuSelect;
	private Boolean padre;
	private String nombreMenuSelect;
	
	private MenuServices menuServices;
	private SistemaServices sistemasServices;
	private MenuPerfilServices menuPerfilServices;
	
	private Boolean editar;
	private List<String> listaIconos;

	
	
	//private PerfilServices perfilServices;
	private Perfil perfil;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public MenuModuloMB(){
		
	}
	
	@PostConstruct
	public void inicia(){
		this.padre = Boolean.FALSE;
		this.editar = Boolean.FALSE;
		this.menu = new Menu();
		this.menuSelect  = new Menu();
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.sistemaId =(Integer) flash.get("sistemaId");
		
		//services
		this.menuServices = new MenuServices();
		this.sistemasServices = new SistemaServices();
		this.menuPerfilServices = new MenuPerfilServices();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		this.listaIconos =  new ArrayList<>();
		this.listaIconos.add("icon-editar");
		this.listaIconos.add("icon-eliminar");
		this.listaIconos.add("icon-nuevo");
		this.listaIconos.add("icon-seleccionar");
		this.listaIconos.add("icon-salir");
		this.listaIconos.add("icon-buscar");
		
		try {
			this.listaMenu = this.menuServices.listMenuxSistemaId(new Long(this.sistemaId));
			this.sistema= this.sistemasServices.findSistemaPorCodigo(new Long(this.sistemaId));
			
			int codMenu = menuServices.opcionMenuByPretty("pretty:sistemas");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        this.generarTree(this.sistema.getNombre_sistema(),this.listaMenu);

	}
	
	
	public void generarTree(String nombreRaiz,List<Menu> listaM){
		this.raiz = new DefaultTreeNode("Root", null); 
		TreeNode node0 = new DefaultTreeNode(nombreRaiz, this.raiz);  
        for(Menu mn: listaM){
			//System.out.println("menu :"+mn.getDescripcion());
			new DefaultTreeNode(mn.getNombre(), node0);  
		}
	}

	
	public void agregarMenu(){
		
		RequestContext context = RequestContext.getCurrentInstance();  
		
		if(this.padre){
			try {
			if(this.editar){
				System.out.println("entro en esta parte");
				//this.menuServices.actualizarMenu(this.menu);
				
			}else{//es nuevo menu
				this.menu.setCod_sistema(this.sistema.getCod_sistema());
			
					this.menuServices.insertMenu(this.menu);
					log.setAccion("INSERT");
		             log.setDescripcion("Se registro el menu " + menu.getDescripcion());
		             logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Menú agregado correctamente.",Constante.INFORMACION);
					context.update("sms");
					context.execute("nuevoMenuDialog.hide()");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else{
			
			if(this.editar){
				try {
					this.menuServices.updateMenu(this.menu);
					//this.menuServices.updateMenu(this.menu);
					log.setAccion("UPDATE");
		             log.setDescripcion("Se actualiza el menu: " + menu.getDescripcion());
		             logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Menú guardado correctamente.",Constante.INFORMACION);
					context.update("sms");
					context.execute("nuevoMenuDialog.hide()");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		/*
		if(this.editar){
			if(!this.padre){//si no es padre se edita
				
				try {
					this.menuServices.updateMenu(this.menu);
					FacesUtils.showFacesMessage("Menú guardado correctamente.",Constante.INFORMACION);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}else{//nuevo
			if(this.padre){//si es padre se agrega menu
				this.menu.setCod_sistema(this.sistema.getCod_sistema());
				try {
					this.menuServices.insertMenu(this.menu);
					FacesUtils.showFacesMessage("Menú agregado correctamente.",Constante.INFORMACION);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{//si es hijo se va a la miel
				FacesUtils.showFacesMessage("no puedes agregar menus a un hijo.",Constante.ERROR);
				 //context.update("sms");
			}
		}
		*/
		//regenerando el trees en cualquier caso
		try {
			this.listaMenu = this.menuServices.listMenuxSistemaId(new Long(this.sistemaId));
			this.generarTree(this.sistema.getNombre_sistema(),this.listaMenu);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.editar = Boolean.FALSE;
	}

	
	
	
	
	
	
	public void onNodeSelect(NodeSelectEvent event) {
		this.nombreMenuSelect = event.getTreeNode().getData().toString();
		//seteando menu
		try {
			this.menuSelect = this.menuServices.findMenuxNombre(this.nombreMenuSelect);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(this.nodoSelec != null) {   
            System.out.println("nodo seleccionado :"+ this.nodoSelec.getData().toString());
        }  
		//System.out.println("el papa :"+this.nodoSelec.getParent().getData());
		
		if(this.nodoSelec.getParent().getData().toString().equals("Root")){//es el modulo
			//disponible solo agregar menu 
			//activando banderin pacre
			this.padre = Boolean.TRUE;
			
		}else{
			this.padre = Boolean.FALSE;
		}
		
	}
	
	
	public void eliminarMenu(){
		if(!this.padre){
			try {
				RequestContext context = RequestContext.getCurrentInstance();
				//validar
				Integer valor = this.menuPerfilServices.obtenerMenusxId(this.menuSelect.getCod_menu());
				System.out.println("el valor es " + valor);
				if(valor>0){
					context.execute("dlgOmiso.show()");
				}else{
					this.menuServices.deleteMenu(this.menuSelect);
					log.setAccion("DELETE");
		             log.setDescripcion("Se eliminó el menu: " + menuSelect.getDescripcion());
		             logmb.insertarLog(log);
					this.listaMenu = this.menuServices.listMenuxSistemaId(new Long(this.sistemaId));
					this.generarTree(this.sistema.getNombre_sistema(),this.listaMenu);
					FacesUtils.showFacesMessage("Menú eliminado correctamente.",Constante.INFORMACION);	
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public void editarMenu(){
		System.out.println("editando el menu");
			this.editar = Boolean.TRUE;
			this.menu = this.menuSelect;
	}
	
	public void nuevoMenu()
	{
		this.menu = new Menu();
		
	}
	
	
	/*###############----gettres and setters-------######################*/

	public TreeNode getRaiz() {
		return raiz;
	}

	public void setRaiz(TreeNode raiz) {
		this.raiz = raiz;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Menu> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<Menu> listaMenu) {
		this.listaMenu = listaMenu;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public TreeNode getNodoSelec() {
		return nodoSelec;
	}

	public void setNodoSelec(TreeNode nodoSelec) {
		this.nodoSelec = nodoSelec;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Boolean getPadre() {
		return padre;
	}

	public void setPadre(Boolean padre) {
		this.padre = padre;
	}

	public String getNombreMenuSelect() {
		return nombreMenuSelect;
	}

	public void setNombreMenuSelect(String nombreMenuSelect) {
		this.nombreMenuSelect = nombreMenuSelect;
	}

	public Menu getMenuSelect() {
		return menuSelect;
	}

	public void setMenuSelect(Menu menuSelect) {
		this.menuSelect = menuSelect;
	}

	public List<String> getListaIconos() {
		return listaIconos;
	}

	public void setListaIconos(List<String> listaIconos) {
		this.listaIconos = listaIconos;
	}

	
}

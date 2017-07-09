package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Menu;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.SistemaServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="sistemaMB")
@ViewScoped
public class SistemaMB  extends GenericBeans implements Serializable{
	@ManagedProperty(value="#{loginMB.usuario.nombre}")
	private String nombre_usuario;
	private String titulo;
	
	@ManagedProperty(value="#{loginMB}")
	private LoginMB login;

	private Sistema sistema;
    private List<Sistema> listaSistema;
    private List<Sistema> listaSistemaFilter;
    
    //services
    private SistemaServices sistemaServices;
    private MenuServices menuServices;
    //private Sistema sistema2;
   // private Menu menu;
    //private Menu menuPadre;
    //private List<Menu> listaMenuModulo;
    //private List<Menu> listaMenu;
    //private int id_menu;
    private Boolean editar;
    
    private Log log;
	private LogMB logmb;
    
    
    public SistemaMB() throws Exception {

    }
    
    @PostConstruct
    public void inicia(){
    	System.out.println("ingresando a sistema MB");
    	this.sistema= new Sistema();
    	this.editar = Boolean.FALSE;
    	sistemaServices = new SistemaServices();
        try {
			listaSistema = sistemaServices.listSistema();
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
        menuServices=new MenuServices();
        sistema = new Sistema();
        
        log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:sistemas");
			System.out.println("codmenu "+codMenu);
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    

    
    public String listSistema() {
        try {
            listaSistema = sistemaServices.listSistema();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void nuevoSistema(){
    	this.sistema = new Sistema();
    	this.editar = Boolean.FALSE;
    	this.titulo = "Registrar nuevo m�dulo";
    }
    
    public void editarSistema(Sistema s){
    	this.editar = Boolean.TRUE;
    	this.sistema = s;
    	this.titulo = "Modificar m�dulo";
    }

    public void guardarSistema() throws Exception {
    	 Boolean valido=Boolean.TRUE;
    	 //Boolean aviso = Boolean.FALSE;
    	 RequestContext context = RequestContext.getCurrentInstance();  
    	 context.addCallbackParam("esValido", valido );
    
    	 if(this.editar){ //editando
    		 
    		 this.editarSistema();
    		 FacesUtils.showFacesMessage("M�dulo actualizado correctamente.",Constante.INFORMACION);
        	 context.update("msgGeneral");
        	 context.execute("dlgNuevo.hide()");
    		 log.setAccion("UPDATE");
             log.setDescripcion("Se actualiz� el sistema: " + sistema.getNombre_sistema());
             logmb.insertarLog(log);

    	 }else{//guardando un nuevo registro
    		 int i=0;
    		 for(Sistema s: listaSistema){
    			 if(this.sistema.getNombre_sistema().equalsIgnoreCase(s.getNombre_sistema()))  				 
    				 i++;    			  
    		 }
    		 
    		 if(i==0){
				 this.sistema.setInd_activo(new Integer(1));
	    		 sistemaServices.insertSistema(this.sistema);
	             this.listaSistema = sistemaServices.listSistema();
	             log.setAccion("INSERT");
	             log.setDescripcion("Se insert� el sistema: " + sistema.getNombre_sistema());
	             logmb.insertarLog(log);
	             this.sistema =new Sistema();
	             FacesUtils.showFacesMessage("M�dulo registrado correctamente.",Constante.INFORMACION);
	        	 context.update("msgGeneral");
	        	 context.execute("dlgNuevo.hide()");
			 }else{
				 //context.addCallbackParam("esAviso", Boolean.TRUE );
				 System.out.println("entro en este metodo");
				 FacesUtils.showFacesMessage("M�dulo se encuentra ya registrado.",Constante.ERROR);
				 context.update("smsNuevoEdit");
			 } 
             
    	 }
    	 
    	 

    }

   public void editarSistema() throws Exception {
	   System.out.println("editando sistema");
       sistemaServices.updateSistema(sistema);
       listaSistema = sistemaServices.listSistema();
       this.editar=Boolean.FALSE;
    }
    
   public void newDelete(Long cod_sistema) throws Exception {
       this.sistema = new Sistema();
       this.sistema = sistemaServices.findSistemaPorCodigo(cod_sistema);
   }
   
   public void eliminarSistema(){
    	
        try {
        	RequestContext context = RequestContext.getCurrentInstance();
        	
        	//validamos que no tenga menus registrados 
        	List<Menu> listaMenus = menuServices.listaMenusxId(sistema.getCod_sistema());
        	
        	if(listaMenus.size()>0){
        		context.execute("dlgOmiso.show()");
        	}else{
        		sistemaServices.deleteSistema(sistema.getCod_sistema());
    			listaSistema = sistemaServices.listSistema();
    	        FacesUtils.showFacesMessage("M�dulo eliminado correctamente.",Constante.INFORMACION);
    	        
    	        log.setAccion("DELETE");
                log.setDescripcion("Se elimin� el sistema: " + sistema.getNombre_sistema());
                logmb.insertarLog(log);
        	}
 
		} catch (Exception e) {
			FacesUtils.showFacesMessage("M�dulo no se puede eliminar, posiblemente tenga men�es asociados.",Constante.ERROR);
			e.printStackTrace();
		}
        
   }
   
    /**
     * cambia de estado
     * @param sistem
     */
   public void cambiarEstado(Sistema sistem){
	   if(sistem.getInd_activo().compareTo(new Integer(1)) == 0 ){
		   //System.out.println("es igual a uno se pone a cero");
		   sistem.setInd_activo(new Integer(0));
	   }else{
		   //System.out.println("es igual a cero");
		   sistem.setInd_activo(new Integer(1));
	   }
	   
	   try {
		this.sistemaServices.updateSistema(sistem);
		log.setAccion("CHANGE ESTADO");
        log.setDescripcion("Se cambio el estado en sistema: " + sistema.getDescripcion());
        logmb.insertarLog(log);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   
   }
   
   
   /*
   
   public String findSistema() {
        try {
            sistemaServices.findSistema(sistema);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    */

    
    /*
    public void newInsert() {
        sistema = new Sistema();
    }
    
    public void newUpdate(Long cod_sistema) throws Exception {
        this.sistema = new Sistema();
        this.sistema = sistemaServices.findSistemaPorCodigo(cod_sistema);
    }
    
*/
    
    
    
    /*****************************************************************/

    public String agregarMenu(int id){
    	String outcome = null;
    	try {
    		/*
    		System.out.println("sistama 2 codigo:"+this.sistema2.getCod_sistema());
    		System.out.println("id de algo :"+id);
    		setMenu(new Menu());
    		listaMenuModulo=new ArrayList<Menu>();
    		listaMenu=menuServices.listMenu();
    		*/
    		//enviando a MenuModuloMB
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("sistemaId", id);
    		
    		//return "/faces/control_acceso/addMenuSistema?faces-redirect=true";	
    		outcome="pretty:menuSistema";		
		} catch (Exception e) {			
			e.printStackTrace();
		}
    	return outcome;
    }
    /*
    public void newMenuInsert() {
        menu = new Menu();
    }
    
    
    public void agregarMenu(){
		try{
			
			if(getId_menu()>0)	getMenu().setMenuPadre(getMenuPadre());
			else getMenu().setMenuPadre(new Menu());
			
			getMenu().setCod_sistema(getSistema2().getCod_sistema());			
			getMenu().setUsuario_registro(nombre_usuario);
			getMenu().setUsuario_modif(nombre_usuario);			
			listaMenuModulo.add(getMenu());
			setListaMenuModulo(listaMenuModulo);
			setMenuPadre(new Menu());
			setMenu(new Menu());
			setId_menu(0);
			FacesUtils.showFacesMessage("Men� registrado correctamente.", 3);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
    
    public void setearMenuPadre(AjaxBehaviorEvent ajaxBehaviorEvent) {
        try {        	
        	Menu p=new Menu();
        	if (getId_menu() != 0) {             
            	int posicion=-1;
            	for(int k=0;k<listaMenu.size();k++){
            		p=listaMenu.get(k);
            		if(p.getCod_menu()==getId_menu()){
            			posicion=k;break;} 
            	}
            	if(posicion>-1) setMenuPadre(listaMenu.get(posicion));
            	             	
            }else{
            	setMenuPadre(new Menu());}
           
        	setId_menu(0);
        } catch (Exception a) {
            System.out.println("Error:" + a.getMessage());
        }
    }
    
	public void insertMenu(){
		try{	
			
			for(int k=0;k<getListaMenuModulo().size();k++){				
				menuServices.insertMenu(getListaMenuModulo().get(k));
			}
			setMenu(new Menu());
			setMenuPadre(new Menu());
			setListaMenuModulo(new ArrayList<Menu>());
			setId_menu(0);
			FacesUtils.showFacesMessage("Men� registrado correctamente.", 3);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    */
   
   
   
   
   
   
   
    /*############----setters and getters------##############*/
		
    	public void setLogin(LoginMB login) {
    		this.login = login;
    	}	
    	
    	public MenuServices getMenuServices() {
			return menuServices;
		}

		public void setMenuServices(MenuServices menuServices) {
			this.menuServices = menuServices;
		}


		public Sistema getSistema() {
	        return sistema;
	    }
	    
	    public void setSistema(Sistema sistema) {
	        this.sistema = sistema;
	    }

	    public String getNombre_usuario() {
			return nombre_usuario;
		}

		public void setNombre_usuario(String nombre_usuario) {
			this.nombre_usuario = nombre_usuario;
		}

		public SistemaServices getSistemaServices() {
	        return sistemaServices;
	    }

	    public void setSistemaServices(SistemaServices sistemaServices) {
	        this.sistemaServices = sistemaServices;
	    }

		public List<Sistema> getListaSistema() throws Exception {    
	        return listaSistema;
	    }
	    
	    public void setListaSistema(List<Sistema> listaSistema) {
	        this.listaSistema = listaSistema;
	    }

		public Boolean getEditar() {
			return editar;
		}

		public void setEditar(Boolean editar) {
			System.out.println("valor de editar "+editar);
			this.editar = editar;
		}

		public Log getLog() {
			return log;
		}

		public void setLog(Log log) {
			this.log = log;
		}

		public LogMB getLogmb() {
			return logmb;
		}

		public void setLogmb(LogMB logmb) {
			this.logmb = logmb;
		}

		public LoginMB getLogin() {
			return login;
		}

		public List<Sistema> getListaSistemaFilter() {
			return listaSistemaFilter;
		}

		public void setListaSistemaFilter(List<Sistema> listaSistemaFilter) {
			this.listaSistemaFilter = listaSistemaFilter;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		
		
    
}
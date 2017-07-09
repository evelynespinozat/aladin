package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Parametro;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ParametroServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean
@SessionScoped
public class ParametroMB extends GenericBeans implements Serializable{

	private ParametroServices parametroServices;
	private Parametro parametro;
	private List<Parametro> listaParametro;
	private MenuServices menuServices;
	

	private Log log;
	private LogMB logmb;
	    
    @PostConstruct
    public void inicia(){
    	 menuServices=new MenuServices();
    	
    	log = (Log)getSpringBean(Constante.SESSION_LOG);
  		logmb = new LogMB();
  		try {
  			
  			parametroServices=new ParametroServices();
			parametro = new Parametro();
		    listaParametro=parametroServices.listParametro();
  			int codMenu = menuServices.opcionMenuByPretty("pretty:parametros");
  			log.setCod_menu(codMenu);
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		} 
    }
    
	public ParametroMB() {	
		;
	}

	

	public Parametro getParametro() 
	{return parametro;} 
 
	public void setParametro(Parametro parametro) 
	{this.parametro=parametro;} 
 
	public List<Parametro> getListaParametro()	throws Exception{
		return listaParametro;
	} 
 
	public void setListaParametro(List<Parametro> listaParametro) 
	{this.listaParametro=listaParametro;} 
 
	public void listParametro(){
		try{
			listaParametro=parametroServices.listParametro();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void insertParametro(){
		try{
			RequestContext context = RequestContext.getCurrentInstance();  
		    	
			parametroServices.insertParametro(parametro);
			listaParametro=parametroServices.listParametro();
			
			 log.setAccion("INSERT");
             log.setDescripcion("Se registro el parametro: " + parametro.getDescripcion());
             logmb.insertarLog(log);
			 FacesUtils.showFacesMessage("Parametro registrado correctamente.",Constante.INFORMACION);
	    	 context.update("sms");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void updateParametro(){
		try{
			RequestContext context = RequestContext.getCurrentInstance();  
		    
			parametroServices.updateParametro(parametro);
			listaParametro=parametroServices.listParametro();
			 log.setAccion("UPDATE");
             log.setDescripcion("Se actualizo el parametro: " + parametro.getDescripcion());
             logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Parametro actualizado correctamente.",Constante.INFORMACION);
	    	 context.update("sms");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void findParametro(){
		try{
			parametroServices.findParametro(parametro);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void findParametros(){
		try{
			listaParametro=parametroServices.findParametros(parametro);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void deleteParametro(){
		
			try {
				RequestContext context = RequestContext.getCurrentInstance();  
			    
				parametroServices.deleteParametro(parametro.getCod_parametro());
				listaParametro=parametroServices.listParametro();
				 log.setAccion("DELETE");
	             log.setDescripcion("Se eliminó el parametro: " + parametro.getDescripcion());
	             logmb.insertarLog(log);
	             
	             FacesUtils.showFacesMessage("Parametro eliminado correctamente.",Constante.INFORMACION);
		    	 context.update("sms"); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}

	public void newInsert(){
		parametro = new Parametro();
	}

	public void newUpdate(int cod_parametro)  throws Exception {
		try{
		this.parametro = new Parametro();
		this.parametro = parametroServices.findParametroPorCodigo(cod_parametro);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void newDelete(int cod_parametro)  throws Exception {
		try{
		this.parametro = new Parametro();
		this.parametro = parametroServices.findParametroPorCodigo(cod_parametro);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void cambiarEstado(Parametro parame) throws Exception{
		 if(parame.isInd_activo()){
			   //System.out.println("es igual a uno se pone a cero");
			 parame.setInd_activo(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   parame.setInd_activo(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.parametroServices.updateParametro(parame);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Parámetro: " + parame.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de parámetro actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   } 
	}

	}


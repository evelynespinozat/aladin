package com.certicom.ittsa.managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.certicom.ittsa.domain.UmbralCaptura;
import com.certicom.ittsa.domain.UmbralDeteccion;
import com.certicom.ittsa.domain.UmbralVerificacion;
import com.certicom.ittsa.services.UmbralCapturaService;
import com.certicom.ittsa.services.UmbralDeteccionService;
import com.certicom.ittsa.services.UmbralVerificacionService;
import com.pe.certicom.ittsa.commons.GenericBeans;


@ManagedBean(name="configuracionBiometricaMB")
@ViewScoped
public class ConfiguracionBiometricaMB extends GenericBeans{
	
	private List<UmbralCaptura> listaUmbralCaptura;
	private List<UmbralDeteccion> listaUmbralDeteccion;
	private List<UmbralVerificacion> listaUmbralVerificacion;
	
	private UmbralCaptura umbralCaptura;
	private UmbralDeteccion umbralDeteccion;
	private UmbralVerificacion umbralVerificacion;

	
	private UmbralCaptura umbralCapturaSelec;
	private UmbralDeteccion umbralDeteccionSelec;
	private UmbralVerificacion umbralVerificacionSelec;


	private UmbralCapturaService umbralCapturaservice;
	private UmbralDeteccionService umbralDeteccionService;
	private UmbralVerificacionService umbralVerificacionService;
	
	private int id_captura;
	private int id_deteccion;
	private int id_verificacion;
	
	
	@PostConstruct
	public void inicia(){
		
				this.umbralCapturaservice=new UmbralCapturaService();
				this.umbralDeteccionService=new UmbralDeteccionService();
				this.umbralVerificacionService=new UmbralVerificacionService();
				
				this.umbralCaptura=new UmbralCaptura();
				this.umbralDeteccion=new UmbralDeteccion();
				this.umbralVerificacion=new UmbralVerificacion();
				
				this.umbralCapturaSelec=new UmbralCaptura();
				this.umbralDeteccionSelec=new UmbralDeteccion();
				this.umbralVerificacionSelec=new UmbralVerificacion();
		
		
		try {
				this.listaUmbralCaptura=this.umbralCapturaservice.findAll();
				this.listaUmbralDeteccion=this.umbralDeteccionService.findAll();
				this.listaUmbralVerificacion=this.umbralVerificacionService.findAll();
				
				listarValoresDefault();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	public void listarValoresDefault(){
		if(this.listaUmbralCaptura.size()>0){
			for(UmbralCaptura uc:this.listaUmbralCaptura){
				if(uc!=null){
					if(uc.isEstado()==Boolean.TRUE){
						this.umbralCapturaSelec.setId_captura(uc.getId_captura());
					}
				}
			}
		}
		if(this.listaUmbralDeteccion.size()>0){
			for(UmbralDeteccion ud:this.listaUmbralDeteccion){
				if(ud.isEstado()==Boolean.TRUE){
					this.umbralDeteccionSelec.setId_deteccion(ud.getId_deteccion());
				}
			}
		}
		
		if(this.listaUmbralVerificacion.size()>0){
			for(UmbralVerificacion uv:this.listaUmbralVerificacion){
				if(uv.isEstado()==Boolean.TRUE){
					this.umbralVerificacionSelec.setId_verificacion(uv.getId_verificacion());
				}
			}
		}
	}
	
	public void guardar(){
		
		System.out.println("***** guardando..");
		try {
			this.umbralCapturaSelec.setEstado(true);
			this.umbralCapturaservice.actualizarUmbralCaptura(this.umbralCapturaSelec);
			for(UmbralCaptura uc: this.listaUmbralCaptura){
				if(uc.getId_captura()!=this.umbralCapturaSelec.getId_captura()){
					uc.setEstado(false);
					this.umbralCapturaservice.actualizarUmbralCaptura(uc);
				}
			}
			
			this.umbralDeteccionSelec.setEstado(true);
			this.umbralDeteccionService.actualizarUmbralDeteccion(this.umbralDeteccionSelec);
			for(UmbralDeteccion ud : this.listaUmbralDeteccion){
				if(ud.getId_deteccion()!=this.umbralDeteccionSelec.getId_deteccion()){
					ud.setEstado(false);
					this.umbralDeteccionService.actualizarUmbralDeteccion(ud);
				}
			}
			
			this.umbralVerificacionSelec.setEstado(true);
			this.umbralVerificacionService.actualizarUmbralVerificacion(this.umbralVerificacionSelec);
			for(UmbralVerificacion uv:this.listaUmbralVerificacion){
				if(uv.getId_verificacion()!=this.umbralVerificacionSelec.getId_verificacion()){
					uv.setEstado(false);
					this.umbralVerificacionService.actualizarUmbralVerificacion(uv);
				}
			}
			
			System.out.println("*** umbrales configurados");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public List<UmbralCaptura> getListaUmbralCaptura() {
		return listaUmbralCaptura;
	}


	public void setListaUmbralCaptura(List<UmbralCaptura> listaUmbralCaptura) {
		this.listaUmbralCaptura = listaUmbralCaptura;
	}


	public List<UmbralDeteccion> getListaUmbralDeteccion() {
		return listaUmbralDeteccion;
	}


	public void setListaUmbralDeteccion(List<UmbralDeteccion> listaUmbralDeteccion) {
		this.listaUmbralDeteccion = listaUmbralDeteccion;
	}


	public List<UmbralVerificacion> getListaUmbralVerificacion() {
		return listaUmbralVerificacion;
	}


	public void setListaUmbralVerificacion(
			List<UmbralVerificacion> listaUmbralVerificacion) {
		this.listaUmbralVerificacion = listaUmbralVerificacion;
	}


	public UmbralCaptura getUmbralCaptura() {
		return umbralCaptura;
	}


	public void setUmbralCaptura(UmbralCaptura umbralCaptura) {
		this.umbralCaptura = umbralCaptura;
	}


	public UmbralDeteccion getUmbralDeteccion() {
		return umbralDeteccion;
	}


	public void setUmbralDeteccion(UmbralDeteccion umbralDeteccion) {
		this.umbralDeteccion = umbralDeteccion;
	}


	public UmbralCapturaService getUmbralCapturaservice() {
		return umbralCapturaservice;
	}


	public void setUmbralCapturaservice(UmbralCapturaService umbralCapturaservice) {
		this.umbralCapturaservice = umbralCapturaservice;
	}


	public int getId_captura() {
		return id_captura;
	}


	public void setId_captura(int id_captura) {
		this.id_captura = id_captura;
	}
	

	public UmbralVerificacion getUmbralVerificacion() {
		return umbralVerificacion;
	}


	public void setUmbralVerificacion(UmbralVerificacion umbralVerificacion) {
		this.umbralVerificacion = umbralVerificacion;
	}


	public int getId_deteccion() {
		return id_deteccion;
	}


	public void setId_deteccion(int id_deteccion) {
		this.id_deteccion = id_deteccion;
	}


	public int getId_verificacion() {
		return id_verificacion;
	}


	public void setId_verificacion(int id_verificacion) {
		this.id_verificacion = id_verificacion;
	}


	public UmbralCaptura getUmbralCapturaSelec() {
		return umbralCapturaSelec;
	}


	public void setUmbralCapturaSelec(UmbralCaptura umbralCapturaSelec) {
		this.umbralCapturaSelec = umbralCapturaSelec;
	}


	public UmbralDeteccion getUmbralDeteccionSelec() {
		return umbralDeteccionSelec;
	}


	public void setUmbralDeteccionSelec(UmbralDeteccion umbralDeteccionSelec) {
		this.umbralDeteccionSelec = umbralDeteccionSelec;
	}


	public UmbralVerificacion getUmbralVerificacionSelec() {
		return umbralVerificacionSelec;
	}


	public void setUmbralVerificacionSelec(
			UmbralVerificacion umbralVerificacionSelec) {
		this.umbralVerificacionSelec = umbralVerificacionSelec;
	}
	
	
}

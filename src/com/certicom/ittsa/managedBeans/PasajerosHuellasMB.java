package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;















//import org.certicom.lectorhuella.impl.LectorHuellaImpl;
import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.PersonaTrama;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.PersonaTramaServices;
import com.certicom.ittsa.services.UmbralCapturaService;
import com.certicom.ittsa.services.UmbralDeteccionService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import org.apache.axis.encoding.Base64;


@ManagedBean(name  ="pasajerosHuellasMB")
@ViewScoped
public class PasajerosHuellasMB extends GenericBeans implements Serializable {

	RequestContext context; 
	
	private int umbralCaptura;
	private int umbralDeteccion;
	public int image_Width = -1;
	public int image_Height = -1;
	
	public byte[]imagen=null;
	public String trama;
	
	
	private String auxdni;
	
	private String dni;
	private Persona persona;
	private Persona personaSelec;
	
	private PersonaServices personaServices;
	
	//MODIFICACION 13-08-2014 
	private String imagenCadena;
	
	
	//MODIFICACION 18-08-2014
	private PersonaTramaServices personaTramaServices;
	private PersonaTrama personaTrama;
	
	//MODIFICACION 19-08-2014
	private int umbralCapturaX;
	private int umbralDeteccionX;
	
	private UmbralCapturaService umbralCapturaService;
	private UmbralDeteccionService umbralDeteccionService;
	
	//MODIFICACION 30-10-2014
	private boolean estadoBtnCaptura;
	private String mensajeCaptura;
	private boolean estadoApplet;
	
	@PostConstruct
	public void init(){
		
		this.persona = new Persona();
		this.personaSelec=new Persona();
		this.personaServices = new PersonaServices();
		
		//MODIFICACION 18-08-2014
		this.personaTramaServices=new PersonaTramaServices();
		this.personaTrama=new PersonaTrama();
		
		//MODIFICACION 19-08-2014
		this.umbralCapturaService=new UmbralCapturaService();
		this.umbralDeteccionService=new UmbralDeteccionService();
		
		//MODIFICACION 30-10-2014
		this.estadoBtnCaptura=Boolean.TRUE;
		this.mensajeCaptura="";
		this.estadoApplet=Boolean.TRUE;
		
		try {
			System.out.println("entr ");
			this.umbralCaptura=this.umbralCapturaService.findByEstado(true).getId_captura();
//			System.out.println("*** en init umbralCaptura "+this.umbralCaptura);
			
			this.umbralDeteccion=this.umbralDeteccionService.findByEstado(true).getId_deteccion();
			System.out.println("*** en init umbralDeteccion "+this.umbralDeteccion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void buscarPersona(){
		try {
			this.persona = this.personaServices.findByNroDocumento(this.dni);
			if(this.persona!=null){
				this.estadoBtnCaptura=Boolean.FALSE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ocultarApplet(){
		this.estadoApplet=Boolean.FALSE;
	}
	
/*
public void captura(){
		boolean estado=true;
		System.out.println("metodo captura");
		int res=this.lector.iniciar();
		System.out.println("Resultado de iniciar "+res);
		if(res==0){
			while(estado){
				//this.imagen =this.lector.capturarImagen();
				//System.out.println(" calidad imagen "+this.lector.obtenerCalidadImagenCapturada());
				System.out.println(" calidad deteccion "+this.lector.obtenerCalidadImagenDetectada());
			
			if(this.lector.obtenerCalidadImagenCapturada()<=this.umbralCaptura && 
					this.lector.obtenerCalidadImagenDetectada()<=this.umbralDeteccion)
			{
				byte[]tramaImagen=this.lector.capturarHuella();
				if(tramaImagen!=null){
					System.out.println("la huella en bytes es "+tramaImagen.length);
					//this.trama=org.apache.catalina.util.Base64.encode(tramaImagen);//error al importar
					//this.trama = Base64.encodeBase64String(tramaImagen);
					System.out.println("CAPTURA CORRECTA");
					//this.trama=Base64.encodeBase64String(tramaImagen);
					estado=false;
				}

			}else{
				System.out.println("Imagen de Huella de mala calidad");
			}
			}
		}
		
		
	}
	*/
	public void guardar(){
		
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		PersonaTrama pt;
		System.out.println("Metodo guardar");
		System.out.println(" la huella desde la javascript es "+this.trama);
		
		
		String tramaImgAux=getImagenCadena();		
		byte[] arrayImagen = null;
		try {		
				if(!this.trama.equals("") && this.trama!=null && this.imagenCadena!=null && !this.imagenCadena.equals("") ){
					
						arrayImagen = Base64.decode(tramaImgAux);
						System.out.println(" la imagen byte "+arrayImagen.length);
						
						this.imagen=arrayImagen;		
						System.out.println(" dni a modificar "+this.dni);
						
						this.personaSelec=this.persona;
//						System.out.println("++++ MOSTRANDO DATOS");
//						System.out.println("\n Nombre de busqueda---------"+this.personaSelec.getNombres());
						
						
						this.personaSelec.setDni(this.dni);
//						System.out.println(" envia "+this.trama.substring(0, 100));
						this.personaSelec.setTrama(this.trama);
						
						this.personaSelec.setImagen(arrayImagen);
						System.out.println(" despues de Setear");
						
						this.estadoApplet=Boolean.TRUE;
						
						//18-08-2014
						personaTrama.setDni(this.personaSelec.getDni());
						personaTrama.setTrama(this.personaSelec.getTrama());
						personaTrama.setDescripcion("GUARDADO");
						personaTrama.setImagen(this.personaSelec.getImagen());
		
		
						try {			
								//this.personaServices.actualizaHuella(this.personaSelec);
								//18-08-2014
								pt=this.personaTramaServices.findByNroDocumento(this.dni);
					
									if(pt!=null){
												this.personaTramaServices.actualizarPersonaTrama(personaTrama);
												pt=new PersonaTrama();
												System.out.println("**** ACTUALIZA");
												 FacesUtils.showFacesMessage("Huella digital del pasajero actualizada correctamente.",Constante.INFORMACION);
												 context.update("sms");																					
									}else{
												this.personaTramaServices.crearPersonaTrama(personaTrama);
												pt=new PersonaTrama();
												System.out.println("*** NUEVO");
												FacesUtils.showFacesMessage("Enrolamiento correcto.",Constante.INFORMACION);
												context.update("sms");
												 
									}
						} catch (Exception e) {
						System.out.println("Error de actualizacion");
						e.printStackTrace();
					}
				}else{
					cancelar();
					activarApplet();
					FacesUtils.showFacesMessage("Realice captura de huella.",Constante.ERROR);
					context.addCallbackParam("esValido", Boolean.FALSE );
					context.update("sms");
					
				}	
			} catch (Exception e) {
			
			e.printStackTrace();
		}
					tramaImgAux="";
					this.trama="";
					this.personaTrama=new PersonaTrama();
					this.estadoBtnCaptura=Boolean.TRUE;
					this.trama=null;
					this.imagenCadena=null;
					this.dni=null;
					this.persona=new Persona();
					this.personaSelec=new Persona();
					this.imagenCadena="";
	}
	
	public void activarApplet(){
		RequestContext context = RequestContext.getCurrentInstance();  
		context.update("captura()");
		
	}
	
	public void recuperar(){
		this.auxdni=this.dni;
		System.out.println(" dni auxiliar es "+this.auxdni);
		
		try {
			Persona p=new Persona();
			p=this.personaServices.findByNroDocumento(dni);
			System.out.println(" trama guardada es "+p.getTrama());
			System.out.println("\n longitud de trama "+p.getTrama().length());
			//byte[] im=p.getImagen();
			//System.out.println("la imagen guardada "+im.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setParametros(){
		System.out.println(" ======> seteando Parametros");
		this.umbralCaptura=this.umbralCapturaX;
		this.umbralDeteccion=this.umbralDeteccionX;
		System.out.println(" captura "+this.umbralCaptura);
		System.out.println(" deteccion "+this.umbralDeteccion);
	}
	
	public void cancelar(){
		RequestContext context = RequestContext.getCurrentInstance();  
		System.out.println("***** INGRESA A CANCELAR");
		this.mensajeCaptura="";
		this.trama=null;
		this.imagenCadena=null;
		this.estadoApplet=Boolean.TRUE;
//		formGuardar
		context.update(":formGuardar");
	}
	
	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}


	public int getUmbralCaptura() {
		return umbralCaptura;
	}


	public void setUmbralCaptura(int umbralCaptura) {
		this.umbralCaptura = umbralCaptura;
	}


	public int getUmbralDeteccion() {
		return umbralDeteccion;
	}


	public void setUmbralDeteccion(int umbralDeteccion) {
		this.umbralDeteccion = umbralDeteccion;
	}


	public int getImage_Width() {
		return image_Width;
	}


	public void setImage_Width(int image_Width) {
		this.image_Width = image_Width;
	}


	public int getImage_Height() {
		return image_Height;
	}


	public void setImage_Height(int image_Height) {
		this.image_Height = image_Height;
	}

	//13-08-2014
	public String getTrama() {
		return trama;
	}


	public void setTrama(String trama) {
		this.trama = trama;
	}


	public String getImagenCadena() {
		return imagenCadena;
	}


	public void setImagenCadena(String imagenCadena) {
		this.imagenCadena = imagenCadena;
	}
	
	
	//19-08-2014
	
	
	public void setUmbralDeteccionX(){
		this.umbralDeteccion=this.umbralDeteccionX;
	}

	public void setUmbralCapturaX(){
		this.umbralCaptura=this.umbralCapturaX;
	}

	public int getUmbralCapturaX() {
		return umbralCapturaX;
	}


	public void setUmbralCapturaX(int umbralCapturaX) {
		this.umbralCapturaX = umbralCapturaX;
	}


	public int getUmbralDeteccionX() {
		return umbralDeteccionX;
	}


	public void setUmbralDeteccionX(int umbralDeteccionX) {
		this.umbralDeteccionX = umbralDeteccionX;
	}

	//MODIFICACION 30-10-2014
	
	public boolean isEstadoBtnCaptura() {
		return estadoBtnCaptura;
	}

	public void setEstadoBtnCaptura(boolean estadoBtnCaptura) {
		this.estadoBtnCaptura = estadoBtnCaptura;
	}

	public String getMensajeCaptura() {
		return mensajeCaptura;
	}

	public void setMensajeCaptura(String mensajeCaptura) {
		this.mensajeCaptura = mensajeCaptura;
	}


	public boolean isEstadoApplet() {
		return estadoApplet;
	}


	public void setEstadoApplet(boolean estadoApplet) {
		this.estadoApplet = estadoApplet;
	}

	
	
}

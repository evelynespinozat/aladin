package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Empresa;
import com.certicom.ittsa.domain.ListaNegra;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.UsuarioAutorizante;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.ListaNegraService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.UsuarioAutorizanteServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "listaNegraMB")
@ViewScoped
public class ListaNegraMB extends GenericBeans implements Serializable {

	public ListaNegra listaNegra;
	public List<ListaNegra> listaNegralist;
	public List<ListaNegra> listaNegralistfilter;
	public List<UsuarioAutorizante> listaUsuarioAutorizante;

	private ListaNegraService listaNegraService;
	private UsuarioAutorizanteServices usuarioAutorizanteServices;
	private PersonaServices personaServices;

	@ManagedProperty(value = "#{loginMB.usuario}")
	private Usuario usuarioLogin;

	@PostConstruct
	public void inicio() {
		this.listaNegra = new ListaNegra();
		this.listaNegraService = new ListaNegraService();
		this.usuarioAutorizanteServices = new UsuarioAutorizanteServices();
		this.personaServices = new PersonaServices();
		
		try {
			this.listaNegralist = this.listaNegraService.findAll();
			this.listaUsuarioAutorizante = this.usuarioAutorizanteServices.listaUsuarioAutorizanteActivos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void nuevoListaNegra() {
		this.listaNegra = new ListaNegra();
	}

	public void registrarListaNegra() {
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);

		this.listaNegra.setEstado(Boolean.FALSE);
		this.listaNegra.setFlagAutorizacion(0);
		this.listaNegra.setUsuarioRegistro(getUsuarioLogin().getIdusuario());
		this.listaNegra.setfRegistro(new Date());

		try {
			this.listaNegraService.registrarListaNegra(this.listaNegra);
			FacesUtils.showFacesMessage("Se registro la persona a la lista negra.",	Constante.INFORMACION);
			context.update("sms");

			this.listaNegralist = this.listaNegraService.findAll();

			this.listaNegra = new ListaNegra();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setearListaNegra(ListaNegra ln) {
		this.listaNegra = ln;
	}
	public void actualizarListaNegra() {
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);

		this.listaNegra.setFlagAutorizacion(1);
		this.listaNegra.setfActualizacion(new Date());
		this.listaNegra.setEstado(Boolean.TRUE);
		

		try {
			this.listaNegraService.actualizarListaNegra(this.listaNegra);
			FacesUtils.showFacesMessage("Se autorizó a la persona de la lista negra.",Constante.INFORMACION);
			context.update("sms");

			this.listaNegralist = this.listaNegraService.findAll();

			this.listaNegra = new ListaNegra();
		} catch (Exception e) {
			e.printStackTrace();
					}

	}

	public void cambiarEstado(ListaNegra ln) {
		// Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		// context.addCallbackParam("esValido", valido );

		try {
			if (ln.isEstado()) {
				ln.setEstado(Boolean.FALSE);
			} else {
				ln.setEstado(Boolean.TRUE);
			}

			this.listaNegraService.actualizarEstado(ln.getIdListaNegra(),
					ln.isEstado());

			FacesUtils.showFacesMessage("Estado de lista negra actualizado correctamente. ",
					Constante.INFORMACION);
			context.update("sms");
			this.listaNegralist = this.listaNegraService.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscarPersona() {
		try {
			Persona p = this.personaServices.findByNroDocumento(this.listaNegra
					.getDNI());
			if (p != null)
				this.listaNegra.setNombreCompleto(p.getNombres() + " "
						+ p.getAPaterno() + " " + p.getAMaterno());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// INICIO PISCOYA
	public void editar(ListaNegra p) {
		
		try {
			ListaNegra pe = new ListaNegra();
			pe = this.listaNegraService.findByIndividuo(p.getDNI());
			this.listaNegra = pe;			
		} catch (Exception e) {
			System.out.println("ERROR EN EDITAR()");
		}

	}

	public void editarListaNegra() {
		
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);		
		try {			
			this.listaNegraService.actualizarListaNegra(this.listaNegra);
			FacesUtils.showFacesMessage("Persona de la lista negra actualizada correctamente.",Constante.INFORMACION);
			context.update("sms");
			this.listaNegralist = this.listaNegraService.findAll();
			this.listaNegra = new ListaNegra();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR editarListaNegra()");
		}
	}

	public void limpiarValores() {

	}

	// FIN PISCOYA

	public List<ListaNegra> getListaNegralist() {
		return listaNegralist;
	}

	public void setListaNegralist(List<ListaNegra> listaNegralist) {
		this.listaNegralist = listaNegralist;
	}

	public ListaNegra getListaNegra() {
		return listaNegra;
	}

	public void setListaNegra(ListaNegra listaNegra) {
		this.listaNegra = listaNegra;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public List<UsuarioAutorizante> getListaUsuarioAutorizante() {
		return listaUsuarioAutorizante;
	}

	public void setListaUsuarioAutorizante(
			List<UsuarioAutorizante> listaUsuarioAutorizante) {
		this.listaUsuarioAutorizante = listaUsuarioAutorizante;
	}

	public List<ListaNegra> getListaNegralistfilter() {
		return listaNegralistfilter;
	}

	public void setListaNegralistfilter(List<ListaNegra> listaNegralistfilter) {
		this.listaNegralistfilter = listaNegralistfilter;
	}
	
	

}

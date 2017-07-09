package com.certicom.ittsa.managedBeans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Producto_DetalleEnc;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.DetalleEncomiendaServices;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.Producto_DetalleEncService;

@ManagedBean(name = "tracking_EncomiendaMB")
@ViewScoped
public class Tracking_EncomiendaMB {
	
	private Encomienda filter;
	private Encomienda encoSelect;

	private List<Encomienda> listDetEnco;
	private List<Encomienda> listDetEncoFilter;
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<DetalleEncomienda> listDetalle;
	
	private EncomiendaServices encomiendaServices;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private DetalleEncomiendaServices detalleEncomiendaServices;
	
	@PostConstruct
	public void init(){
		
		this.filter = new Encomienda();
		this.filter.setFecIni(new Date());
		this.filter.setFecFin(new Date());
		this.encoSelect = new Encomienda();
		
		this.encomiendaServices = new EncomiendaServices();
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.detalleEncomiendaServices = new DetalleEncomiendaServices();
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void listarDestinos() {
		try {
			this.listaDestino = destinoServices.obtenerDestino(this.filter.getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void consultarEncomienda(){
		try {
			this.listDetEnco = this.encomiendaServices.listarEncomiendasporFiltros(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void consultarDetalleEnc(Encomienda e){
		String remiteCompleto = "";
		if(e.getTipoPersona().equals("N")){
			remiteCompleto = e.getRemitente() + "-DNI:"+ e.getDniRemitente();
		} else if(e.getTipoPersona().equals("J")){
			remiteCompleto =  e.getRazonSocialRemitente() + "-RUC:"+e.getRucRemitente()+" | "+e.getRemitente() + "-DNI:"+ e.getDniRemitente();
		}
		this.encoSelect = e;
		this.encoSelect.setRemitcompleto(remiteCompleto);
		
		try {
			this.listDetalle = this.detalleEncomiendaServices.findByIdEncomienda(e.getIdEncomienda());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public Encomienda getFilter() {
		return filter;
	}


	public void setFilter(Encomienda filter) {
		this.filter = filter;
	}


	public List<Encomienda> getListDetEnco() {
		return listDetEnco;
	}


	public List<Encomienda> getListDetEncoFilter() {
		return listDetEncoFilter;
	}


	public void setListDetEnco(List<Encomienda> listDetEnco) {
		this.listDetEnco = listDetEnco;
	}


	public void setListDetEncoFilter(List<Encomienda> listDetEncoFilter) {
		this.listDetEncoFilter = listDetEncoFilter;
	}


	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}


	public List<Destino> getListaDestino() {
		return listaDestino;
	}


	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}


	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public Encomienda getEncoSelect() {
		return encoSelect;
	}

	public void setEncoSelect(Encomienda encoSelect) {
		this.encoSelect = encoSelect;
	}

	public List<DetalleEncomienda> getListDetalle() {
		return listDetalle;
	}

	public void setListDetalle(List<DetalleEncomienda> listDetalle) {
		this.listDetalle = listDetalle;
	}
	
	
	
}

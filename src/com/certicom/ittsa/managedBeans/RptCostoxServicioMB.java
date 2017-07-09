package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.SalidaDetalle;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.SalidaDetalleService;

@ManagedBean(name = "rptCostoxServicioMB")
@ViewScoped
public class RptCostoxServicioMB implements Serializable{

	private Date fecIni;
	private Date fecFin;
	
	private List<Programacion> listProgramacion;
	private List<Programacion> listProgramacionFilter;
	
	private ProgramacionService programacionService;
	private SalidaDetalleService salidaDetalleService;
	
	@PostConstruct
	public void init(){
		fecIni = new Date();
		fecFin = new Date();
		
		this.programacionService = new ProgramacionService();
		this.salidaDetalleService = new SalidaDetalleService();
	}
	
	
	public void listarProgCostoxServicio(){
		try {
			
			this.listProgramacion = this.programacionService.listProgrCostxServicio(getFecIni(), getFecFin());
			
			List<SalidaDetalle> listSD = new ArrayList<SalidaDetalle>();
			
			for (int i = 0; i < this.listProgramacion.size(); i++) {
				Programacion p = this.listProgramacion.get(i);
				listSD = this.salidaDetalleService.listaProductosxSalida(p.getIdSalida());
				double sumCostInd = 0,sumPreUni= 0,sumPreCost = 0;
				for (int j = 0; j < listSD.size(); j++) {
					SalidaDetalle sd = listSD.get(j);
					//sumCostInd += sd.getCostoIndiviual();
					if(sd.getDesgregacion().equals("SI")){
						//sumCostInd += sd.getPrecioUni()/p.getNumAsientos();
						sumCostInd += sd.getCostoxPasj();
					}else{
						sumCostInd += sd.getPrecioUni();
					}
					sumPreUni += sd.getPrecioUni();
					
					sumPreCost += sd.getPrecioCosto();
				}
				System.out.println(" " +p.getNumAsientos()*sumPreUni);
				p.setCostoGlobal(roundDecimal(p.getNumAsientos()*sumPreUni));
				p.setCostoUnitario(roundDecimal(sumCostInd));
				
				this.listProgramacion.set(i, p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private double roundDecimal(double value){
		BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}


	public Date getFecIni() {
		return fecIni;
	}


	public Date getFecFin() {
		return fecFin;
	}


	public List<Programacion> getListProgramacion() {
		return listProgramacion;
	}


	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
	}


	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}


	public void setListProgramacion(List<Programacion> listProgramacion) {
		this.listProgramacion = listProgramacion;
	}


	public List<Programacion> getListProgramacionFilter() {
		return listProgramacionFilter;
	}


	public void setListProgramacionFilter(List<Programacion> listProgramacionFilter) {
		this.listProgramacionFilter = listProgramacionFilter;
	}
	
	
	
}

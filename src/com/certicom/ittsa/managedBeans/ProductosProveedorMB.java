package com.certicom.ittsa.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.certicom.ittsa.domain.Compra;
import com.certicom.ittsa.domain.CompraDetalle;
import com.certicom.ittsa.domain.Proveedor;
import com.certicom.ittsa.services.CompraDetalleService;
import com.certicom.ittsa.services.CompraService;

@ManagedBean(name = "productosProveedorMB")
@ViewScoped
public class ProductosProveedorMB {
	
	private Proveedor proveedor;
	
	private String ordenCompra ="";
	
	private List<Compra> listaCompra;
	private List<Compra> listaCompraFilter;
	private List<CompraDetalle> listaCompraDetalle;
	private List<CompraDetalle> listaCompraDetalleFilter;
	
	private CompraService compraService;
	private CompraDetalleService compraDetalleService;
	
	
	@PostConstruct
	public void inicio(){
		
		this.compraService = new CompraService();
		this.compraDetalleService = new CompraDetalleService();
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.proveedor =(Proveedor) flash.get("proveedor");
		
		
		try {
		//	this.listaCompra = this.compraService.listarCompraxProveedor(this.proveedor.getIdProveedor());
			this.listaCompraDetalle = this.compraDetalleService.listaProductosXProveedor(this.proveedor.getIdProveedor());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void listarComprasxProveedor(){
		try {
			this.listaCompra = this.compraService.listarCompraxProveedor(this.proveedor.getIdProveedor());
			System.out.println("size " + this.listaCompra.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void a(){
		System.out.println("sss");
	}
	
	public void listarCompraDetalle(Compra c){
		System.out.println("entro");
		ordenCompra = c.getOrdenCompra();
		try {
			this.listaCompraDetalle = this.compraDetalleService.listarxIdCompra(c.getIdCompra());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	public Proveedor getProveedor() {
		return proveedor;
	}


	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}


	public List<Compra> getListaCompra() {
		return listaCompra;
	}


	public void setListaCompra(List<Compra> listaCompra) {
		this.listaCompra = listaCompra;
	}


	public List<Compra> getListaCompraFilter() {
		return listaCompraFilter;
	}


	public void setListaCompraFilter(List<Compra> listaCompraFilter) {
		this.listaCompraFilter = listaCompraFilter;
	}


	public List<CompraDetalle> getListaCompraDetalle() {
		return listaCompraDetalle;
	}


	public void setListaCompraDetalle(List<CompraDetalle> listaCompraDetalle) {
		this.listaCompraDetalle = listaCompraDetalle;
	}


	public String getOrdenCompra() {
		return ordenCompra;
	}


	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}


	public List<CompraDetalle> getListaCompraDetalleFilter() {
		return listaCompraDetalleFilter;
	}


	public void setListaCompraDetalleFilter(
			List<CompraDetalle> listaCompraDetalleFilter) {
		this.listaCompraDetalleFilter = listaCompraDetalleFilter;
	}
	
	
	

}

package com.certicom.ittsa.domain;

public class PlantillaDetalle {
	
	private Integer IdPlanDet;
	private Integer IdPlantilla;
	//Id t_agencia_producto
	private Integer IdAgPro;
	private Integer cantDefecto =0;
	
	private Integer IdProducto;
	private String desProducto;
	private String desMedida;
	private String Medida;
	
	private Integer Stock;
	private Integer Idagencia;
	private Integer IdOficina;
	private Integer IdClase;
	private Integer IdOrigen;
	private Integer IdDestino;
	
	private Integer cantSalida = 0;
	private Integer IdServicio;
	
	private Integer idAutoparte;
	private double costoUni; 
	private double costo;
	private Integer cantDist;
	private double costoxPasj;
	private String disgregacion;

	public Integer getIdPlanDet() {
		return IdPlanDet;
	}

	public Integer getIdAutoparte() {
		return idAutoparte;
	}

	public void setIdAutoparte(Integer idAutoparte) {
		this.idAutoparte = idAutoparte;
	}

	public Integer getIdPlantilla() {
		return IdPlantilla;
	}

	public Integer getIdProducto() {
		return IdProducto;
	}

	public String getDesProducto() {
		return desProducto;
	}

	public void setIdPlanDet(Integer idPlanDet) {
		IdPlanDet = idPlanDet;
	}

	public void setIdPlantilla(Integer idPlantilla) {
		IdPlantilla = idPlantilla;
	}

	public void setIdProducto(Integer idProducto) {
		IdProducto = idProducto;
	}

	public void setDesProducto(String desProducto) {
		this.desProducto = desProducto;
	}

	public String getDesMedida() {
		return desMedida;
	}

	public void setDesMedida(String desMedida) {
		this.desMedida = desMedida;
	}

	public String getMedida() {
		return Medida;
	}

	public void setMedida(String medida) {
		Medida = medida;
	}

	public Integer getIdAgPro() {
		return IdAgPro;
	}

	public void setIdAgPro(Integer idAgPro) {
		IdAgPro = idAgPro;
	}

	public Integer getStock() {
		return Stock;
	}

	public Integer getIdagencia() {
		return Idagencia;
	}

	public Integer getIdOficina() {
		return IdOficina;
	}

	public Integer getIdClase() {
		return IdClase;
	}

	public Integer getIdOrigen() {
		return IdOrigen;
	}

	public Integer getIdDestino() {
		return IdDestino;
	}

	public void setStock(Integer stock) {
		Stock = stock;
	}

	public void setIdagencia(Integer idagencia) {
		Idagencia = idagencia;
	}

	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}

	public void setIdClase(Integer idClase) {
		IdClase = idClase;
	}

	public void setIdOrigen(Integer idOrigen) {
		IdOrigen = idOrigen;
	}

	public void setIdDestino(Integer idDestino) {
		IdDestino = idDestino;
	}

	public Integer getCantSalida() {
		return cantSalida;
	}

	public void setCantSalida(Integer cantSalida) {
		this.cantSalida = cantSalida;
	}

	public Integer getIdServicio() {
		return IdServicio;
	}

	public void setIdServicio(Integer idServicio) {
		IdServicio = idServicio;
	}

	public Integer getCantDefecto() {
		return cantDefecto;
	}

	public void setCantDefecto(Integer cantDefecto) {
		this.cantDefecto = cantDefecto;
	}

	public double getCostoUni() {
		return costoUni;
	}

	public void setCostoUni(double costoUni) {
		this.costoUni = costoUni;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Integer getCantDist() {
		return cantDist;
	}

	public void setCantDist(Integer cantDist) {
		this.cantDist = cantDist;
	}

	public double getCostoxPasj() {
		return costoxPasj;
	}

	public void setCostoxPasj(double costoxPasj) {
		this.costoxPasj = costoxPasj;
	}

	public String getDisgregacion() {
		return disgregacion;
	}

	public void setDisgregacion(String disgregacion) {
		this.disgregacion = disgregacion;
	}
	
	
	

}

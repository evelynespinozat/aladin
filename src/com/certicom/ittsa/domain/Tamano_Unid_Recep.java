package com.certicom.ittsa.domain; 

public class Tamano_Unid_Recep implements java.io.Serializable 
{ 
	private static final long serialVersionUID = 1L; 
	private Long cod_tamanour; 
	private String des_tamanour; 

	public Long getCod_tamanour() 
	{return cod_tamanour;} 
 
	public void setCod_tamanour(Long cod_tamanour) 
	{this.cod_tamanour=cod_tamanour;} 
 
	public String getDes_tamanour() 
	{return des_tamanour;} 
 
	public void setDes_tamanour(String des_tamanour) 
	{this.des_tamanour=des_tamanour.toUpperCase();} 
 
} 

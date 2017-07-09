package com.pe.certicom.ittsa.commons;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.services.PilotoService;
@FacesConverter("copiloto")
public class CopilotoConverter implements Converter{
	
	public static List<Piloto> listaPilotos;
	
	static{
		PilotoService pilotoService = new PilotoService();
		try {
			listaPilotos = pilotoService.listaPilotoActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		if (submittedValue.trim().equals("")) {  
	            return null;  
	        } else {  
	            try {  
	              //  int number = Integer.parseInt(submittedValue);  
	  
	                for (Piloto p : listaPilotos) { 
	                    if (p.getNombres().equals(submittedValue)) {  
	                        return p;  
	                    }  
	                }  
	  
	            } catch(NumberFormatException exception) {  
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));  
	            }  
	        }  
	  
	        return null; 
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		 if (value == null || value.equals("")) {  
	            return "";  
	        } else {  
//	            return String.valueOf(((Flota) value).getNumero());  
	            return String.valueOf(((Piloto) value).getNombres());  
	        }  
	}

}

package com.certicom.ittsa.domain;

import java.util.List;   
import javax.faces.model.ListDataModel;   
import org.primefaces.model.SelectableDataModel;   
import com.certicom.ittsa.domain.Encomienda;

		public class EncomiendaDataModel extends ListDataModel<Encomienda> implements SelectableDataModel<Encomienda> {     
		  
			    public EncomiendaDataModel() {   
			    }   
			  
			    public EncomiendaDataModel(List<Encomienda> data) {   
			        super(data);   
			    }   
			       
			    @Override  
			    public Encomienda getRowData(String rowKey) {   
			        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data   
			           
			        List<Encomienda> encomiendas = (List<Encomienda>) getWrappedData();   
			           
			        for(Encomienda enc : encomiendas) {   
			            if(String.valueOf(enc.getIdEncomienda()).equals(rowKey))   
			                return enc;   
			        }   
			           
			        return null;   
			    }   
			  
			    @Override  
			    public Object getRowKey(Encomienda enc) {   
			        return enc.getIdEncomienda();   
			    }   
			}   
			      



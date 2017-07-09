package com.certicom.ittsa.services; 

import java.util.List; 

import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.mapper.SistemaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class SistemaServices implements SistemaMapper{

	SistemaMapper sistemaMapper = (SistemaMapper)ServiceFinder.findBean("sistemaMapper");

	public List<Sistema> listSistema() throws Exception{
	    return sistemaMapper.listSistema();
	}

	public void insertSistema(Sistema sistema) throws Exception{
	    sistemaMapper.insertSistema(sistema);
	}

	public Sistema findSistema(Sistema sistema)  throws Exception{
	    return sistemaMapper.findSistema(sistema);
	}

    @Override
    public void deleteSistema(Long cod_sistema) throws Exception {
        // TODO Auto-generated method stub
        sistemaMapper.deleteSistema(cod_sistema);
    }

    @Override
    public Sistema findSistemaPorCodigo(Long cod_sistema) throws Exception {
        // TODO Auto-generated method stub
        return sistemaMapper.findSistemaPorCodigo(cod_sistema);
    }

    @Override
    public void updateSistema(Sistema sistema) throws Exception {
        // TODO Auto-generated method stub
        sistemaMapper.updateSistema(sistema);
    }

    public List<Sistema> listSistemaxPerfil(Perfil perfil) throws Exception{
	    return sistemaMapper.listSistemaxPerfil(perfil);
	}
} 

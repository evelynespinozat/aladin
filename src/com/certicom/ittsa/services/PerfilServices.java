package com.certicom.ittsa.services; 

import java.util.List; 

import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.mapper.PerfilMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PerfilServices implements PerfilMapper{

	PerfilMapper perfilMapper = (PerfilMapper)ServiceFinder.findBean("perfilMapper");

	public List<Perfil> listPerfil() throws Exception{
	    return perfilMapper.listPerfil();
	}

	public Perfil findPerfil(Perfil perfil)  throws Exception{
	    return perfilMapper.findPerfil(perfil);
	}

	public void updatePerfil(Perfil perfil) throws Exception{
        perfilMapper.updatePerfil(perfil);
    }

    public void insertPerfil(Perfil perfil) throws Exception{
        perfilMapper.insertPerfil(perfil);
    }
    
    @Override
    public void deletePerfil(int cod_perfil) throws Exception {
        // TODO Auto-generated method stub
        perfilMapper.deletePerfil(cod_perfil);
    }

    @Override
    public Perfil findPerfilPorCodigo(int cod_perfil) throws Exception {
        // TODO Auto-generated method stub
        return perfilMapper.findPerfilPorCodigo(cod_perfil);
    }
    
	public void insertUsuarioPerfil(Perfil perfil) throws Exception{
	   perfilMapper.insertUsuarioPerfil(perfil);
	}
	
	public List<Perfil> listarPerfilesxUsuario(Usuario usuario) throws Exception{
		return perfilMapper.listarPerfilesxUsuario(usuario);
		
	}

	@Override
	public List<Perfil> listPerfilActivo() throws Exception {
		return perfilMapper.listPerfilActivo();
	}

} 

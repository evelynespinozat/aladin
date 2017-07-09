package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.mapper.UsuarioMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class UsuarioServices implements UsuarioMapper {

	UsuarioMapper usuarioMapper = (UsuarioMapper) ServiceFinder.findBean("usuarioMapper");

	@Override
	public List<Usuario> buscarPorLoginClave(Usuario usuario) {

		return usuarioMapper.buscarPorLoginClave(usuario);
	}

	@Override
	public void insertUsuario(Usuario usuario) {
		usuarioMapper.insertUsuario(usuario);
	}

	@Override
	public List<Usuario> getlistaUsuario() {
		return usuarioMapper.getlistaUsuario();
	}

	@Override
	public List<Usuario> buscarusuario(Usuario usuario) {
		return usuarioMapper.buscarusuario(usuario);
	}

	@Override
	public Usuario buscarPorId(int idusuario) {
		return usuarioMapper.buscarPorId(idusuario);
	}

	@Override
	public void actualizarEstado(Usuario usuario) throws Exception {
		usuarioMapper.actualizarEstado(usuario);
	}

	@Override
	public void actualizar(Usuario usuario) {
		usuarioMapper.actualizar(usuario);
	}

	@Override
	public void actualizarLogin(Usuario usuario) {
		usuarioMapper.actualizarLogin(usuario);
	}

	@Override
	public void actualizarPassword(Usuario usuario) {
		usuarioMapper.actualizarPassword(usuario);
	}

	@Override
	public void actualizarFechaAcceso(Usuario usuario) {
		usuarioMapper.actualizarFechaAcceso(usuario);
	}

	@Override
	public List<Usuario> buscarPorDNI(String dni) {
		return usuarioMapper.buscarPorDNI(dni);
	}

	@Override
	public List<Usuario> buscarPorNombre(String nombre) {
		return usuarioMapper.buscarPorNombre(nombre);
	}

	@Override
	public List<Usuario> buscarPorApellido(String apellido) {
		return usuarioMapper.buscarPorApellido(apellido);
	}

	@Override
	public Usuario buscarPorLogin(String string) throws Exception {

		return usuarioMapper.buscarPorLogin(string);
	}

	@Override
	public List<Usuario> getlistaUsuario_Perfil(Usuario usuario) {
		return usuarioMapper.getlistaUsuario_Perfil(usuario);
	}

	@Override
	public List<Usuario> getlistaUsuario_byCentroAtencion(
			Integer id_centro_atencion) {
		return usuarioMapper.getlistaUsuario_byCentroAtencion(id_centro_atencion);
	}

	@Override
	public void deleteUsuario(Integer idusuario) throws Exception {
		usuarioMapper.deleteUsuario(idusuario);

	}

	@Override
	public List<Usuario> listarUsuariosXPerfilProceso(String proceso)
			throws Exception {
		return usuarioMapper.listarUsuariosXPerfilProceso(proceso);
	}

	public Usuario getUsuarioxDni(String dni) {
		return usuarioMapper.getUsuarioxDni(dni);
	}

	@Override
	public List<Usuario> listarUsuariosActivos() {
		return usuarioMapper.listarUsuariosActivos();
	}

	@Override
	public int obtenerPuntoVentaxUsu(Integer idPuntoVenta) throws Exception {
		return usuarioMapper.obtenerPuntoVentaxUsu(idPuntoVenta);
	}

	@Override
	public Usuario getUsuario_byIdUsuario(Integer idusuario) {
		return usuarioMapper.getUsuario_byIdUsuario(idusuario);
	}

	@Override
	public List<Usuario> buscarPorPerfil(Integer idOficina, Integer codPerfil) {
		return usuarioMapper.buscarPorPerfil(idOficina, codPerfil);
	}

	@Override
	public int verificarDniCarnetRepetidos(String dniUsuario) throws Exception {
		return usuarioMapper.verificarDniCarnetRepetidos(dniUsuario);
	}
	
	@Override
	public int verificarPuntoVentaRepetidos(Integer idPuntoVenta) throws Exception {
		return usuarioMapper.verificarPuntoVentaRepetidos(idPuntoVenta);
	}

	@Override
	public List<Usuario> buscarPorOficina(Integer idOficina) {
		// TODO Auto-generated method stub
		return usuarioMapper.buscarPorOficina(idOficina);
	}

	public Usuario findByIdUsuarioVenta(Integer idasientoventa) {
		// TODO Auto-generated method stub
		return usuarioMapper.findByIdUsuarioVenta(idasientoventa);
	}

}

package br.ufpa.psi.comportamente.labgame.seguranca;

import br.ufpa.psi.comportamente.labgame.entidades.Usuario;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class DetalhesUsuario implements UserDetails {

	Usuario usuario = null;
	List<GrantedAuthority> permissoes = null;
	
	public DetalhesUsuario(Usuario usua, List<GrantedAuthority> perm) {
		usuario = usua;
		permissoes = perm;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return permissoes;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

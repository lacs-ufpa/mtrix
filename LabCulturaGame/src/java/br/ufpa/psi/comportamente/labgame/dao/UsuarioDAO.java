package br.ufpa.psi.comportamente.labgame.dao;

import br.ufpa.psi.comportamente.labgame.entidades.Usuario;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Weslley
 */
public class UsuarioDAO extends BaseDAO<Usuario> {

      public Usuario find(Usuario usuario) {
            Query query = getEntityManager().createQuery(
                    "SELECT usuario FROM " + Usuario.class.getCanonicalName() + " usuario WHERE usuario.username = :login AND usuario.password = :senha");
            query.setParameter("login", usuario.getUsername());
            query.setParameter("senha", usuario.getPassword());
            return (Usuario) query.getSingleResult();
      }

      public Usuario encontrarPorLogin(String login) {
            Query q = getEntityManager().createQuery("SELECT usuario FROM "+ Usuario.class.getCanonicalName() +" as usuario WHERE  usuario.username = ?1");
            q.setParameter(1, login);
            List<Usuario> usuarios = q.getResultList();
            return usuarios.size() > 0 ? usuarios.get(0) : null;
      }

}

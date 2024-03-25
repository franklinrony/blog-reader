/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package sv.gob.bandesal.blog.facade;

import com.password4j.Hash;
import com.password4j.Password;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.bandesal.blog.entities.Usuario;

/**
 *
 * @author cash america
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "blogPU")
    private EntityManager em;

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public UsuarioFacade(Class<Usuario> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private Usuario getUserByUsername(String user) {
        Usuario usuario = new Usuario();
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario=:usuario");
        q.setParameter("usuario", user);
        usuario = (Usuario) q.getSingleResult();
        return usuario;
    }

    public boolean authenticateUser(String username, String password) {
        Usuario user = getUserByUsername(username);
        boolean verified = Password.check(password, user.getPassword()).withArgon2();
        return verified;
        //return user != null && user.getPassword().equals(password);
    }

}

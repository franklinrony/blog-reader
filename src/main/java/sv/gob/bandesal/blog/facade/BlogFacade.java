/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package sv.gob.bandesal.blog.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.gob.bandesal.blog.entities.Blog;

/**
 *
 * @author cash america
 */
@Stateless
public class BlogFacade extends AbstractFacade<Blog> {

    @PersistenceContext(unitName = "blogPU")
    private EntityManager em;

    public BlogFacade() {
        super(Blog.class);
    }

    public BlogFacade(Class<Blog> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

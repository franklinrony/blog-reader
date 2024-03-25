/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package sv.gob.bandesal.blog.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.gob.bandesal.blog.entities.Reader;

/**
 *
 * @author cash america
 */
@Stateless
public class ReaderFacade extends AbstractFacade<Reader> {

    @PersistenceContext(unitName = "blogPU")
    private EntityManager em;

    public ReaderFacade() {
        super(Reader.class);
    }

    public ReaderFacade(Class<Reader> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}

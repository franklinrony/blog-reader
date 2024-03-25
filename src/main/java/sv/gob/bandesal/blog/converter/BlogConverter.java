/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.bandesal.blog.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import sv.gob.bandesal.blog.entities.Blog;
import sv.gob.bandesal.blog.facade.BlogFacade;

/**
 *
 * @author cash america
 */
@ManagedBean(name = "blogConverter")
@RequestScoped
public class BlogConverter implements Converter {

    @EJB
    private BlogFacade blogFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

            if (value == null) {
                return null;
            } else if (value.trim().isEmpty()) {
                return null;
            }
            return blogFacade.find(Long.valueOf(value));
  
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Blog) {
            Blog blog = (Blog) value;
            if (blog.getId() != null) {
                return blog.getId().toString();
            }
            return null;
        } else {
            throw new ConverterException("El objeto " + value + " es del tipo " + value.getClass().getName()
                    + "; el tipo esperado es: " + Blog.class.getName());
        }
    }

}

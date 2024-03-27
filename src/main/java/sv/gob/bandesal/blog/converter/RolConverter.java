/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.bandesal.blog.converter;

import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import sv.gob.bandesal.blog.entities.Rol;
import sv.gob.bandesal.blog.facade.RolFacade;

/**
 *
 * @author cash america
 */
@ManagedBean(name = "rolConverter")
@RequestScoped
public class RolConverter implements Converter {

    @EJB
    private RolFacade rolFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        BigDecimal decimal = new BigDecimal(value);
        if (value == null) {
            return null;
        } else if (value.trim().isEmpty()) {
            return null;
        }
        return rolFacade.find(decimal);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Rol) {
            Rol rol = (Rol) value;
            if (rol.getId() != null) {
                return rol.getId().toString();
            }
            return null;
        } else {
            throw new ConverterException("El objeto " + value + " es del tipo " + value.getClass().getName()
                    + "; el tipo esperado es: " + Rol.class.getName());
        }
    }

}

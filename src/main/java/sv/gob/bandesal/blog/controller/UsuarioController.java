/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.bandesal.blog.controller;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;
import sv.gob.bandesal.blog.entities.Usuario;
import sv.gob.bandesal.blog.facade.UsuarioFacade;
import sv.gob.bandesal.blog.util.JsfUtil;

/**
 *
 * @author Franklin Rony Cortez
 */
@Named(value = "usuarioController")
@ViewScoped
public class UsuarioController implements Serializable {

    private Usuario usuarioSeleccionado;
    private List<Usuario> listaUsuarios;

    @EJB
    UsuarioFacade usuarioFacade;

    /**
     * Creates a new instance of UsuarioController
     */
    public UsuarioController() {
        listaUsuarios = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaUsuarios = usuarioFacade.findAll();
    }

    public void nuevo() {
        usuarioSeleccionado = new Usuario();
        PrimeFaces.current().executeScript("PF('blockUI').hide();");
    }

    public void crear() {
        try {
            //Si no hay errores de validacion
            if (!validar()) {

                //Persistir el objeto
                usuarioFacade.create(usuarioSeleccionado);
                JsfUtil.addSuccessMessage("Usuario guardado correctamente");
                PrimeFaces.current().ajax().update("growl");
                PrimeFaces.current().executeScript("PF('createEditDlg').hide();");
                init();
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage("Ocurrio un error al intentar guardar el nuevo elemento, contacte al administrador.");
        }
    }

    private boolean validar() {
        boolean errores = false;
        if (usuarioSeleccionado.getUsuario().isEmpty()) {
            JsfUtil.addErrorMessage("El usuario no puede estar vacio.");
            errores = true;
        }
        if (usuarioSeleccionado.getPassword().isEmpty()) {
            JsfUtil.addErrorMessage("El usuario no puede estar vacio.");
            errores = true;
        }
        return errores;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    
    
}

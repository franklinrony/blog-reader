/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.bandesal.blog.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sv.gob.bandesal.blog.facade.UsuarioFacade;
import sv.gob.bandesal.blog.util.JsfUtil;

/**
 *
 * @author cash america
 */
@Named(value = "sessionController")
@SessionScoped
public class SessionController implements Serializable {

    private String nombreUsuarioLogueado;
    private String rol;
    @EJB
    private UsuarioFacade usuarioFacade;

    public SessionController() {
    }

    @PostConstruct
    public void init() {
        try {
            nombreUsuarioLogueado = JsfUtil.getUsuarioContextJsf();
            rol = usuarioFacade.getUserByUsername(nombreUsuarioLogueado).getRolList().get(0).getDescripcion();
            System.out.println("rol "+rol);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ocurrio un error al intentar recuperar la informacion del usuario.");

        }

    }

    public void activarIdle() {
        System.out.println("OCURRIO UN EVENTO!!!!!!!");
    }

    public void logout() throws ServletException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        try {
            HttpSession session = (HttpSession) request.getSession();
            session.invalidate();
            request.logout();
            externalContext.redirect(externalContext.getRequestContextPath() + "/faces/dashboard.xhtml?faces-redirect=false");
        } catch (IOException e) {
            facesContext.addMessage(e.getMessage(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Autenticación", "Fallo al cerrar la session actual..."));
        }
    }

    public String obtenerFechaString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        return dateFormat.format(new Date());
    }

    public String getNombreUsuarioLogueado() {
        return nombreUsuarioLogueado;
    }

    public void setNombreUsuarioLogueado(String nombreUsuarioLogueado) {
        this.nombreUsuarioLogueado = nombreUsuarioLogueado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}

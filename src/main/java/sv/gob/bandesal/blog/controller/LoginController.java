/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package sv.gob.bandesal.blog.controller;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sv.gob.bandesal.blog.entities.Reader;
import sv.gob.bandesal.blog.entities.Usuario;
import sv.gob.bandesal.blog.facade.UsuarioFacade;
import sv.gob.bandesal.blog.util.JsfUtil;

/**
 *
 * @author cash america
 */
@Named(value = "loginController")
@ViewScoped
public class LoginController implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;
    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

    private Usuario usuario;

    public LoginController() {
        usuario = new Usuario();
    }

    public void login() {
        if (!this.validar()) {
            boolean resultado = usuarioFacade.authenticateUser(usuario.getUsuario().trim(), usuario.getPassword().trim());
            if (resultado) {
                try {
                    HttpSession session = (HttpSession) context.getSession(true);

                    // Guarda un atributo en la sesión
                    session.setAttribute("usuario", usuario.getUsuario());
                    context.redirect("/blog/blog/listaBlog.xhtml");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JsfUtil.addErrorMessage("El usuario o password son incorrectos. Intente nuevamente");

            }
        }

    }

    private boolean validar() {
        boolean errores = false;

        if (usuario.getUsuario().isEmpty()) {
            JsfUtil.addErrorMessage("El usuario no puede estar vacio.");
            errores = true;
        }
        if (usuario.getPassword().isEmpty()) {
            JsfUtil.addErrorMessage("Debe escribir un password.");
            errores = true;
        }
        return errores;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

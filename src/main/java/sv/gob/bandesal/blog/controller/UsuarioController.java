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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;
import sv.gob.bandesal.blog.entities.Rol;
import sv.gob.bandesal.blog.entities.Usuario;
import sv.gob.bandesal.blog.facade.RolFacade;
import sv.gob.bandesal.blog.facade.UsuarioFacade;
import sv.gob.bandesal.blog.util.Elytron;
import sv.gob.bandesal.blog.util.JsfUtil;

/**
 *
 * @author Franklin Rony Cortez
 */
@Named(value = "usuarioController")
@ViewScoped
public class UsuarioController implements Serializable {

    private Usuario usuarioSeleccionado;
    private Rol rolSeleccionado;
    private String password;
    private String rePassword;
    //Listas
    private List<Usuario> listaUsuarios;
    private List<Rol> listaRoles;
    private List<Rol> rolesUsuario;

    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private RolFacade rolFacade;

    /**
     * Creates a new instance of UsuarioController
     */
    public UsuarioController() {
        listaUsuarios = new ArrayList<>();
        listaRoles = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaUsuarios = usuarioFacade.findAll();
        listaRoles = rolFacade.findAll();
    }

    public void nuevo() {
        usuarioSeleccionado = new Usuario();
        PrimeFaces.current().executeScript("PF('blockUI').hide();");
    }

    public void crear() {
        try {
            //Si no hay errores de validacion
            if (!validar()) {
                //Generar hash del password
                usuarioSeleccionado.setPassword(Elytron.hashGenerator(password));
                //Setear el rol seleccionado
                this.agregarRoles();
                usuarioSeleccionado.setRolList(rolesUsuario);
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

    private void agregarRoles() {
        rolesUsuario = new ArrayList<>();
        rolesUsuario.add(rolSeleccionado);
    }

    private boolean validar() {
        boolean errores = false;
        Pattern regAlfabetico = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", Pattern.CASE_INSENSITIVE);
        Pattern regemail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", Pattern.CASE_INSENSITIVE);

        Matcher matchNombres = regAlfabetico.matcher(usuarioSeleccionado.getNombres());
        Matcher matchApellidos = regAlfabetico.matcher(usuarioSeleccionado.getApellidos());
        Matcher matchEmail = regemail.matcher(usuarioSeleccionado.getEmail());

        for (Usuario usuarioAgregar : listaUsuarios) {
            if (usuarioAgregar!= usuarioSeleccionado) {
                if (usuarioAgregar.getUsuario().toUpperCase()
                        .equals(usuarioSeleccionado.getUsuario().toUpperCase().trim())) {
                    JsfUtil.addErrorMessage("Ya existe un registro con el mismo usuario.");
                    errores = true;
                }
                if (usuarioAgregar.getEmail().toUpperCase()
                        .equals(usuarioSeleccionado.getEmail().toUpperCase().trim())) {
                    JsfUtil.addErrorMessage("Ya existe un registro con el mismo email.");
                    errores = true;
                }
            }
        }
        if(rolSeleccionado==null){
            JsfUtil.addErrorMessage("Debe seleccionar un rol.");
            errores = true;
        }
        if (usuarioSeleccionado.getNombres().isEmpty()) {
            JsfUtil.addErrorMessage("Nombres no puede estar vacio.");
            errores = true;
        }
        if (usuarioSeleccionado.getApellidos().isEmpty()) {
            JsfUtil.addErrorMessage("Apellidos no puede estar vacio.");
            errores = true;
        }
        if (usuarioSeleccionado.getEmail().isEmpty()) {
            JsfUtil.addErrorMessage("El email no puede estar vacio.");
            errores = true;
        }
        if (usuarioSeleccionado.getUsuario().isEmpty()) {
            JsfUtil.addErrorMessage("El usuario no puede estar vacio.");
            errores = true;
        }
        if (password.isEmpty() && usuarioSeleccionado.getPassword()==null) {
            JsfUtil.addErrorMessage("Password no puede estar vacio.");
            errores = true;
        }
        if (rePassword.isEmpty() && usuarioSeleccionado.getPassword()==null) {
            JsfUtil.addErrorMessage("Repetir password no puede estar vacio.");
            errores = true;
        }
        if (!matchNombres.matches()) {
            JsfUtil.addErrorMessage("Nombres solamente puede contener caracteres Alfabeticos.");
            errores = true;
        }
        if (!matchApellidos.matches()) {
            JsfUtil.addErrorMessage("Apellidos solamente puede contener caracteres Alfabeticos.");
            errores = true;
        }
        if (!matchEmail.matches()) {
            JsfUtil.addErrorMessage("Escriba una direccion de correo valida: nombre@dominio.com");
            errores = true;
        }
        return errores;
    }
    public void editar() {
        try {
            if (!validar()) {
                if(!password.isEmpty()){
                    //Generar hash del password
                    usuarioSeleccionado.setPassword(Elytron.hashGenerator(password));
                }
               
                //Setear el rol seleccionado
                this.agregarRoles();
                usuarioSeleccionado.setRolList(rolesUsuario);
                //Editar el objeto
                usuarioFacade.edit(usuarioSeleccionado);
                JsfUtil.addSuccessMessage("Usuario actualizado correctamente");
                PrimeFaces.current().ajax().update("growl");
                PrimeFaces.current().executeScript("PF('createEditDlg').hide();");
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Ocurrio un error al intentar editar el usuario, contacte al administrador.");

        }
    }

    public void borrar() {
        try {
     
                //Borrar el objeto
                usuarioFacade.remove(usuarioSeleccionado);
                JsfUtil.addSuccessMessage("usuario borrado correctamente");
                PrimeFaces.current().ajax().update("growl");
                init();
            
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Ocurrio un error al intentar borrar el usuario, contacte al administrador.");

        }
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

}

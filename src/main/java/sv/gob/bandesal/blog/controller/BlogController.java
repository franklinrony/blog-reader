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
import sv.gob.bandesal.blog.entities.Blog;
import sv.gob.bandesal.blog.facade.BlogFacade;
import sv.gob.bandesal.blog.util.JsfUtil;

/**
 *
 * @author cash america
 */
@Named(value = "blogController")
@ViewScoped
public class BlogController implements Serializable {

    private Blog blogSeleccionado;
    private List<Blog> listaBlogs;
    //EJB's
    @EJB
    private BlogFacade blogFacade;

    /**
     * Creates a new instance of BlogController
     */
    public BlogController() {
        listaBlogs = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaBlogs = blogFacade.findAll();
    }

    /**
     * Formulario crear nuevo blog
     */
    public void nuevo() {
        blogSeleccionado = new Blog();
        PrimeFaces.current().executeScript("PF('blockUI').hide();");
    }

    /**
     * Crear un nuevo blog
     */
    public void crear() {
        try {
            //Si no hay errores de validacion
            if (!validar()) {

                //Persistir el objeto
                blogFacade.create(blogSeleccionado);
                JsfUtil.addSuccessMessage("Blog guardado correctamente");
                PrimeFaces.current().ajax().update("growl");
                PrimeFaces.current().executeScript("PF('createEditDlg').hide();");
                init();
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage("Ocurrio un error al intentar guardar el nuevo elemento, contacte al administrador.");
        }
    }

    /**
     * Editar un blog
     */
    public void editar() {
        try {
            if (!validar()) {
                //Editar el objeto
                blogFacade.edit(blogSeleccionado);
                JsfUtil.addSuccessMessage("Blog actualizado correctamente");
                PrimeFaces.current().ajax().update("growl");
                PrimeFaces.current().executeScript("PF('createEditDlg').hide();");
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Ocurrio un error al intentar editar el blog, contacte al administrador.");

        }
    }

    public void borrar() {
        try {
            if (!validarBorrar()) {
                //Borrar el objeto
                blogFacade.remove(blogSeleccionado);
                JsfUtil.addSuccessMessage("Blog borrado correctamente");
                PrimeFaces.current().ajax().update("growl");
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Ocurrio un error al intentar borrar el blog, contacte al administrador.");

        }
    }

    private boolean validarBorrar() {
        boolean errores = false;
        if (!blogSeleccionado.getReaders().isEmpty()) {
            JsfUtil.addErrorMessage("Este blog tiene  readers inscritos. Debe removerlos del blog antes de eliminarlo");
            PrimeFaces.current().ajax().update("growl");
            errores = true;
        }
        return errores;
    }

    /**
     * Validacion antes de guardar
     */
    private boolean validar() {
        boolean errores = false;
        for (Blog blogAgregar : listaBlogs) {
            if (blogAgregar != blogSeleccionado) {
                if (blogAgregar.getTitle().toUpperCase().trim()
                        .equals(blogSeleccionado.getTitle().toUpperCase().trim())) {
                    JsfUtil.addErrorMessage("Ya existe un blog con el mismo nombre.");
                    errores = true;
                }

            }
        }
        if (blogSeleccionado.getTitle().isEmpty()) {
            JsfUtil.addErrorMessage("El titulo  no puede estar vacio.");
            errores = true;
        }
        if (blogSeleccionado.getDescription().isEmpty()) {
            JsfUtil.addErrorMessage("La descripcion no puede estar vacia.");
            errores = true;
        } else if (blogSeleccionado.getDescription().length() < 20) {
            JsfUtil.addErrorMessage("La descripcion debe ser mayor a 20 caracteres.");
            errores = true;
        }
        return errores;
    }

    public List<Blog> getListaBlogs() {
        return listaBlogs;
    }

    public Blog getBlogSeleccionado() {
        return blogSeleccionado;
    }

    public void setBlogSeleccionado(Blog blogSeleccionado) {
        this.blogSeleccionado = blogSeleccionado;
    }

}

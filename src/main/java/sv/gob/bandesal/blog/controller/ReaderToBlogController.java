/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
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
import javax.persistence.EntityTransaction;
import org.primefaces.PrimeFaces;
import sv.gob.bandesal.blog.entities.Blog;
import sv.gob.bandesal.blog.entities.Reader;
import sv.gob.bandesal.blog.facade.BlogFacade;
import sv.gob.bandesal.blog.facade.ReaderFacade;
import sv.gob.bandesal.blog.util.JsfUtil;

/**
 *
 * @author cash america
 */
@Named(value = "readerToBlogController")
@ViewScoped
public class ReaderToBlogController implements Serializable {

    private List<Reader> listaReaders;
    private List<Blog> listaBlogs;

    private Blog blogSeleccionado;
    private Reader readerSeleccionado;
    //EJB's
    @EJB
    private ReaderFacade readerFacade;
    @EJB
    private BlogFacade blogFacade;

    /**
     * Creates a new instance of ReaderToBlog
     */
    public ReaderToBlogController() {
        listaBlogs = new ArrayList<>();
        listaReaders = new ArrayList<>();
        blogSeleccionado = new Blog();
        readerSeleccionado = new Reader();
    }

    @PostConstruct
    public void init() {
        listaReaders = readerFacade.findAll();
        listaBlogs = blogFacade.findAll();

    }

    public void asignar() {
        try {
            List<Reader> readers = this.findReadersToBlog(blogSeleccionado.getId());
            if (readers == null) {
                readers = new ArrayList<>();
                readers.add(readerSeleccionado);
            }
            if (readers.contains(readerSeleccionado)) {
                JsfUtil.addErrorMessage("Reader ya ha sido agregado a este blog. Seleccione otro reader");
            } else {
                readers.add(readerSeleccionado);
                JsfUtil.addSuccessMessage("Reader agregado correctamente al blog");

            }
            blogSeleccionado.setReaders(readers);
            blogFacade.edit(blogSeleccionado);
            listaBlogs = blogFacade.findAll();
            PrimeFaces.current().ajax().update("growl");
            PrimeFaces.current().executeScript("PF('createEditDlg').hide();");
            init();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage("Ocurrio un error al intentar asociar el reader al blog, contacte al administrador.");
        }

    }

    public void borrar() {
        try {
            List<Reader> readers = this.findReadersToBlog(blogSeleccionado.getId());
            if (readers != null) {
                readers.remove(readerSeleccionado);
                JsfUtil.addSuccessMessage("Reader removido correctamente del blog");
            } else if (!readers.contains(readerSeleccionado)) {
                JsfUtil.addErrorMessage("Usuario ya no esta registrado en el blog seleccionado");

            }

            blogSeleccionado.setReaders(readers);
            blogFacade.edit(blogSeleccionado);
            listaBlogs = blogFacade.findAll();
            PrimeFaces.current().ajax().update("growl");
            PrimeFaces.current().executeScript("PF('createEditDlg2').hide();");
            init();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage("Ocurrio un error al intentar quitar el reader al blog, contacte al administrador.");
        }

    }

    private List<Reader> findReadersToBlog(Long id) {
        List<Reader> readers = blogFacade.find(id).getReaders();
        return readers;

    }

    public List<Reader> getListaReaders() {
        return listaReaders;
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

    public Reader getReaderSeleccionado() {
        return readerSeleccionado;
    }

    public void setReaderSeleccionado(Reader readerSeleccionado) {
        this.readerSeleccionado = readerSeleccionado;
    }

}

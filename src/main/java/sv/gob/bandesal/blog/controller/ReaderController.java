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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;
import sv.gob.bandesal.blog.entities.Reader;
import sv.gob.bandesal.blog.facade.ReaderFacade;
import sv.gob.bandesal.blog.util.JsfUtil;

/**
 *
 * @author cash america
 */
@Named(value = "readerController")
@ViewScoped
public class ReaderController implements Serializable {

    private Reader readerSeleccionado;
    private List<Reader> listaReaders;
    //EJB's
    @EJB
    private ReaderFacade readerFacade;

    public ReaderController() {
        listaReaders = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaReaders = readerFacade.findAll();
    }

    /**
     * Formulario crear nuevo reader
     */
    public void nuevo() {
        readerSeleccionado = new Reader();
        PrimeFaces.current().executeScript("PF('blockUI').hide();");
    }

    /**
     * Crear un nuevo reader
     */
    public void crear() {
        try {
            //Si no hay errores de validacion
            if (!validar()) {

                //Persistir el objeto
                readerFacade.create(readerSeleccionado);
                JsfUtil.addSuccessMessage("Reader guardado correctamente");
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
     * Editar un reader
     */
    public void editar() {
        try {
            if (!validar()) {
                //Editar el objeto
                readerFacade.edit(readerSeleccionado);
                JsfUtil.addSuccessMessage("Reader actualizado correctamente");
                PrimeFaces.current().ajax().update("growl");
                PrimeFaces.current().executeScript("PF('createEditDlg').hide();");
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Ocurrio un error al intentar editar el reader, contacte al administrador.");

        }
    }

    public void borrar() {
        try {
            if (!validar()) {
                //Borrar el objeto
                readerFacade.remove(readerSeleccionado);
                JsfUtil.addSuccessMessage("Reader borrado correctamente");
                PrimeFaces.current().ajax().update("growl");
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Ocurrio un error al intentar borrar el reader, contacte al administrador.");

        }
    }

    /**
     * Validacion antes de guardar
     */
    private boolean validar() {
        boolean errores = false;
//Probar expresion regular
        Pattern pattern = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(readerSeleccionado.getName());
        for (Reader readerAgregar : listaReaders) {
            if (readerAgregar != readerSeleccionado) {
                if (readerAgregar.getName().toUpperCase()
                        .equals(readerSeleccionado.getName().toUpperCase().trim())) {
                    JsfUtil.addErrorMessage("Ya existe un reader con el mismo nombre.");
                    errores = true;
                }

            }
        }
        if (readerSeleccionado.getName().isEmpty()) {
            JsfUtil.addErrorMessage("El nombre no puede estar vacio.");
            errores = true;
        }
        if (!matcher.matches()) {
            JsfUtil.addErrorMessage("El nombre solamente puede contener caracteres Alfabeticos.");
            errores = true;
        }
        return errores;
    }

    public List<Reader> getListaReaders() {
        return listaReaders;
    }

    public Reader getReaderSeleccionado() {
        return readerSeleccionado;
    }

    public void setReaderSeleccionado(Reader readerSeleccionado) {
        this.readerSeleccionado = readerSeleccionado;
    }

}

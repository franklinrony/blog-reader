/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.bandesal.blog.rest;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import sv.gob.bandesal.blog.entities.Reader;
import sv.gob.bandesal.blog.facade.ReaderFacade;

/**
 *
 * @author cash america
 */
@Path("readers")
public class ReaderResource {

    @EJB
    private ReaderFacade readerFacade;

    @GET
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public Response getAllReaders() {
        List<Reader> readers = readerFacade.findAll();

        return Response
                .ok(readers)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public Response getReaderById(@PathParam("id") Long id) {
        Reader reader = readerFacade.find(id);

        if (reader != null) {
            return Response.ok(reader).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

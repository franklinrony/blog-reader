package sv.gob.bandesal.blog.rest;

import sv.gob.bandesal.blog.entities.Blog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import sv.gob.bandesal.blog.facade.BlogFacade;

@Path("blogs")
public class BlogResource {

    @EJB
    private BlogFacade blogFacade;

    @GET
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public Response getAllBlogs() {
        List<Blog> blogs = blogFacade.findAll();

        return Response
                .ok(blogs)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public Response getBlogById(@PathParam("id") Long id) {
        Blog blog = blogFacade.find(id);

        if (blog != null) {
            return Response.ok(blog).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

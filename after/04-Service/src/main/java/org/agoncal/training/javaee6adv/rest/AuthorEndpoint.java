package org.agoncal.training.javaee6adv.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.agoncal.training.javaee6adv.model.Author;
import org.agoncal.training.javaee6adv.service.AuthorService;

/**
 * 
 */
@Stateless
@Path("/authors")
public class AuthorEndpoint
{

   @Inject
   private AuthorService service;

   @POST
   @Consumes("application/xml")
   public Response create(Author entity)
   {
      entity = service.persist(entity);
      return Response.created(UriBuilder.fromResource(AuthorEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Author entity = service.findById(id);
      if (entity == null) {
        return Response.status(Status.NOT_FOUND).build();
      }
      service.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/xml")
   public Response findById(@PathParam("id") Long id)
   {
      Author entity = service.findById(id);
      if (entity == null) {
        return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/xml")
   public List<Author> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
   {
      return service.listAll(startPosition, maxResult);
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/xml")
   public Response update(Author entity)
   {
      entity = service.merge(entity);
      return Response.noContent().build();
   }
}
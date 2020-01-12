package at.htl.library.rest;

import at.htl.library.business.LoanDaoPan;
import at.htl.library.business.PersonDao;
import at.htl.library.model.Exemplar;
import at.htl.library.model.Loan;
import at.htl.library.model.Person;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonEndpoint {
    @Inject
    PersonDao personDao;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        List<Person> entities = personDao.get();
        return Response.ok().entity(entities).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response get(@PathParam("id") long id){
        Person entity = personDao.get(id);
        if(entity != null){
            return Response.ok().entity(entity).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id){
        Person entity = personDao.get(id);
        if(entity != null){
            personDao.remove(entity);
        }
        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Person entity){
        try {
            entity = personDao.save(entity);
        }catch(PersistenceException e){
            return Response.status(400).build();
        }
        return Response.ok().entity(entity).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(Person entity){
        entity = personDao.update(entity);
        return Response.ok().entity(entity).build();
    }
}
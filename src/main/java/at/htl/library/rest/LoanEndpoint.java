package at.htl.library.rest;

import at.htl.library.business.LoanDao;
import at.htl.library.business.LoanDaoPan;
import at.htl.library.model.Exemplar;
import at.htl.library.model.Loan;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("loans")
@Produces(MediaType.APPLICATION_JSON)
public class LoanEndpoint {
    @Inject
    LoanDaoPan loanDao;


    @GET
    @Path("/person/{id}")
    public Response getUnfinishedLoans(@PathParam("id") long pid){
        try {
            List<Loan> loans = loanDao.getUnfinishedByPerson(pid);
            return Response.ok(loans).build();
        } catch (Exception e){
            return Response.serverError().build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/finish")
    public Response finish(List<Exemplar> exemplars){
        try {
            Loan l = loanDao.getUnfinishedLoanWithExemplars(exemplars);
            if(l == null){
                return Response.noContent().build();
            }
            l = loanDao.finish(l);
            return Response.ok(l).build();
        } catch (Exception e){
            return Response.serverError().build();
        }
    }
}

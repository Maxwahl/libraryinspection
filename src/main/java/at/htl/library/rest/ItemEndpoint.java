package at.htl.library.rest;


import at.htl.library.business.ItemDao;
import at.htl.library.business.ItemDaoPan;
import at.htl.library.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("items")
@Produces(MediaType.APPLICATION_JSON)
public class ItemEndpoint {

    @Inject
    ItemDaoPan itemDao;

    @Path("/ranking")
    @GET
    public Response itemRanking(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();

            List<Object[]> stats = itemDao.getStatistics();
            for (Object[] line :stats) {
                ObjectNode objectNode = mapper.createObjectNode();
                ObjectNode childNode = objectNode.putObject("item");
                childNode = ((Item)line[0]).jsonify(childNode);
                objectNode.put("count", Integer.parseInt(line[1].toString()));
                arrayNode.add(objectNode);
            }
            return Response.ok(arrayNode).build();
        }catch (Exception ex){
            return Response.serverError().build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Item entity){
        try {
            entity = itemDao.save(entity);
            return Response.ok().entity(entity).build();
        }catch(PersistenceException e){
            return Response.serverError().build();
        }
    }
    @PUT
    @Path("exemplar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createExemplars(Item entity){
        try {
            if(entity instanceof Book){
                entity = itemDao.getBook(entity.getId());
            }
            else {
                entity = itemDao.getCD(entity.getId());
            }
            Exemplar ex = itemDao.saveExemplar(entity);
            return Response.ok().entity(ex).build();
        }catch(PersistenceException e){
            return Response.serverError().build();
        }
    }

}

package monitoringweb.beans.rest;

import dao.EventFacade;
import dao.MeasureFacade;
import dao.MetersFacade;
import entities.Event;
import entities.Measure;
import entities.Meter;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/monitoring")
public class RestApi {
    
    @EJB
    MetersFacade metersFacade;
    @EJB
    EventFacade eventFacade;
    @EJB
    MeasureFacade measureFacade;
    
    
    @GET
    @Path("/meters")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response getSupportedMeters(){
        Response response;
       List<Meter> meters = metersFacade.findAll();
       if(meters==null){
           response = Response.noContent().build();
       }
       else{
           response = Response.accepted(meters).build();
       }
       return response;
    }    
    @GET
    @Path("/events")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response getSupportedEvents(){
        Response response;
       List<Event> events = eventFacade.findAll();
       if(events==null){
           response = Response.noContent().build();
       }
       else{
           response = Response.accepted(events).build();
       }
       return response;
    }
    
    @GET
    @Path("/measures")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response getMeasureByDate(@QueryParam("dateFrom")Date dateFrom,
                                     @QueryParam("dateTo")Date dateTo){
        
        Response response;
        List<Measure> measures = measureFacade.findByDate(dateFrom,dateTo);
        if(measures==null){
           response = Response.noContent().build();
       }
       else{
           response = Response.accepted(measures).build();
       }
       return response;
        
    }
}

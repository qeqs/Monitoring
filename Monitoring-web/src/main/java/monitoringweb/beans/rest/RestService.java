package monitoringweb.beans.rest;

import dao.EventFacade;
import dao.MeasureFacade;
import dao.MetersFacade;
import entities.Event;
import entities.Measure;
import entities.Meter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("monitoring")
public class RestService {

    @EJB
    MetersFacade metersFacade;
    @EJB
    EventFacade eventFacade;
    @EJB
    MeasureFacade measureFacade;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessages(){
        return "Hello world!";
    }
    
    
    @GET
    @Path("meters")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Meter> getSupportedMeters() {
        return metersFacade.findAll();
    }

    @GET
    @Path("events")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> getSupportedEvents() {       
        return eventFacade.findAll();
    }

    @POST
    @Path("measures")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getMeasureByDate(@QueryParam("dateFrom") String dateFrom,
            @QueryParam("dateTo") String dateTo) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        if (dateFrom == null || dateTo == null) {
            dateFrom = new Date().toString();
            dateTo = new Date().toString();
        }
        Response response;
        List<Measure> measures = null;
        try {
            measures = measureFacade.findByDate(dateFormat.parse(dateFrom), dateFormat.parse(dateTo));
        } catch (ParseException ex) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        if (measures == null) {
            response = Response.noContent().build();
        } else {
            response = Response.ok(measures).build();
        }
        return response;

    }

    @POST
    @Path("meters")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createNewMeter(Meter meter) {
        if (meter == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            metersFacade.create(meter);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }

    @POST
    @Path("events")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createNewEvent(Event event) {
        if (event == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            eventFacade.create(event);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }
    
    @POST
    @Path("measures/measure")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response createNewMeasure(Measure measure){
        if(measure == null) return Response.status(Response.Status.BAD_REQUEST).build();
        try {            
        measureFacade.create(measure);
        return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        
    }
}

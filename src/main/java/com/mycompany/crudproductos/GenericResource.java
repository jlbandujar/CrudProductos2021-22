/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crudproductos;

import java.util.List;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import modelo.Crud;
import modelo.Productos;

/**
 * REST Web Service
 *
 * @author DAW2
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.crudproductos.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }

     @GET
    @Path("/test/{name}")
    public Response ping2( @PathParam("name") String name ){
                return Response
                .ok("Hola " + name)
                .build();
    }  
     
    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {

        //TODO return proper representation object
       JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject persona = factory.createObjectBuilder()
     .add("nombre", "Juan")
     .add("apellido", "Sanchez")
     .add("edad", 25)
     .add("direccion", factory.createObjectBuilder()
         .add("calle", "Norte 21")
         .add("ciudad", "Alcázar")
         .add("provincia", "Ciudad Real ")
         .add("codpostal", "10021"))
     .add("telefono", factory.createArrayBuilder()
         .add(factory.createObjectBuilder()
             .add("tipo", "casa")
             .add("numero", "212 555-1234"))
         .add(factory.createObjectBuilder()
             .add("tipo", "fax")
             .add("numero", "646 555-4567")))
     .build();
    /* ResponseBuilder res = Response.ok(persona.toString());   
    return res.build();*/
    return Response
                .ok(persona.toString())
                .build();
    }
    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    
     @GET
     @Path("/productos/")
     @Produces(MediaType.APPLICATION_JSON)
     public List<Productos> getProductos(){
        List<Productos>  misProductos = Crud.getProductos();
        return misProductos;
}   

       @GET
     @Path("/producto/{id}")
     @Produces(MediaType.APPLICATION_JSON)
     public Productos getProducto(@PathParam("id") int id){
        Productos  miProducto = Crud.getProducto(id);
        return miProducto;
     }
     
    @PUT
    @Path("/producto/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Productos updateProducto(Productos prod) {
        Crud.actualizaProducto(prod);
        return prod;
    }

    @POST
    @Path("/producto/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void SetProducto(Productos prod) {
        Crud.insertaProducto(prod);
            }

     @DELETE
     @Path("/producto/{id}")
     public void borraProducto(@PathParam("id") int id){
        Crud.destroyProducto(id);
     }

}

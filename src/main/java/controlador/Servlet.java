/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Crud;
import modelo.Productos;

/**
 *
 * @author DAW2
 */
public class Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                       
            /* TODO output your page here. You may use following sample code. */
            String op = "listar";
            if (request.getParameter("op")!=null ){
                op=request.getParameter("op");
            }
          if ( op.equals("listar") ) {
            
            List<Productos> listaProductos=Crud.getProductos();
            request.setAttribute("listado", listaProductos);
            request.setAttribute("mensaje", "");
            request.getRequestDispatcher("listar.jsp").forward(request,response);
            
             }
          
          if ( op.equals("borrar") ) {
              int id=Integer.parseInt(request.getParameter("id")) ;
              if ( Crud.destroyProducto(id)>0) {
                  request.setAttribute("mensaje", "Producto con id" + id + "Borrado");
              } else {
                  request.setAttribute("mensaje", "No se ha borrado ningún producto");
              }
               List<Productos> listaProductos=Crud.getProductos();
               request.setAttribute("listado", listaProductos);
               request.getRequestDispatcher("listar.jsp").forward(request,response);             
             }
            /******************************************/
            /*    BORRAR                              */
            /******************************************/
            if ( op.equals("borrar") ) {
              int id=Integer.parseInt(request.getParameter("id")) ;
              if ( Crud.destroyProducto(id)>0) {
                  request.setAttribute("mensaje", "Producto con id" + id + "Borrado");
              } else {
                  request.setAttribute("mensaje", "No se ha borrado ningún producto");
              }
               List<Productos> listaProductos=Crud.getProductos();
               request.setAttribute("listado", listaProductos);
               request.getRequestDispatcher("listar.jsp").forward(request,response);             
             }

             /******************************************/
            /*    ACTUALIZAR                           */
            /******************************************/    
            if ( op.equals("actualizar") ) {
               int id=Integer.parseInt(request.getParameter("id")) ;
               Productos miProducto = Crud.getProducto(id);
               request.setAttribute("producto", miProducto);
               request.getRequestDispatcher("actualizar.jsp").forward(request,response);
            }
            /******************************************/
            /*    ACTUALIZAR DATOS                      */
            /******************************************/ 
            if ( op.equals("actualizardatos") ) {
              int id=Integer.parseInt(request.getParameter("id")) ;
              String nombre=request.getParameter("nombre");
              String categoria=request.getParameter("categoria");
              String imagen=request.getParameter("imagen");
              float precio = Float.parseFloat(request.getParameter("precio"));
              
              Productos miProducto=new Productos( id,nombre,imagen,categoria,precio);
              if ( Crud.actualizaProducto(miProducto)>0) {
                  request.setAttribute("mensaje", "Producto con id" + id + "Actualizado");
              } else {
                  request.setAttribute("mensaje", "No se ha podido actualizar el producto");
              }
              request.setAttribute("producto", miProducto);
              request.getRequestDispatcher("actualizar.jsp").forward(request,response);          
             
              
             }

        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

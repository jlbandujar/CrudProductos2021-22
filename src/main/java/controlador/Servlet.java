/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.Crud;
import modelo.Productos;

/**
 *
 * @author DAW2
 */
@MultipartConfig( fileSizeThreshold=1024*1024*10, 
            maxFileSize=1024*1024*50, maxRequestSize=1024*1024*10)
public class Servlet extends HttpServlet {
final int NUM_LINEAS_PAGINA = 5;
 int pagina=1;
 int offset=0;
 int num_paginas=0;
 String path = "";
public void init(ServletConfig config){
path = config.getServletContext().getRealPath("").
                        concat(File.separator).concat("ficheros");

}

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
            
          if ( op.equals("insertar") ) {
            
            request.setAttribute("operacion", "insertardatos");
            request.setAttribute("mensaje", "");
            request.getRequestDispatcher("actualizar.jsp").forward(request,response);
            
             }  
          if ( op.equals("listar") ) {
            listar(request, response);
            /*List<Productos> listaProductos=Crud.getProductos();
            // cálculos para la paginación

            
            if ( request.getParameter("pagina")!=null){
                pagina = Integer.parseInt(request.getParameter("pagina"));
                offset = ( pagina-1 ) * NUM_LINEAS_PAGINA;
            }
            num_paginas = ( int ) Math.ceil(listaProductos.size() / ( double ) NUM_LINEAS_PAGINA);
            listaProductos = Crud.getProductosPaginado(offset, NUM_LINEAS_PAGINA);
            
            request.setAttribute("listado", listaProductos);
            request.setAttribute("pagina", pagina);
            request.setAttribute("num_paginas", String.valueOf(num_paginas));
            
            request.setAttribute("mensaje", "");
            request.getRequestDispatcher("listar.jsp").forward(request,response);*/
            
             }
          
         /* if ( op.equals("borrar") ) {
              int id=Integer.parseInt(request.getParameter("id")) ;
              if ( Crud.destroyProducto(id)>0) {
                  request.setAttribute("mensaje", "Producto con id" + id + "Borrado");
              } else {
                  request.setAttribute("mensaje", "No se ha borrado ningún producto");
              }
               List<Productos> listaProductos=Crud.getProductos();
               request.setAttribute("listado", listaProductos);
               request.getRequestDispatcher("listar.jsp").forward(request,response);             
             } */
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
               //List<Productos> listaProductos=Crud.getProductos();
               //request.setAttribute("listado", listaProductos);
               //request.getRequestDispatcher("listar.jsp").forward(request,response);
               this.listar(request, response);
             }

             /******************************************/
            /*    ACTUALIZAR                           */
            /******************************************/    
            if ( op.equals("actualizar") ) {
               int id=Integer.parseInt(request.getParameter("id")) ;
               Productos miProducto = Crud.getProducto(id);
               request.setAttribute("operacion", "actualizardatos");
               request.setAttribute("producto", miProducto);
               request.setAttribute("path", path);
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
           /******************************************/
            /*    INSERTAR DATOS                      */
            /******************************************/ 
            if ( op.equals("insertardatos") ) {               
              String nombre=request.getParameter("nombre");
              String categoria=request.getParameter("categoria");
              
              //String imagen=request.getParameter("imagen");
              String imagen = this.subirArchivo(request, response);
              float precio = Float.parseFloat(request.getParameter("precio"));
              
               Productos miProducto = new Productos();
                miProducto.setNombre(nombre);
                miProducto.setPrecio(precio);
                miProducto.setImagen(imagen);
                miProducto.setCategoria(categoria); 
              
              Crud.insertaProducto(miProducto);
              /*List<Productos> listaProductos=Crud.getProductos();
              request.setAttribute("listado", listaProductos);
              request.setAttribute("mensaje", "");
            request.setAttribute("pagina", pagina);
            request.setAttribute("num_paginas", String.valueOf(num_paginas));
              request.getRequestDispatcher("listar.jsp").forward(request,response);   */
              this.listar(request, response);
              
             }

        
    }
     protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          // path = request.getServletContext().getRealPath("").concat(File.separator).concat("ficheros");     
            List<Productos> listaProductos=Crud.getProductos();
            /* cálculos para la paginación */

            
            if ( request.getParameter("pagina")!=null){
                pagina = Integer.parseInt(request.getParameter("pagina"));
                offset = ( pagina-1 ) * NUM_LINEAS_PAGINA;
            }
            num_paginas = ( int ) Math.ceil(listaProductos.size() / ( double ) NUM_LINEAS_PAGINA);
            listaProductos = Crud.getProductosPaginado(offset, NUM_LINEAS_PAGINA);
            
            request.setAttribute("listado", listaProductos);
            request.setAttribute("pagina", pagina);
            request.setAttribute("num_paginas", String.valueOf(num_paginas));
            
            request.setAttribute("mensaje", "");
            request.getRequestDispatcher("listar.jsp").forward(request,response);
         
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

public String  subirArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    // path = request.getServletContext().getRealPath("").concat(File.separator).concat("ficheros");
    Part filePart = request.getPart("imagen"); // Obtiene el archivo el input en el form se llama imagen
    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

    //InputStream fileContent = filePart.getInputStream(); //Lo transforma en InputStream

    //String path="/archivos/";
    File uploads = new File(path); //Carpeta donde se guardan los archivos
    uploads.mkdirs(); //Crea los directorios necesarios
    File file = File.createTempFile("cod"+""+"-", "-"+fileName, uploads); //Evita que hayan dos archivos con el mismo nombre

    try (InputStream input = filePart.getInputStream()){
        Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    //return file.getPath();
    String archivo = file.getName();
    return archivo;
}

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }


}

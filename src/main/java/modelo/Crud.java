/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author DAW2
 */
public class Crud {
    
      public static void insertaProducto(Productos producto) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
        EntityManager manager = factory.createEntityManager();
         manager.getTransaction().begin();
       /* Productos producto = new Productos();
        producto.setNombre("Espinacas");
        producto.setPrecio(10);
        producto.setImagen("espinacas.jpg");
        producto.setCategoria("complementos"); */
        manager.merge(producto);
        manager.getTransaction().commit();
        }
    
     public static int actualizaProducto(Productos miProducto) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
        EntityManager manager = factory.createEntityManager();
        String sql = "UPDATE Productos p SET p.nombre = :nombre, p.imagen = :imagen, p.categoria = :categoria, p.precio = :precio WHERE p.id = :id";
        Query q = manager.createQuery(sql,Productos.class);
        q.setParameter("categoria", miProducto.getCategoria());
        q.setParameter("nombre", miProducto.getNombre());
        q.setParameter("imagen", miProducto.getImagen());
        q.setParameter("precio", miProducto.getPrecio());
        q.setParameter("id", miProducto.getId());
        manager.getTransaction().begin();
        int filasAfectadas = q.executeUpdate();
        manager.getTransaction().commit();
        //manager.close();
        return filasAfectadas;      
    }
    public static Productos getProducto(int id) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
        EntityManager manager = factory.createEntityManager();
        String sql = "SELECT p FROM Productos p WHERE p.id=" + id; //consulta en JPQL 
        Query q = manager.createQuery(sql,Productos.class); //método para consultas en JPQL
        Productos productosBD =  ( Productos )q.getSingleResult();
        return productosBD;
        
    } 
    
    
       public static List<Productos> getProductos() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
        EntityManager manager = factory.createEntityManager();
        String sql = "SELECT * FROM productos";
        Query q = manager.createNativeQuery(sql,Productos.class); //método para consultas en SQL
        List<Productos> productosBD =  q.getResultList();

        return productosBD;        
        }
       
        public static List<Productos> getProductosPaginado(int offset, int lineas_pagina) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
        EntityManager manager = factory.createEntityManager();
        String sql = "SELECT * FROM productos";
        Query q = manager.createNativeQuery(sql,Productos.class); //método para consultas en SQL
        q.setFirstResult(offset);
        q.setMaxResults(lineas_pagina);
        List<Productos> productosBD =  q.getResultList();

        return productosBD;        
        }
       
        public static int destroyProducto(int id) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
        EntityManager manager = factory.createEntityManager();
        String sql = "DELETE from Productos p WHERE p.id = " + id;
        Query q = manager.createQuery(sql);
        manager.getTransaction().begin();
        int filasAfectadas = q.executeUpdate(); //para las consultas de modif. datos se usa el método executeUpdate
        manager.getTransaction().commit();
        return filasAfectadas;  
    }

}

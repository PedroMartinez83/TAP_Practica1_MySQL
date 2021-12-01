/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pitte
 */
public class BaseDatos {
    Connection conexion;
    Statement transaccion;
    ResultSet cursor; 
    
    
    public BaseDatos(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tap_practica1?zeroDateTimeBehavior=CONVERT_TO_NULL","root","");
            transaccion = conexion.createStatement();
        }catch(SQLException ex){
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    
    public boolean insertar(Productos productos){
        String SQL_Insert = "INSERT INTO PRODUCTOS VALUES(NULL, '%DES%','%PRE%','%EXI%')"; 
        
        SQL_Insert = SQL_Insert.replaceAll("%DES%", productos.descripcion);
        SQL_Insert = SQL_Insert.replaceAll("%PRE%", productos.precio);   
        SQL_Insert = SQL_Insert.replaceAll("%EXI%", productos.existencia);
        
        try {
            transaccion.execute(SQL_Insert);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
    
    public ArrayList<String[]> consultarTodos(){
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        
        try {
            cursor = transaccion.executeQuery("SELECT * FROM PRODUCTOS");
            if(cursor.next()){
                do{
                   String[] renglon = {cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)};
                   
                   resultado.add(renglon);
                }while(cursor.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public Productos consultarID(String ID){
        Productos productosResultado = new Productos();
        
        try {
            cursor = transaccion.executeQuery("SELECT * FROM PRODUCTOS WHERE ID="+ID);
            if(cursor.next()){
                productosResultado.descripcion = cursor.getString(2);
                productosResultado.precio = cursor.getString(3);
                productosResultado.existencia= cursor.getString(4);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productosResultado;
    }
    
    public boolean eliminar(String ID){
        try {
            transaccion.execute("DELETE FROM PRODUCTOS WHERE ID="+ID);
        } catch (SQLException ex) {
            return false;
        }
        return true;
       
    }
    
    public boolean Actualizar(Productos productos){
        String update = "UPDATE PRODUCTOS SET DESCRIPCION='%DES%',PRECIO='%PRE%',EXISTENCIA='%EXI%' WHERE ID="+ productos.id;       
        update = update.replaceAll("%DES%", productos.descripcion);
        update = update.replaceAll("%PRE%", productos.precio);   
        update = update.replaceAll("%EXI%", productos.existencia);
        
        try {
            transaccion.executeUpdate(update);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
}

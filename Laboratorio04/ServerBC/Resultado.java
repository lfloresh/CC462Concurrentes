 import java.io.Serializable;
public class Resultado implements Serializable{
    int key;
    Long time;
    int id_minero;
    int nzeros;
    String palabra;
    String encriptado;
    
    Resultado(int key, Long time, int id_minero, int nzeros, String palabra, String encriptado){
    this.key = key;
    this.time = time;
    this.id_minero = id_minero;
    this.nzeros = nzeros;
    this.palabra = palabra;
    this.encriptado = encriptado;
    
    }
    
    public void mostrar(){
    System.out.println("--------------------------");
    System.out.println("RESULTADO DE TAREA:");
    System.out.println("key: " + key);
    System.out.println("tiempo: " + time);  
    System.out.println("id: " + id_minero);  
    System.out.println("n ceros: " + nzeros);  
    System.out.println("palabra: " + palabra);
    System.out.println("encriptado: " + encriptado);  
    System.out.println("--------------------------");
    
    }
    
}

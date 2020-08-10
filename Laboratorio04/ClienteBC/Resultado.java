 import java.io.Serializable;
public class Resultado implements Serializable{
    int key;
    Long time;
    int id_minero;
    int nzeros;
    String palabra;
    
    Resultado(int key, Long time, int id_minero, int nzeros, String palabra){
    this.key = key;
    this.time = time;
    this.id_minero = id_minero;
    this.nzeros = nzeros;
    this.palabra = palabra;
    
    }
    
    public void mostrar(){
    System.out.println("--------------------------");
    System.out.println("RESULTADO DE TAREA:");
    System.out.println("key: " + key);
    System.out.println("tiempo: " + time);  
    System.out.println("id: " + id_minero);  
    System.out.println("n ceros: " + nzeros);  
    System.out.println("palabra: " + palabra);  
    System.out.println("--------------------------");
    }
    
}

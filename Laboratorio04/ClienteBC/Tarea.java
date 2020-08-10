 import java.io.Serializable;
public class Tarea implements Serializable{
    int nzeros;
    String mensaje;
    
    Tarea(int nzeros, String mensaje){
        this.nzeros = nzeros;
        this.mensaje = mensaje;
    }
}

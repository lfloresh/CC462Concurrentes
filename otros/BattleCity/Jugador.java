
public class Jugador {
    int x;
    int y;
    
    public Jugador(int x, int y){
        this.x = x;
        this.y = y;
    
    }
    
    public void left(){
        this.y = this.y - 2;
    }
    
    public void right(){
        this.y = this.y + 2;
    }
    
    public void up(){
        this.x = this.x - 2;
    }
    
    public void down(){
        this.x = this.x + 2;
    }
}

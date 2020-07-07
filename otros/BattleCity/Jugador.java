
public class Jugador {
    int x;
    int y;
    char simbol;
    char bala_simbol;
    public boolean solto_bala = false;
    int id_bala = 0;
    Bala bala[] = new Bala[30];
    
    public Jugador(int x, int y, char simbol, char bala_simbol) {
        this.x = x;
        this.y = y;
        this.simbol = simbol;
        this.bala_simbol = bala_simbol;

    }
    
    public int insertar_bala(){
        bala[id_bala++] = new Bala(this.x, this.y);
        return id_bala - 1;
    }
    
    public void insertar_bala(int x, int y, int id_bala_2){
    
       bala[id_bala_2] = new Bala(x, y);
    }
    public void left() {
        this.y = this.y - 1;
    }

    public void right() {
        this.y = this.y + 1;
    }

    public void up() {
        this.x = this.x - 1;
    }

    public void down() {
        this.x = this.x + 1;
    }
    

}

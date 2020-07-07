
public class Jugador {
    int x;
    int y;
    char simbol;
    char bala_simbol;
    public boolean solto_bala = false;
    
    int bala[] = new int[2];
    
    public Jugador(int x, int y, char simbol, char bala_simbol) {
        this.x = x;
        this.y = y;
        this.simbol = simbol;
        this.bala_simbol = bala_simbol;

    }

    public void left() {
        this.y = this.y - 2;
    }

    public void right() {
        this.y = this.y + 2;
    }

    public void up() {
        this.x = this.x - 2;
    }

    public void down() {
        this.x = this.x + 2;
    }
    

}

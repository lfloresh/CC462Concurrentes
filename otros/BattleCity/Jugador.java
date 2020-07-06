
public class Jugador {
    int x;
    int y;
    char simbol;

    public Jugador(int x, int y, char simbol) {
        this.x = x;
        this.y = y;
        this.simbol = simbol;

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


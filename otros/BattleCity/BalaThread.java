public class BalaThread extends Thread{
    int x;
    int y;
    int id;
    char mov;
    Escenario obj;
    BalaThread(int x, int y, int id, Escenario obj){
        this.x = x;
        this.y = y;
        this.id = id;
        //this.mov = mov;
        this.obj = obj;
    }
    
    public void left(){
        this.y = this.y - 1;
    }
    
    
    public void right(){
        this.y = this.y + 1;
    }
    
    
    
    public void up(){
        this.x = this.x - 1;
    }
    
    
    
    public void down(){
        this.x = this.x + 1;
    }
    
    public void run() {
        //System.out.println(this.obj.esta_vacio(this.x, this.y));
        while (!this.obj.esta_ocupado(this.x, this.y)){
            //System.out.println("posicion actual "+ this.obj.matriz[this.x][this.y]);
            int antx = this.x;
            int anty = this.y;
            this.obj.pintar_pos_bala(this.id);
            
            if (this.mov == 'w'){
            up();
            }
            else if (this.mov == 's'){
            down();
       
            }
            else if (this.mov == 'a'){
                
            left();

            }
            else if (this.mov == 'd'){
            right();

            }

        try {
            // thread to sleep for 200 milliseconds
            Thread.sleep(200);
         } catch (Exception e) {
            System.out.println(e);
         }
            
            if (!this.obj.esta_ocupado(this.x, this.y)){
            //System.out.println("posicion actual "+ this.obj.matriz[this.x][this.y]);
            this.obj.limpiar_campo(antx, anty);
            this.obj.pintar_pos_bala(this.id);
            
            this.obj.mostrar();
            }
        }
        
        
}

}
   

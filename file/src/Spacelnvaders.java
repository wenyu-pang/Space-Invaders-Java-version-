import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Spacelnvaders extends JPanel implements ActionListener,KeyListener{
    int tileSize = 32;
    int rows = 16;
    int columns = 16;

    int boardWidth = tileSize * columns; // 32 * 16
    int boardHeight = tileSize * rows; // 32 * 16
    class   Block{
        int x;
        int y;
        int width;
        int height;
        Image img;
        boolean alive =true;//use for alien
        boolean used=false;//used for  bullets
        Block(int x,int y,int width,int height,Image img){
                    this.x=x;
                    this.y=y;
                    this.width =width;
                    this.height=height;
                    this.img=img;
        }
    
    }
    Image ship;
    Image alien;
    Image aliencyan;
    Image alienmagenta;
    Image alienYellow;
    ArrayList<Image>alienImgArray;
    //ship
    int swidth=64;
    int sHeight=32;
    int sx=224;
    int sy=448;
    int svelocityX = 32;
    Block Ship;
    //alliens
    ArrayList<Block>alienArray;
    int awidth =tileSize*2;
    int aHeight =tileSize;
    int aX=tileSize;
    int aY=tileSize;

    int aRows=2;
    int acolums=3;
    int aCount =0;//number of qliens to defeat
    int aVelocityX=1;//alien  move 1 px
    //bullets
    ArrayList<Block>bulletArray;
    int bulletwidth =tileSize/8;
    int bulletHeight =tileSize/2;
    int bulletVelocityY=-10;//bullet shoting speed

    Timer gameloop;
    int score=0;
    boolean gameOver =false;
    Spacelnvaders (){
        setPreferredSize(new Dimension(512,512));
        setBackground(Color.black);
        setFocusable(true);//let Jpanle listen the keypress
        addKeyListener(this);
        
        ship =new ImageIcon(getClass().getResource("./ship.png")).getImage();
        alien =new ImageIcon(getClass().getResource("./alien.png")).getImage();
        aliencyan =new ImageIcon(getClass().getResource("./alien-cyan.png")).getImage();
        alienmagenta =new ImageIcon(getClass().getResource("./alien-magenta.png")).getImage();
        alienYellow =new ImageIcon(getClass().getResource("./alien-yellow.png")).getImage();
        alienImgArray=new ArrayList<Image>();
        alienImgArray.add(alien);
        alienImgArray.add(aliencyan);
        alienImgArray.add(alienmagenta);
        alienImgArray.add(alienYellow);
        Ship=new Block(sx,sy,swidth,sHeight,ship);
        alienArray=new ArrayList<Block>();
        bulletArray=new ArrayList<Block>();

        createAliens();
        //game timer
        gameloop=new Timer(1000/60,this);//1000/60=16.7
        gameloop.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //draw ship
        g.drawImage(Ship.img,Ship.x,Ship.y,Ship.width,Ship.height,null);
        //draw aliens
        for (int i=0;i<alienArray.size();i++) {
            Block alien=alienArray.get(i);
            if(alien.alive){
                g.drawImage(alien.img,alien.x,alien.y,alien.width,alien.height,null);
            }
        }
        //bullet
        g.setColor(Color.red);
        for(int i=0;i<bulletArray.size();i++){
            Block bullet =bulletArray.get(i);
            if(!bullet.used){
               // g.drawRect(bullet.x, bullet.y, bullet.width, bullet.height);
                g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
            }
        }
        //score
        g.setColor(Color.green);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameOver){
            g.drawString("Game Over:"+String.valueOf(score),10,35);
        }
        else{
            g.drawString(String.valueOf(score),10,35);
        }
        g.setFont(new Font("Arial",Font.PLAIN,16));
        g.drawString("please press J to attack",200 ,35);
    }

    public  void move(){
        //aliens
        for(int i=0;i<alienArray.size();i++){
            Block alien =alienArray.get(i);
            if(alien.alive){
                alien.x+=aVelocityX;

                //if alien touches the borders
                if(alien.x+alien.width>=boardWidth||alien.x<=0){
                    aVelocityX*=-1;
                    alien.x+=aVelocityX*2;

                    //alien move down one row
                    for (int j=0;j<alienArray.size();j++) {
                        alienArray.get(j).y+=aHeight;
                    }
                }
                if(alien.y >=Ship.y){
                        gameOver=true;
                }
            }
        }
        //bullets
        for(int i=0;i<bulletArray.size();i++){
            Block bullet=bulletArray.get(i);
            bullet.y+=bulletVelocityY;

            //bullet collision with aliens
            for(int j=0;j<alienArray.size();j++){
                Block alien =alienArray.get(j);
                if(!bullet.used && alien.alive && detectCollision(bullet,alien)){
                    bullet.used=true;
                    alien.alive=false;
                    aCount--;
                    score+=100;
                }
            }
        }

        //clear bullets
        while (bulletArray.size()>0 && (bulletArray.get(0).used||bulletArray.get(0).y<0)){
            bulletArray.remove(0);//remove the first bullet out side the window
        }

        //next level
        if(aCount==0){
            //include the number of aliens in colums and row by 1
            score +=acolums*aRows*100;//bouns points
            acolums=Math.min(acolums + 1,columns/2-2);//cap colums at 16/2-2=6
            aRows=Math.min(aRows + 1,rows-6);//cap colums at 16-6=10
            alienArray.clear();
            bulletArray.clear();
            aVelocityX =1;
            createAliens();
        }
    }
    //make the alien size something like that
    public void createAliens(){
        Random R=new Random();
        for(int r=0;r<aRows;r++){
            for(int c=0;c<acolums;c++){
                int randomImgIndex=R.nextInt(alienImgArray.size());
                    Block alien =new Block(
                        aX+c*awidth,
                        aY+r*aHeight,
                        awidth,
                        aHeight,
                        alienImgArray.get(randomImgIndex)
                    );
                    alienArray.add(alien);
            }
        }
        aCount=alienArray.size();
    }
    public boolean detectCollision(Block a,Block b){
        return a.x<b.x+b.width &&
               a.x+a.width>b.x &&
               a.y<b.y+b.height &&
               a.y +a.height>b.y;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameloop.stop();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
}
    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver&&e.getKeyCode()==KeyEvent.VK_SPACE){
            Ship.x=sx;
            alienArray.clear();
            bulletArray.clear();
            score=0;
            aVelocityX=1;
            acolums=3;
            aRows=2;
            gameOver =false;
            createAliens();
            gameloop.start();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT&&Ship.x-svelocityX>=0){
            Ship.x-=svelocityX;//move left one title
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT&Ship.x+Ship.width+svelocityX<=512){
            Ship.x+=svelocityX;//move right one title
        }
        else if(e.getKeyCode()==KeyEvent.VK_J){
            Block bullet =new Block(Ship.x+swidth*15/32,Ship.y,bulletwidth,bulletHeight,null);
            bulletArray.add(bullet);
        }
    }
}

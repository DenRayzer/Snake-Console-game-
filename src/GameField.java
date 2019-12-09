import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 520;
    private final int DOT_SIZE = 20;
    private final int ALL_DOTS = 676;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int[] score = new int[ALL_DOTS];
    private int scoreI = 0;


    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 60 - i*DOT_SIZE;
            y[i] = 60;
        }
        timer = new Timer(150,this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("src/Images/apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("src/Images/dot_.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{
            String str = "Game Over";
            Font f = new Font("Arial",Font.BOLD, 26);
            g.setFont(f);
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
            g.drawString(str,125,SIZE/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }









    protected void paintComponentScore(Graphics g) {
        super.paintComponent(g);
        String b = String.valueOf(score[scoreI]);

        g.setColor(Color.white);
        g.drawString(b,125,50);
    }







    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
            for (int i = dots; i > 0; i--) {
                if(x[i] == appleX && y[i] == appleY){
                    createApple();
                }
            }

         /*


          scoreI++;
           paintComponentScore(Graphics);


           */
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0 ; i--) {
            if( x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

           if(x[0]>SIZE){
                x[0] = 0;
            }
            if(x[0]<0){
                x[0] = SIZE;
            }
            if(y[0]>SIZE){
                y[0] = 0;
            }
            if(y[0]<0){
                y[0] = SIZE;
            }
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }


}

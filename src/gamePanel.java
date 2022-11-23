import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;


public class gamePanel extends JPanel implements ActionListener {

    static final int ScreenWidth = 600;
    static final int ScreenHeight = 600;
    static final int UnitSize = 30;                 // Screen area is 600x600 & take 1 unitArea as 30
    static final int GameUnits = (ScreenHeight*ScreenWidth)/UnitSize;   // Gives the number of units on the game panel

    static int Delay = 100;

    final int x[] = new int[GameUnits];             // The snake's body lies in the these 2 arrays
    final int y[] = new int[GameUnits];             // Determine how many X,Y coordinates (Units) are there in the panel (Create an Array with 0 s)

    int bodyParts = 3;                              // Snake's length in the beginning = 3 units
    int appleEaten;
    int appleX;
    int appleY;                                     // X,Y coordinates of the apple

    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;



    gamePanel() {                                      // Determine game panel attributes
        random = new Random();
        this.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
        this.setBackground(Color.green);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    public void paintComponent(Graphics g) {            // Component to draw itself on the screen using java swing
        super.paintComponent(g);                        // 'g' ofr 2D class
        draw(g);
    }

    public void draw(Graphics g) {
        if(running) {
            for(int i = 0; i < ScreenHeight/UnitSize; i++) {                   // Draws lines on the panel
                g.drawLine(i*UnitSize, 0, i*UnitSize,ScreenHeight); // Draws a line from x1,y1 to x2,y2
                g.drawLine(0, i*UnitSize, ScreenWidth, i*UnitSize);
            }


            g.setColor(Color.red);                                            // Apple's components
            g.fillOval(appleX, appleY, UnitSize, UnitSize);


            for(int i = 0; i < bodyParts; i++) {                              // Snake's components
                g.setColor(Color.black);
                g.fillOval(x[i], y[i], UnitSize, UnitSize);
            }


            g.setColor(Color.magenta);                                           // Display Live score
            g.setFont(new Font("Times New Roman", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: " + appleEaten,(ScreenWidth - metrics.stringWidth("SCORE: " + appleEaten))/1 , g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }

    public void newApple() {                                                  // Place new apple
        appleX = random.nextInt((int)(ScreenWidth/UnitSize)) * UnitSize;
        appleY = random.nextInt((int)(ScreenHeight/UnitSize)) * UnitSize;
    }

    public void startGame() {                                                 // Begin the game
        newApple();
        running = true;
        timer = new Timer(Delay, this);
        timer.start();
    }

    public void move() {
        for(int i = bodyParts; i>0; i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {                                                   // Direction of the Snake's head
            case 'U':
                y[0] = y[0] - UnitSize;
                break;

            case 'D':
                y[0] = y[0] + UnitSize;
                break;

            case 'L':
                x[0] = x[0] - UnitSize;
                break;

            case 'R':
                x[0] = x[0] + UnitSize;
                break;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {                              // Actual performance of the Snake
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }


    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {                                          // Check if the pressed key is the already traveling direction
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    public void checkApple() {
        if((x[0] == appleX) && (y[0]==appleY)) {                    // If the locations of apple and Snake head's are the same
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }


    public void checkCollisions() {
        //head
        for(int i = bodyParts; i>0; i--) {                          // If the head coincides with one of body locations - collision
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        if(x[0] < 0) {                                              // If the head coincides with one of the borders - collision
            running = false;                                        // Left
        }

        if(x[0] > ScreenWidth) {                                   // Right
            running = false;
        }

        if(y[0] < 0) {                                              // Top
            running = false;
        }

        if(y[0] > ScreenHeight) {                                  // Bottom
            running = false;
        }

        if(!running) {
            timer.stop();
        }

    }


    public void gameOver(Graphics g) {

        g.setColor(Color.BLACK);                                      // Score
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("YOUR SCORE: " + appleEaten,(ScreenWidth - metrics.stringWidth("YOUR SCORE: " + appleEaten))/2 , ScreenHeight/3);

        g.setColor(Color.red);                                      // Game over
        g.setFont(new Font("Times New Roman", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER!" ,(ScreenWidth - metrics2.stringWidth("GAME OVER!"))/2 , ScreenHeight/2);
    }


}

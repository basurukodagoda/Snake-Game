import javax.swing.JFrame;              // This class provides more sophisticated set of GUI components

public class gameFrame extends JFrame {
    gameFrame() {
        this.add(new gamePanel());      // 'this' refers to the current constructor
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();                    // Sizes the frame & all the components
        this.setVisible(true);
        this.setLocationRelativeTo(null);   // Sets the location of the dialog

    }
}

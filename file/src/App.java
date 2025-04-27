import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {

        ImageIcon logo=new ImageIcon("src\\icone.png");
        JFrame frame=new JFrame("Space Invaders");
        

        frame.setIconImage(logo.getImage());
        //frame.setVisible(true);
        frame.setSize(512,512);
        //opean the window on the center on computer screen
        frame.setLocationRelativeTo(null);
        //user can not change the size of the window
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Spacelnvaders sp =new Spacelnvaders();
        frame.add(sp);
        frame.pack();
       sp.requestFocus();//tell the computer to collect the key press
        frame.setVisible(true);
        
    }
}

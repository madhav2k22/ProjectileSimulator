import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ProjectileSimulator extends JFrame implements ActionListener {
      
    private static final long serialVersionUID = 1L;
    private DrawingPanel panel;
    private JButton shootButton, resetButton;
    private JSlider velocitySlider, angleSlider;
    private JLabel velocityLabel, angleLabel, displacementLabel;
    private JTextField velocityField, angleField;
    private double velocity, angle, time, x, y, g1 = 9.8;
    private Timer timer;
    private int delay = 10;

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            g2d.fillOval((int) x + 50, 500 - (int) y, 10, 10);

            g2d.setColor(Color.BLUE);
            Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
            g2d.setStroke(dashed);
            int trajectoryX = (int) (velocity * Math.cos(Math.toRadians(angle)) * time) + 50;
            int trajectoryY = 500 - (int) (velocity * Math.sin(Math.toRadians(angle)) * time - 0.5 * g1 * time * time);
            g2d.drawLine(50, 500, trajectoryX, trajectoryY);
        }
    }
    public ProjectileSimulator(){

    }
    public static void main (String [] args){
        new ProjectileSimulator();
    }
    


}

 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ProjectileSimulator extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private DrawingPanel panel;
    private JButton shootButton, resetButton;
    private JSlider velocitySlider, angleSlider;
    private JLabel velocityLabel, angleLabel, displacementLabel, maxHeightLabel, timeOfFlightLabel;
    private JTextField velocityField, angleField;
    private double velocity, angle, time, x, y, g1 = 9.8, maxHeight = 0;
    private Timer timer;
    private int delay = 10;
    private ArrayList<Point> trajectoryPoints = new ArrayList<>();

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            g2d.fillOval((int) x + 50, 500 - (int) y, 10, 10);

            g2d.setColor(Color.BLUE);
            if (!trajectoryPoints.isEmpty()) {
                Point start = trajectoryPoints.get(0);
                for (int i = 1; i < trajectoryPoints.size(); i++) {
                    Point end = trajectoryPoints.get(i);
                    g2d.drawLine(start.x, start.y, end.x, end.y);
                    start = end;
                }
            }
        }
    }

    public ProjectileSimulator() {
        setTitle("Projectile Simulator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new DrawingPanel();
        panel.setLayout(null);

        velocityLabel = new JLabel("Velocity (m/s): ");
        velocityLabel.setBounds(50, 300, 100, 30);
        panel.add(velocityLabel);

        velocitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        velocitySlider.setBounds(150, 300, 200, 30);
        velocitySlider.setMajorTickSpacing(10);
        velocitySlider.setMinorTickSpacing(5);
        velocitySlider.setPaintTicks(true);
        velocitySlider.setPaintLabels(true);
        panel.add(velocitySlider);

        angleLabel = new JLabel("Angle (degrees): ");
        angleLabel.setBounds(50, 250, 100, 30);
        panel.add(angleLabel);

        angleSlider = new JSlider(JSlider.HORIZONTAL, 0, 90, 45);
        angleSlider.setBounds(150, 250, 200, 30);
        angleSlider.setMajorTickSpacing(10);
        angleSlider.setMinorTickSpacing(5);
        angleSlider.setPaintTicks(true);
        angleSlider.setPaintLabels(true);
        panel.add(angleSlider);

        velocityField = new JTextField();
        velocityField.setBounds(360, 300, 100, 30);
        velocityField.setEditable(false);
        panel.add(velocityField);

        angleField = new JTextField();
        angleField.setBounds(360, 250, 100, 30);
        angleField.setEditable(false);
        panel.add(angleField);

        shootButton = new JButton("Shoot");
        shootButton.setBounds(50, 500, 100, 30);
        shootButton.addActionListener(this);
        panel.add(shootButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(200, 500, 100, 30);
        resetButton.addActionListener(this);
        panel.add(resetButton);

        displacementLabel = new JLabel("Displacement: ");
        displacementLabel.setBounds(50, 50, 200, 30);
        panel.add(displacementLabel);

        maxHeightLabel = new JLabel("Max Height: ");
        maxHeightLabel.setBounds(50, 80, 200, 30);
        panel.add(maxHeightLabel);

        timeOfFlightLabel = new JLabel("Time of Flight: ");
        timeOfFlightLabel.setBounds(50, 110, 200, 30);
        panel.add(timeOfFlightLabel);

        timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                time += delay / 1000.0;
                x = velocity * Math.cos(Math.toRadians(angle)) * time;
                y = velocity * Math.sin(Math.toRadians(angle)) * time - 0.5 * g1 * time * time;
                trajectoryPoints.add(new Point((int) x + 50, 500 - (int) y));
                panel.repaint();
                double displacement = velocity * Math.cos(Math.toRadians(angle)) * time;
                double currentHeight = velocity * Math.sin(Math.toRadians(angle)) * time - 0.5 * g1 * time * time;
                displacementLabel.setText("Displacement: " + String.format("%.2f", displacement));
                if (currentHeight > maxHeight) {
                    maxHeight = currentHeight;
                    maxHeightLabel.setText("Max Height: " + String.format("%.2f", maxHeight));
                }
                timeOfFlightLabel.setText("Time of Flight: " + String.format("%.2f", time));
                if (y < 0) {
                    y = 0;
                    timer.stop();
                }
            }
        });

        velocitySlider.addChangeListener(e -> {
            velocity = velocitySlider.getValue();
            velocityField.setText(String.format("%.2f", velocity));
        });

        angleSlider.addChangeListener(e -> {
            angle = angleSlider.getValue();
            angleField.setText(String.format("%.2f", angle));
        });

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == shootButton) {
            velocity = velocitySlider.getValue();
            angle = angleSlider.getValue();
            velocityField.setText(String.format("%.2f", velocity));
            angleField.setText(String.format("%.2f", angle));
            time = 0;
            x = 0;
            y = 0;
            displacementLabel.setText("Displacement: 0.00");
            maxHeightLabel.setText("Max Height: 0.00");
            timeOfFlightLabel.setText("Time of Flight: 0.00");
            trajectoryPoints.clear();
            maxHeight = 0;
            timer.start();
        } else if (e.getSource() == resetButton) {
            velocitySlider.setValue(50);
            angleSlider.setValue(45);
            velocityField.setText("");
            angleField.setText("");
            time = 0;
            x = 0;
            y = 0;
            displacementLabel.setText("Displacement: 0.00");
            maxHeightLabel.setText("Max Height: 0.00");
            timeOfFlightLabel.setText("Time of Flight: 0.00");
            trajectoryPoints.clear();
            maxHeight = 0;
            timer.stop();
            panel.repaint();
        }
    }

    public static void main(String[] args) {
        new ProjectileSimulator();
    }
}

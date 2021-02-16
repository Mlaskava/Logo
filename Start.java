import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;



class MyPanel extends JPanel implements ActionListener {

    private int i = 0, d = 0;


    private final JButton Left = new JButton("Left");
    private final JButton Right = new JButton("Right");
    private final JButton Forward = new JButton("Forward");
    private final JButton  PenUp = new JButton("Pen Up");
    private final JButton PenDown = new JButton("Pen Down");
    private final JButton  Clean = new JButton("Clean");
    private final JButton  Quit = new JButton("Quit");


    private boolean up = true;


    private final String [] directions = {"UP", "RIGHT", "DOWN","LEFT"};


    private double x = 10 + 630.0/2, y = 90 + 550.0/2, dx = 0, dy = -25;

    private String pos = "Aktualna pozycja x: " + x + ", y: " + y;
    private String turn = "Kierunek ustawienia: 0 (UP)";
    private String upStr = "Pióro podniesione";


    private final Line2D [] line = new Line2D.Double[1000];
    private final Rectangle2D rectangle = new Rectangle2D.Double(10, 90, 630, 550);
    private final Ellipse2D point = new Ellipse2D.Double(x - 1.5, y - 1.5, 4, 4);
    private final Rectangle2D frame = new Rectangle2D.Double(15, 55, 620, 20);


    public MyPanel(){


        setPreferredSize(new Dimension(650, 650));



        Left.addActionListener(this);
        Right.addActionListener(this);
        Forward.addActionListener(this);
        PenUp.addActionListener(this);
        PenDown.addActionListener(this);
        Clean.addActionListener(this);
        Quit.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        add(Left);
        add(Right);
        add(Forward);
        add(PenUp);
        add(PenDown);
        add(Clean);
        add(Quit);

    }

    private void setDirection(String dir){

        switch (dir) {
            case "UP":
                dy = -25;
                dx = 0;
                break;
            case "LEFT":
                dy = 0;
                dx = -25;
                break;
            case "RIGHT":
                dy = 0;
                dx = 25;
                break;
            case "DOWN":
                dy = 25;
                dx = 0;
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src == Quit){
            System.exit(0);
        }

        if(src == PenDown){
            upStr = "Pióro opuszczone";
            up = false;
            repaint();
        }

        if(src == PenUp){
            upStr = "Pióro podniesione";
            up = true;
            repaint();
        }

        if(src == Left){
            d--;
            if(d < 0){
                d = 3;
            }
            turn = "Kierunek ustawienia: " + d*90+ " (" + directions[d] + ")";
            setDirection(directions[d]);
            repaint();
        }

        if(src == Right){
            d++;
            if(d > 3){
                d = 0;
            }
            turn = "Kierunek ustawienia: " + d*90+ " (" + directions[d] + ")";
            setDirection(directions[d]);
            repaint();
        }

        if(src == Clean){
            for(int l = 0; l <= i; l++){
                line[i] = null;
            }
            i = 0;
            setDirection("UP");
            turn = "Kierunek ustawienia: 0 (UP)";
            repaint();
            x = 10 + 630.0/2;
            y = 90 + 550.0/2;
        }

        if(src == Forward){
            if(!up) {
                line[i] = new Line2D.Double(x, y, x + dx, y + dy);
                i++;
            }
            x += dx;
            y += dy;
            pos = "Aktualna pozycja x: " + x + ", y: " + y;
            repaint();
        }

    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


            g2d.setColor(Color.WHITE);
            g2d.draw(rectangle);
            g2d.fill(rectangle);

                 g2d.draw(frame);
                 g2d.setColor(Color.GRAY);
                 g2d.fill(frame);
                 g2d.setColor(Color.WHITE);
                 g2d.drawString(pos, 20, 70);
                 g2d.drawString(turn, 250, 70);
                 g2d.drawString(upStr, 540, 70);


                if (!up) {
                    g2d.setColor(Color.GREEN);
                } else {
                    g2d.setColor(Color.RED);
                }

                point.setFrame(x - 1.5, y - 1.5, 4, 4);
                g2d.draw(point);
                g2d.fill(point);
                g2d.setColor(Color.BLACK);

                for (int l = 0; l < i; l++) {
                    g2d.draw(line[l]);
                }

    }
}

class Start extends JFrame {

    public Start(){
        setTitle("LOGO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(450, 100);


        JPanel myPanel = new MyPanel();
        add(myPanel);
        pack();
        setVisible(true);
    }

    public static void main(String [] args){
        new Start();
    }
}

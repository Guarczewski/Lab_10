package Lab_11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class CPanel extends JPanel implements MouseListener, MouseMotionListener {

    int oldX, oldY, x, y, size = 32;
    boolean oval = true, clear = false;
    Color color = Color.BLACK;
    List<Line2D> line2DS;


    CPanel(){
        super();
        line2DS = new ArrayList<>();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        if (clear) super.paintComponent(g);

        g.setColor(color);

        Line2D j = new Line2D.Float(oldX, oldY, x, y);

        if (oval)
            g2D.setStroke(new BasicStroke(size,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        else
            g2D.setStroke(new BasicStroke(size,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));

        for ( Line2D tmp : line2DS) {
            g2D.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2D.draw(tmp);
        }

        oldX = x;
        oldY = y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        oldX = x = e.getX();
        oldY = y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        Line2D line2D = new Line2D.Float(x,y,oldX,oldY);
        line2DS.add(line2D);

        repaint();
    }


}

public class Aplikacja extends JFrame implements ActionListener {


    public static Aplikacja A;
    File chosenOne;
    CPanel cPanel;
    JButton saveButton, readButton;


    Aplikacja(){
        super("Title");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,300,300);
        JPanel mainJPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0,2));
        saveButton = new JButton("Zapisz Plika");
        readButton = new JButton("Wczytaj Plika");
        saveButton.addActionListener(this);
        readButton.addActionListener(this);
        buttonPanel.add(saveButton);
        buttonPanel.add(readButton);
        cPanel = new CPanel();
        mainJPanel.add(cPanel, BorderLayout.CENTER);
        mainJPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainJPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        A = new Aplikacja();

        //A.Zapisz();
        //A.Wczytaj();
    }

    void Zapisz() {

        try {

            JFileChooser wybieracz = new JFileChooser();
            int retValue = wybieracz.showSaveDialog(null);

            if (retValue == JFileChooser.APPROVE_OPTION)
                chosenOne = wybieracz.getSelectedFile();

            FileOutputStream fileOutputStream = new FileOutputStream(chosenOne);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(A.cPanel.line2DS);
            objectOutputStream.close();
            System.out.println("Dane zapisane");

        }
        catch (Exception e) {
            System.out.println("NO SAVE FILE");
        }
    }

    void Wczytaj() {

        try {

            JFileChooser wybieracz = new JFileChooser();
            int retValue = wybieracz.showSaveDialog(null);

            if (retValue == JFileChooser.APPROVE_OPTION)
                chosenOne = wybieracz.getSelectedFile();

            FileInputStream fileInputStream = new FileInputStream(chosenOne);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            A.cPanel.line2DS = (ArrayList)objectInputStream.readObject();
            A.cPanel.repaint();
            objectInputStream.close();

        }
        catch (Exception e) {
            System.out.println("NO READ FILE");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton)
            Zapisz();
        else if (e.getSource() == readButton)
            Wczytaj();
    }
}

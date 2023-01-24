package Lab_10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Index extends JFrame implements ActionListener {

    public static final int MAX_AMOUNT_OF_BYTES = 999;
    public static int[] inputArray;
    public static int[] outputArray;

    public static Index Main;

    JButton wczytaj,szyfruj, deszyfruj, zapisz;
    JTextArea nazwaWczytaj, nazwaZapisz;

    Index() {
        super("Title");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 600, 300);

        JPanel menuPanel = new JPanel(new GridLayout(6,0));

        nazwaWczytaj = new JTextArea("Podaj nazwę pliku wczytywanego");
        wczytaj = new JButton("Wczytaj");
        szyfruj = new JButton("Szyfruj");
        deszyfruj = new JButton("Deszyfruj");
        zapisz = new JButton("Zapisz");
        nazwaZapisz = new JTextArea("Podaj nazwę pliku końcowego");

        wczytaj.addActionListener(this);
        szyfruj.addActionListener(this);
        deszyfruj.addActionListener(this);
        zapisz.addActionListener(this);

        menuPanel.add(nazwaWczytaj);
        menuPanel.add(wczytaj);
        menuPanel.add(szyfruj);
        menuPanel.add(deszyfruj);
        menuPanel.add(zapisz);
        menuPanel.add(nazwaZapisz);

        setContentPane(menuPanel);
        setVisible(true);
    }



    public static void main(String[] args) {

        Main = new Index();

        inputArray = new int[MAX_AMOUNT_OF_BYTES];
        outputArray = new int[MAX_AMOUNT_OF_BYTES];

    }

    public static String WczytajPlik(String fileName) {
        String outputString = "";
        int input, i = 0;
        try {
            FileInputStream readFile = new FileInputStream (fileName);
            BufferedInputStream bufor = new BufferedInputStream(readFile);
            do {
                input = bufor.read();
                inputArray[i] = input;
                outputString += (char) input;
                i++;
            } while (input != -1);
            readFile.close();
        }
        catch (IOException e){
            System.out.println("Error - Nie można otworzyć pliku!");
        }
        return outputString;
    }

    public static void ZapiszPlik(String fileName) {
        try {
            FileOutputStream saveFile = new FileOutputStream (fileName);
            for(int j = 0; j < MAX_AMOUNT_OF_BYTES; j++) {
                if(inputArray[j] != -1) { saveFile.write(outputArray[j]); }
                else { break; }
            }
            saveFile.close();
        }
        catch (IOException e){
            System.out.println("Error - Nie można zapisać pliku!");
        }
    }

    public static String ModyfikujPlik(boolean encode) {
        String outputString = "";
        for(int j = 0; j < MAX_AMOUNT_OF_BYTES; j++) {
            if(inputArray[j] != -1) {
                if (encode) outputArray[j] = (inputArray[j] + 1);
                else outputArray[j] = (inputArray[j] - 1);
                outputString += (char) outputArray[j];
            }
            else { break; }
        }
        return outputString;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == wczytaj)
            WczytajPlik(Main.nazwaWczytaj.getText());
        else if (e.getSource() == szyfruj)
            ModyfikujPlik(true);
        else if (e.getSource() == deszyfruj)
            ModyfikujPlik(false);
        else if (e.getSource() == zapisz)
            ZapiszPlik(Main.nazwaZapisz.getText());
    }
}
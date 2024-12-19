import uk.ac.leedsbeckett.oop.OOPGraphics;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;

public class Main extends OOPGraphics {
    public static void main(String[] args)
    {
        System.out.println("Hello world!");
        new Main();
    }
    public Main(){
        JFrame gui = new JFrame("Leeds Beckett Nissi Attakora   ");//
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// close the app when the exit bottom is pressed
        gui.add(this);//this refers to the instance of "Main " class
        gui.pack();
        gui.setVisible(true);
        about();

    }
    @Override
    public void processCommand(String s) {
        System.out.println("print hello");

    }


}
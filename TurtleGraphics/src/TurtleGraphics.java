import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import uk.ac.leedsbeckett.oop.OOPGraphics;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TurtleGraphics extends OOPGraphics {

    JFrame frame=new JFrame("turtle image") ;
    GUI_window_display ErrorMessage=new GUI_window_display();


    public static void main(String[] args)
    {
        System.out.println("Hello Turtle!");
        new TurtleGraphics();

    }
    public  TurtleGraphics() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
    @Override public void about()
    {
        clear();
        BufferedImage text=getBufferedImage();
        Graphics g=text.getGraphics();
        JLabel label=null;
        Font font = new Font("Arial",Font.BOLD,30);
        g.setFont(font);
        g.setColor(Color.yellow);
        String name="Nissi Attakora";
        super.about();
        g.drawString(name,300,370);
        setBufferedImage(text);
    }
//    frame.add(label,BorderLayout.SOUTH);

    public void reset(){
        super.reset();

    }

    @Override
    public void processCommand(String input) {
        int distance;
        int amount;

        List<String> ListofCommands = new ArrayList<>();

        Error_handling IsNumeric=new Error_handling();
        Error_handling comd=new Error_handling();

        Save_load_exiting ldCommand=new Save_load_exiting();
        ldCommand.LoadCommandInFile(input);

        String [] arraycommand =input.split(" ");
        String command= arraycommand[0];
        command=command.toLowerCase();

        if(arraycommand.length<=2){


            if(command.isEmpty()||command.equals("about"))
            {
                about();
            }

            else if (input.equals("Loadcommandworks")) {
                FileDialog Dialog = new FileDialog((Frame) null, "Load Image");
                Dialog.setMode(FileDialog.LOAD);
                Dialog.setVisible(true);
                String Directory = Dialog.getDirectory();
                String FileName = Dialog.getFile();
                Dialog.dispose();

                if (Directory != null && FileName != null){
                    if (FileName.toLowerCase().endsWith("png")){
                        try {
                            displayMessage("WARNING: Current image and entered commands will not be saved");
                            ListofCommands.clear();
                            Thread.sleep(5000);
                            File file = new File(Directory, FileName);
                            BufferedImage image = ImageIO.read(file);
                            reset();
                            clear();
                            setBufferedImage(image);
                            displayMessage("Image loaded");
                            return;
                        } catch (IOException ex) {
                            displayMessage("Error loading image");
                            ex.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }
                    }
                }
            }


            else if(input.equals("Savecommandworks"))
            {

                String fileName=JOptionPane.showInputDialog("enter the name of the file to save the image");
                String Imagepath=fileName+".png";
                File filecommands=new File(Imagepath);
                try {
                    ImageIO.write(this.getBufferedImage(),"png",filecommands);
                    ErrorMessage.warning_messages("image has been saved");
                } catch(IOException e)
                {
                    ErrorMessage.warning_messages("error in saving image");

                    throw new RuntimeException(e);
                }


            }
            else if (command.equals("ldfile"))
            {
                String line;
                String fileName=JOptionPane.showInputDialog("enter the name of the file to you want load and execute ");
                fileName=fileName+".txt";
                try {
                    BufferedReader reader=new BufferedReader(new FileReader(fileName));
                    while ((line=reader.readLine())!=null){
                        processCommand(line);
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
            else if(command.equals("loadcomand"))
            {
                String filename="user_input.txt";
                clear();
                reset();
                try {
                    BufferedWriter writers;
                    String line;
                    String fileName=JOptionPane.showInputDialog("enter the name of the file to save the commands");
                    fileName=fileName+".txt";


                    BufferedReader reader=new BufferedReader(new FileReader(filename));
                    while ((line=reader.readLine())!=null){

                        System.out.println(line);

                        String[]commands=line.split(" ");
                        try {
                            PrintWriter writer = new PrintWriter(filename);
                            writer.print("");
                        }catch (IOException e){
                            ErrorMessage.warning_messages("Error in deleting File Content");
                        }
                        if(line.equals("loadcomand")){
                            break;
                        }
                        processCommand(line);
                        writers = new BufferedWriter(new FileWriter(fileName,true));
                        writers.append(line);
                        writers.newLine();
                        writers.flush();

                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    ErrorMessage.warning_messages("Error in file reading");
                    throw new RuntimeException(e);
                }
                ErrorMessage.warning_messages("content File deleted");

            }
            else if (command.equals("name")) {
                String filename = "user_input.txt";

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(filename));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {

                    FileWriter writer = new FileWriter(filename);
                    writer.write("");
                    writer.close();
                    System.out.println("File content deleted successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
            else if (command.equals("square"))

            {
                penDown();
                String height=JOptionPane.showInputDialog("Enter height of the square");
                IsNumeric.is_number(height);
                height=Integer.toString(IsNumeric.getNumber());
                forward(height);
                turnLeft();
                IsNumeric.is_number(height);
                height=Integer.toString(IsNumeric.getNumber());
                forward(height);
                turnLeft();
                forward(height);
                turnLeft();
                forward(height);
                reset();
            }
            else if (command.equals("pencolour"))
            {
                String RGBcolour = JOptionPane.showInputDialog("Enter the three parameters for the RGB colour separated by commas (e.g., 255 0 0):");
                String[] params = RGBcolour.split(" ");
                if (params.length == 3) { // Ensure there are three parts: red, green, blue
                    try {
                        int red = Integer.parseInt(params[0]);
                        int green = Integer.parseInt(params[1]);
                        int blue = Integer.parseInt(params[2]);
                        setPenColour(new Color(red, green, blue));
                        penDown();
                    } catch (NumberFormatException e) {
                        ErrorMessage.warning_messages("***OOPGraphics Exception*** Invalid RGB values.");
                    }
                } else {
                    ErrorMessage.warning_messages("***OOPGraphics Exception*** Incorrect number of parameters for pencolour command.");
                }
                displayMessage("OOPGraphics V5.1");
            }
            else if (command.equals("penwidth"))
            {
                String penStroke=JOptionPane.showInputDialog("Set Pen width");
                IsNumeric.is_number(penStroke);
                setStroke(Integer.parseInt(penStroke));
            } else if (input.equals("equilateral")){
                String side=JOptionPane.showInputDialog("Enter the side of the equilateral triangle");
                penDown();
                IsNumeric.is_number(side);
                turnLeft(30);
                forward(Integer.parseInt(side));
                turnRight(120);
                forward(Integer.parseInt(side));
                turnRight(120);
                forward(Integer.parseInt(side));
                reset();
            } else if (command.equals("triangle"))
            {
                double cosA,C;
                final int sumangle=180;
                penDown();
                String side = JOptionPane.showInputDialog("enter the 3 size of the triangle that you want to draw clockwise");
                String [] sides=side.split(" ");
                int size1=Integer.parseInt(sides[0]);
                IsNumeric.is_number(String.valueOf(size1));
                size1=IsNumeric.getNumber();//b
                int size2=Integer.parseInt(sides[1]);
                IsNumeric.is_number(String.valueOf(size2));
                size2=IsNumeric.getNumber();//c
                int size3=Integer.parseInt(sides[2]);
                IsNumeric.is_number(String.valueOf(size3));
                size3=IsNumeric.getNumber();//a


                cosA=(Math.pow(size1,2)+Math.pow(size2,2)-Math.pow(size3,2))/(2*size1*size2);
                double angleA =Math.toDegrees(Math.acos(cosA));
                double sinB=(Math.sin(angleA)*size1)/size3;
                double angleB=Math.toDegrees(Math.asin(sinB));
                double angleC=sumangle-(angleA+angleB);

                turnLeft((int) (angleB/2));
                forward(size3);
                turnRight((int) (sumangle-angleC));
                forward(size1);
                turnRight((int)(sumangle-angleA));
                forward(size2);
                reset();

            }
            else
            {
                switch (command){
                    case "about":
                        about();
                        break;
                    case "load":
                        Frame frame= new JFrame();
                        Save_load_exiting loadimg=new Save_load_exiting();
                        loadimg.loadImage();
                        setTurtleImage(loadimg.getresizedimageFilepath());
                        frame.add(this);
                        frame.pack();
                        frame.add(this);
                        frame.setVisible(true);
                        break;
                    case"penup":
                        penUp();
                        break;
                    case "pendown":
                        penDown();
                        ErrorMessage.warning_messages("Pen down");

                        break;
                    case "turnleft":
                        if(arraycommand.length==2){
                            IsNumeric.is_number(String.valueOf(arraycommand[1]));
                            amount=IsNumeric.getNumber();
                            if(amount<0){
                                ErrorMessage.warning_messages("Invalid parameter");
                            }
                            else {turnLeft(amount);}

                        } else if (arraycommand.length==1){
                            turnLeft();
                        }
                        break;
                    case "turnright":
                        if (arraycommand.length==2){
                            IsNumeric.is_number(String.valueOf(arraycommand[1]));
                            amount=IsNumeric.getNumber();
                            if(amount<0){
                                ErrorMessage.warning_messages("Invalid parameter");
                            }
                            else {
                                turnRight(amount);
                            }


                        }
                        else if(arraycommand.length==1){
                            turnRight();
                        }
                        break;
                    case "forward":
                        if(arraycommand.length==2)
                        {
                            IsNumeric.is_number(String.valueOf(arraycommand[1]));
                            distance=IsNumeric.getNumber();
                            if(distance<0||distance>1500){
                                ErrorMessage.warning_messages(" paramter out of bond");
                            }
                            else{
                                forward(distance);
                                int newX = getxPos();
                                int newY = getyPos();
                                if (newX >= 800 || newY >= 400 )
                                {
                                    newX = 800 - 1;
                                    newY = 400 - 1;
                                    this.reset();
                                    ErrorMessage.warning_messages("you out of bond");

                                }
                            }

                        }
                        else if (arraycommand.length>=3)
                        {
                            ErrorMessage.warning_messages("Invalid Number of  Parameters");
                        } else {
                            ErrorMessage.warning_messages("Missing Parameter");
                        }
                        break;

                    case "backwards":
                        if(arraycommand.length==2)
                        {
                            IsNumeric.is_number(String.valueOf(arraycommand[1]));
                            distance=IsNumeric.getNumber();
                            if(distance<0||distance>2000){
                                ErrorMessage.warning_messages("Inavlid Paramter MUST be positive");
                            }
                            else {
                                forward(-distance);
                                int newX = getxPos();
                                int newY = getyPos();
                                if (newX >= 800 || newY >= 400 )
                                {
                                    newX = 800 - 1;
                                    newY = 400 - 1;
                                    this.reset();
                                    ErrorMessage.warning_messages("you out of bond");

                                }
                            }

                        } else if (arraycommand.length>=3)
                        {
                            ErrorMessage.warning_messages("Invalid Number of  Parameters");
                        } else {
                            ErrorMessage.warning_messages("Missing Parameter");
                        }
                        break;
                    case "black":
                        setPenColour(Color.black);
                        break;
                    case "green":
                        setPenColour(Color.green);
                        break;
                    case "red":
                        setPenColour(Color.red);
                        break;
                    case "white":
                        setPenColour(Color.white);
                        break;
                    case "magenta":
                        setPenColour(Color.magenta);
                        break;
                    case"orange":
                        setPenColour(Color.orange);
                        break;
                    case "yellow":
                        setPenColour(Color.yellow);
                        break;
                    case "reset":
                        reset();
                        break;
                    case "clear":
                        clear();
                        break;
                    default:
                        ErrorMessage.warning_messages("Invalid Command");
                        break;
                }
            }
        }
        else if (arraycommand.length>=3)
        {ErrorMessage.warning_messages("invalid number of parameters");}
    }
}
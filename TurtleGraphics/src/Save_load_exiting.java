import uk.ac.leedsbeckett.oop.OOPGraphics;


import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.util.List;
public class Save_load_exiting    {
    private final int x=72;
    private final int y=69;
    private boolean imageSaved = false;
    private boolean savecommand=false;
    private BufferedImage Current_Image;
    private  String resizedimageFilepath;
    private File Filepath_Image;
    GUI_window_display window= new GUI_window_display();
    private String fileCommands="user_input.txt";
    private List <String> commands=new ArrayList<>();

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;


    public BufferedImage getCurrent_Image() {
        try {
            this.Current_Image=ImageIO.read(OOPGraphics.class.getResource("turtle90.png"));

        }catch (Exception e){
            window.warning_messages("Can't get Current Image");
        }

        return Current_Image;
    }

    public String getresizedimageFilepath(){
        return resizedimageFilepath;
    }
    public void loadImage(){
        JFrame windows=new JFrame();
        if( imageSaved==false){
            int confirm=JOptionPane.showConfirmDialog(windows,"Save current image before loading a new one");
            if(confirm==JOptionPane.YES_OPTION){
                saveImage();
                this.imageSaved=true;
                JFileChooser fileChooser = new JFileChooser();
                int selected = fileChooser.showOpenDialog(windows);
                if (selected == JFileChooser.APPROVE_OPTION) {
                    this.Filepath_Image = fileChooser.getSelectedFile();

                    try {

                        BufferedImage originalImage = ImageIO.read(Filepath_Image);
                        BufferedImage resizedImage = new BufferedImage(this.x, this.y, BufferedImage.TYPE_INT_RGB);

                        //resize image
                        Graphics2D g = resizedImage.createGraphics();
                        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g.drawImage(originalImage, 0, 0, this.x, this.y, null);
                        g.dispose();
                        window.warning_messages("image resized");

                        String fileName=JOptionPane.showInputDialog("enter the name of the file to save the image");


                        File outputResizedFile = new File(fileName);
                        if(outputResizedFile.exists()){
                            window.warning_messages("the Image alredy exists in the System");
                        }
                        else {ImageIO.write(resizedImage, "jpg", outputResizedFile);
                            this.resizedimageFilepath = outputResizedFile.getAbsolutePath();
                            window.warning_messages("image has been loaded");
                            this.Current_Image=resizedImage;}
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            else if(confirm==JOptionPane.NO_OPTION){
                window.warning_messages("Image not saved");
                window.warning_messages("you must save the current image before loading a new one");
                this.imageSaved=false;
            }
            else {window.warning_messages("out of load command");
            }
        }
        else if(imageSaved==true)
        {
            JFileChooser fileChooser = new JFileChooser();
            int selected = fileChooser.showOpenDialog(windows);
            if (selected == JFileChooser.APPROVE_OPTION) {
                this.Filepath_Image = fileChooser.getSelectedFile();

                try {

                    BufferedImage originalImage = ImageIO.read(Filepath_Image);
                    BufferedImage resizedImage = new BufferedImage(this.x, this.y, BufferedImage.TYPE_INT_RGB);

                    //resize image
                    Graphics2D g = resizedImage.createGraphics();
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g.drawImage(originalImage, 0, 0, this.x, this.y, null);
                    g.dispose();
                    window.warning_messages("image resized");

                    String fileName=JOptionPane.showInputDialog("enter the name of the file to save the image");

                    File outputResizedFile = new File(fileName);
                    ImageIO.write(resizedImage, "jpg", outputResizedFile);
                    this.resizedimageFilepath = outputResizedFile.getAbsolutePath();
                    window.warning_messages("image has been loaded");
                    this.Current_Image=resizedImage;



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void saveImage()
    {
        String file_name = JOptionPane.showInputDialog("Enter file name for image");
        File file=new File(file_name);
        if(file.exists()){
            window.warning_messages("File \""+file_name+ "\" exists ");
        }
        else { try {
            Current_Image = getCurrent_Image();
            ImageIO.write(Current_Image,"png",file);
            window.warning_messages("Image saved");

        } catch (IOException e){
            window.warning_messages("Cannot save image");
        }}

    }


    public void LoadCommandInFile (String command){
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(fileCommands, true));
            this.bufferedWriter.write(command);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (IOException e) {
            window.warning_messages("Error writing command to file");
            throw new RuntimeException(e);
        }
    }



    public void close(){
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List <String> getCommands(){
        return this.commands;
    }

    public void CommandReader()
    {
        String line;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileCommands));
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("file")) {
                    // Skip or handle the line if it equals "file"
                    continue;
                }
                // Add the line to the commands list if it's not "file"
                this.commands.add(line);
            }
        } catch (IOException e) {
            window.warning_messages("Error reading commands from file");
            throw new RuntimeException(e);
        }
    }

}
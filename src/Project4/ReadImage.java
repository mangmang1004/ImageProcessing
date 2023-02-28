package Project4;


import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class ReadImage implements ActionListener {

    JFileChooser fileChooser = new JFileChooser();
    File selectedFile;
    Image image;

    ReadImage(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter("png", "jpeg", "JPEG", "jpg");
        fileChooser.addChoosableFileFilter(filter);

        int rval = fileChooser.showOpenDialog(null);
        if(rval == 0){
            System.out.println("파일 열기를 선택하였습니다.");
            selectedFile = fileChooser.getSelectedFile();
            try {
                image = ImageIO.read(new File(selectedFile.getAbsolutePath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("파일 열기를 취소하였습니다.");

        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {


    }
}



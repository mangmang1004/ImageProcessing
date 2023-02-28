package Project4;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Screen extends JFrame implements ActionListener, ChangeListener {

    ReadImage readImage; //ReadImage class를 사용

    //왼쪽 이미지 사이즈 조절
    ImageIcon icon;
    Image image;
    Image image1;
    Image changeImg;
    ImageIcon changeIcon;


    ToBufferedImage BImage; //ToBufferdImage class 사용
    //왼쪽 이미지를 BufferedImage로 바꾼 뒤 가져온 것
    BufferedImage Bimg;

    JLabel orginalImage = new JLabel(); //왼쪽 이미지
    JLabel newImage = new JLabel(); //오른쪽 buffered이미지
    JPanel buttonset = new JPanel();

    JButton load = new JButton("파일 업로드하기");
    JButton gray = new JButton("흑백");
    JButton light = new JButton("밝기");
    JButton contrast = new JButton("대비");
    JButton edge = new JButton("edge 추출");
    JButton Smooth = new JButton("스무싱");
    JButton masking = new JButton("마스킹");

    JSlider slider;
    JSlider contrastslider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
    int brightness_control;
    int contrast_control;

    Screen() {

        setLayout(null);
        setBackground(Color.BLACK);
        setSize(885, 700);

        orginalImage.setBounds(25, 70, 400, 500);
        orginalImage.setBackground(Color.white);
        add(orginalImage);

        newImage.setBounds(450, 70, 400, 500);
        newImage.setBackground(Color.white);
        add(newImage);


        load.setBounds(25, 10, 100, 40);
        add(load);
        load.addActionListener(this);

        gray.setBounds(145, 10, 100, 40);
        add(gray);
        gray.addActionListener(this);

        light.setBounds(265, 10, 100, 40);
        add(light);
        light.addActionListener(this);

        contrast.setBounds(385, 10, 100, 40);
        add(contrast);
        contrast.addActionListener(this);

        edge.setBounds(505, 10, 100, 40);
        add(edge);
        edge.addActionListener(this);

        Smooth.setBounds(625,10,100,40);
        add(Smooth);
        Smooth.addActionListener(this);

        masking.setBounds(745,10,100,40);
        add(masking);
        masking.addActionListener(this);

        slider = new JSlider(JSlider.HORIZONTAL, -50, 50, 0);
        slider.setBounds(25, 600, 400, 50);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider value = (JSlider) e.getSource();
                //show_font.setText(String.valueOf(value.getValue()));
                brightness_control = value.getValue();
                System.out.println(brightness_control);

                brightMenu();
            }
        });
        add(slider);

        contrastslider.setBounds(450, 600, 400, 50);
        contrastslider.setMinorTickSpacing(5);
        contrastslider.setMajorTickSpacing(20);
        contrastslider.setPaintTicks(true);
        contrastslider.setPaintLabels(true);
        contrastslider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider value = (JSlider) e.getSource();
                contrast_control = value.getValue();
                System.out.println("안녕" + contrast_control);

                contrastMenu();
            }
        });
        add(contrastslider);

        orginalImage = new JLabel(); //왼쪽 이미지
        newImage = new JLabel(); //오른쪽 buffered이미지
        orginalImage.setBounds(25, 70, 400, 500);
        newImage.setBounds(450, 70, 400, 500);
        add(orginalImage);
        add(newImage);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == load) {
            readImage = new ReadImage(); //ReadImage class를 사용

            //왼쪽 이미지 사이즈 조절
            icon = new ImageIcon(readImage.image);
            image = icon.getImage();
            changeImg = image.getScaledInstance(400, 500, Image.SCALE_SMOOTH);
            changeIcon = new ImageIcon(changeImg);

            BImage = new ToBufferedImage(); //ToBufferdImage class 사용
            //왼쪽 이미지를 BufferedImage로 바꾼 뒤 가져온 것
            Bimg = BImage.imageToBufferedImage(changeImg);

            orginalImage.setIcon(changeIcon); //왼쪽 이미지
            newImage.setIcon(new ImageIcon(Bimg)); //오른쪽 buffered이미지

        } else if (e.getSource() == gray) {

            for (int y = 0; y < Bimg.getHeight(); y++) {
                for (int x = 0; x < Bimg.getWidth(); x++) {
                    Color color = new Color(Bimg.getRGB(x, y));

                    int Y = (int) (0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue());
                    Bimg.setRGB(x, y, new Color(Y, Y, Y).getRGB());
                }
            }
            newImage.setIcon(new ImageIcon(Bimg));

        } else if (e.getSource() == edge) {
            int[][] edgeColors = new int[Bimg.getWidth()][Bimg.getHeight()];
            BImage = new ToBufferedImage();
            Bimg = BImage.imageToBufferedImage(changeImg);
            int maxGradient = -1;


            for (int y = 1; y < Bimg.getHeight() - 1; y++) {
                for (int x = 1; x < Bimg.getWidth() - 1; x++) {
                    int val00 = getGrayScale(new Color(Bimg.getRGB(x-1, y-1)));
                    int val01 = getGrayScale(new Color(Bimg.getRGB(x-1,y)));
                    int val02 = getGrayScale(new Color(Bimg.getRGB(x-1,y+1)));

                    int val10 = getGrayScale(new Color(Bimg.getRGB(x, y-1)));
                    int val11 = getGrayScale(new Color(Bimg.getRGB(x, y)));
                    int val12 = getGrayScale(new Color(Bimg.getRGB(x, y+1)));

                    int val20 = getGrayScale(new Color(Bimg.getRGB(x+1, y-1)));
                    int val21 = getGrayScale(new Color(Bimg.getRGB(x+1, y)));
                    int val22 = getGrayScale(new Color(Bimg.getRGB(x+1, y+1)));

                    int gy = ((-1 * val00) + (-2 * val01) + (-1 * val02)) +
                            ((0 * val10) + (0 * val11) + (0 * val12)) +
                            ((1 * val20) + (2 * val21) + (1 * val22));

                    int gx = ((-1 * val00) + (0 * val01) + (1 * val02)) +
                            ((-2 * val10) + (0 * val11) + (2 * val12)) +
                            ((-1 * val20) + (0 * val21) + (1 * val22));


                    double gval = Math.sqrt((gx * gx) + (gy * gy));
                    int g = (int) gval;
                    //int edgeColor =  edgeColors[x][y];

                    if(maxGradient < g){
                        maxGradient = g;
                    }

                    edgeColors[x][y] = g;
                }
            }

            double scale = 255.0 / maxGradient;

            for (int y = 1; y < Bimg.getHeight() - 1; y++) {
                for (int x = 1; x < Bimg.getWidth() - 1; x++) {
                    int edgeColor = edgeColors[x][y];
                    edgeColor = (int)(edgeColor * scale);
                    edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                    Bimg.setRGB(x, y, edgeColor);
                }
            }

            newImage.setIcon(new ImageIcon(Bimg));

        } else if (e.getSource() == Smooth) {
            int[][] edgeColors = new int[Bimg.getWidth()][Bimg.getHeight()];
            BImage = new ToBufferedImage();
            Bimg = BImage.imageToBufferedImage(changeImg);
            int maxGradient = -1;

            int R = 0,G = 0, B = 0;
            int a = 0;

            int Red[][] = new int[Bimg.getWidth()][Bimg.getHeight()];
            int Green[][] = new int[Bimg.getWidth()][Bimg.getHeight()];
            int Blue[][] = new int[Bimg.getWidth()][Bimg.getHeight()];

            for (int y = 0; y < Bimg.getHeight() - 1; y++) {
                for (int x = 0; x < Bimg.getWidth() - 1; x++) {

                    for(int i = -1; i < 2; i++){
                        for(int j = -1; j < 2; j++){
                            if( i + x > 0 && j + y > 0 && i + x < Bimg.getWidth() && j + y < Bimg.getHeight()){

                                a ++;
                                Color color = new Color(Bimg.getRGB(i+x, j+y));
                                R += color.getRed();
                                G += color.getGreen();
                                B += color.getBlue();

                            }
                        }
                    }
                    Red[x][y] = R/a;
                    Green[x][y] = G/a;
                    Blue[x][y] = B/a;
                    Bimg.setRGB(x, y, new Color(Red[x][y], Green[x][y], Blue[x][y]).getRGB());
                    R = G = B = 0;
                    a = 0;
                }
            }

            newImage.setIcon(new ImageIcon(Bimg));

        }else if (e.getSource() == masking) {
            BImage = new ToBufferedImage();
            Bimg = BImage.imageToBufferedImage(changeImg);

            BufferedImage image2 = new BufferedImage(Bimg.getWidth(), Bimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image2.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(image1, 0, 0, null);

            newImage.setIcon(new ImageIcon(image1));

        }
    }

    public int getGrayScale(Color inputColor) {
        int gray = (int) (0.2126 * inputColor.getRed() + 0.7152 * inputColor.getGreen() + 0.0722 * inputColor.getBlue());

        return gray;
    }

    public int colorScale(Color inputColor) {
        int color = (int) inputColor.getRed() +  inputColor.getGreen() + inputColor.getBlue();

        return color;
    }

    public void brightMenu() {
        BImage = new ToBufferedImage();
        Bimg = BImage.imageToBufferedImage(changeImg);

        for (int y = 0; y < Bimg.getHeight(); y++) {
            for (int x = 0; x < Bimg.getWidth(); x++) {
                Color color = new Color(Bimg.getRGB(x, y));
                int R = color.getRed() + brightness_control;
                if (R > 255) R = 255;
                else if (R < 0) R = 0;

                int G = color.getGreen() + brightness_control;
                if (G > 255) G = 255;
                else if (G < 0) G = 0;

                int B = color.getBlue() + brightness_control;
                if (B > 255) B = 255;
                else if (B < 0) B = 0;

                Bimg.setRGB(x, y, new Color(R, G, B).getRGB());
            }
            newImage.setIcon(new ImageIcon(Bimg));
        }
    }

    public void contrastMenu() {
        BImage = new ToBufferedImage();
        Bimg = BImage.imageToBufferedImage(changeImg);

        for (int y = 0; y < Bimg.getHeight(); y++) {
            for (int x = 0; x < Bimg.getWidth(); x++) {
                Color color = new Color(Bimg.getRGB(x, y));
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();

                if (R > 128) {
                    R = R + contrast_control;
                    if (R > 255) R = 255;
                    else if (R < 0) R = 0;
                } else {
                    R = R - contrast_control;
                    if (R > 255) R = 255;
                    else if (R < 0) R = 0;
                }

                if (G > 128) {
                    G = G + contrast_control;
                    if (G > 255) G = 255;
                    else if (G < 0) G = 0;
                } else {
                    G = G - contrast_control;
                    if (G > 255) G = 255;
                    else if (G < 0) G = 0;
                }

                if (B > 128) {
                    B = B + contrast_control;
                    if (B > 255) B = 255;
                    else if (B < 0) B = 0;
                } else {
                    B = B - contrast_control;
                    if (B > 255) B = 255;
                    else if (B < 0) B = 0;
                }
                Bimg.setRGB(x, y, new Color(R, G, B).getRGB());
            }
            newImage.setIcon(new ImageIcon(Bimg));
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}

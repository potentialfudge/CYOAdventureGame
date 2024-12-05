package ui.gui;

import javax.swing.*;

import java.awt.Image;
import java.net.URL;

public class SplashScreen {
    private JFrame frame;

    public SplashScreen(String imagePath, int displayTime) {
        frame = new JFrame();
        JLabel label = new JLabel();

        try {
            URL imageUrl = getClass().getClassLoader().getResource(imagePath);
            if (imageUrl == null) {
                throw new IllegalArgumentException("Image not found: " + imagePath);
            }
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image originalImage = originalIcon.getImage();

            int width = 750;
            int height = (int) ((double) originalImage.getHeight(null) / originalImage.getWidth(null) * width);

            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            label.setIcon(new ImageIcon(scaledImage));

        } catch (Exception e) {
            System.err.println("Error loading splash screen image: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        frame.add(label);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void showSplashScreen(int duration, Runnable onCloseAction) {
        frame.setVisible(true);
        Timer timer = new Timer(duration, e -> {
            frame.dispose();
            onCloseAction.run();
        });
        timer.setRepeats(false);
        timer.start();
    }
}
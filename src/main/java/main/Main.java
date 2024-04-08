package main;

import javax.swing.JFrame;

public class Main {

    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Heroes vs Monsters");

        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel); // Ajoute le panneau de jeu à la fenêtre
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
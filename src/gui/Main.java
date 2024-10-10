package gui;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        //detta är viktigt för trådsäkerhet
        SwingUtilities.invokeLater(() -> new Window());
    }
}

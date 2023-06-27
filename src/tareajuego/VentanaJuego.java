/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tareajuego;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class VentanaJuego implements ActionListener {
    private JFrame frame;
    private JButton[][] botones;
    private JButton botonGuardar;
    private JTextField campoJugador1;
    private JTextField campoJugador2;
    private String jugadorActual;
    private String nombreJugador1;
    private String nombreJugador2;
    private boolean finJuego;
    private ArrayList<String> partidasGuardadas;

    public VentanaJuego() {
        frame = new JFrame("Gato");
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(3, 3));

        botones = new JButton[3][3];
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botones[fila][columna] = new JButton();
                botones[fila][columna].addActionListener(this);
                botones[fila][columna].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                panelTablero.add(botones[fila][columna]);
            }
        }

        JPanel panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout());

        campoJugador1 = new JTextField(10);
        campoJugador2 = new JTextField(10);
        JLabel etiquetaJugador1 = new JLabel("Jugador 1 (X):");
        JLabel etiquetaJugador2 = new JLabel("Jugador 2 (O):");

        panelControl.add(etiquetaJugador1);
        panelControl.add(campoJugador1);
        panelControl.add(etiquetaJugador2);
        panelControl.add(campoJugador2);

        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(this);
        panelControl.add(botonGuardar);

        frame.add(panelTablero, BorderLayout.CENTER);
        frame.add(panelControl, BorderLayout.NORTH);

        jugadorActual = "X";
        nombreJugador1 = "";
        nombreJugador2 = "";
        finJuego = false;
        partidasGuardadas = new ArrayList<>();
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonGuardar) {
            guardarPartida();
        } else {
            JButton botonClickeado = (JButton) e.getSource();
            if (!botonClickeado.getText().equals("") || finJuego) {
                return;
            }

            botonClickeado.setText(jugadorActual);
            if (hayGanador()) {
                JOptionPane.showMessageDialog(frame, "¡" + getNombreJugadorActual() + " gana!");
                finJuego = true;
            } else if (hayEmpate()) {
                JOptionPane.showMessageDialog(frame, "¡Empate!");
                finJuego = true;
            } else {
                jugadorActual = jugadorActual.equals("X") ? "O" : "X";
            }
        }
    }

    private boolean hayGanador() {
        String[][] tablero = new String[3][3];
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                tablero[fila][columna] = botones[fila][columna].getText();
            }
        }

        // Comprobar filas
        for (int fila = 0; fila < 3; fila++) {
            if (tablero[fila][0].equals(jugadorActual) && tablero[fila][1].equals(jugadorActual) && tablero[fila][2].equals(jugadorActual)) {
                return true;
            }
        }

        // Comprobar columnas
        for (int columna = 0; columna < 3; columna++) {
            if (tablero[0][columna].equals(jugadorActual) && tablero[1][columna].equals(jugadorActual) && tablero[2][columna].equals(jugadorActual)) {
                return true;
            }
        }

        // Comprobar diagonales
        if (tablero[0][0].equals(jugadorActual) && tablero[1][1].equals(jugadorActual) && tablero[2][2].equals(jugadorActual)) {
            return true;
        }
        if (tablero[0][2].equals(jugadorActual) && tablero[1][1].equals(jugadorActual) && tablero[2][0].equals(jugadorActual)) {
            return true;
        }

        return false;
    }

    private boolean hayEmpate() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                if (botones[fila][columna].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private String getNombreJugadorActual() {
        return jugadorActual.equals("X") ? nombreJugador1 : nombreJugador2;
    }

    private void guardarPartida() {
        nombreJugador1 = campoJugador1.getText();
        nombreJugador2 = campoJugador2.getText();

        if (nombreJugador1.equals("") || nombreJugador2.equals("")) {
            JOptionPane.showMessageDialog(frame, "Debe ingresar los nombres de ambos jugadores.");
            return;
        }

        if (partidasGuardadas.size() >= 10) {
            partidasGuardadas.remove(0);
        }

        partidasGuardadas.add(getNombreJugadorActual());

        try {
            FileOutputStream fileOut = new FileOutputStream("partidas.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(partidasGuardadas);
            out.close();
            fileOut.close();
            JOptionPane.showMessageDialog(frame, "Partida guardada exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al guardar la partida.");
        }
    }
}

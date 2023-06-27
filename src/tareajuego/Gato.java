/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tareajuego;

/**
 *
 * @author Andre Sebastián
 */
   import javax.swing.*; 
public class Gato {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VentanaJuego ventanaJuego = new VentanaJuego();
                ventanaJuego.mostrar();
            }
        });
    }
}

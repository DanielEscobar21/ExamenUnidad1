package icon;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Dibujo extends JPanel {

    private JFrame ventana;
    private Container contenedor;
    int choques = 0;
    private final int[] figura = {
        0x001C000,
        0x001FF10,
        0x100FC00,
        0x110FF00,
        0x1C07FC0,
        0x1E07FE0,
        0x3FFFFFF,
        0x7FFFFF1,
        0x3FFFFFF,
        0x1E07FE0,
        0x1C07FC0,
        0x110FF00,
        0x100FC00,
        0x001FF10,
        0x001C000
    };
    private final int MASCARA = 0x8000000;
    private final int ANCHO_BITS = 32;
    private int coordenada_x, coordenada_y;
    private Thread hilo;

    public Dibujo() {
        ventana = new JFrame("Dibujando Icono");
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        contenedor = ventana.getContentPane();
        contenedor.setSize(800, 600);
        contenedor.add(this, BorderLayout.CENTER);

        this.hilo = new Thread();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        System.out.println(this.coordenada_x);
        System.out.println(this.coordenada_y);
        for (int i = 0; i < this.figura.length; i++) {
            //iterador para el anchi en bits de la figura
            for (int j = 0; j < this.ANCHO_BITS - 1; j++) {
                int temp = this.figura[i] & (this.MASCARA >> j);
                if (temp != 0) {
                    graphics.drawLine(this.coordenada_y + j, this.coordenada_x + i, this.coordenada_y + j, this.coordenada_x + i);
                }
            }
        }
    }

    public void dibujar() {
        do {
            this.coordenada_x = (int) (Math.random() * ventana.getHeight() - 200);
            this.coordenada_y = (int) (Math.random() * ventana.getWidth() - ANCHO_BITS - 50);
            int direccion = (int) (Math.random() * 4);
            elegir(direccion);
            JOptionPane.showMessageDialog(ventana, "Choques: " + choques + "/10");
        } while (choques < 10);
        String[] Opp = {"Reiniciar", "Salir"};
        int op = JOptionPane.showOptionDialog(ventana, "Final 10/10\n¿Que desea hacer?","¿Salir?" , 0, 0, null, Opp, Opp[1]);
        switch (op) {
            case 0:
                dibujar();
                break;
            case 1:
                System.exit(0);
                break;
            default:
                System.exit(0);
        }

    }

    public void elegir(int direccion) {
        switch (direccion) {
            case 0:
                do {
                    try {
                        this.coordenada_x = this.coordenada_x - 10;
                        this.hilo.sleep(100);
                        paint(getGraphics());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (this.coordenada_x > 0);
                paint(getGraphics());
                break;
            case 1:
                do {
                    try {
                        this.coordenada_y = this.coordenada_y - 10;
                        this.hilo.sleep(100);
                        paint(getGraphics());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (this.coordenada_y > 0);
                paint(getGraphics());
                break;
            case 2:
                do {
                    try {
                        this.coordenada_x = this.coordenada_x + 10;
                        this.hilo.sleep(100);
                        paint(getGraphics());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (this.coordenada_x < ventana.getHeight() - 70);
                paint(getGraphics());
                break;
            case 3:
                do {
                    try {
                        this.coordenada_y = this.coordenada_y + 10;
                        this.hilo.sleep(100);
                        paint(getGraphics());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (this.coordenada_y < ventana.getWidth() - ANCHO_BITS);
                paint(getGraphics());
                break;
        }
        choques++;
    }
}

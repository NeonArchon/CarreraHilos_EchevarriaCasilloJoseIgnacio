import javax.swing.*;
import java.awt.*;

public class HilosCorredores implements Runnable {

    //logice de los hilos para mover los corredores

    private static int posicion = 1; // Controla la posición de llegada de los corredores
    private final String nombre;
    private final JLabel personaje;
    private final JLabel finalPos;
    private final Carrera carreraPrincipal; // Referencia a la clase principal
    private final Thread t;

    public HilosCorredores(String nombre, JLabel personaje, JLabel finalPos, Carrera carreraPrincipal) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.finalPos = finalPos;
        this.carreraPrincipal = carreraPrincipal;
        this.t = new Thread(this, nombre);
        t.start();
    }

    @Override
    public void run() {
        int delay;
        try {
            finalPos.setVisible(false);
            personaje.setVisible(true);

            for (int i = 0; i <= 500; i += 2) {
                delay = (int) (Math.random() * 8) + 2;
                personaje.setLocation(i, personaje.getY());
                Thread.sleep(delay);
            }

            personaje.setVisible(false);
            finalPos.setVisible(true);
            synchronized (HilosCorredores.class) { // Sincronización para actualizar la posición

                if (posicion == 1) {
                    finalPos.setBounds(350, 80, 400, 50);  // Nave 1
                } else if (posicion == 2) {
                    finalPos.setBounds(350, 180, 400, 50);  // Nave 2
                } else if (posicion == 3) {
                    finalPos.setBounds(350, 280, 400, 50);  // Nave 3
                } else if (posicion == 4) {
                    finalPos.setBounds(350, 380, 400, 50);  // Nave 4
                }
                finalPos.setText(" La nave " + nombre + " llegó en la posición " + posicion);
                finalPos.setForeground(Color.WHITE);
                posicion++;
                if (posicion>4){
                    posicion =1;
                }
            }

            // Notificar a la clase principal que este corredor terminó
            carreraPrincipal.corredorTermino();

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}

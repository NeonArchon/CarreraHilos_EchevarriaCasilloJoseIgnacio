import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Carrera extends JFrame {

    // interfaz gráfica y la gestion de eventos

    private boolean carreraEnCurso = false; // Controla si la carrera está activa
    private int corredoresFinalizados = 0; // Contador de corredores que terminan
    private JButton iniciarCarrera; // Boton para iniciar la carrera
    private JButton Salir; //Boton para salir del programa

    public class FondoPanel extends JPanel {
        private Image imagen;

        // Constructor que recibe la ruta de la imagen
        public FondoPanel(String rutaImagen) {
            this.imagen = new ImageIcon(rutaImagen).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                // Dibuja la imagen para que ocupe todo el tamaño del panel
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public Carrera() {
        super("Carrera Espacial del Espacio!");

        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el panel de fondo
        FondoPanel panel = new FondoPanel("src/imagenes/Espacio.jpg");
        panel.setLayout(null); // Layout nulo para posicionar manualmente los componentes

        // Crear corredores con imágenes específicas
        JLabel Nave1 = crearLabelCorredor(50, 50, "src/imagenes/Nave1.png");
        JLabel Nave2 = crearLabelCorredor(50, 150, "src/imagenes/Nave2.png");
        JLabel Nave3 = crearLabelCorredor(50, 250, "src/imagenes/Nave3.png");
        JLabel Nave4 = crearLabelCorredor(50, 350, "src/imagenes/Nave4.png");

        JLabel c1Pos = crearLabelPosicion(350, 50);
        JLabel c2Pos = crearLabelPosicion(350, 100);
        JLabel c3Pos = crearLabelPosicion(350, 150);
        JLabel c4Pos = crearLabelPosicion(350, 200);

        // Icono de ventana
        ImageIcon Logo = new ImageIcon("src/imagenes/Planeta.png");
        setIconImage(Logo.getImage());

        // Botón para iniciar la carrera
        iniciarCarrera = new JButton("Iniciar Carrera");
        iniciarCarrera.setBounds(100, 450, 150, 50);

        iniciarCarrera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!carreraEnCurso) { // Evita iniciar múltiples carreras
                    carreraEnCurso = true;
                    corredoresFinalizados = 0;
                    iniciarCarrera.setEnabled(false);

                    // Inicializar hilos de los corredores
                    new HilosCorredores("Corredor 1", Nave1, c1Pos, Carrera.this);
                    new HilosCorredores("Corredor 2", Nave2, c2Pos, Carrera.this);
                    new HilosCorredores("Corredor 3", Nave3, c3Pos, Carrera.this);
                    new HilosCorredores("Corredor 4", Nave4, c4Pos, Carrera.this);
                }
            }
        });

        // Botón para salir del programa
        Salir = new JButton("Salir");
        Salir.setBounds(350, 450, 150, 50);
        Salir.addActionListener(e -> System.exit(0));

        // Agregar elementos al panel
        panel.add(Nave1);
        panel.add(c1Pos);
        panel.add(Nave2);
        panel.add(c2Pos);
        panel.add(Nave3);
        panel.add(c3Pos);
        panel.add(Nave4);
        panel.add(c4Pos);
        panel.add(iniciarCarrera);
        panel.add(Salir);

        // Agregar el panel a la ventana
        add(panel);
        setVisible(true);
    }


    // Método sincronizado para notificar cuando un corredor termina
    public synchronized void corredorTermino() {
        corredoresFinalizados++;
        if (corredoresFinalizados == 4) { // Cuando todos terminan
            carreraEnCurso = false;
            SwingUtilities.invokeLater(() -> iniciarCarrera.setEnabled(true));
        }
    }

    // Método para crear el JLabel de un corredor
    private JLabel crearLabelCorredor(int x, int y, String rutaImagen) {
        // Cargar la imagen original
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagen = iconoOriginal.getImage();

        // Crear una imagen rotada
        Image imagenRotada = rotarImagen(imagen, 90); // Rotar 90 grados (para que quede horizontal)

        // Crear el ImageIcon con la imagen rotada
        ImageIcon iconoCorredor = new ImageIcon(imagenRotada.getScaledInstance(100, 50, Image.SCALE_SMOOTH));

        // Crear el JLabel con la imagen rotada
        JLabel label = new JLabel();
        label.setIcon(iconoCorredor);
        label.setBounds(x, y, 100, 50); // Ajustar el tamaño del JLabel
        return label;
    }

    //metodo para rotar la imagen
    private Image rotarImagen(Image imagen, double grados) {
        // Crear un objeto Graphics2D para rotar la imagen
        int w = imagen.getWidth(null);
        int h = imagen.getHeight(null);

        // Crear un objeto BufferedImage para la imagen rotada
        BufferedImage imagenRotada = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagenRotada.createGraphics();

        // Rotar la imagen alrededor de su centro
        g2d.rotate(Math.toRadians(grados), w / 2, h / 2);

        // Dibuja la imagen original sobre el objeto Graphics2D
        g2d.drawImage(imagen, 0, 0, null);
        g2d.dispose(); // Liberar los recursos

        return imagenRotada;
    }



    // Método para crear el JLabel de la posición
    private JLabel crearLabelPosicion(int x, int y) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 200, 50);
        return label;
    }

}

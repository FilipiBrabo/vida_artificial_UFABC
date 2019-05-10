import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Main extends JPanel{

    private static int grid[][];
    private static int xOffSet  = 50;
    private static int yOffSet  = 50;
    private static int window_w = 1600;
    private static int window_h = 1000;
    private static int grid_w   = window_w - 2 * xOffSet;
    private static int grid_h   = window_h - 2 * yOffSet;

    public Main() {
        grid = new int [300][500];
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        // Pinta o background de preto
        g.setColor(Color.BLACK);
        g.fillRect(xOffSet,yOffSet, grid_w, grid_h);

        // Varre a matrix pintando as células vivas de branco
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 500; j++) {
                if (grid[i][j] == 1) {
                    int cellY= xOffSet + (i * 3);
                    int cellX = yOffSet + (j * 3);
                    g.setColor(Color.WHITE);
                    g.fillRect(cellX, cellY, 3, 3);
                }
            }
        }

        //Desenha as linhas e colunas da matriz
        g.setColor(Color.BLACK);
        for (int i = xOffSet; i <= xOffSet + grid_h; i+= 3) {
            g.drawLine(xOffSet, i, grid_w + xOffSet, i);
        }

        for (int i = yOffSet; i <= yOffSet + grid_w; i+= 3) {
            g.drawLine(i, xOffSet, i, grid_h + yOffSet);
        }
    }

    public static void main (String[] args) {

        int num = Integer.parseInt(JOptionPane.showInputDialog("Digite a regra que deseja visualizar:"));

        // Enquanto não houver um input válido, é requisitado um novo número ao usúario
        while(num < 0 || num >= 255) {
            JOptionPane.showMessageDialog(null, "Número inválido\n Digite um número entre 0 e 255");
            num = Integer.parseInt(JOptionPane.showInputDialog("Digite a regra que deseja visualizar:"));
        }

        // Escolha entre inicialização randomica ou apenas única célula viva
        String[] choices = {"Randômico", "Uma célula viva no centro"};
        String choice = (String) JOptionPane.showInputDialog(null, "Escolha uma configuração inicial", "Inicialização",
                JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        
        JOptionPane.showMessageDialog(null, "Se o padrão não for totalmente impresso tente executar novamente");

        // Conversão do número em decimal para o número binário contendo 8 casas
        String s = Integer.toBinaryString(num);
        s = s.format("%8s", s);        
        s = s.replace(' ', '0');

        // Inicialização da janela
        JFrame frame = new JFrame();
        frame.setSize(1600, 1000);
        frame.getContentPane().add(new Main());
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        regra(s, choice);
        frame.repaint();
    }

    /* Determina a configuração das células (Vivas ou mortas) a cada iteração baseando-se na regra escolhida
       Recebe o modo que a primeira geração de células deve estar (random ou apenas uma viva) */
    public static void regra(String s, String choice) {

        if (choice.equals("Randômico")) { //Random
            Random rd = new Random();
            // Inicialização das células com configuração aleatória
            for (int i = 0; i < grid_w/3; i ++) {
                if(rd.nextInt(100) >= 50){
                    grid[0][i] = 1;
                }
            }

        } else { // Apenas a célula do meio viva
            grid[0][250] = 1;
        }

        int left, right, middle;

        for (int i = 1; i < 300; i++) {
            for (int j = 0; j < 500; j++) {

                // Determina os vizinhos esquerdo e direito
                if (j == 0) {  // Primeiro elemento da linha
                    left = grid[i-1][grid[0].length-1];
                } else {
                    left = grid[i-1][j-1];
                }

                if (j == 499) { //Último elemento da linha
                    right = grid[i-1][0];
                } else {
                    right = grid[i - 1][j + 1];
                }

                middle = grid[i-1][j];

                //Converte os números em uma string que conterá o número em binário correspondente a posição da regra
                String sl, sr, sm;
                sl = String.valueOf(left);
                sr = String.valueOf(right);
                sm = String.valueOf(middle);
                String s1 = sl.concat(sm);
                s1 = s1.concat(sr);

                //Transforma o num binário em decimal
                int num = Integer.parseInt(s1,2);

                //Determina o valor da célula
                grid[i][j] = Character.getNumericValue(s.charAt(7-num));
            }
        }
    }
}

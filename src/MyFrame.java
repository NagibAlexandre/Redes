import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyFrame extends JFrame {

    public MyFrame() {
        this.setTitle("Trabalho Pratico Redes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Ícone da janela
        ImageIcon imageIcon = new ImageIcon("img/puc.png");
        this.setIconImage(imageIcon.getImage());

        // Fundo
        BackgroundLabel background = new BackgroundLabel("PUC MINAS");
        background.setBounds(0, 0, 900, 800);

        // JLayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 800));
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Adiciona botões usando função separada
        adicionarBotoesDinamicamente(layeredPane, 40, 550, 120, 40, 20, 24);

        this.setContentPane(layeredPane);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Adiciona uma quantidade de botões dinamicamente ao painel em linha.
     *
     * @param pane       O JLayeredPane onde os botões serão adicionados
     * @param startX     Posição X inicial
     * @param startY     Posição Y fixa
     * @param width      Largura de cada botão
     * @param height     Altura de cada botão
     * @param spacing    Espaçamento horizontal entre botões
     * @param quantidade Quantidade de botões a adicionar
     */
    private void adicionarBotoesDinamicamente(JLayeredPane pane, int startX, int startY, int width, int height, int spacing, int quantidade) {
        int botoesPorLinha = 6; // Máximo de botões por linha

        for (int i = 0; i < quantidade; i++) {
            int linha = i / botoesPorLinha;
            int coluna = i % botoesPorLinha;

            int x = startX + coluna * (width + spacing);
            int y = startY + linha * (height + spacing); // desce 50 a cada nova linha

            JButton button = new JButton("Opção " + (i + 1));
            button.setBounds(x, y, width, height);
            button.setFocusable(false);
            int index = i + 1;
            button.addActionListener(e -> System.out.println("Botão " + index + " clicado"));

            pane.add(button, JLayeredPane.PALETTE_LAYER);
        }
    }

}

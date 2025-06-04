import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyFrame extends JFrame {

    private List<Candidato> candidatos;

    public MyFrame(List<Candidato> candidatos) {
        this.candidatos = candidatos;

        this.setTitle("Trabalho Pratico Redes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("img/puc.png");
        this.setIconImage(imageIcon.getImage());

        BackgroundLabel background = new BackgroundLabel("PUC MINAS");
        background.setBounds(0, 0, 900, 800);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 800));
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        adicionarBotoesDinamicamente(layeredPane, 40, 550, 120, 40, 20);

        this.setContentPane(layeredPane);
        this.pack();
        this.setVisible(true);
    }

    private void adicionarBotoesDinamicamente(JLayeredPane pane, int startX, int startY, int width, int height,
            int spacing) {
        int botoesPorLinha = 6;

        for (int i = 0; i < candidatos.size(); i++) {
            Candidato candidato = candidatos.get(i);

            int linha = i / botoesPorLinha;
            int coluna = i % botoesPorLinha;

            int x = startX + coluna * (width + spacing);
            int y = startY + linha * (height + spacing);

            JButton button = new JButton(candidato.getNome());
            button.setBounds(x, y, width, height);
            button.setFocusable(false);

            JLabel votosLabel = new JLabel(
                    "<html>Candidato: " + candidato.getNome() + "<br>Votos: " + candidato.getVotos() + "</html>");
            votosLabel.setForeground(Color.WHITE);
            votosLabel.setBounds(x, y - 400, 200, 40); // 50 px acima do botão
            pane.add(votosLabel, JLayeredPane.PALETTE_LAYER);

            button.addActionListener(e -> {
                candidato.incrementarVoto();
                votosLabel.setText(
                        "<html>Candidato: " + candidato.getNome() + "<br>Votos: " + candidato.getVotos() + "</html>");
                System.out.println(candidato.getNome() + " recebeu um voto!");
            });

            pane.add(button, JLayeredPane.PALETTE_LAYER);
        }
    }
}

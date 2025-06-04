import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaVotar extends JFrame {

    private List<Candidato> candidatos;
    private static String tempo = "00:00:00"; // Placeholder for time, can be updated with a timer
    private static String abertura = "01/06/2024 14:00"; // Placeholder for opening time
    private static boolean status = false; // false for open, true for closed

    public TelaVotar(List<Candidato> candidatos) {
        this.candidatos = candidatos;
        this.setTitle("Trabalho Pratico Redes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1300, 950);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("img/puc.png");
        this.setIconImage(imageIcon.getImage());

        BackgroundLabel background = new BackgroundLabel("PUC MINAS");
        background.setBounds(0, 0, 1300, 950);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1300, 950));
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        barraInformacoes(layeredPane);
        adicionarCardsGrid(layeredPane);

        this.setContentPane(layeredPane);
        this.pack();
        this.setVisible(true);
    }

    private void barraInformacoes(JLayeredPane pane) {
        int barraAltura = 90;
        JPanel barra = new JPanel();
        barra.setLayout(null);
        barra.setBounds(0, 0, 1300, barraAltura);
        barra.setBackground(new Color(40, 40, 40, 230));

        Font infoFont = new Font("Segoe UI", Font.BOLD, 18);
        Font subFont = new Font("Segoe UI", Font.PLAIN, 15);
        Color textColor = new Color(230, 230, 230);

        JLabel statusLabel = new JLabel("Votação " + (status ? "aberta" : "fechada"));
        statusLabel.setFont(infoFont);
        statusLabel.setForeground(textColor);
        statusLabel.setBounds(30, 10, 250, 30);

        JLabel tempoLabel = new JLabel("Tempo: " + tempo + " | " + "Abertura: " + abertura);
        tempoLabel.setFont(subFont);
        tempoLabel.setForeground(textColor);
        tempoLabel.setBounds(30, 45, 350, 25);

        // Encontrar candidato mais votado
        Candidato maisVotado = null;
        for (Candidato c : candidatos) {
            if (maisVotado == null || c.getVotos() > maisVotado.getVotos()) {
                maisVotado = c;
            }
        }
        String maisVotadoStr = maisVotado != null ? maisVotado.getNome() + " - " + maisVotado.getVotos() + " votos"
                : "-";
        JLabel maisVotadoLabel = new JLabel("Mais votado: " + maisVotadoStr);
        maisVotadoLabel.setFont(infoFont);
        maisVotadoLabel.setForeground(textColor);
        maisVotadoLabel.setBounds(700, 25, 450, 30);
        maisVotadoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        barra.add(statusLabel);
        barra.add(tempoLabel);
        barra.add(maisVotadoLabel);
        pane.add(barra, JLayeredPane.PALETTE_LAYER);
    }

    private void adicionarCardsGrid(JLayeredPane pane) {
        int cardsPorLinha = 6;
        int cardWidth = 170;
        int cardHeight = 140;
        int spacing = 30;
        int startX = 60;
        int startY = 120; // abaixo da barra superior

        Color cardBg = new Color(60, 60, 60, 220);
        Color textColor = new Color(230, 230, 230);
        Font nomeFont = new Font("Segoe UI", Font.BOLD, 16);
        Font votosFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        for (int i = 0; i < candidatos.size(); i++) {
            Candidato candidato = candidatos.get(i);
            int linha = i / cardsPorLinha;
            int coluna = i % cardsPorLinha;
            int x = startX + coluna * (cardWidth + spacing);
            int y = startY + linha * (cardHeight + spacing);

            JPanel card = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(cardBg);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
                    super.paintComponent(g2);
                    g2.dispose();
                }
            };
            card.setLayout(null);
            card.setBounds(x, y, cardWidth, cardHeight);
            card.setOpaque(false);

            JLabel nomeLabel = new JLabel(candidato.getNome());
            nomeLabel.setBounds(10, 10, cardWidth - 20, 30);
            nomeLabel.setFont(nomeFont);
            nomeLabel.setForeground(textColor);
            nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel votosLabel = new JLabel("Votos: " + candidato.getVotos());
            votosLabel.setBounds(10, 45, cardWidth - 20, 25);
            votosLabel.setFont(votosFont);
            votosLabel.setForeground(textColor);
            votosLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JButton votarButton = new JButton("Votar");
            votarButton.setBounds(25, 85, cardWidth - 50, 35);
            votarButton.setFont(buttonFont);
            votarButton.setForeground(textColor);
            votarButton.setBackground(new Color(100, 100, 100));
            votarButton.setFocusPainted(false);
            votarButton.setBorderPainted(false);
            votarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            votarButton.addActionListener(e -> {
                candidato.incrementarVoto();
                votosLabel.setText("Votos: " + candidato.getVotos());
                // Atualizar o label do mais votado na barra superior
                pane.removeAll();
                BackgroundLabel background = new BackgroundLabel("PUC MINAS");
                background.setBounds(0, 0, 1300, 950);
                pane.add(background, JLayeredPane.DEFAULT_LAYER);
                barraInformacoes(pane);
                adicionarCardsGrid(pane);
                pane.repaint();
            });

            card.add(nomeLabel);
            card.add(votosLabel);
            card.add(votarButton);
            pane.add(card, JLayeredPane.PALETTE_LAYER);
        }
    }
}

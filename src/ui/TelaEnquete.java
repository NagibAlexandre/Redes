package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Enquete;
import client.ClienteTCP;
import client.ClienteUDP;

public class TelaEnquete extends JFrame {
    private JLayeredPane layeredPane;
    private List<Enquete> enquetes;

    public TelaEnquete() {
        this.setTitle("Trabalho Pratico Redes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1300, 950);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("img/puc.png");
        this.setIconImage(imageIcon.getImage());

        BackgroundLabel background = new BackgroundLabel("PUC MINAS");
        background.setBounds(0, 0, 1300, 950);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1300, 950));
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        enquetes = ClienteTCP.listarEnquetes();
        if (enquetes != null) {
            adicionarBarraSuperior(layeredPane);
            adicionarListaEnquetes(layeredPane);
        }

        this.setContentPane(layeredPane);
        this.pack();
        this.setVisible(true);

        // Inicia o listener UDP para atualizações
        ClienteUDP.iniciarRecebimento(enquetes -> {
            // Callback automático quando novas enquetes são recebidas via UDP
            SwingUtilities.invokeLater(() -> {
                System.out.println("[UDP] Atualizando enquetes recebidas...");
                atualizarEnquetesComDados(enquetes); // ou atualizarEnquetes(), se já acessa
                                                     // ClienteUDP.getUltimasEnquetes()
            });
        });
    }

    private void adicionarBarraSuperior(JLayeredPane pane) {
        int barraAltura = 90;
        JPanel barra = new JPanel();
        barra.setLayout(null);
        barra.setBounds(0, 0, 1300, barraAltura);
        barra.setBackground(new Color(40, 40, 40, 230));

        Font infoFont = new Font("Segoe UI", Font.BOLD, 18);
        Color textColor = new Color(230, 230, 230);

        JLabel tituloLabel = new JLabel("Enquetes Disponíveis");
        tituloLabel.setFont(infoFont);
        tituloLabel.setForeground(textColor);
        tituloLabel.setBounds(30, 25, 300, 30);

        JButton criarEnqueteButton = new JButton("Criar Nova Enquete");
        criarEnqueteButton.setBounds(1000, 25, 250, 40);
        criarEnqueteButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        criarEnqueteButton.setForeground(textColor);
        criarEnqueteButton.setBackground(new Color(100, 100, 100));
        criarEnqueteButton.setFocusPainted(false);
        criarEnqueteButton.setBorderPainted(false);
        criarEnqueteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        criarEnqueteButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                TelaCriarEnquete telaCriarEnquete = new TelaCriarEnquete();
                telaCriarEnquete.setVisible(true);
                this.dispose();
            });
        });

        barra.add(tituloLabel);
        barra.add(criarEnqueteButton);
        pane.add(barra, JLayeredPane.PALETTE_LAYER);
    }

    private void adicionarListaEnquetes(JLayeredPane pane) {
        int cardWidth = 300;
        int cardHeight = 180;
        int spacing = 30;
        int startX = 60;
        int startY = 120;
        int cardsPorLinha = 4;

        Color cardBg = new Color(60, 60, 60, 220);
        Color textColor = new Color(230, 230, 230);
        Font tituloFont = new Font("Segoe UI", Font.BOLD, 16);
        Font infoFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        for (int i = 0; i < enquetes.size(); i++) {
            Enquete enquete = enquetes.get(i);
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

            JLabel tituloLabel = new JLabel(enquete.getTitulo());
            tituloLabel.setBounds(15, 15, cardWidth - 30, 25);
            tituloLabel.setFont(tituloFont);
            tituloLabel.setForeground(textColor);

            JLabel statusLabel = new JLabel("Status: " + (enquete.isStatus() ? "Aberta" : "Fechada"));
            statusLabel.setBounds(15, 45, cardWidth - 30, 20);
            statusLabel.setFont(infoFont);
            statusLabel.setForeground(textColor);

            JLabel duracaoLabel = new JLabel("Duração: " + enquete.getTempoDuracao());
            duracaoLabel.setBounds(15, 70, cardWidth - 30, 20);
            duracaoLabel.setFont(infoFont);
            duracaoLabel.setForeground(textColor);

            JLabel candidatosLabel = new JLabel("Candidatos: " + enquete.getCandidatos().size());
            candidatosLabel.setBounds(15, 95, cardWidth - 30, 20);
            candidatosLabel.setFont(infoFont);
            candidatosLabel.setForeground(textColor);

            JButton entrarButton = new JButton("Entrar");
            entrarButton.setBounds(15, 125, cardWidth - 30, 35);
            entrarButton.setFont(buttonFont);
            entrarButton.setForeground(textColor);
            entrarButton.setBackground(new Color(100, 100, 100));
            entrarButton.setFocusPainted(false);
            entrarButton.setBorderPainted(false);
            entrarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            entrarButton.addActionListener(e -> abrirTelaVotar(enquete));

            card.add(tituloLabel);
            card.add(statusLabel);
            card.add(duracaoLabel);
            card.add(candidatosLabel);
            card.add(entrarButton);
            pane.add(card, JLayeredPane.PALETTE_LAYER);
        }
    }

    private void atualizarEnquetesComDados(List<Enquete> novasEnquetes) {
    SwingUtilities.invokeLater(() -> {
        if (novasEnquetes != null) {
            this.enquetes = novasEnquetes;
            layeredPane.removeAll();

            BackgroundLabel background = new BackgroundLabel("PUC MINAS");
            background.setBounds(0, 0, 1300, 950);
            layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

            adicionarBarraSuperior(layeredPane);
            adicionarListaEnquetes(layeredPane);

            layeredPane.repaint();
            layeredPane.revalidate();
        }
    });
}
    private void abrirTelaVotar(Enquete enquete) {
        TelaVotar telaVotar = new TelaVotar(enquete);
        telaVotar.setVisible(true);
        this.dispose();
    }
}

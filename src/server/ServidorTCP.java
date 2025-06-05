package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Arrays;
import model.Candidato;
import model.Enquete;

public class ServidorTCP {

    private static final int PORTA = 9871;
    private static Enquete enquete;

    public static void main(String[] args) throws Exception {
        // Cria enquete global com tempo de abertura atual
        LocalDateTime tempoAbertura = LocalDateTime.now();
        System.out.println("Tempo de abertura da enquete: " + tempoAbertura);

        enquete = new Enquete(
                "Votação para o melhor candidato",
                Arrays.asList(
                        new Candidato("Alice"),
                        new Candidato("Bruno"),
                        new Candidato("Carla"),
                        new Candidato("Diego"),
                        new Candidato("Eduarda"),
                        new Candidato("Fernanda"),
                        new Candidato("Gustavo"),
                        new Candidato("Helena"),
                        new Candidato("Igor"),
                        new Candidato("Joana"),
                        new Candidato("Kaique"),
                        new Candidato("Larissa"),
                        new Candidato("Marcos")),
                tempoAbertura,
                "00:00:10",
                true);

        ServidorUDP.iniciar(enquete);

        ServerSocket servidor = new ServerSocket(PORTA);
        System.out.println("\nServidor TCP ouvindo na porta " + PORTA);

        while (true) {
            Socket conexao = servidor.accept();
            System.out.println("\nNova conexão recebida de: " + conexao.getInetAddress().getHostAddress());

            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());

            String comando = entrada.readLine();
            System.out.println("Comando recebido: " + comando);

            if (comando.startsWith("VOTO:")) {
                if (!enquete.isStatus()) {
                    saida.writeBytes("A enquete está fechada. Voto não registrado.\n");
                } else {
                    String nomeCandidato = comando.substring(5).trim();
                    boolean sucesso = false;

                    for (Candidato c : enquete.getCandidatos()) {
                        if (c.getNome().equalsIgnoreCase(nomeCandidato)) {
                            c.incrementarVoto();
                            sucesso = true;
                            System.out.println("Voto registrado para: " + nomeCandidato);
                            System.out.println("Total de votos atual: " + c.getVotos());
                            break;
                        }
                    }

                    if (sucesso) {
                        saida.writeBytes("Voto recebido para " + nomeCandidato + "\n");
                    } else {
                        System.out.println("Candidato não encontrado: " + nomeCandidato);
                        saida.writeBytes("Candidato não encontrado.\n");
                    }
                }
            } else if (comando.equals("INFO")) {
                boolean statusAtual = enquete.isStatus();

                System.out.println("\nEnviando informações da enquete:");
                System.out.println("Título: " + enquete.getTitulo());
                System.out.println("Status: " + (statusAtual ? "Aberta" : "Fechada"));
                System.out.println("Tempo de Abertura: " + enquete.getTempoAbertura());
                System.out.println("Tempo de Duração: " + enquete.getTempoDuracao());
                System.out.println("\nVotos por candidato:");

                saida.writeBytes(enquete.getTitulo() + "\n");
                saida.writeBytes(statusAtual + "\n");
                saida.writeBytes(enquete.getTempoAbertura().toString() + "\n");
                saida.writeBytes(enquete.getTempoDuracao() + "\n");

                for (Candidato c : enquete.getCandidatos()) {
                    String info = c.getNome() + ":" + c.getVotos();
                    System.out.println(info);
                    saida.writeBytes(info + "\n");
                }
                saida.writeBytes("\n");
            }

            conexao.close();
            System.out.println("Conexão encerrada\n");
        }
    }
}

import java.util.Arrays;
import ui.TelaVotar;
import model.Candidato;
import model.Enquete;

public class Main {
    public static void main(String[] args) {
        
        Enquete enquete = new Enquete(
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
                "01/06/2024 14:00",
                "00:14:00");

        TelaVotar frame = new TelaVotar(enquete);

    }
}
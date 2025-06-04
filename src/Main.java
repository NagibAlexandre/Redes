import java.util.Arrays;
import java.util.List;
import ui.TelaVotar;
import model.Candidato;

public class Main {
    public static void main(String[] args) {
        List<Candidato> candidatos = Arrays.asList(
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
                new Candidato("Marcos"));

        TelaVotar frame = new TelaVotar(candidatos);

    }
}
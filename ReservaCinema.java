import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReservaCinema {

    public record Assento(int numero, boolean disponivel) {
        public Assento reservar() {
            return new Assento(numero, false);
        }
    }

    public static List<Assento> reservarAssentos(
            List<Assento> assentos,
            List<Integer> assentosSolicitados
    ) {
        Map<Integer, Assento> assentosPorNumero = assentos.stream()
                .collect(Collectors.toMap(Assento::numero, Function.identity()));

        for (Integer numeroAssento : assentosSolicitados) {
            Assento assento = assentosPorNumero.get(numeroAssento);

            if (assento == null) {
                System.out.println("Erro: assento " + numeroAssento + " nao existe.");
                continue;
            }

            if (!assento.disponivel()) {
                System.out.println("Erro: assento " + numeroAssento + " ja esta reservado.");
                continue;
            }

            assentosPorNumero.put(numeroAssento, assento.reservar());
        }

        return assentos.stream()
                .map(assento -> assentosPorNumero.get(assento.numero()))
                .toList();
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("========== CINEMA ==========");
        System.out.println("1 - Ver assentos");
        System.out.println("2 - Reservar assentos");
        System.out.println("3 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static void exibirAssentos(List<Assento> assentos) {
        System.out.println();
        System.out.println("Tela");
        System.out.println("----------------------------------------");

        for (int i = 0; i < assentos.size(); i++) {
            Assento assento = assentos.get(i);
            String status = assento.disponivel() ? "D" : "R";

            System.out.printf("[%02d-%s] ", assento.numero(), status);

            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("D = disponivel | R = reservado");
    }

    private static List<Integer> lerAssentosSolicitados(Scanner scanner) {
        System.out.println();
        System.out.print("Digite os assentos desejados, separados por espaco ou virgula: ");

        String entrada = scanner.nextLine().trim();
        List<Integer> assentosSolicitados = new ArrayList<>();

        if (entrada.isBlank()) {
            return assentosSolicitados;
        }

        String[] valores = entrada.split("[,\\s]+");

        for (String valor : valores) {
            try {
                assentosSolicitados.add(Integer.parseInt(valor));
            } catch (NumberFormatException erro) {
                System.out.println("Erro: '" + valor + "' nao e um numero de assento valido.");
            }
        }

        return assentosSolicitados;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Assento> assentos = IntStream.rangeClosed(1, 80)
                .mapToObj(numero -> new Assento(numero, true))
                .toList();

        assentos = reservarAssentos(assentos, List.of(5, 12, 27));

        boolean executando = true;

        while (executando) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> exibirAssentos(assentos);
                case "2" -> {
                    List<Integer> assentosSolicitados = lerAssentosSolicitados(scanner);

                    if (assentosSolicitados.isEmpty()) {
                        System.out.println("Nenhum assento informado.");
                        break;
                    }

                    assentos = reservarAssentos(assentos, assentosSolicitados);
                    System.out.println("Reserva processada.");
                }
                case "3" -> {
                    System.out.println("Encerrando sistema de reservas.");
                    executando = false;
                }
                default -> System.out.println("Opcao invalida. Escolha 1, 2 ou 3.");
            }
        }

        scanner.close();
    }
}

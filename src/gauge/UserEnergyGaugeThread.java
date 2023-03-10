package gauge;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class UserEnergyGaugeThread implements Runnable {
    private static String matriculScanner;

    public static void main(String[] args) throws InterruptedException, IOException {
        
        Scanner scanner = new Scanner(System.in);
        // Loop para solicitar a entrada da matrícula do servidor até que ela seja válida
        do {
            System.out.println("Digite o numero de matricula deste Servidor:");
            matriculScanner = scanner.nextLine();
        } while (matriculScanner == null || authenticator(matriculScanner) == false);
        
        boolean on = true;
        CounterUpdater updater = new CounterUpdater();
        Thread updaterThread = new Thread(updater);
        updaterThread.start();

        while (on) {

            System.out.print("Digite o novo valor que deseja(ou n para sair) :");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("n")) {
                // Quando o usuário digitar "n", o programa é encerrado após um minuto
                on = false;
                Thread.sleep(60 * 1000);
                updater.stop();
                updaterThread.join();

            } else {
                try {
                    updater.setEdition(Double.parseDouble(input));
                    System.out.println("O valor do medidor foi alterado para:" + input);

                } catch (NumberFormatException e) {
                    // Caso a entrada não seja um número, o usuário é solicitado a inserir novamente
                    System.out.println("Entrada invalida, Digite novamente:");
                }
            }
        }
        scanner.close();
    }

    // Runnable que envia a leitura do medidor para o servidor em intervalos regulares
    @Override
    public void run() {
        boolean onMed = true;
        CounterUpdater updater = new CounterUpdater();
        while (onMed) {

            try {
                Thread.sleep(60000); // Espera 1 minuto
                double gaugeValue = updater.getGauge();
                SendReceiveMed sendReceive = new SendReceiveMed();
                // Constrói uma string com os dados da medição
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String dataHora = now.format(formatter);
                String medicao = matriculScanner + "," + gaugeValue + "," + dataHora;
                sendReceive.sendMessage(medicao);
                updater.setGauge(0); // Reinicia o contador do medidor

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Verifica se a matrícula é válida e se o servidor pode ser autenticado
    public static boolean authenticator(String id) throws IOException {
        SendReceiveMed sendReceive = new SendReceiveMed();
        sendReceive.sendMessage(id);
        String resposta = sendReceive.receiveMessage();
        if (resposta.equals("ok")) {
            return true;
        } else {
            return false;
        }
    }
}

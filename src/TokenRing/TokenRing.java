package TokenRing;
 
import java.io.*;
import java.util.ArrayList;
import java.util.logging.*;
 
public class TokenRing {
 
    public static void main(String[] args) throws IOException {
        String ip_port;
        int port;
        int t_token = 0;
        boolean token = false;
        String nickname;
 
        /* Le arquivo de configura��o. */
        try ( BufferedReader inputFile = new BufferedReader(new FileReader("ring.txt"))) {
             
            /* L� IP e Porta */
            ip_port = inputFile.readLine();
            String aux[] = ip_port.split(":");
            port = Integer.parseInt(aux[1]);
 
            /* L� apelido */
            nickname = inputFile.readLine();
                 
            /* L� tempo de espera com o token. Usado para fins de depura��o. Em caso de 
            execu��o normal use valor 0. */
            t_token = Integer.parseInt(inputFile.readLine());
             
            /* L� se a esta��o possui o token inicial. */
            token = Boolean.parseBoolean(inputFile.readLine());
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TokenRing.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
         
        /* Cria uma fila de mensagens. */
        MessageQueue queue = new MessageQueue();
         
        MessageController controller = new MessageController(queue, ip_port, t_token, token, nickname);
        Thread thr_controller = new Thread(controller);
        Thread thr_receiver = new Thread(new MessageReceiver(queue, port, controller));
         
        thr_controller.start();
        thr_receiver.start();
         
        /* Neste ponto, a thread principal deve ficar aguarando o usu�rio entrar com o destinat�rio
         * e a mensagem a ser enviada. Destinat�rio e mensagem devem ser adicionados na fila de mensagens pendentes.
         * MessageQueue()
         *
         */
         
    }
     
}
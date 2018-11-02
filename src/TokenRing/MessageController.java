package TokenRing;
 
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;
 
public class MessageController implements Runnable {
 
    private MessageQueue queue;
    /*Tabela de roteamento */
    private InetAddress IPAddress;
    private int port;
    private Semaphore WaitForMessage;
    private String nickname;
    private int time_token;
    private Boolean token;
 
    public MessageController(MessageQueue q,
            String ip_port,
            int t_token,
            Boolean t,
            String n) throws UnknownHostException {
 
        queue = q;
        String aux[] = ip_port.split(":");
        IPAddress = InetAddress.getByName(aux[0]);
        port = Integer.parseInt(aux[1]);
        time_token = t_token;
        token = t;
        nickname = n;
        WaitForMessage = new Semaphore(0);
 
    }
 
    /**
     * ReceiveMessage() Nesta função, vc deve decidir o que fazer com a mensagem
     * recebida do vizinho da esquerda: Se for um token, é a sua chance de
     * enviar uma mensagem de sua fila (queue); Se for uma mensagem de dados e
     * se for para esta estação, apenas a exiba no console, senão, envie para
     * seu vizinho da direita; Se for um ACK e se for para você, sua mensagem
     * foi enviada com sucesso, passe o token para o vizinho da direita, senão,
     * repasse o ACK para o seu vizinho da direita.
     */
    public String ReceivedMessage(String msg) {
        String[] array = msg.split(";");
 
        switch (array[0].trim()) {
            case "1234":
                if (queue.getSize() == 0) {
                    return msg;
                } else {
                    try {
                        WaitForMessage.acquire();
                        return queue.RemoveMessage();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
       /*     case "4067":
                String aux = array[1].trim();
                if (aux.equalsIgnoreCase(nickname)) {
                    WaitForMessage.release();
                    return "4060";
                } else {
                    try {
                        WaitForMessage.acquire();
                        return queue.RemoveMessage();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
 
                    return msg;
                }*/
            case "2345":
                String aux2 = new String(msg).trim();
 
                String[] array2 = aux2.split(";");
                String[] restoArray = array[1].split(":");
 
                // Não copiado
                String remetente = restoArray[0];
                String destino = restoArray[1];
                // Tipo Aquivo
                String mensagem = restoArray[2];
 
                if (destino.equalsIgnoreCase(nickname)) {
                    System.out.println("Message from " + remetente + ": " + mensagem);
                    try {
                        WaitForMessage.acquire();
                        return queue.RemoveMessage();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
 
                    return "4067;" + remetente;
                } else {
                    WaitForMessage.release();
                    return msg;
                }
        }
 
        return null;
    }
 
    @Override
    public void run() {
        DatagramSocket clientSocket = null;
        byte[] sendData;
 
        /* Cria socket para envio de mensagem */
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
 
        while (true) {
 
            /* Neste exemplo, considera-se que a estação sempre recebe o token 
               e o repassa para a próxima estação. */
            try {
                /* Espera time_token segundos para o envio do token. Isso é apenas para depuração,
                   durante execução real faça time_token = 0,*/
                Thread.sleep(time_token * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
            }
 
            /* Converte string para array de bytes para envio pelo socket. */
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.next().toString();
            sendData = msg.getBytes();
 
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
 
            /* Realiza envio da mensagem. */
            try {
                clientSocket.send(sendPacket);
            } catch (IOException ex) {
                Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
            }
 
            /* A estação fica aguardando a ação gerada pela função ReceivedMessage(). */
            try {
                WaitForMessage.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
package TokenRing;
 
import java.net.InetAddress;
import java.util.ArrayList;
import javax.print.DocFlavor;
 
/* Esta classe deve implementar uma fila de mensagens. Observe que esta fila será
 * acessada por um consumidor (MessageSender) e um produtor (Classe principal, TokenRing).
 * Portanto, implemente controle de acesso (sincronização), para acesso a fila. 
 */
 
public class MessageQueue {
    /*Implemente uma estrutura de dados para manter uma lista de mensagens em formato string. 
     * Você pode, por exemplo, usar um ArrayList(). 
     * Não se esqueça que em uma fila, o primeiro elemente a entrar será o primeiro
     * a ser removido.
    */
    ArrayList<String> queue = new ArrayList<>();
     
    public MessageQueue(){
     
    }
     
     
    public void AddMessage(String Message){
        /* Adicione a mensagem no final da fila. Não se esqueça de garantir que apenas uma thread faça isso 
        por vez. */
        queue.add(Message);   
    }
     
    public String RemoveMessage(){
        if(queue.size()>0){
            String aux = queue.get(0);
            return aux;
        } else{
            return null;
        } 
    }
     
    public int getSize(){
        return queue.size();
    }
}
package TokenRing;
 
import java.net.InetAddress;
import java.util.ArrayList;
import javax.print.DocFlavor;
 
/* Esta classe deve implementar uma fila de mensagens. Observe que esta fila ser�
 * acessada por um consumidor (MessageSender) e um produtor (Classe principal, TokenRing).
 * Portanto, implemente controle de acesso (sincroniza��o), para acesso a fila. 
 */
 
public class MessageQueue {
    /*Implemente uma estrutura de dados para manter uma lista de mensagens em formato string. 
     * Voc� pode, por exemplo, usar um ArrayList(). 
     * N�o se esque�a que em uma fila, o primeiro elemente a entrar ser� o primeiro
     * a ser removido.
    */
    ArrayList<String> queue = new ArrayList<>();
     
    public MessageQueue(){
     
    }
     
     
    public void AddMessage(String Message){
        /* Adicione a mensagem no final da fila. N�o se esque�a de garantir que apenas uma thread fa�a isso 
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
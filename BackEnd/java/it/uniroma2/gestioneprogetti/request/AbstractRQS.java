package it.uniroma2.gestioneprogetti.request;
/**
 * La classe AbstractRQS serve a mettere a fattor comunue la proprietà
 * String message per tutte le classi RQS.
 * @author Gruppo Talocci
 */
public abstract class AbstractRQS {
    
    private String message;
   
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

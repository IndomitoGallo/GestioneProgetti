package it.uniroma2.gestioneprogetti.response;
/**
 * La classe AbstractRES serve a mettere a fattor comunue la proprietà
 * String errorCode, String message ed boolean esito per tutte le classi RES.
 * @author Gruppo Talocci
 */
public abstract class AbstractRES {

    private String errorCode;
    private String message;
    private boolean esito;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEsito() {
        return esito;
    }

    public void setEsito(boolean esito) {
        this.esito = esito;
    }
}

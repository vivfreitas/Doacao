package org.com.programming.animal.globalExceptions;

/* Uma classes com excecao customizada para a plicação que estende de RuntimeExpetion */
public class ExceptionCustomized extends RuntimeException{
    private String code;
    private String message;

    public ExceptionCustomized(String code, String message){
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

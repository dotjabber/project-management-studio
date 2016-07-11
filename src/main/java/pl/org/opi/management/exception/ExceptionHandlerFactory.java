package pl.org.opi.management.exception;

/**
 * @author mkowalski@opi.org.pl
 */
public class ExceptionHandlerFactory extends javax.faces.context.ExceptionHandlerFactory {
    private final javax.faces.context.ExceptionHandlerFactory parent;

    public ExceptionHandlerFactory(javax.faces.context.ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public javax.faces.context.ExceptionHandler getExceptionHandler() {
        return new ExceptionHandler(parent.getExceptionHandler());
    }
}

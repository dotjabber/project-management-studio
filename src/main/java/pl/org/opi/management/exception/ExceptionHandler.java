package pl.org.opi.management.exception;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import pl.org.opi.management.exception.utils.ExceptionHandlerUtils;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author mkowalski@opi.org.pl
 */
public class ExceptionHandler extends ExceptionHandlerWrapper {
    private static final Logger LOG = Logger.getGlobal();

    private static final String EXCEPTION_PAGE = "/pages/error/503";
    private static final String EXPIRATION_PAGE = "/pages/error/401";

    private javax.faces.context.ExceptionHandler wrapped;

    public ExceptionHandler(javax.faces.context.ExceptionHandler exception) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();

        JavaMailSenderImpl mailSender = application.evaluateExpressionGet(
                facesContext,
                "#{mailSender}",
                JavaMailSenderImpl.class
        );

        String devTeam = application.evaluateExpressionGet(
                facesContext,
                "#{mailTeam}",
                String.class
        );

        this.wrapped = exception;
    }

    @Override
    public javax.faces.context.ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        for (ExceptionQueuedEvent event : getUnhandledExceptionQueuedEvents()) {
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable throwable = context.getException();
            throwable.printStackTrace();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

            String redirectPage = ExceptionHandlerUtils.getContextPath(request);

            try {
                if (throwable instanceof ViewExpiredException) {
                    redirectPage = redirectPage + EXPIRATION_PAGE;

                } else {
                    redirectPage = redirectPage + EXCEPTION_PAGE;
                }

                facesContext.getExternalContext().redirect(redirectPage);

            } catch (IOException e) {
                LOG.severe(e.getMessage());
            }

            LOG.severe("pojawił się błąd: " + throwable.getMessage());
        }

        getWrapped().handle();
    }
}

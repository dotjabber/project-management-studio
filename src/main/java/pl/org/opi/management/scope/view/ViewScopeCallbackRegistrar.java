package pl.org.opi.management.scope.view;

import javax.faces.component.UIViewRoot;
import javax.faces.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mkowalski@opi.org.pl
 */
public class ViewScopeCallbackRegistrar implements ViewMapListener {

    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if(event instanceof PostConstructViewMapEvent) {
            PostConstructViewMapEvent viewMapEvent = (PostConstructViewMapEvent)event;

            UIViewRoot viewRoot = (UIViewRoot) viewMapEvent.getComponent();
            viewRoot.getViewMap().put(ViewScope.VIEW_SCOPE_CALLBACKS, new HashMap<>());

        } else if(event instanceof PreDestroyViewMapEvent) {
            PreDestroyViewMapEvent viewMapEvent = (PreDestroyViewMapEvent)event;

            UIViewRoot viewRoot = (UIViewRoot)viewMapEvent.getComponent();
            Map<String, Runnable> callbacks = (Map<String, Runnable>) viewRoot.getViewMap().get(ViewScope.VIEW_SCOPE_CALLBACKS);

            if(callbacks != null) {
                callbacks.values().forEach(Runnable::run);
                callbacks.clear();
            }
        }
    }

    public boolean isListenerForSource(Object source) {
        return source instanceof UIViewRoot;
    }

}
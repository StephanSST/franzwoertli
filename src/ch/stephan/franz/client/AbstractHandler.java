package ch.stephan.franz.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;

public abstract class AbstractHandler implements ClickHandler, KeyUpHandler {

    /**
     * Fired when the user clicks on the button.
     */
    public void onClick(ClickEvent event) {
      execute();
    }

    /**
     * Fired when the user presses the 'enter' key.
     */
    public void onKeyUp(KeyUpEvent event) {
      if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
        execute();
      }
    }

    /**
     * executes the request to the server and wait for a response.
     */
    protected abstract void execute();

}

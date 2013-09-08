package ch.stephan.franz.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ErrorToShowEventHandler extends EventHandler {
  void onErrorToShow(ErrorToShowEvent event);
}

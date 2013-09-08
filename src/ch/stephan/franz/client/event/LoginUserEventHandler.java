package ch.stephan.franz.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginUserEventHandler extends EventHandler {
  void onLoginUser(LoginUserEvent event);
}

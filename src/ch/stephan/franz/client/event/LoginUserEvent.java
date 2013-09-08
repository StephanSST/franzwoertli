package ch.stephan.franz.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoginUserEvent extends GwtEvent<LoginUserEventHandler> {
  public static Type<LoginUserEventHandler> TYPE = new Type<LoginUserEventHandler>();

  @Override
  public Type<LoginUserEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(LoginUserEventHandler handler) {
    handler.onLoginUser(this);
  }
}

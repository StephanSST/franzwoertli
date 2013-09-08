package ch.stephan.franz.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ErrorToShowEvent extends GwtEvent<ErrorToShowEventHandler> {
  public static Type<ErrorToShowEventHandler> TYPE = new Type<ErrorToShowEventHandler>();
  private final String errorMessage;
  private final Throwable cause;

  public ErrorToShowEvent(String errorMessage, Throwable cause) {
    this.errorMessage = errorMessage;
    this.cause = cause;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public Throwable getCause() {
    return cause;
  }

  @Override
  public Type<ErrorToShowEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ErrorToShowEventHandler handler) {
    handler.onErrorToShow(this);
  }
}

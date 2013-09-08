package ch.stephan.franz.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class NextWoertliEvent extends GwtEvent<NextWoertliEventHandler> {
  public static Type<NextWoertliEventHandler> TYPE = new Type<NextWoertliEventHandler>();

  @Override
  public Type<NextWoertliEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(NextWoertliEventHandler handler) {
    handler.onNextWoertli(this);
  }
}

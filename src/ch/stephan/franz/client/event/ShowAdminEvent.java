package ch.stephan.franz.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowAdminEvent extends GwtEvent<ShowAdminEventHandler> {
  public static Type<ShowAdminEventHandler> TYPE = new Type<ShowAdminEventHandler>();

  @Override
  public Type<ShowAdminEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowAdminEventHandler handler) {
    handler.onShowAdmin(this);
  }
}

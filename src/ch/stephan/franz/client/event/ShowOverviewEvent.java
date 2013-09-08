package ch.stephan.franz.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowOverviewEvent extends GwtEvent<ShowOverviewEventHandler>{
  public static Type<ShowOverviewEventHandler> TYPE = new Type<ShowOverviewEventHandler>();

  @Override
  public Type<ShowOverviewEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowOverviewEventHandler handler) {
    handler.onShowOverview(this);
  }
}

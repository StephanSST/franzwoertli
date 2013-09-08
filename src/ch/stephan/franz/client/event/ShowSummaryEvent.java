package ch.stephan.franz.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowSummaryEvent extends GwtEvent<ShowSummaryEventHandler> {
  public static Type<ShowSummaryEventHandler> TYPE = new Type<ShowSummaryEventHandler>();

  private final int correctCount;

  public ShowSummaryEvent(int aCorrectCount) {
    correctCount = aCorrectCount;
  }

  public int getCorrectCount() {
    return correctCount;
  }

  @Override
  public Type<ShowSummaryEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowSummaryEventHandler handler) {
    handler.onShowSummary(this);
  }
}

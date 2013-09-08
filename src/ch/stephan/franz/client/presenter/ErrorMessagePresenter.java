package ch.stephan.franz.client.presenter;

import ch.stephan.franz.client.FranzWoertliServiceAsync;
import ch.stephan.franz.client.event.LoginUserEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class ErrorMessagePresenter implements Presenter {

  public interface Display {
    HasClickHandlers getCloseClickable();

    HasText getErrorStackTrace();

    HasHTML getBoxTitle();

    Widget asWidget();
  }

  // private final FranzWoertliServiceAsync rpcService;
  private final HandlerManager eventBus;
  private final Display display;
  private final String title;
  private final Throwable cause;

  public ErrorMessagePresenter(FranzWoertliServiceAsync aRpcService, HandlerManager aEventBus, Display aDisplay, String aTitle, Throwable aCause) {
    // rpcService = aRpcService;
    eventBus = aEventBus;
    display = aDisplay;
    title = aTitle;
    cause = aCause;
    bind();

    goGetSummaryData();
  }

  public void bind() {
    display.getCloseClickable().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        eventBus.fireEvent(new LoginUserEvent());
      }
    });
  }

  @Override
  public void go(final HasWidgets container) {
    container.clear();
    container.add(display.asWidget());
  }

  private void goGetSummaryData() {
    display.getBoxTitle().setText(title);
    display.getErrorStackTrace().setText(toString(cause));
  }

  private String toString(Throwable throwable) {
    StringBuilder builder = new StringBuilder();
    for (StackTraceElement ste : throwable.getStackTrace()) {
      builder.append(ste.toString()).append("\n");
    }
    return builder.toString();
  }

}

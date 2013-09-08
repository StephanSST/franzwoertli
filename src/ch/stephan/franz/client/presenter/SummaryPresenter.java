package ch.stephan.franz.client.presenter;

import ch.stephan.franz.client.FranzWoertliServiceAsync;
import ch.stephan.franz.client.event.ShowOverviewEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class SummaryPresenter implements Presenter {

  public interface Display {
    HasClickHandlers getOverviewClickable();

    Focusable getOverviewFocus();

    HasKeyPressHandlers getOverviewEnterPressed();

    HasText getSummaryComment();

    Widget asWidget();
  }

  // private final FranzWoertliServiceAsync rpcService;
  private final HandlerManager eventBus;
  private final Display display;
  private final int correctCount;

  public SummaryPresenter(FranzWoertliServiceAsync aRpcService, HandlerManager aEventBus, Display aDisplay, int aCorrectCount) {
    // rpcService = aRpcService;
    eventBus = aEventBus;
    display = aDisplay;
    correctCount = aCorrectCount;
    bind();

    showSummaryData();
  }

  public void bind() {
    display.getOverviewClickable().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doShowOverview();
      }
    });
    display.getOverviewEnterPressed().addKeyPressHandler(new KeyPressHandler() {
      @Override
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
          doShowOverview();
        }
      }
    });
  }

  @Override
  public void go(final HasWidgets container) {
    container.clear();
    container.add(display.asWidget());
  }

  private void showSummaryData() {
    String summaryComment = null;
    switch (correctCount) {
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
      summaryComment = "Das kannst Du besser! Nur " + correctCount + " von 10 Woertli richtig.";
      break;
    case 5:
    case 6:
    case 7:
      summaryComment = "Gut, " + correctCount + " von 10 Woertli richtig.";
      break;
    case 8:
    case 9:
      summaryComment = "Sehr gut, " + correctCount + " von 10 Woertli richtig!";
      break;
    case 10:
      summaryComment = "Super! Alle " + correctCount + " Woertli richtig!!!";
      break;
    default:
      summaryComment = "" + correctCount + " von 10 Woertli richtig.";
      break;
    }
    display.getSummaryComment().setText(summaryComment);
    display.getOverviewFocus().setFocus(true);
  }

  private void doShowOverview() {
    eventBus.fireEvent(new ShowOverviewEvent());
  }
}

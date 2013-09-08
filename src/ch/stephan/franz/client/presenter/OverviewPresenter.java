package ch.stephan.franz.client.presenter;

import ch.stephan.franz.client.FranzWoertliServiceAsync;
import ch.stephan.franz.client.event.ErrorToShowEvent;
import ch.stephan.franz.client.event.NextWoertliEvent;
import ch.stephan.franz.shared.UserStatsCO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class OverviewPresenter implements Presenter {

  public interface Display {
    HasClickHandlers getNext10Button();

    HasKeyPressHandlers getNext10EnterPressed();

    Focusable getNext10Focus();

    HasValue<String> getNewWords();

    HasValue<String> getOnlyWrong();

    HasValue<String> getOneRight();

    HasValue<String> getTwoRight();

    HasValue<String> getThreeRight();

    HasValue<String> getFourRight();

    HasValue<String> getFiveRight();

    HasText getUserName();

    HasValue<String> getUnites();

    Widget asWidget();
  }

  private final FranzWoertliServiceAsync rpcService;
  private final HandlerManager eventBus;
  private final Display display;

  public OverviewPresenter(FranzWoertliServiceAsync rpcService, HandlerManager eventBus, Display display) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.display = display;
    bind();

    initOverview();
  }

  public void bind() {
    display.getNext10Button().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doLoadNext10();
      }
    });
    display.getNext10EnterPressed().addKeyPressHandler(new KeyPressHandler() {
      @Override
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
          doLoadNext10();
        }
      }
    });
  }

  @Override
  public void go(final HasWidgets container) {
    container.clear();
    container.add(display.asWidget());
  }

  private void initOverview() {
    rpcService.loadUserStat(new AsyncCallback<UserStatsCO>() {
      @Override
      public void onSuccess(UserStatsCO result) {
        display.getNewWords().setValue(result.getNewWords());
        display.getOnlyWrong().setValue(result.getOneWrong());
        display.getOneRight().setValue(result.getOneRight());
        display.getTwoRight().setValue(result.getTwoRight());
        display.getThreeRight().setValue(result.getThreeRight());
        display.getFourRight().setValue(result.getFourRight());
        display.getFiveRight().setValue(result.getFiveRight());
        display.getUserName().setText(result.getUserName());
        display.getUnites().setValue(result.getUnites());
        display.getNext10Focus().setFocus(true);
      }

      @Override
      public void onFailure(Throwable caught) {
        eventBus.fireEvent(new ErrorToShowEvent("Keine Daten für den Benutzer gefunden.", caught));

      }
    });
  }

  private void doLoadNext10() {
    eventBus.fireEvent(new NextWoertliEvent());
  }
}

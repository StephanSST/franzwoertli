package ch.stephan.franz.client.presenter;

import ch.stephan.franz.client.FranzWoertliServiceAsync;
import ch.stephan.franz.client.event.ErrorToShowEvent;
import ch.stephan.franz.client.event.ShowSummaryEvent;
import ch.stephan.franz.shared.WoertliCO;

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
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class WoertliCheckPresenter implements Presenter {

  public interface Display {
    HasKeyPressHandlers getFranzEnterPressed();

    HasKeyPressHandlers getNextButtonEnterPressed();

    HasClickHandlers getCheckButtonClickable();

    HasClickHandlers getNextButtonClickable();

    HasClickHandlers getMaennlichClickable();

    HasClickHandlers getWeiblichClickable();

    HasValue<String> getFranz();

    HasText getGerman();

    HasText getCorrectSolution();

    HasText getRound();

    HasVisibility getCheckButtonVisible();

    HasVisibility getNextButtonVisible();

    HasVisibility getCorrectSolutionVisible();

    HasVisibility getRichtigVisible();

    HasVisibility getFalschVisible();

    HasVisibility getMaennlichVisible();

    HasVisibility getWeiblichVisible();

    Focusable getFranzFocus();

    Focusable getNextButtonFocus();

    HasValue<Boolean> getMaennlichSelected();

    HasValue<Boolean> getWeiblichSelected();

    HasEnabled getCheckButtonHasEnabled();

    HasEnabled getFranzHasEnabled();

    Widget asWidget();
  }

  private final FranzWoertliServiceAsync rpcService;
  private final HandlerManager eventBus;
  private final Display display;
  private Long woertliId;
  private int round = 0;
  private int correctCount = 0;

  public WoertliCheckPresenter(FranzWoertliServiceAsync rpcService, HandlerManager eventBus, Display display) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.display = display;
    bind();

    goGetNextWoertli();
  }

  public void bind() {
    display.getCheckButtonClickable().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doCheckWoertli();
      }
    });
    display.getNextButtonClickable().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doNextWoertli();
      }
    });
    display.getMaennlichClickable().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        display.getCheckButtonHasEnabled().setEnabled(true);
      }
    });
    display.getWeiblichClickable().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        display.getCheckButtonHasEnabled().setEnabled(true);
      }
    });
    display.getFranzEnterPressed().addKeyPressHandler(new KeyPressHandler() {
      @Override
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
          doCheckWoertli();
        }
      }
    });
    display.getNextButtonEnterPressed().addKeyPressHandler(new KeyPressHandler() {
      @Override
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
          doNextWoertli();
        }
      }
    });
  }

  @Override
  public void go(final HasWidgets container) {
    container.clear();
    container.add(display.asWidget());
  }

  private void goGetNextWoertli() {
    round++;
    rpcService.loadNextWoertli(new AsyncCallback<WoertliCO>() {

      @Override
      public void onSuccess(WoertliCO result) {
        woertliId = result.getWoertliId();
        display.getGerman().setText(result.getGermanText());
        display.getFranz().setValue("");
        display.getCheckButtonVisible().setVisible(true);
        display.getRound().setText(round + "/10");
        display.getMaennlichSelected().setValue(false);
        display.getWeiblichSelected().setValue(false);

        display.getRichtigVisible().setVisible(false);
        display.getFalschVisible().setVisible(false);
        display.getCorrectSolutionVisible().setVisible(false);
        display.getNextButtonVisible().setVisible(false);
        showMaennlichWeiblich(result.getGeschlecht() != null);
        display.getFranzHasEnabled().setEnabled(true);
        display.getFranzFocus().setFocus(true);
      }

      @Override
      public void onFailure(Throwable caught) {
        eventBus.fireEvent(new ErrorToShowEvent("Fehler beim Laden vom nächsten Woertli", caught));
      }
    });
  }

  private void showMaennlichWeiblich(boolean aVisibleFlag) {
    display.getMaennlichVisible().setVisible(aVisibleFlag);
    display.getWeiblichVisible().setVisible(aVisibleFlag);
    if (aVisibleFlag) {
      display.getCheckButtonHasEnabled().setEnabled(false);
    }
  }

  private Boolean getGeschlechtBoolean() {
    Boolean maennlich = display.getMaennlichSelected().getValue();
    Boolean weiblich = display.getWeiblichSelected().getValue();
    Boolean result = null;
    if ((maennlich != null) && (maennlich.booleanValue())) {
      result = Boolean.TRUE;
    } else if ((weiblich != null) && (weiblich.booleanValue())) {
      result = Boolean.FALSE;
    }
    return result;
  }

  private void doCheckWoertli() {
    String guess = display.getFranz().getValue();
    rpcService.checkInput(woertliId, guess, getGeschlechtBoolean(), new AsyncCallback<String>() {
      @Override
      public void onSuccess(String correction) {
        display.getCheckButtonVisible().setVisible(false);
        display.getNextButtonVisible().setVisible(true);
        display.getFranzHasEnabled().setEnabled(false);
        display.getNextButtonFocus().setFocus(true);
        if (correction != null) {
          display.getFalschVisible().setVisible(true);
          display.getCorrectSolution().setText(correction);
          display.getCorrectSolutionVisible().setVisible(true);
        } else {
          display.getRichtigVisible().setVisible(true);
          correctCount++;
        }

      }

      @Override
      public void onFailure(Throwable caught) {
        eventBus.fireEvent(new ErrorToShowEvent("Fehler beim Prüfen vom Woertli", caught));
      }
    });
  }

  private void doNextWoertli() {
    if (round >= 10) {
      round = 0;
      eventBus.fireEvent(new ShowSummaryEvent(correctCount));
      correctCount = 0;
    } else {
      goGetNextWoertli();
    }
  }

}

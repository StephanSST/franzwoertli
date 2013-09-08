package ch.stephan.franz.client.presenter;

import ch.stephan.franz.client.FranzWoertliServiceAsync;
import ch.stephan.franz.client.event.ErrorToShowEvent;
import ch.stephan.franz.client.event.LoginUserEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class WoertliAdminPresenter implements Presenter {

  public interface Display {
    HasClickHandlers getUploadButton();

    HasClickHandlers getDeleteButton();

    HasClickHandlers getBackButton();

    HasValue<String> getInputArea();

    HasValue<String> getUniteToDelete();

    Widget asWidget();
  }

  private final FranzWoertliServiceAsync rpcService;
  private final HandlerManager eventBus;
  private final Display display;

  public WoertliAdminPresenter(FranzWoertliServiceAsync rpcService, HandlerManager eventBus, Display display) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.display = display;
    bind();
  }

  public void bind() {
    display.getUploadButton().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doUploadWoertli();
      }
    });

    display.getDeleteButton().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doDeleteWoertli();
      }
    });

    display.getBackButton().addClickHandler(new ClickHandler() {
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

  private void doUploadWoertli() {
    String jsonStream = display.getInputArea().getValue();

    rpcService.saveAllWoertli(jsonStream, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        eventBus.fireEvent(new ErrorToShowEvent("Fehler beim Speichern der Woertli", caught));
      }

      @Override
      public void onSuccess(Void result) {
        display.getInputArea().setValue("");
      }
    });
  }

  private void doDeleteWoertli() {
    final String uniteToDelete = display.getUniteToDelete().getValue();
    rpcService.deleteWoertli(Integer.valueOf(uniteToDelete), new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        eventBus.fireEvent(new ErrorToShowEvent("Fehler beim Löschen der Woertli für Unité " + uniteToDelete, caught));
      }

      @Override
      public void onSuccess(Void result) {
        display.getUniteToDelete().setValue("");
      }
    });
  }

}

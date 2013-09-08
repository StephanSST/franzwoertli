package ch.stephan.franz.client.presenter;

import ch.stephan.franz.client.FranzWoertliServiceAsync;
import ch.stephan.franz.client.event.ErrorToShowEvent;
import ch.stephan.franz.client.event.ShowOverviewEvent;
import ch.stephan.franz.shared.FieldVerifier;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class LoginPresenter implements Presenter {

  public interface Display {
    HasClickHandlers getLoginClickable();

    HasClickHandlers getNewUserClickable();

    HasClickHandlers getForgotPasswordClickable();

    HasValue<String> getUserName();

    HasValue<String> getPassword();

    HasValue<String> getEmail();

    HasVisibility getEmailVisible();

    HasVisibility getEmailLabelVisible();

    Widget asWidget();
  }

  private final FranzWoertliServiceAsync rpcService;
  private final HandlerManager eventBus;
  private final Display display;

  public LoginPresenter(FranzWoertliServiceAsync rpcService, HandlerManager eventBus, Display display) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.display = display;
    bind();
  }

  public void bind() {
    display.getEmailLabelVisible().setVisible(false);
    display.getEmailVisible().setVisible(false);

    display.getLoginClickable().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doLogin();
      }
    });
    display.getNewUserClickable().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        String email = display.getEmail().getValue();
        if ((email != null) && (email.length() > 0)) {
          addNewUser();
        } else {
          display.getEmailLabelVisible().setVisible(true);
          display.getEmailVisible().setVisible(true);
        }
      }
    });
    display.getForgotPasswordClickable().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        sendPassword();
      }
    });
  }

  @Override
  public void go(final HasWidgets container) {
    container.clear();
    container.add(display.asWidget());
  }

  private void doLogin() {
    String userName = display.getUserName().getValue();
    String password = display.getPassword().getValue();

    rpcService.loginUser(userName, password, new AsyncCallback<Void>() {
      @Override
      public void onSuccess(Void result) {
        eventBus.fireEvent(new ShowOverviewEvent());
      }

      @Override
      public void onFailure(Throwable caught) {
        eventBus.fireEvent(new ErrorToShowEvent("Benutzer nicht gefunden oder Passwort falsch!", caught));
      }
    });
  }

  private void sendPassword() {
    String userName = display.getUserName().getValue();
    rpcService.sendPassword(userName, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        eventBus.fireEvent(new ErrorToShowEvent("Fehler beim Versenden vom Passwort.", caught));
      }

      @Override
      public void onSuccess(Void result) {
        Window.alert("Das Passwort wurde an die registrierte EMail-Adresse verschickt.");
        eventBus.fireEvent(new ShowOverviewEvent());
      }
    });
  }

  private void addNewUser() {
    String userName = display.getUserName().getValue();
    String password = display.getPassword().getValue();
    String email = display.getEmail().getValue();
    if (!FieldVerifier.isValidName(userName)) {
      eventBus.fireEvent(new ErrorToShowEvent("Der Benutzername ist zu kurz, muss mind. vier Buchstaben lang sein.", null));
      return;
    }

    rpcService.saveUser(userName, password, email, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        eventBus.fireEvent(new ErrorToShowEvent(caught.getMessage(), caught));
      }

      @Override
      public void onSuccess(Void result) {
        eventBus.fireEvent(new ShowOverviewEvent());
      }
    });
  }
}

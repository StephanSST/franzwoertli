package ch.stephan.franz.client;

import ch.stephan.franz.client.event.ErrorToShowEvent;
import ch.stephan.franz.client.event.ErrorToShowEventHandler;
import ch.stephan.franz.client.event.LoginUserEvent;
import ch.stephan.franz.client.event.LoginUserEventHandler;
import ch.stephan.franz.client.event.NextWoertliEvent;
import ch.stephan.franz.client.event.NextWoertliEventHandler;
import ch.stephan.franz.client.event.ShowAdminEvent;
import ch.stephan.franz.client.event.ShowAdminEventHandler;
import ch.stephan.franz.client.event.ShowOverviewEvent;
import ch.stephan.franz.client.event.ShowOverviewEventHandler;
import ch.stephan.franz.client.event.ShowSummaryEvent;
import ch.stephan.franz.client.event.ShowSummaryEventHandler;
import ch.stephan.franz.client.presenter.ErrorMessagePresenter;
import ch.stephan.franz.client.presenter.LoginPresenter;
import ch.stephan.franz.client.presenter.OverviewPresenter;
import ch.stephan.franz.client.presenter.Presenter;
import ch.stephan.franz.client.presenter.SummaryPresenter;
import ch.stephan.franz.client.presenter.WoertliAdminPresenter;
import ch.stephan.franz.client.presenter.WoertliCheckPresenter;
import ch.stephan.franz.client.view.ErrorMessageBox;
import ch.stephan.franz.client.view.LoginView;
import ch.stephan.franz.client.view.OverviewView;
import ch.stephan.franz.client.view.SummaryView;
import ch.stephan.franz.client.view.WoertliAdminView;
import ch.stephan.franz.client.view.WoertliCheckView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {
  private final HandlerManager eventBus;
  private final FranzWoertliServiceAsync rpcService;
  private HasWidgets container;

  public AppController(FranzWoertliServiceAsync rpcService, HandlerManager eventBus) {
    this.eventBus = eventBus;
    this.rpcService = rpcService;
    bind();
  }

  @Override
  public void go(final HasWidgets container) {
    this.container = container;

    if ("".equals(History.getToken())) {
      History.newItem("login");
    } else {
      History.fireCurrentHistoryState();
    }
  }

  @Override
  public void onValueChange(ValueChangeEvent<String> event) {
    if ("login".equals(event.getValue())) {
      GWT.runAsync(new RunAsyncCallback() {
        @Override
        public void onFailure(Throwable caught) {
        }

        @Override
        public void onSuccess() {
          doLoginUser();
        }
      });
    } else if ("admin".equals(event.getValue())) {
      GWT.runAsync(new RunAsyncCallback() {
        @Override
        public void onFailure(Throwable caught) {
        }

        @Override
        public void onSuccess() {
          doShowAdmin();
        }
      });
    }
  }

  private void bind() {
    History.addValueChangeHandler(this);

    eventBus.addHandler(LoginUserEvent.TYPE, new LoginUserEventHandler() {
      @Override
      public void onLoginUser(LoginUserEvent event) {
        doLoginUser();
      }
    });

    eventBus.addHandler(ShowAdminEvent.TYPE, new ShowAdminEventHandler() {
      @Override
      public void onShowAdmin(ShowAdminEvent event) {
        doShowAdmin();
      }
    });

    eventBus.addHandler(NextWoertliEvent.TYPE, new NextWoertliEventHandler() {
      @Override
      public void onNextWoertli(NextWoertliEvent event) {
        doNextWoertli();
      }
    });

    eventBus.addHandler(ShowOverviewEvent.TYPE, new ShowOverviewEventHandler() {
      @Override
      public void onShowOverview(ShowOverviewEvent event) {
        doShowOverview();
      }
    });

    eventBus.addHandler(ShowSummaryEvent.TYPE, new ShowSummaryEventHandler() {
      @Override
      public void onShowSummary(ShowSummaryEvent event) {
        doShowSummary(event.getCorrectCount());
      }
    });

    eventBus.addHandler(ErrorToShowEvent.TYPE, new ErrorToShowEventHandler() {
      @Override
      public void onErrorToShow(ErrorToShowEvent event) {
        doShowErrorMessage(event.getErrorMessage(), event.getCause());
      }
    });

  }

  private void doLoginUser() {
    History.newItem("login", false);
    new LoginPresenter(rpcService, eventBus, new LoginView()).go(container);
  }

  private void doShowOverview() {
    new OverviewPresenter(rpcService, eventBus, new OverviewView()).go(container);
  }

  private void doShowAdmin() {
    History.newItem("admin", false);
    new WoertliAdminPresenter(rpcService, eventBus, new WoertliAdminView()).go(container);
  }

  private void doNextWoertli() {
    new WoertliCheckPresenter(rpcService, eventBus, new WoertliCheckView()).go(container);
  }

  private void doShowSummary(int aCorrectCount) {
    new SummaryPresenter(rpcService, eventBus, new SummaryView(), aCorrectCount).go(container);
  }

  private void doShowErrorMessage(String aErrorMessage, Throwable aCause) {
    new ErrorMessagePresenter(rpcService, eventBus, new ErrorMessageBox(), aErrorMessage, aCause).go(container);
  }

}

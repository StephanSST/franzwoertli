package ch.stephan.franz.client.view;

import ch.stephan.franz.client.presenter.ErrorMessagePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ErrorMessageBox extends Composite implements ErrorMessagePresenter.Display {

  private static ErrorMessageBoxUiBinder uiBinder = GWT.create(ErrorMessageBoxUiBinder.class);

  interface ErrorMessageBoxUiBinder extends UiBinder<Widget, ErrorMessageBox> {
  }

  public ErrorMessageBox() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @UiField
  Button closeButton;
  @UiField
  TextArea errorStackTrace;

  @Override
  public HasClickHandlers getCloseClickable() {
    return closeButton;
  }

  @Override
  public HasText getErrorStackTrace() {
    return errorStackTrace;
  }

  @Override
  public HasHTML getBoxTitle() {
    return (HasHTML) this.getWidget();
  }

}

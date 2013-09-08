package ch.stephan.franz.client.view;

import ch.stephan.franz.client.presenter.LoginPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements LoginPresenter.Display {

  private static LoginViewImplUiBinder uiBinder = GWT.create(LoginViewImplUiBinder.class);

  interface LoginViewImplUiBinder extends UiBinder<Widget, LoginView> {
  }

  @UiField
  TextBox userName;
  @UiField
  PasswordTextBox password;
  @UiField
  Button btnLogin;
  @UiField
  Button btnNewUser;
  @UiField
  Button btnForgotPassword;
  @UiField
  TextBox eMail;
  @UiField
  Label lblEmail;

  public LoginView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public HasValue<String> getUserName() {
    return userName;
  }

  @Override
  public HasValue<String> getPassword() {
    return password;
  }

  @Override
  public HasClickHandlers getLoginClickable() {
    return btnLogin;
  }

  @Override
  public HasClickHandlers getNewUserClickable() {
    return btnNewUser;
  }

  @Override
  public HasClickHandlers getForgotPasswordClickable() {
    return btnForgotPassword;
  }

  @Override
  public HasValue<String> getEmail() {
    return eMail;
  }

  @Override
  public HasVisibility getEmailVisible() {
    return eMail;
  }

  @Override
  public HasVisibility getEmailLabelVisible() {
    return lblEmail;
  }

}

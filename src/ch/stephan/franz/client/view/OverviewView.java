package ch.stephan.franz.client.view;

import ch.stephan.franz.client.presenter.OverviewPresenter;
import ch.stephan.franz.shared.UserStatsCO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class OverviewView extends Composite implements OverviewPresenter.Display {

  private static OverviewPanelUiBinder uiBinder = GWT.create(OverviewPanelUiBinder.class);
  @UiField
  TextBox newWords;
  @UiField
  TextBox onlyWrong;
  @UiField
  TextBox oneRight;
  @UiField
  TextBox twoRight;
  @UiField
  TextBox threeRight;
  @UiField
  TextBox fourRight;
  @UiField
  TextBox fiveRight;
  @UiField
  TextBox unites;
  @UiField
  Button btnNext10;
  @UiField
  Label userName;

  interface OverviewPanelUiBinder extends UiBinder<Widget, OverviewView> {
  }

  public OverviewView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public OverviewView(UserStatsCO result) {
    initWidget(uiBinder.createAndBindUi(this));
    getNewWords().setValue(result.getNewWords());
    getOnlyWrong().setValue(result.getOneWrong());
    getOneRight().setValue(result.getOneRight());
    getTwoRight().setValue(result.getTwoRight());
    getThreeRight().setValue(result.getThreeRight());
    getFourRight().setValue(result.getFourRight());
    getFiveRight().setValue(result.getFiveRight());
    getUserName().setText(result.getUserName());
  }

  @Override
  public HasClickHandlers getNext10Button() {
    return btnNext10;
  }

  @Override
  public HasValue<String> getNewWords() {
    return newWords;
  }

  @Override
  public HasValue<String> getOnlyWrong() {
    return onlyWrong;
  }

  @Override
  public HasValue<String> getOneRight() {
    return oneRight;
  }

  @Override
  public HasValue<String> getTwoRight() {
    return twoRight;
  }

  @Override
  public HasValue<String> getThreeRight() {
    return threeRight;
  }

  @Override
  public HasValue<String> getFourRight() {
    return fourRight;
  }

  @Override
  public HasValue<String> getFiveRight() {
    return fiveRight;
  }

  @Override
  public HasText getUserName() {
    return userName;
  }

  @Override
  public HasValue<String> getUnites() {
    return unites;
  }

  @Override
  public HasKeyPressHandlers getNext10EnterPressed() {
    return btnNext10;
  }

  @Override
  public Focusable getNext10Focus() {
    return btnNext10;
  }

}

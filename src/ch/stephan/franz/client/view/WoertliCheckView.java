package ch.stephan.franz.client.view;

import ch.stephan.franz.client.presenter.WoertliCheckPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class WoertliCheckView extends Composite implements WoertliCheckPresenter.Display {

  private static WoertliCheckUiBinder uiBinder = GWT.create(WoertliCheckUiBinder.class);
  @UiField
  TextBox franz;
  @UiField
  Button btnCheck;
  @UiField
  Button btnNext;
  @UiField
  Label richtig;
  @UiField
  Label falsch;
  @UiField
  Label round;
  @UiField
  Label german;
  @UiField
  Label correctSolution;
  @UiField
  RadioButton rbMaennlich;
  @UiField
  RadioButton rbWeiblich;

  interface WoertliCheckUiBinder extends UiBinder<Widget, WoertliCheckView> {
  }

  public WoertliCheckView() {
    initWidget(uiBinder.createAndBindUi(this));
    richtig.setVisible(false);
    falsch.setVisible(false);
    correctSolution.setVisible(false);
    btnNext.setVisible(false);
  }

  @Override
  public HasClickHandlers getCheckButtonClickable() {
    return btnCheck;
  }

  @Override
  public HasClickHandlers getNextButtonClickable() {
    return btnNext;
  }

  @Override
  public HasValue<String> getFranz() {
    return franz;
  }

  @Override
  public HasText getGerman() {
    return german;
  }

  @Override
  public HasText getRound() {
    return round;
  }

  @Override
  public HasVisibility getCheckButtonVisible() {
    return btnCheck;
  }

  @Override
  public HasVisibility getNextButtonVisible() {
    return btnNext;
  }

  @Override
  public HasText getCorrectSolution() {
    return correctSolution;
  }

  @Override
  public HasVisibility getCorrectSolutionVisible() {
    return correctSolution;
  }

  @Override
  public HasVisibility getRichtigVisible() {
    return richtig;
  }

  @Override
  public HasVisibility getFalschVisible() {
    return falsch;
  }

  @Override
  public HasVisibility getMaennlichVisible() {
    return rbMaennlich;
  }

  @Override
  public HasVisibility getWeiblichVisible() {
    return rbWeiblich;
  }

  @Override
  public HasValue<Boolean> getMaennlichSelected() {
    return rbMaennlich;
  }

  @Override
  public HasValue<Boolean> getWeiblichSelected() {
    return rbWeiblich;
  }

  @Override
  public HasEnabled getCheckButtonHasEnabled() {
    return btnCheck;
  }

  @Override
  public HasClickHandlers getMaennlichClickable() {
    return rbMaennlich;
  }

  @Override
  public HasClickHandlers getWeiblichClickable() {
    return rbWeiblich;
  }

  @Override
  public Focusable getFranzFocus() {
    return franz;
  }

  @Override
  public HasEnabled getFranzHasEnabled() {
    return franz;
  }

  @Override
  public HasKeyPressHandlers getFranzEnterPressed() {
    return franz;
  }

  @Override
  public HasKeyPressHandlers getNextButtonEnterPressed() {
    return btnNext;
  }

  @Override
  public Focusable getNextButtonFocus() {
    return btnNext;
  }

}

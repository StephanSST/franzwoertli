package ch.stephan.franz.client.view;

import ch.stephan.franz.client.presenter.SummaryPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SummaryView extends Composite implements SummaryPresenter.Display {

  private static SummaryViewUiBinder uiBinder = GWT.create(SummaryViewUiBinder.class);
  @UiField
  Button btnOverview;
  @UiField
  Label summaryComment;

  interface SummaryViewUiBinder extends UiBinder<Widget, SummaryView> {
  }

  public SummaryView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public HasClickHandlers getOverviewClickable() {
    return btnOverview;
  }

  @Override
  public HasText getSummaryComment() {
    return summaryComment;
  }

  @Override
  public HasKeyPressHandlers getOverviewEnterPressed() {
    return btnOverview;
  }

  @Override
  public Focusable getOverviewFocus() {
    return btnOverview;
  }

}

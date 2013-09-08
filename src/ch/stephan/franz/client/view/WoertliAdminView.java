/**
 * 
 */
package ch.stephan.franz.client.view;

import ch.stephan.franz.client.presenter.WoertliAdminPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Stephan
 * 
 */
public class WoertliAdminView extends Composite implements WoertliAdminPresenter.Display {

  private static WoertliUploadPanelUiBinder uiBinder = GWT.create(WoertliUploadPanelUiBinder.class);

  interface WoertliUploadPanelUiBinder extends UiBinder<Widget, WoertliAdminView> {
  }

  @UiField
  Button btnUpload;
  @UiField
  Button btnBack;
  @UiField
  TextArea inputArea;
  @UiField
  Button btnDelete;
  @UiField
  TextBox uniteToDelete;

  public WoertliAdminView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public HasClickHandlers getBackButton() {
    return btnBack;
  }

  @Override
  public HasClickHandlers getUploadButton() {
    return btnUpload;
  }

  @Override
  public HasValue<String> getInputArea() {
    return inputArea;
  }

  @Override
  public HasClickHandlers getDeleteButton() {
    return btnDelete;
  }

  @Override
  public HasValue<String> getUniteToDelete() {
    return uniteToDelete;
  }

}

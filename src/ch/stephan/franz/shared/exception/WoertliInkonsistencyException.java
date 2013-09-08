/**
 * 
 */
package ch.stephan.franz.shared.exception;

public class WoertliInkonsistencyException extends Exception {

  private static final long serialVersionUID = 1L;

  public WoertliInkonsistencyException() {
    super();
  }

  public WoertliInkonsistencyException(String aMessage) {
    super(aMessage);
  }

  public WoertliInkonsistencyException(String aMessage, Throwable aArg1) {
    super(aMessage, aArg1);
  }

}

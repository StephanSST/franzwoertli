/**
 * 
 */
package ch.stephan.franz.shared.exception;

public class UserHandlingException extends Exception {

  private static final long serialVersionUID = 1L;

  public UserHandlingException() {
    super();
  }

  public UserHandlingException(String aMessage) {
    super(aMessage);
  }

  public UserHandlingException(String aMessage, Throwable aArg1) {
    super(aMessage, aArg1);
  }

}

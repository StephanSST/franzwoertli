/**
 * 
 */
package ch.stephan.franz.shared.exception;

public class SecurityException extends Exception {

  private static final long serialVersionUID = 1L;

  public SecurityException() {
    super();
  }

  public SecurityException(String aMessage) {
    super(aMessage);
  }

  public SecurityException(String aMessage, Throwable aArg1) {
    super(aMessage, aArg1);
  }

}

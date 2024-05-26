package subsystem;

import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.payment.PaymentTransaction;
import entity.payment.RefundTransaction;

import java.io.IOException;
import java.util.Map;

public class PaymentSubsystem implements IPaymentSubsystem {

	private final IPaymentSubsystem controller;

	/**
	 * Initializes a newly created {@code PaymentSubsystem} object with the specified controller.
	 *
	 * @param controller The controller for this subsystem.
	 */
	public PaymentSubsystem(IPaymentSubsystem controller) {
		this.controller = controller;
	}

	@Override
	public PaymentTransaction getPaymentTransaction(Map<String, String> res)
			throws PaymentException, UnrecognizedException, IOException {
		return controller.getPaymentTransaction(res);
	}

	@Override
	public String generateURL(int amount, String content) throws IOException {
		return controller.generateURL(amount, content);
	}

	@Override
	public RefundTransaction refund(PaymentTransaction paymentTransaction) throws IOException {
		return controller.refund(paymentTransaction);
	}
}

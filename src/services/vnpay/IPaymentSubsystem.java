package services.vnpay;

import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.payment.PaymentTransaction;
import entity.payment.RefundTransaction;

import java.io.IOException;
import java.util.Map;

public interface IPaymentSubsystem {

	/**
	 * Retrieves a {@link PaymentTransaction} based on the response data.
	 *
	 * @param res A map containing response data.
	 * @return The payment transaction.
	 * @throws PaymentException     If there's an issue with the payment.
	 * @throws UnrecognizedException If the response is unrecognized.
	 * @throws IOException          If an I/O error occurs.
	 */
	PaymentTransaction getPaymentTransaction(Map<String, String> res)
			throws PaymentException, UnrecognizedException, IOException;

	/**
	 * Generates a URL for a payment transaction.
	 *
	 * @param amount  The transaction amount.
	 * @param content Additional content related to the transaction.
	 * @return The generated URL.
	 * @throws IOException If an I/O error occurs.
	 */
	String generateURL(int amount, String content) throws IOException;

	/**
	 * Initiates a refund for a given payment transaction.
	 *
	 * @param paymentTransaction The original payment transaction.
	 * @return The refund transaction.
	 * @throws IOException If an I/O error occurs.
	 */
	RefundTransaction refund(PaymentTransaction paymentTransaction) throws IOException;
}

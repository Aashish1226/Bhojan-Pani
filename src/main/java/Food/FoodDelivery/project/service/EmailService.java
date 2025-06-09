package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.Entity.*;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    public static String buildSuccessEmailBody(Payments payments) {
        return """
            Hi,

            ✅ Your payments was successful!

            🔹 Order Number: %s
            🔹 Payment ID: %s
            🔹 Amount Paid: ₹%.2f
            🔹 Payment Method: %s
            🔹 Time: %s

            Thank you for choosing Bhojan-pani 🍽️

            Regards,
            Team Bhojan-pani
            """.formatted(
                payments.getOrders().getOrderNumber(),
                payments.getRazorpayPaymentId(),
                payments.getAmount(),
                payments.getRazorpayMethod(),
                payments.getCreatedAt()
        );
    }

    public static String buildFailureEmailBody(Payments payments) {
        return """
            Hi,

            ❌ Your payments attempt failed.

            🔹 Order Number: %s
            🔹 Payment ID: %s
            🔹 Amount: ₹%.2f
            🔹 Payment Method: %s
            🔹 Reason: %s
            🔹 Time: %s

            Please try again or contact support.

            Regards,
            Team Bhojan pani
            """.formatted(
                payments.getOrders().getOrderNumber(),
                payments.getRazorpayPaymentId(),
                payments.getAmount(),
                payments.getRazorpayMethod(),
                payments.getFailureReason() != null ? payments.getFailureReason() : "Unknown error",
                payments.getCreatedAt()
        );
    }


    public static String buildOrderFailureHtmlEmailBody(Orders order, String failureReason) {
        StringBuilder cartItemsBuilder = new StringBuilder();
        for (CartItem item : order.getCart().getItems()) {
            if (item.getIsActive()) {
                cartItemsBuilder.append("<tr>")
                        .append("<td style=\"padding: 8px 0; font-size: 14px;\">")
                        .append(item.getFood().getName());
                if (item.getFoodVariant() != null) {
                    cartItemsBuilder.append(" (").append(item.getFoodVariant().getLabel()).append(")");
                }
                cartItemsBuilder.append("</td>")
                        .append("<td style=\"padding: 8px 0; text-align: right; font-size: 14px;\">")
                        .append(item.getQuantity()).append("</td>")
                        .append("</tr>");
            }
        }

        Addresses address = order.getDeliveryAddress();
        String addressStr = (address != null)
                ? String.format("%s, %s, %s, %s - %s",
                address.getAddressLine1(), address.getCity().getName(), address.getState().getName(),
                address.getCountry().getName(), address.getPostalCode())
                : "N/A";

        return """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #f8f8f8; padding: 20px;">
          <table width="100%%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
            <tr>
              <td style="background-color: #f44336; padding: 20px; text-align: center; color: #ffffff;">
                <h2 style="margin: 0;">Order Failed ❌</h2>
              </td>
            </tr>
            <tr>
              <td style="padding: 20px;">
                <p style="font-size: 16px;">Hi %s,</p>
                <p style="font-size: 15px;">Unfortunately, your order could not be processed due to the following reason:</p>
                <blockquote style="background-color: #ffe0e0; padding: 10px 15px; border-left: 4px solid #f44336; color: #b00020;">
                  %s
                </blockquote>

                <h3 style="margin-top: 20px;">Order Details:</h3>
                <p><strong>Order Number:</strong> %s</p>
                <p><strong>Status:</strong> %s</p>
                <p><strong>Placed At:</strong> %s</p>

                <h3 style="margin-top: 20px;">Cart Items:</h3>
                <table width="100%%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
                  <thead>
                    <tr>
                      <th align="left" style="border-bottom: 1px solid #ddd; padding-bottom: 10px;">Item</th>
                      <th align="right" style="border-bottom: 1px solid #ddd; padding-bottom: 10px;">Quantity</th>
                    </tr>
                  </thead>
                  <tbody>
                    %s
                  </tbody>
                </table>

                <h3 style="margin-top: 20px;">Amount Summary:</h3>
                <p><strong>Total Amount:</strong> ₹%.2f</p>
                <p><strong>Tax:</strong> ₹%.2f</p>
                <p><strong>Discount:</strong> ₹%.2f</p>
                <p><strong>Delivery Charges:</strong> ₹%.2f</p>
                <p><strong>Final Amount:</strong> ₹%.2f</p>

                <h3 style="margin-top: 20px;">Delivery Address:</h3>
                <p>%s</p>

                <p style="margin-top: 30px;">Please try placing the order again or contact our support for assistance.</p>
                <p style="font-size: 14px; color: #777;">Regards,<br>Team Bhojan-pani 🍽️</p>
              </td>
            </tr>
            <tr>
              <td style="background-color: #f0f0f0; padding: 15px; text-align: center; font-size: 13px; color: #555;">
                &copy; %d Bhojan-pani. All rights reserved.
              </td>
            </tr>
          </table>
        </body>
        </html>
        """.formatted(
                order.getCart().getUser().getFirstName(),
                failureReason != null ? failureReason : "Unknown error",
                order.getOrderNumber(),
                order.getOrderStatus(),
                order.getOrderPlacedAt(),
                cartItemsBuilder,
                order.getTotalAmount(),
                order.getTax(),
                order.getDiscount(),
                order.getDeliveryCharges(),
                order.getFinalAmount(),
                addressStr,
                LocalDateTime.now().getYear()
        );
    }


    public static String buildOrderSuccessHtmlEmailBody(Orders order) {
        StringBuilder cartItemsBuilder = new StringBuilder();
        for (CartItem item : order.getCart().getItems()) {
            if (item.getIsActive()) {
                cartItemsBuilder.append("<tr>")
                        .append("<td style=\"padding: 8px 0; font-size: 14px;\">")
                        .append(item.getFood().getName());
                if (item.getFoodVariant() != null) {
                    cartItemsBuilder.append(" (").append(item.getFoodVariant().getLabel()).append(")");
                }
                cartItemsBuilder.append("</td>")
                        .append("<td style=\"padding: 8px 0; text-align: right; font-size: 14px;\">")
                        .append(item.getQuantity()).append("</td>")
                        .append("</tr>");
            }
        }

        Addresses address = order.getDeliveryAddress();
        String addressStr = (address != null)
                ? String.format("%s, %s, %s, %s - %s",
                address.getAddressLine1(), address.getCity().getName(), address.getState().getName(),
                address.getCountry().getName(), address.getPostalCode())
                : "N/A";

        return """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #f6f6f6; padding: 20px;">
          <table width="100%%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
            <tr>
              <td style="background-color: #4CAF50; padding: 20px; text-align: center; color: #ffffff;">
                <h2 style="margin: 0;">Order Confirmed 🎉</h2>
              </td>
            </tr>
            <tr>
              <td style="padding: 20px;">
                <p style="font-size: 16px;">Hi %s,</p>
                <p style="font-size: 15px;">Thank you for your order! Here's your order summary:</p>

                <h3 style="margin-top: 20px;">Order Details:</h3>
                <p><strong>Order Number:</strong> %s</p>
                <p><strong>Status:</strong> %s</p>
                <p><strong>Placed At:</strong> %s</p>

                <h3 style="margin-top: 20px;">Cart Items:</h3>
                <table width="100%%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
                  <thead>
                    <tr>
                      <th align="left" style="border-bottom: 1px solid #ddd; padding-bottom: 10px;">Item</th>
                      <th align="right" style="border-bottom: 1px solid #ddd; padding-bottom: 10px;">Quantity</th>
                    </tr>
                  </thead>
                  <tbody>
                    %s
                  </tbody>
                </table>

                <h3 style="margin-top: 20px;">Amount Summary:</h3>
                <p><strong>Total Amount:</strong> ₹%.2f</p>
                <p><strong>Tax:</strong> ₹%.2f</p>
                <p><strong>Discount:</strong> ₹%.2f</p>
                <p><strong>Delivery Charges:</strong> ₹%.2f</p>
                <p><strong>Final Amount:</strong> ₹%.2f</p>

                <h3 style="margin-top: 20px;">Delivery Address:</h3>
                <p>%s</p>

                <p style="margin-top: 30px;">We will notify you once your order is out for delivery.</p>
                <p style="font-size: 14px; color: #777;">Thanks for choosing Bhojan-pani 🍽️</p>
              </td>
            </tr>
            <tr>
              <td style="background-color: #f0f0f0; padding: 15px; text-align: center; font-size: 13px; color: #555;">
                &copy; %d Bhojan-pani. All rights reserved.
              </td>
            </tr>
          </table>
        </body>
        </html>
        """.formatted(
                order.getCart().getUser().getFirstName(),
                order.getOrderNumber(),
                order.getOrderStatus(),
                order.getOrderPlacedAt(),
                cartItemsBuilder,
                order.getTotalAmount(),
                order.getTax(),
                order.getDiscount(),
                order.getDeliveryCharges(),
                order.getFinalAmount(),
                addressStr,
                LocalDateTime.now().getYear()
        );
    }

    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

}

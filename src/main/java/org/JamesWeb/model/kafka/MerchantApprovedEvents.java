package org.JamesWeb.model.kafka;

public record MerchantApprovedEvents(
        String eventId,
        String merchantId,
        String ownerName,
        String businessEmail,
        String status
) {
}

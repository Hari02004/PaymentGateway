package org.JamesWeb.service.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.JamesWeb.DTO.PaginationTransactionDetails;
import org.JamesWeb.DTO.TransactionDto;
import org.JamesWeb.DTO.adminResponseDtos.AdminMerchantDetailsDto;
import org.JamesWeb.exception.AdminErrorException;
import org.JamesWeb.mapper.AdminMerchantDetailsMapper;
import org.JamesWeb.mapper.TransactionMapper;
import org.JamesWeb.model.MerchantDetails;
import org.JamesWeb.model.TransactionDetails;
import org.JamesWeb.model.kafka.MerchantApprovedEvents;
import org.JamesWeb.model.kafka.OutboxEvent;
import org.JamesWeb.model.kafka.OutboxStatus;
import org.JamesWeb.repository.MerchantRepository;
import org.JamesWeb.repository.OutboxEventRepository;
import org.JamesWeb.repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class AdminMerchantDetailsPagination {
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private AdminMerchantDetailsMapper adminMerchantDetailsMapper;
    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OutboxEventRepository outboxEventRepository;
    @Autowired
    private ObjectMapper objectMapper;
    public PaginationTransactionDetails<AdminMerchantDetailsDto> adminMerchantDetailsDto(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<MerchantDetails> merchantDetailsPage = merchantRepository.findAll(pageable);
        Page<AdminMerchantDetailsDto> merchantDetailsDtos = merchantDetailsPage.map(adminMerchantDetailsMapper::adminMerchantDetailsDto);
        return PaginationTransactionDetails.from(merchantDetailsDtos);
    }
    public PaginationTransactionDetails<TransactionDto> transactionDtoPaginationTransactionDetails(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<TransactionDetails> transactionDetailsPage = transactionDetailsRepository.findAll(pageable);
        Page<TransactionDto> transactionDtoPage = transactionDetailsPage.map(transactionMapper::toDto);
        return PaginationTransactionDetails.from(transactionDtoPage);
    }
    @Transactional
    public String merchantActivate(String MerchantId){
        MerchantDetails merchantDetails = merchantRepository.findByMerchantId(MerchantId);
        if(merchantDetails==null){
            throw new AdminErrorException("Merchant id is not present");
        }
        merchantDetails.setStatus("APPROVED");
        merchantRepository.save(merchantDetails);
        String eventId = UUID.randomUUID().toString();
        MerchantApprovedEvents events = new MerchantApprovedEvents(
                eventId,merchantDetails.getMerchantId(),
                merchantDetails.getOwner_name(),merchantDetails.getBusiness_email(),
                merchantDetails.getStatus());
        try {

            String payload =
                    objectMapper.writeValueAsString(events);

            OutboxEvent outboxEvent =
                    new OutboxEvent();

            outboxEvent.setEventId(eventId);

            outboxEvent.setEventType(
                    "merchant-approved"
            );

            outboxEvent.setAggregateId(
                    merchantDetails.getMerchantId()
            );

            outboxEvent.setPayload(payload);

            outboxEvent.setStatus(
                    OutboxStatus.PENDING
            );

            outboxEvent.setCreatedAt(
                    LocalDateTime.now()
            );

            outboxEventRepository.save(
                    outboxEvent
            );

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Failed to create outbox event",
                    e
            );
        }
        emailService.sendApprovalEmail(merchantDetails.getBusiness_email(),merchantDetails.getOwner_name());
        return "SuccessFull";
    }
}

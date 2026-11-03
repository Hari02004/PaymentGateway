package org.JamesWeb.service.admin;

import org.JamesWeb.exception.AdminErrorException;
import org.JamesWeb.model.kafka.OutboxEvent;
import org.JamesWeb.model.kafka.OutboxStatus;
import org.JamesWeb.repository.OutboxEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private OutboxEventRepository outboxEventRepository;
    private final int RETRY_LIMIT = 10;
    @Scheduled(fixedDelay = 10000)
    public void publicEvents(){
        List<OutboxEvent> eventListWithRestriction = outboxEventRepository.findTheOrderToPublish
                (List.of(OutboxStatus.FAILED,OutboxStatus.PENDING),LocalDateTime.now(), PageRequest.of(0,100));
        for(OutboxEvent event:eventListWithRestriction){
            try{
                event.setStatus(OutboxStatus.PROCESSING);
                event.setLastAttempted(LocalDateTime.now());
                outboxEventRepository.save(event);
                kafkaTemplate.send(event.getEventType(),event.getAggregateId(), event.getPayload()).get();
                event.setStatus(OutboxStatus.PUBLISHED);
                event.setPublishedAt(LocalDateTime.now());
                event.setLastError(null);
                outboxEventRepository.save(event);
            }catch (Exception e){
                LocalDateTime now = LocalDateTime.now();
                event.setLastAttempted(now);
                event.setLastError(e.getMessage());
                event.setRetryCount(event.getRetryCount()+1);
                if(event.getRetryCount()<RETRY_LIMIT){
                    event.setStatus(OutboxStatus.FAILED);
                    event.setNextAttempt(now.plusSeconds(10));
                }
                else{
                    event.setStatus(OutboxStatus.DEAD);
                }
                outboxEventRepository.save(event);
            }
        }
    }

}

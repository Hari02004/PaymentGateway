package org.JamesWeb.repository;

import org.JamesWeb.model.kafka.OutboxEvent;
import org.JamesWeb.model.kafka.OutboxStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OutboxEventRepository
        extends JpaRepository<OutboxEvent, Long> {

    @Query("SELECT o FROM OutboxEvent o where o.status IN :statuses AND (o.nextAttempt <= :now OR o.nextAttempt IS null)")
    List<OutboxEvent> findTheOrderToPublish(
            @Param("statuses") List<OutboxStatus> statuses,
            @Param("now") LocalDateTime now,
            Pageable pageable
    );
}

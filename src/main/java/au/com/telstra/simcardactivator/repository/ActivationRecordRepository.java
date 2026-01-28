package au.com.telstra.simcardactivator.repository;

import au.com.telstra.simcardactivator.model.ActivationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationRecordRepository extends JpaRepository<ActivationRecord, Long> {
}

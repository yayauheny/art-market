package by.yayauheny.repository;

import by.yayauheny.entity.BidHoldBalanceEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidHoldBalanceRepository extends JpaRepository<BidHoldBalanceEntity, UUID> {

}

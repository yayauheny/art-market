package by.yayauheny.repository;

import by.yayauheny.entity.BidEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<BidEntity, UUID> {

}

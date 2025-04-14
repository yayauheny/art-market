package by.yayauheny.repository;

import by.yayauheny.entity.AuctionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<AuctionEntity, UUID> {

}

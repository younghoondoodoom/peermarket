package peermarket.peershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peermarket.peershop.entity.CategoryItem;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {
}

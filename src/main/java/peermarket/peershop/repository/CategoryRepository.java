package peermarket.peershop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import peermarket.peershop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String categoryName);

}

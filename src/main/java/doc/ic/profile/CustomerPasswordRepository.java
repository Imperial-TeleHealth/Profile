package doc.ic.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPasswordRepository extends JpaRepository<Customerpassword, String> {}

package library.repository;

import library.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByReaderId(Long id);
    List<Loan> findByBookId(Long id);
    @Query("SELECT CASE " +
            "WHEN b.numberOfCopies > (SELECT COUNT(l) FROM Loan l WHERE l.book = b AND l.returned IS NULL) " +
            "THEN true ELSE false END " +
            "FROM Book b WHERE b.id = :bookId")
    Boolean isBookAvailable(@Param("bookId") Long bookId);

}

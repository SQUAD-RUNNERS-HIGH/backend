package runnershigh.capstone.global.applicationRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterTableRunner implements ApplicationRunner {

    private final DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) {
        try (Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(
                "ALTER TABLE crew ADD FULLTEXT (specific_location) WITH PARSER ngram");
            stmt.executeUpdate("ALTER TABLE crew ADD FULLTEXT (name) WITH PARSER ngram");

            System.out.println("FULLTEXT 인덱스 생성 완료");

        } catch (SQLException e) {
            System.err.println("인덱스 생성 실패: " + e.getMessage());
        }
    }
}

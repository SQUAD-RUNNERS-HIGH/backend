package runnershigh.capstone.personalrunninghistory.repository;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import runnershigh.capstone.personalrunninghistory.domain.PersonalRunningHistory;

public interface PersonalRunningHistoryRepository extends MongoRepository<PersonalRunningHistory, ObjectId> {

}

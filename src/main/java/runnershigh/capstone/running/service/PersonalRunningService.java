package runnershigh.capstone.running.service;

import org.springframework.stereotype.Service;
import runnershigh.capstone.running.dto.PersonalRunningInfo;
import runnershigh.capstone.running.dto.PersonalRunningResponse;
import runnershigh.capstone.running.dto.RunningStatus;

@Service
public class PersonalRunningService {

    public PersonalRunningResponse calculatePersonalRunning(final PersonalRunningInfo info){
        Double mySpeed = info.mySpeed();
        double remainTime = info.coursePerimeter() / mySpeed;
        if(remainTime < info.competitorRemainTime()){
            return new PersonalRunningResponse(RunningStatus.LEADING);
        }
        return new PersonalRunningResponse(RunningStatus.LAGGING);
    }
}

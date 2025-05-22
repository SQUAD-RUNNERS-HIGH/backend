package runnershigh.capstone.builders;

import java.util.HashSet;
import java.util.Set;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.location.domain.Location;
import runnershigh.capstone.user.domain.User;

public class CrewTestBuilder {

    private String name = "Default Crew Name";
    private String description = "Default Description";
    private int maxCapacity = 100;
    private String image = "default_image_url";
    private Location crewLocation = new Location("대한민국", "서울시", "강남구", "역삼동", "서울시 강남구 역삼동 2121-1");
    private User crewLeader = null;
    private Set<CrewParticipant> crewParticipant = new HashSet<>();

    public static CrewTestBuilder builder() {
        return new CrewTestBuilder();
    }

    public CrewTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CrewTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public CrewTestBuilder maxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        return this;
    }

    public CrewTestBuilder image(String image) {
        this.image = image;
        return this;
    }

    public CrewTestBuilder crewLocation(Location crewLocation) {
        this.crewLocation = crewLocation;
        return this;
    }

    public CrewTestBuilder crewLeader(User crewLeader) {
        this.crewLeader = crewLeader;
        return this;
    }

    public CrewTestBuilder crewParticipant(Set<CrewParticipant> crewParticipant) {
        this.crewParticipant = crewParticipant;
        return this;
    }

    public Crew build() {
        if (crewLeader == null) {
            throw new IllegalStateException("크루 리더는 반드시 존재해야 합니다.");
        }
        return new Crew(name, description, maxCapacity, image, crewLocation, crewLeader,
            crewParticipant);
    }
}

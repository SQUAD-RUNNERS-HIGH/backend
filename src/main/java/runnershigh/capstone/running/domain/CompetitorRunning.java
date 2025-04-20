package runnershigh.capstone.running.domain;

import runnershigh.capstone.running.dto.response.CompetitorRunningResponse;

public class CompetitorRunning {

    private final CourseGeometry geometry;
    private final UserCoordinate userCoordinate;

    public CompetitorRunning(final CourseGeometry geometry, final UserCoordinate userCoordinate) {
        this.geometry = geometry;
        this.userCoordinate = userCoordinate;
    }

    public CompetitorRunningResponse calcauteCompetitorRunning(){

    }
}

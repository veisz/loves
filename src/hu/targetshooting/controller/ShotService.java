package hu.targetshooting.controller;

import hu.targetshooting.model.domain.ShotResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShotService {

    private final List<ShotResult> results;

    public ShotService(List<ShotResult> results) {
        this.results = results;
    }

    public String getTwoSuccessfulShotIds() {
        return results.stream()
                .filter(ShotResult::hasTwoSuccessfulShotsInRow)
                .map(ShotResult::getId)
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }

    public int getLongestShotSequenceId() {
        return results.stream()
                .max(Comparator.comparing(ShotResult::getShotCount))
                .map(ShotResult::getId)
                .get();

    }

    public String getSuccessShotIndexesById(int id) {
        return getResultById(id).getSuccessShotIndexes();
    }

    public int getScoreById(int id) {
        return getResultById(id).getScore();
    }

    public long countSuccessShotsById(int id) {
        return getResultById(id).countSuccessShots();
    }

    public int getLongestSuccessSequenceSizeById(int id) {
        return getResultById(id).getLongestSuccessSequenceSize();
    }

    private ShotResult getResultById(int id) {
        return results.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .get();
    }

    public List<String> getFinalResult() {
    List<String> results = new ArrayList<>();
    List<ShotResult> orderedResults = createFinalReuslt();
    int prevScore = 0;
    int order;
    for (int i = 0; i < orderedResults.size(); i++) {
        int order = i;
        if (prevScore == orderedResults.get(i).getId()) {
            order =preOrder;
        }
        int id = orderedResults.get(i).getId();
        int score = orderedResults.get(i).getScore();
        results.add(String.format("%d\t%d\t%d", order, id, score));
    }
    return results;
    }

    private List<ShotResult> createFinalReuslt() {
        return results.stream()
                .sorted(Comparator.comparing(ShotResult::getScore).reversed())
                .collect(Collectors.toList());
    }
}
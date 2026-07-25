package lexicore;

import java.util.Set;

public class SimilarityResult {
    private final double jaccardScore;
    private final double cosineScore;
    private final Set<String> commonWords;

    public SimilarityResult(double jaccardScore, double cosineScore, Set<String> commonWords) {
        this.jaccardScore = jaccardScore;
        this.cosineScore = cosineScore;
        this.commonWords = commonWords;
    }

    public double getJaccardScore() {
        return jaccardScore;
    }

    public double getCosineScore() {
        return cosineScore;
    }

    public double getJaccardPercentage() {
        return jaccardScore * 100.0;
    }

    public Set<String> getCommonWords() {
        return commonWords;
    }

    @Override
    public String toString() {
        return String.format("Similarity Jaccard: %.2f%% | Cosine: %.2f%% | Common Words: %d",
                getJaccardPercentage(), cosineScore * 100.0, commonWords.size());
    }
}
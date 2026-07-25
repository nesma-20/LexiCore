package lexicore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SimilarityDetector {
    public SimilarityResult compareTexts(List<String> tokens1, List<String> tokens2) {
        if (tokens1 == null || tokens2 == null || tokens1.isEmpty() || tokens2.isEmpty()) {
            return new SimilarityResult(0.0, 0.0, new HashSet<>());
        }


        Set<String> set1 = new HashSet<>();
        for (String t : tokens1) set1.add(t.toLowerCase());

        Set<String> set2 = new HashSet<>();
        for (String t : tokens2) set2.add(t.toLowerCase());

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        double jaccardScore = union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();


        double cosineScore = calculateCosineSimilarity(tokens1, tokens2);

        return new SimilarityResult(jaccardScore, cosineScore, intersection);
    }

    private double calculateCosineSimilarity(List<String> tokens1, List<String> tokens2) {
        Map<String, Integer> freq1 = getFrequencyMap(tokens1);
        Map<String, Integer> freq2 = getFrequencyMap(tokens2);

        Set<String> allWords = new HashSet<>(freq1.keySet());
        allWords.addAll(freq2.keySet());

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (String word : allWords) {
            int count1 = freq1.getOrDefault(word, 0);
            int count2 = freq2.getOrDefault(word, 0);

            dotProduct += count1 * count2;
            normA += count1 * count1;
            normB += count2 * count2;
        }

        if (normA == 0.0 || normB == 0.0) return 0.0;
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private Map<String, Integer> getFrequencyMap(List<String> tokens) {
        Map<String, Integer> freq = new HashMap<>();
        for (String t : tokens) {
            String w = t.toLowerCase();
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
        return freq;
    }
}

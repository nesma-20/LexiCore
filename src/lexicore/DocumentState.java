package lexicore;

import java.util.ArrayList;
import java.util.List;

//public class DocumentState {
//    private final String rawText;
//    private final List<String> masterCorpus;
//    private final List<List<String>> sentenceCorpus;
//
//    public DocumentState(String rawText, List<String> masterCorpus, List<List<String>> sentenceCorpus) {
//        this.rawText = rawText;
//        // إنشاء نسخة عميقة  لمنع التعديل على البيانات الحالية
//        this.masterCorpus = new ArrayList<>(masterCorpus);
//
//        this.sentenceCorpus = new ArrayList<>();
//        for (List<String> sentence : sentenceCorpus) {
//            this.sentenceCorpus.add(new ArrayList<>(sentence));
//        }
//    }
//
//    public String getRawText() {
//        return rawText;
//    }
//
//    public List<String> getMasterCorpus() {
//        return new ArrayList<>(masterCorpus);
//    }
//
//    public List<List<String>> getSentenceCorpus() {
//        List<List<String>> copy = new ArrayList<>();
//        for (List<String> sentence : sentenceCorpus) {
//            copy.add(new ArrayList<>(sentence));
//        }
//        return copy;
//    }
//}
//



import java.util.*;

public class SortByType implements Sort {
    @Override
    public <K, V extends Comparable<? super V>> Map<K, V> sort(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K,V> entry : list){
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}

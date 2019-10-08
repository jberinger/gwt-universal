package net.sprd.gwt.shared.dom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IdService {
    
    private Set<String> idSet = new HashSet<>();
    private Map<String, Integer> idCounterMap = new HashMap<>();
    
    public String getId(String id) {
        if (idSet.contains(id)) {
            Integer count = idCounterMap.get(id);
            if (count != null) {
                count++;
            } else {
                count = 2;
            }
            idCounterMap.put(id, count);
            id = id + Integer.toString(count, 36).toUpperCase();
        }
        idSet.add(id);
        return id;
    }


}

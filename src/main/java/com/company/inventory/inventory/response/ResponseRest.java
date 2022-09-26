package com.company.inventory.inventory.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class ResponseRest {
    private List<HashMap<String, String>> metadata = new ArrayList<>();

    public void setMetadata(String type, String code, String date) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("code", code);
        map.put("date", date);
        metadata.add(map);
    }
}

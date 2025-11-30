package com.matag.cards.sets;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matag.cards.ResourceLoader;

@Component
public class MtgSets {
    private final Map<String, MtgSet> SETS = new LinkedHashMap<>();

    public MtgSets(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        Resource[] setResources = resourceLoader.getSetsFileNames();
        for (Resource setResource : setResources) {
            try {
                MtgSet mtgSet = objectMapper.readValue(setResource.getInputStream(), MtgSet.class);
                SETS.put(mtgSet.getCode(), mtgSet);

            } catch (Exception e) {
                throw new RuntimeException("Failed to load set: " + setResource.getDescription(), e);
            }
        }
    }

    public Map<String, MtgSet> getSets() {
        return SETS;
    }

    public MtgSet getSet(String code) {
        return SETS.get(code);
    }
}

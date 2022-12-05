package com.json.parser.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class JsonParserService {

    private final ObjectMapper objectMapper;

    public JsonParserService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String parse(String text, String value) throws IOException {
        JsonNode jsonTree = objectMapper.readTree(text);

        if(jsonTree.isArray()) {
            Map<String, List<String>> data = createHeader(jsonTree.get(0), jsonTree.size());
            System.out.println(data);
            for(JsonNode jsonNode : jsonTree) {
                createData(jsonNode, data);
            }
            return JsonToCSVConverter.convert(data, jsonTree.size());
        }
        return "";
    }

    private void createData(JsonNode node, Map<String, List<String>> data) {
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while(fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            data.get(field.getKey()).add(field.getValue().asText());
        }
    }
    private Map<String, List<String>> createHeader(JsonNode nodes, int records) {
        Map<String, List<String>> data = new LinkedHashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = nodes.fields();
        while(fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            data.put(field.getKey(), new ArrayList<>(records));
        }
        return data;
    }
}

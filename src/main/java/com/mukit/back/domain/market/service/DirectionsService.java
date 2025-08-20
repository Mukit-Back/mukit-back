package com.mukit.back.domain.market.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mukit.back.domain.market.dto.request.RouteRequestDTO;
import com.mukit.back.domain.market.dto.response.RouteResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectionsService {
    private final WebClient client;

    public DirectionsService(@Value("${kakao.rest-key}") String kakaoRestKey) {
        this.client = WebClient.builder()
                .baseUrl("https://apis-navi.kakaomobility.com")
                .defaultHeader("Authorization", "KakaoAK " + kakaoRestKey)
                .build();
    }

    public RouteResponseDTO getRoute(RouteRequestDTO req) {
        String origin = req.alng() + "," + req.alat();       // Kakao: x=lng, y=lat
        String destination = req.blng() + "," + req.blat();

        JsonNode json = client.get()
                .uri(b -> b.path("/v1/directions")
                        .queryParam("origin", origin)
                        .queryParam("destination", destination)
                        .queryParam("priority", req.prioritySafe())
                        .queryParam("road_details", true)  // roads[].vertexes 포함
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .timeout(Duration.ofSeconds(5))
                .retryWhen(Retry.backoff(2, Duration.ofMillis(200)))
                .block();

        // 기본값
        List<List<Double>> path = new ArrayList<>();
        double distance = 0.0;
        double duration = 0.0;
        String summaryText = null;

        if (json != null && json.hasNonNull("routes") && json.get("routes").size() > 0) {
            JsonNode first = json.get("routes").get(0);

            // 1) 요약 정보(distance/duration/summary) 추출
            JsonNode summary = first.get("summary");
            if (summary != null) {
                if (summary.has("distance")) distance = summary.get("distance").asDouble(0.0);
                if (summary.has("duration")) duration = summary.get("duration").asDouble(0.0);
                // summary 안에 구간 요약명이 있을 수 있음(없으면 null)
                if (summary.has("summary")) summaryText = summary.get("summary").asText(null);
            }

            // 2) 경로 좌표(path) 구성: sections[].roads[].vertexes = [x1,y1,x2,y2,...]
            JsonNode sections = first.get("sections");
            if (sections != null && sections.isArray()) {
                Double lastLat = null, lastLng = null;

                for (JsonNode section : sections) {
                    JsonNode roads = section.get("roads");
                    if (roads == null || !roads.isArray()) continue;

                    for (JsonNode road : roads) {
                        JsonNode v = road.get("vertexes");
                        if (v == null || !v.isArray()) continue;

                        for (int i = 0; i + 1 < v.size(); i += 2) {
                            double x = v.get(i).asDouble();       // lng
                            double y = v.get(i + 1).asDouble();   // lat

                            // 연속 중복 좌표 제거(같은 점 여러 번 오는 케이스 방지)
                            if (lastLat != null && lastLng != null &&
                                    Double.compare(lastLat, y) == 0 &&
                                    Double.compare(lastLng, x) == 0) {
                                continue;
                            }
                            path.add(List.of(y, x));
                            lastLat = y;
                            lastLng = x;
                        }
                    }
                }
            }
        }

        return new RouteResponseDTO(path, distance, duration, summaryText);
    }

}

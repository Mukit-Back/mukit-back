package com.mukit.back.domain.market.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mukit.back.domain.market.dto.request.CourseRequestDTO;
import com.mukit.back.domain.market.dto.response.CourseResponseDTO;
import com.mukit.back.domain.market.entity.Menu;
import com.mukit.back.domain.market.entity.Shop;
import com.mukit.back.domain.market.entity.enums.MarketType;
import com.mukit.back.domain.market.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private final ObjectMapper objectMapper;
    private final OpenAiChatModel openAiChatModel;
    private final ShopRepository shopRepository;


    public CourseResponseDTO.CourseResultDTO recommendCourse(
            CourseRequestDTO.Survey survey
    ) {

        MarketType marketType = survey.market();
        List<Shop> shops = shopRepository.findAllByMarket_MarketType(marketType);

        log.info("shops: " + shops.toString());

        List<CourseRequestDTO.Candidate> candidates = shops.stream()
                .map(s -> new CourseRequestDTO.Candidate(
                        s.getShopId(),
                        s.getName(),
                        s.getMenus() == null ? List.of()
                                : s.getMenus().stream()
                                .map(Menu::getName)
                                .filter(n -> n != null && !n.isBlank())
                                .limit(3)
                                .toList(),
                        s.getCategory()
                ))
                .toList();

        log.info("candidates: {}", candidates);

        String template = """
            SYSTEM:
            - 아래 '가게 후보' JSON에 있는 가게만 사용할 것. (새 가게/새 메뉴 생성 금지)
            - 코스는 정확히 3개 생성: "코스"라는 단어를 포함한 짧은 한국어 제목으로 작명 (예: "한입 코스", "든든 코스").
            - 각 코스는 정확히 3곳의 가게로 구성.
            - 각 상점 항목은 다음 3개 필드만 포함: shopId, name, signatureMenu
            - 가능하면 각 코스에 category == "DESSERT" 상점을 최소 1개 포함
            - 출력은 **오직 JSON 하나**만. 마크다운 코드펜스(``` 등) 사용하지 말 것. 출력 JSON 루트에서 'course' 이외의 루트 키명(예: '코스', 'courseList') 절대 사용 금지.
            
            USER:
            [가게 후보(JSON)]
            {shopsJson}
            
            [설문(JSON)]
            {surveyJson}
        """;

        Map<String, Object> vars = Map.of(
                "shopsJson", toJson(candidates),
                "surveyJson", toJson(survey)
        );

        Prompt prompt = new PromptTemplate(template).create(vars);
        log.info("prompt\n{}", prompt);

        var response = openAiChatModel.call(prompt);
        String content = response.getResult().getOutput().getText();
        log.info("raw LLM content: {}", content);

        try {
            JsonNode node = objectMapper.readTree(content);

            if (node.has("course") && !node.has("courses")) {
                ObjectNode fixed = (ObjectNode) node;
                fixed.set("courses", node.get("course"));
                fixed.remove("course");
                content = objectMapper.writeValueAsString(fixed);
            }

            // 6) DTO로 직렬화
            return objectMapper.readValue(content, CourseResponseDTO.CourseResultDTO.class);
        } catch (Exception e) {
            throw new IllegalStateException("코스 생성 결과 파싱 실패 " + content, e);
        }
    }


    public CourseResponseDTO.CourseResultDTO recommendRandomCoursePerMarket() {
        Map<MarketType, List<CourseRequestDTO.Candidate>> byMarket = new EnumMap<>(MarketType.class);

        for (MarketType mt : List.of(MarketType.TONGIN, MarketType.MANGWON, MarketType.NAMDAEMUN)) {
            List<Shop> shops = shopRepository.findAllByMarket_MarketType(mt);
            List<CourseRequestDTO.Candidate> candidates = shops.stream()
                    .map(s -> new CourseRequestDTO.Candidate(
                            s.getShopId(),
                            s.getName(),
                            s.getMenus() == null ? List.of()
                                    : s.getMenus().stream()
                                    .map(Menu::getName)
                                    .filter(n -> n != null && !n.isBlank())
                                    .limit(3)
                                    .toList(),
                            s.getCategory()
                    ))
                    .toList();
            byMarket.put(mt, candidates);
        }


        String template = """
        SYSTEM:
        - 아래 '시장별 가게 후보' JSON에 있는 가게만 사용할 것 (새 가게/메뉴 생성 금지)
        - 정확히 3개의 코스를 생성하되, 각 코스는 하나의 시장에서만 구성할 것:
          (1) 통인시장, (2) 망원시장, (3) 남대문시장
        - 각 코스 결과 예시는 아래 구조를 따를 것 (중괄호 제외):
          title: "시장명을 포함한 한국어 제목" (예: "통인 한입 코스")
          shops: [
            shopId: 숫자, name: 문자열, signatureMenu: 문자열
          ]

        - 각 코스는 정확히 3곳의 가게로 구성할 것
        - 가능하면 각 코스에 category == "DESSERT"인 가게를 최소 1개 포함할 것 (불가능하면 생략 가능)
        - 최상위 루트 키는 반드시 'courses' 하나만 사용할 것 (다른 키 금지)
        - 전체 응답은 **오직 JSON 하나만** 출력할 것 (마크다운 코드펜스, 여러 JSON 금지)

        USER:
        [통인시장 가게 후보(JSON)]
        {tongin}

        [망원시장 가게 후보(JSON)]
        {mangwon}

        [남대문시장 가게 후보(JSON)]
        {namdaemun}
        """;

        // 3) 변수 바인딩
        Map<String, Object> vars = Map.of(
                "tongin", toJson(byMarket.getOrDefault(MarketType.TONGIN, List.of())),
                "mangwon", toJson(byMarket.getOrDefault(MarketType.MANGWON, List.of())),
                "namdaemun", toJson(byMarket.getOrDefault(MarketType.NAMDAEMUN, List.of()))
        );
        log.info("json data per market: {}", vars);

        // 4) 프롬프트 생성 및 호출
        Prompt prompt = new PromptTemplate(template).create(vars);
        log.info("AI Per-Market Random Prompt\n{}", prompt);

        var response = openAiChatModel.call(prompt);
        String content = response.getResult().getOutput().getText();
        log.info("AI per-market random raw content: {}", content);

        // 5) 파싱 및 예외 처리
        try {
            JsonNode node = objectMapper.readTree(content);

            // 'course' 단수 키 대응 → 'courses'로 보정
            if (node.has("course") && !node.has("courses")) {
                ObjectNode fixed = (ObjectNode) node;
                fixed.set("courses", node.get("course"));
                fixed.remove("course");
                content = objectMapper.writeValueAsString(fixed);
            }

            // optional: null 또는 shops 누락된 course 제거 로직 추가 가능
            return objectMapper.readValue(content, CourseResponseDTO.CourseResultDTO.class);

        } catch (Exception e) {
            throw new IllegalStateException("시장별 랜덤 코스 생성 결과 파싱 실패: " + content, e);
        }
    }


    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);

        } catch (Exception e) {
            return "[]";
        }
    }
}

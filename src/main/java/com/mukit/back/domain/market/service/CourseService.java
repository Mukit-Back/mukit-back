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

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);

        } catch (Exception e) {
            return "[]";
        }
    }
}

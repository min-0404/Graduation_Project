package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.*;
import Graduation.CardVisor.domain.benefit.Benefit;
import Graduation.CardVisor.domain.benefit.BenefitDto;
import Graduation.CardVisor.domain.serviceone.ServiceOne;
import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final FeeRepository feeRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;
    private final ServiceOneRepository serviceOneRepository;
    private final ServiceTwoRepository serviceTwoRepository;
    private final BenefitRepository benefitRepository;
    private final DtoService dtoService;

//-----단일 카드 조회-----------------------------------------------------------------------------------------------------

    // 1. id로 Card 객체 조회 후 리스트에 담아 반환
    public List<Card> getCard(Long id) {
        List<Card> list = new ArrayList<>();
        list.add(cardRepository.findCardById(id));
        return list;
    }

    // 2. id로 유효한 Fee 객체 조회 후 리스트에 담아 반환
    public List<Map> getFee(Long id){

        Fee fee = feeRepository.findFeeByCard_Id(id);

        List<Map> feeList = new ArrayList<>();

        Field[] fields = fee.getClass().getDeclaredFields();

        fields = Arrays.copyOfRange(fields, 2, 42); // id, card_code 칼럼은 제외
        for(Field field : fields) {
            field.setAccessible(true); // fee 객체에 테이블명으로 접근
            try {
                Integer value = (Integer)field.get(fee);
                if(value != -1) { // 해당 칼럼값이 -1아닐때만 추가
                    Map<String, Object> store = new HashMap<>();
                    store.put("provider", field.getName());
                    store.put("pay", value);
                    feeList.add(store);
                }
            }
            catch (IllegalAccessException e) {
                log.info("Reflection Error. {}", e);
            }
        }

        return feeList;
    }

    // 3. Category 객체 반환
    public Set<String> getCategory(Long id){

        List<Benefit> benefits = benefitRepository.findAllByCardId(id);
        Set<String> categories = new HashSet<>(); // 중복값 제거를 위해 Set 사용

        for (Benefit benefit:benefits) {
            categories.add(benefit.getCategory().getName());
        }
        return categories;
    }

    // 4. BenefitDto 객체 리스트 반환
    public List<BenefitDto> getBenefits(Long id) {

        return benefitRepository.findAllByCardId(id) // 카드 id로 해당하는 Benefit 객체 리스트 조회 후,

                // 리스트의 Benefit 객체들을 BenefitDto 로 변환 후 반환
                .stream()
                .map(it -> dtoService.benefitToDto(it))
                .collect(Collectors.toList());
    }


//-----모든 카드 조회------------------------------------------------------------------------------------------------------

    // 1. 모든 Card 객체 리스트에 담기
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }


    // 찜하기 함수 .... 일단 나중에 수정
    public Integer getFavoriteCount(Long card_code) {
        return favoriteRepository.findFavoriteByCard_id(card_code).size();
    }

}

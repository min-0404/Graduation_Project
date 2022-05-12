package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.*;
import Graduation.CardVisor.domain.benefit.Benefit;
import Graduation.CardVisor.domain.benefit.BenefitDto;
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

    // 카드 객체를 리스트에 담아 가져오기
    public List<Card> getCard(Long id) {
        List<Card> list = new ArrayList<>();
        list.add(cardRepository.findCardById(id));
        return list;
    }


    // 유효한 연회비 데이터 불러오기
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

    // 카테고리 객체 가져오기
    public Set<String> getCategory(Long id){
        List<Benefit> benefits = benefitRepository.findAllByCardId(id);
        Set<String> categories = new HashSet<>(); // 중복값 제거를 위해 Set 사용
        for (Benefit benefit:benefits) {
             categories.add(benefit.getCategory().getName());
        }
        return categories;
    }



    // 혜택 목록 가져오기
    public List<BenefitDto> getBenefits(Long id) {
        return benefitRepository.findAllByCardId(id)
                .stream()
                .map(it -> benefitToDto(it))
                .collect(Collectors.toList());
    }

    // 모든 카드 가져오기
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }


    public Integer getFavoriteCount(Long card_code) {
        return favoriteRepository.findFavoriteByCard_id(card_code).size();
    }





    //=============================================================================================
    private BenefitDto benefitToDto(Benefit benefit) {
        BenefitDto benefitDto = new BenefitDto();
        benefitDto.setCategoryName(benefit.getCategory().getName());
        benefitDto.setBrandName(benefit.getBrand().getNameKorean());
        benefitDto.setFeeType(benefit.getType());
        benefitDto.setNumberOne(benefit.getNumber1());
        benefitDto.setNumberTwo(benefit.getNumber2());
        return benefitDto;
    }
    //============================================================================================
}

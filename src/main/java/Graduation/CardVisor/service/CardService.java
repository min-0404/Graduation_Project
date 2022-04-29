package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.*;
import Graduation.CardVisor.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

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

    // 카드 객체 가져오기
    public Card getCard(Long id){
        return cardRepository.findCardById(id);
    }

    // 연회비 객체 가져오기
    public Fee getFee(Long id){
        return feeRepository.findFeeByCard_Id(id);
    }

    // 연회비 객체에서 유효값만 가져오기
    public Map<String, Integer> getFeeValue(Fee fee){
        Map<String, Integer> store = new HashMap<>(); // key: 회사, value: 연회비
        Field[] fields = fee.getClass().getDeclaredFields();
        fields = Arrays.copyOfRange(fields, 2, 42); // id, card_code 칼럼은 제외
        for(Field field : fields) {
            field.setAccessible(true); // fee 객체에 테이블명으로 접근
            try {
                Integer value = (Integer)field.get(fee);
                if(value != -1) { // 해당 칼럼값이 -1아닐때만 추가
                    store.put(field.getName(), value);
                }
            }
            catch (IllegalAccessException e) {
                log.info("Reflection Error. {}", e);
            }
        }
        return store;
    }

    // 카테고리 객체 가져오기
    public Set<String> getCategoryFromBenefit(Long id){
        List<Benefit> benefits = benefitRepository.findBenefitByCard_Id(id);

        Set<String> categories = new HashSet<>(); // 중복값 제거를 위해 Set 사용

        for (Benefit benefit:benefits) {
             categories.add(benefit.getCategory().getName());
        }
        return categories;
    }


    // 혜택객체의 주요 데이터 추출
    public Map<String, Object> getWantedBenefits(Benefit benefit){

        Map<String, Object> map = new HashMap<>();

        map.put("카테고리", benefit.getCategory().getName()); // 1. 카테고리 이름 추출
        map.put("브랜드", benefit.getBrand().getNameKorean()); // 2. 브랜드이름 추출
        String benefitType = benefit.getType();
        map.put("할인타입", benefitType); // 3. 혜택타입 추출
        if(benefitType == "FBD" || benefitType == "FID" ||benefitType == "FND" ||benefitType == "FGP" ){
            map.put("혜택숫자1", benefit.getNumber1()); // 4. 혜택숫자 추출
            map.put("혜택숫자2", benefit.getNumber2());  // 5: 혜택숫자2 (공백 가능)
        }
        else map.put("혜택숫자1", benefit.getNumber1()); // 4. 혜택숫자 추출

        return map;
    }

    // 원하는 카드의 모든 혜택 추출 후 Map에 담기
    public Map<String, Object> showWantedBenefits(Long id) {

        List<Benefit> benefits = benefitRepository.findBenefitByCard_Id(id);

        Map<String, Object> result = new HashMap<>(); // key: 칼럼명, value: 뿌려줄 값
        Map<String ,Object> finalResult = new HashMap<>(); // key: 카테고리이름, value: result 의 value 집합
        int count = 0; // count 란? : 총 혜택의 개수(모든 혜택 뒤에 숫자 하나씩 붙음)
        for(Benefit benefit: benefits) {
            result = getWantedBenefits(benefit); // 혜택에 사용할 값만 넣기
            finalResult.put(benefit.getCategory().getName() + count, result);
            count++;
        }
        return finalResult;
    }

}

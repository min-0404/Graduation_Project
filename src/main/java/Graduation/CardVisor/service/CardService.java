package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.*;
import Graduation.CardVisor.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
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

    // 최상단 파트
    public Card getCard(Long id){
        return cardRepository.findCardById(id);
    }

    public Fee getFee(Card card){
        return feeRepository.getFeeByCardIs(card);
    }
    public Map<String, Integer> getFeeValue(Fee fee){
        Map<String, Integer> store = new HashMap<>();
        if(fee.getAmex() != -1) store.put("Amex", fee.getAmex());

        return store;
    }

    public Set<Category> getCategoryFromBenefit(Long id){
        List<Benefit> benefits = benefitRepository.findBenefitByCard_Id(id);
        Set<Category> categories = new HashSet<>();
        for (Benefit benefit:benefits) {
             categories.add(benefit.getCategory());
        }
        return categories;
    }

}

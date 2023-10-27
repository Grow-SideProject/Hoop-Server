package com.hoop.api.service;

import com.hoop.api.domain.Matching;
import com.hoop.api.domain.MatchingAttend;

import com.hoop.api.domain.User;
import com.hoop.api.exception.MatchNotFound;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.MatchingAttendRepository;
import com.hoop.api.repository.MatchingRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.matching.MatchingAttendRequest;
import com.hoop.api.request.matching.MatchingCreate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final MatchingAttendRepository matchingAttendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(Long userId, MatchingCreate matchingCreate) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        Matching matching = matchingCreate.toMatching();
        MatchingAttend matchingAttend = MatchingAttend
                .builder()
                .matching(matching)
                .user(user)
                .matching(matching)
                .isHost(true)
                .IsBallFlag(matchingCreate.getIsBallFlag())
                .build();
        matching.addMatchingAttend(matchingAttend);
        matchingRepository.save(matching);
        matchingAttendRepository.save(matchingAttend);
    }

    public void attendMatching(Long userId, MatchingAttendRequest matchingAttendRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        Matching matching = matchingRepository.findById(matchingAttendRequest.getMatching().getId()).orElseThrow(() -> new MatchNotFound());
        MatchingAttend matchingAttend = MatchingAttend
                .builder()
                .user(user)
                .matching(matching)
                .isHost(matchingAttendRequest.getIsHost())
                .IsBallFlag(matchingAttendRequest.getIsBallFlag())
                .build();
        matching.addMatchingAttend(matchingAttend);
        matchingRepository.save(matching);
        matchingAttendRepository.save(matchingAttend);
    }

    @Transactional
    public void exitMatching(Long userId, Long matchingId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        Matching matching = matchingRepository.findById(matchingId).orElseThrow(() -> new MatchNotFound());
        MatchingAttend matchingAttend = matchingAttendRepository.deleteOneByUserAndMatching(user, matching)
                .orElseThrow(()-> new MatchNotFound());
        matching.popMatchingAttend(matchingAttend);
    }
}

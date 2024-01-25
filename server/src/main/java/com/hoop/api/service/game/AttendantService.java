package com.hoop.api.service.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.*;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.game.AttendantRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.game.AttendantSearch;
import com.hoop.api.response.AttendantResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendantService {
    private final AttendantRepository attendantRepository;

    private final UserRepository userRepository;


    public Page<AttendantResponse> getList(Long userId, AttendantSearch attendantSearch) {
        List<AttendantResponse> gameAttendantResponseList = attendantRepository.getList(attendantSearch)
                .stream()
                .map(AttendantResponse::new)
                .toList();
        return new PageImpl<>(gameAttendantResponseList, PageRequest.of(attendantSearch.getPage(), attendantSearch.getSize()), attendantRepository.count());
    }

    public List<AttendantResponse> getListByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        return attendantRepository.findByUser(user)
                .stream()
                .map(AttendantResponse::new)
                .toList();
    }

    public List<AttendantResponse> getListByHost(Long userId) {
        return attendantRepository.getListByHost(userId)
                .stream()
                .map(AttendantResponse::new)
                .toList();
    }

    @Transactional
    public AttendantResponse approveAttendant(Long hostId, Long attendantId) {
       Attendant attendant = attendantRepository.findById(attendantId).orElseThrow(GameNotFound::new);
        attendant.setAttend(AttendantStatus.APPROVE);
        attendantRepository.save(attendant);
        return new AttendantResponse(attendant);
    }
    @Transactional
    public AttendantResponse rejectAttendant(Long hostId, Long attendantId) {
        Attendant attendant = attendantRepository.findById(attendantId).orElseThrow(GameNotFound::new);
        attendant.setAttend(AttendantStatus.REJECT);
        attendantRepository.save(attendant);
        return new AttendantResponse(attendant);
    }

    @Transactional
    public void removeGameAttend(Long hostId, Long attendantId) {
        Attendant attendant = attendantRepository.findById(attendantId).orElseThrow(GameNotFound::new);
        attendantRepository.delete(attendant);
    }


    //    public Page<AttendantResponse> getListByHost(Long userId, AttendantSearch attendantSearch) {
//        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
//        List<Game> isHostGameList = attendantRepository.findByUserAndIsHost(user, true)
//                .stream()
//                .map(Attendant::getGame)
//                .toList();
//        attendantSearch.setGameList(isHostGameList);
//        List<AttendantResponse> gameAttendantResponseList = attendantRepository.getList(attendantSearch)
//                .stream()
//                .map(AttendantResponse::new)
//                .toList();
//        return new PageImpl<>(gameAttendantResponseList, PageRequest.of(attendantSearch.getPage(), attendantSearch.getSize()), attendantRepository.count());
//    }
}

package com.hoop.api.service.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.*;
import com.hoop.api.exception.AttendantStatusException;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.exception.PermissionException;
import com.hoop.api.repository.attendant.AttendantRepository;
import com.hoop.api.response.game.AttendantResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendantService {
    private final AttendantRepository attendantRepository;


    public List<AttendantResponse> getListByHost(Long userId) {
        return attendantRepository.getListByHost(userId)
                .stream()
                .map(AttendantResponse::new)
                .toList();
    }

    @Transactional
    public AttendantResponse approveAttendant(Long userId, Long attendantId) {
        Attendant attendant = attendantRepository.findById(attendantId).orElseThrow(GameNotFound::new);

        // Host 처리 확인
        attendantRepository.findByUserIdAndGameIdAndIsHost(userId,attendant.getGame().getId(), true).orElseThrow(PermissionException::new);

        if (attendant.getStatus() != AttendantStatus.DEFAULT) throw new AttendantStatusException();
        attendant.setAttend(AttendantStatus.APPROVE);
        attendantRepository.save(attendant);
        return new AttendantResponse(attendant);
    }

    @Transactional
    public AttendantResponse rejectAttendant(Long userId, Long attendantId) {
        Attendant attendant = attendantRepository.findById(attendantId).orElseThrow(GameNotFound::new);
        // Host 처리 확인
        attendantRepository.findByUserIdAndGameIdAndIsHost(userId,attendant.getGame().getId(), true).orElseThrow(PermissionException::new);
        if (attendant.getStatus() != AttendantStatus.DEFAULT) throw new AttendantStatusException();
        attendant.setAttend(AttendantStatus.REJECT);
        attendantRepository.save(attendant);
        return new AttendantResponse(attendant);
    }

    @Transactional
    public void removeGameAttend(Long userId, Long attendantId) {
        Attendant attendant = attendantRepository.findById(attendantId).orElseThrow(GameNotFound::new);
        attendantRepository.delete(attendant);
    }

}

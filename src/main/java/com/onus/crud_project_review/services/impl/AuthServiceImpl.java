package com.onus.crud_project_review.services.impl;

import com.onus.crud_project_review.dtos.EmailDTO;
import com.onus.crud_project_review.dtos.EmailVerifyRequestDTO;
import com.onus.crud_project_review.repositories.UserRepository;
import com.onus.crud_project_review.services.AuthService;
import com.onus.crud_project_review.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String sendVerificationCode(EmailDTO emailDTO) {
        String code = String.format("%06d", new Random().nextInt(1000000));
        emailService.sendVerificationCodeEmail(emailDTO.getEmail(), code);

        //레디스에 저장, 저장시간 5분
        redisTemplate.opsForValue().set("email:verify:" + emailDTO.getEmail(), code, 5, TimeUnit.MINUTES);
        String stored = redisTemplate.opsForValue().get("email:verify:" + emailDTO.getEmail());
        log.info("Redis에서 방금 저장된 값: {}", stored);
        return code;
    }

    @Override
    public boolean verifyEmailCode(EmailVerifyRequestDTO emailVerifyRequestDTO) {
        String redisKey = "email:verify:" + emailVerifyRequestDTO.getEmail();
        String savedCode = redisTemplate.opsForValue().get(redisKey);

        if (savedCode == null) {
            throw new IllegalArgumentException("인증 코드가 존재하지 않거나 만료");
        } else {
            if (!savedCode.equals(emailVerifyRequestDTO.getCode())) {
                throw new IllegalArgumentException("인증코드가 일치하지 않음");
            } else {
                // 일회용 코드라서 삭제함
                redisTemplate.delete(redisKey);
            }
        }
        return true;
    }
}

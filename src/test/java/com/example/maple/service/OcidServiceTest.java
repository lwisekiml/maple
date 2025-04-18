package com.example.maple.service;

import com.example.maple.dto.OcidResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OcidServiceTest {

    @Spy
    private OcidService ocidService;

    @Test
    void getOcidList_shouldReturnValidResponse() {
        // given
        List<String> characterNames = List.of("나이트로드", "나이트워커", "다크나이트");

        OcidResponse response1 = new OcidResponse();
        OcidResponse response2 = new OcidResponse();
        OcidResponse response3 = new OcidResponse();

        response1.setOcid("ocid1");
        response2.setOcid("ocid2");
        response3.setOcid("ocid3");

        /*
        @Spy 객체에서 webClient == null  -->  doReturn() 사용해서 실제 메서드 실행 막기
        when(...).thenReturn(...)       -->  getOcid()가 실행되어 NPE 발생 위험 있음
        테스트 전용 기본 생성자 사용        -->  doReturn().when(...) 조합으로 mocking 해야 안전
         */
        /*
        when().thenReturn()   -->   실제 메서드를 먼저 호출하려고 함 (Spy에서는 위험)
        doReturn().when()     -->   실제 메서드 실행 없이 결과만 지정해서 안전
        Spy 객체에는 반드시 doReturn() / doThrow() 사용
         */
        doReturn(response1).when(ocidService).getOcid("나이트로드");
        doReturn(response2).when(ocidService).getOcid("나이트워커");
        doReturn(response3).when(ocidService).getOcid("다크나이트");

        // when
        List<OcidResponse> result = ocidService.getOcidList(characterNames);

        // then
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(r -> "ocid1".equals(r.getOcid())));
        assertTrue(result.stream().anyMatch(r -> "ocid2".equals(r.getOcid())));
        assertTrue(result.stream().anyMatch(r -> "ocid3".equals(r.getOcid())));
    }

    @Test
    void getOcidListWithRateLimit_shouldSkipFailedRequests() {
        // Given
        List<String> characterNames = List.of("나이트로드", "나이트워커", "에러남");

        OcidResponse response1 = new OcidResponse();
        OcidResponse response2 = new OcidResponse();

        response1.setOcid("ocid1");
        response2.setOcid("ocid2");

        doReturn(response1).when(ocidService).getOcid("나이트로드");
        doReturn(response2).when(ocidService).getOcid("나이트워커");
        doThrow(new RuntimeException("API 에러")).when(ocidService).getOcid("에러남");

        // When
        List<OcidResponse> result = ocidService.getOcidList(characterNames);

        // Then
        assertEquals(2, result.size());
        assertFalse(result.stream().anyMatch(r -> r.getOcid().equals("에러남")));
    }
}